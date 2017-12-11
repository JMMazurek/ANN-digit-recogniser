import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
/**
 * Sie� neuronowa b�d�ca modelem i komunikuj�ca
 * si� bezpo�rednio z kontrolerem
 * @author Jakub Mazurek
 *
 */
public class NeuralNetwork
{
	/** kontroler */
	private Controller controller;
	/** ilo�� warstw neuron�w */
	private int layers;
	/** ilo�� neuron�w wej�ciowych */
	private int in;
	/** ilo�� neuron�w wyj�ciowych */
	private int out;
	public ArrayList<ArrayList<Neuron>> neurons;
	/**
	 * Inicjalicja sieci: stworzenie warstw neuron�w
	 * @param neuronsNumber liczba neuron�w w poszczeg�lnych warstwach
	 * @param bias czy doda� neurony b�d�ce biasem
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
				if(i == 0) // je�li jest to pierwsza wartwa dodajemy neurony wej�ciowe
					neuron = new InputNeuron();
				else
					neuron = new Neuron();
				layer.add(neuron); // dodanie nauronu do danej warstwy
			}
			if(bias && i != neuronsNumber.size()-1) // dodajemy dodatkowy neuron je�li bias == true i je�li nie jest to warstwa wyj�ciowa
			{
				Neuron biasNeuron = new BiasNeuron();
				layer.add(biasNeuron);
			}
			neurons.add(layer); // dodajemy warstw� do sieci
		}
	}
	/**
	 * Stworzenie po��cze� mi�dzy neuronami oraz ustawienie
	 * wag po��cze� na podstawie warto�ci z pliku
	 * @param fileName nazwa pliku z konfiguracj�
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
						weight = Double.parseDouble(reader.readLine()); // odczytujemy wag� z pliku
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					Connection con = new Connection(neurons.get(i).get(j), neurons.get(i+1).get(k), weight); // tworzymy po��czenie mi�dzy neuronami
					neurons.get(i).get(j).addConnectionOut(con); // dodajemy po��czenie wyj�ciowe
					if(!(neurons.get(i+1).get(k) instanceof BiasNeuron)) // dodajemy po��czenie wej�ciowe je�li neuron nie jest neuronem dodatkowym
							neurons.get(i+1).get(k).addConnectionIn(con);
				}
			}
		}
	}
	/**
	 * Zwr�cenie warto�ci wyj�cia sieci dla podanego wej�cia
	 * @param input warto�ci wej�ciowe dla sieci
	 * @return warto�ci wyj�ciowe sieci
	 */
	ArrayList<Double> getOutput(ArrayList<Double> input)
	{
		for(int i=0; i<layers; ++i) // zresetowanie otrzymanych sygna��w
		{
			for(int j=0; j<neurons.get(i).size(); ++j)
			{
				neurons.get(i).get(j).resetSignal();
			}
		}
		ArrayList<Double> output = new ArrayList<Double>();
		
		for(int i=0; i<in; ++i) // ustawienie warto�ci sygna�u dla neuron�w wej�ciowych
		{
			neurons.get(0).get(i).receiveSignal(input.get(i));
		}
		
		for(int i=0; i<layers-1; ++i) // wys�anie sygna�u
		{
			for(Neuron neuron : neurons.get(i))
			{
				neuron.sendSignal();
			}
		}
		
		for(int i=0; i<out; ++i) // wczytanie wyj�cia neuron�w ostatniej warstwy
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
