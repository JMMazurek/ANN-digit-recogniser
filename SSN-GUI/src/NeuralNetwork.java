import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
/**
 * Sieæ neuronowa bêd¹ca modelem i komunikuj¹ca
 * siê bezpoœrednio z kontrolerem
 * @author Jakub Mazurek
 *
 */
public class NeuralNetwork
{
	/** kontroler */
	private Controller controller;
	/** iloœæ warstw neuronów */
	private int layers;
	/** iloœæ neuronów wejœciowych */
	private int in;
	/** iloœæ neuronów wyjœciowych */
	private int out;
	public ArrayList<ArrayList<Neuron>> neurons;
	/**
	 * Inicjalicja sieci: stworzenie warstw neuronów
	 * @param neuronsNumber liczba neuronów w poszczególnych warstwach
	 * @param bias czy dodaæ neurony bêd¹ce biasem
	 */
	public void initialize(ArrayList<Integer> neuronsNumber, boolean bias)
	{
		neurons = new ArrayList<ArrayList<Neuron>>();
		layers = neuronsNumber.size();
		in = neuronsNumber.get(0);
		out = neuronsNumber.get(layers-1);
		for(int i=0; i<layers; ++i)
		{
			ArrayList<Neuron> layer = new ArrayList<Neuron>();
			for(int j=0; j<neuronsNumber.get(i); ++j)
			{
				Neuron neuron;
				if(i == 0) // jeœli jest to pierwsza wartwa dodajemy neurony wejœciowe
					neuron = new InputNeuron();
				else
					neuron = new Neuron();
				layer.add(neuron); // dodanie nauronu do danej warstwy
			}
			if(bias && i != neuronsNumber.size()-1) // dodajemy dodatkowy neuron jeœli bias == true i jeœli nie jest to warstwa wyjœciowa
			{
				Neuron biasNeuron = new BiasNeuron();
				layer.add(biasNeuron);
			}
			neurons.add(layer); // dodajemy warstwê do sieci
		}
	}
	/**
	 * Stworzenie po³¹czeñ miêdzy neuronami oraz ustawienie
	 * wag po³¹czeñ na podstawie wartoœci z pliku
	 * @param fileName nazwa pliku z konfiguracj¹
	 */
	void setConnections(String fileName)
	{
		FileReader fileReader = null;
		try // otwieramy plik
		{
			fileReader = new FileReader(fileName);
		}catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
        BufferedReader reader = new BufferedReader(fileReader);
		for(int i=0; i<layers-1; ++i)
		{
			for(int j=0; j<neurons.get(i).size(); ++j)
			{
				for(int k=0; k<neurons.get(i+1).size(); ++k)
				{
					double weight = 0;
					try
					{
						weight = Double.parseDouble(reader.readLine()); // odczytujemy wagê z pliku
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					Connection con = new Connection(neurons.get(i).get(j), neurons.get(i+1).get(k), weight); // tworzymy po³¹czenie miêdzy neuronami
					neurons.get(i).get(j).addConnectionOut(con); // dodajemy po³¹czenie wyjœciowe
					if(!(neurons.get(i+1).get(k) instanceof BiasNeuron)) // dodajemy po³¹czenie wejœciowe jeœli neuron nie jest neuronem dodatkowym
							neurons.get(i+1).get(k).addConnectionIn(con);
				}
			}
		}
	}
	/**
	 * Zwrócenie wartoœci wyjœcia sieci dla podanego wejœcia
	 * @param input wartoœci wejœciowe dla sieci
	 * @return wartoœci wyjœciowe sieci
	 */
	ArrayList<Double> getOutput(ArrayList<Double> input)
	{
		for(int i=0; i<layers; ++i) // zresetowanie otrzymanych sygna³ów
		{
			for(int j=0; j<neurons.get(i).size(); ++j)
			{
				neurons.get(i).get(j).resetSignal();
			}
		}
		ArrayList<Double> output = new ArrayList<Double>();
		
		for(int i=0; i<in; ++i) // ustawienie wartoœci sygna³u dla neuronów wejœciowych
		{
			neurons.get(0).get(i).receiveSignal(input.get(i));
		}
		
		for(int i=0; i<layers-1; ++i) // wys³anie sygna³u
		{
			for(Neuron neuron : neurons.get(i))
			{
				neuron.sendSignal();
			}
		}
		
		for(int i=0; i<out; ++i) // wczytanie wyjœcia neuronów ostatniej warstwy
		{
			output.add(neurons.get(layers-1).get(i).activateFunction());
		}
		
 		return output;
	}
	/**
	 * Ustawienie kontrolera
	 * @param controller kontroler
	 */
	void setController(Controller controller)
	{
		this.controller = controller;
	}
}
