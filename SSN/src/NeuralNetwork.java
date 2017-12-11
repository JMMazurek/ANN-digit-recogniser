import java.io.*;
import java.util.*;

public class NeuralNetwork
{
	int layers = 0;
	int in, out;
	public ArrayList<ArrayList<Neuron>> neurons = new ArrayList<ArrayList<Neuron>>();
	TrainingSet trainingSet;
	
	public NeuralNetwork(ArrayList<Integer> neuronsNumber, boolean bias)
	{
		layers = neuronsNumber.size();
		in = neuronsNumber.get(0);
		out = neuronsNumber.get(layers-1);
		trainingSet = new TrainingSet(in, out);
		for(int i=0; i<layers; ++i)
		{
			ArrayList<Neuron> layer = new ArrayList<Neuron>();
			for(int j=0; j<neuronsNumber.get(i); ++j)
			{
				Neuron neuron;
				if(i == 0)
					neuron = new InputNeuron();
				else
					neuron = new Neuron();
				layer.add(neuron);
			}
			if(bias && i != neuronsNumber.size()-1)
			{
				Neuron biasNeuron = new BiasNeuron();
				layer.add(biasNeuron);
			}
			neurons.add(layer);
		}
		connectAllLayers();
	}
	
	void connectAllLayers()
	{
		for(int i=0; i<layers-1; ++i)
		{
			for(int j=0; j<neurons.get(i).size(); ++j)
			{
				for(int k=0; k<neurons.get(i+1).size(); ++k)
				{
					Connection con = new Connection(neurons.get(i).get(j), neurons.get(i+1).get(k), 0.8d-(Math.random()*1.6d));
					neurons.get(i).get(j).addConnectionOut(con);
					if(!(neurons.get(i+1).get(k) instanceof BiasNeuron))
							neurons.get(i+1).get(k).addConnectionIn(con);
				}
			}
		}
	}
	
	ArrayList<Double> getOutput(ArrayList<Double> input)
	{
		for(int i=0; i<layers; ++i)
		{
			for(int j=0; j<neurons.get(i).size(); ++j)
			{
				neurons.get(i).get(j).resetSignal();
			}
		}
		ArrayList<Double> output = new ArrayList<Double>();
		
		for(int i=0; i<in; ++i)
		{
			neurons.get(0).get(i).receiveSignal(input.get(i));
		}
		
		for(int i=0; i<layers-1; ++i)
		{
			for(Neuron neuron : neurons.get(i))
			{
				neuron.sendSignal();
			}
		}
		
		for(int i=0; i<out; ++i)
		{
			output.add(neurons.get(layers-1).get(i).activateFunction());
		}
		
 		return output;
	}
	void trainNetwork()
	{
		//trainingSet.mix();
		for(Training training : trainingSet.getTrainings())
		{
			for(int i=0; i<layers; ++i)
			{
				for(int j=0; j<neurons.get(i).size(); ++j)
				{
					neurons.get(i).get(j).resetDelta();
				}
			}
			ArrayList<Double> output = getOutput(training.getInput());
			for(int i=0; i<out; ++i)
			{
				neurons.get(layers-1).get(i).setDelta(training.getOutput().get(i)-output.get(i));
			}
			for(int i=layers-1; i>0; --i)
			{
				for(int j=0; j<neurons.get(i).size(); ++j)
				{
					neurons.get(i).get(j).sendDelta();
				}
			}
			for(int i=1; i<layers; ++i)
			{
				for(int j=0; j<neurons.get(i).size(); ++j)
				{
					neurons.get(i).get(j).fixWeights();
				}
			}
		}
	}
	void writeWeigths(String fileName)
	{
		try
		{
			FileWriter f = new FileWriter(fileName);
			for(int i=0; i<layers; ++i)
			{
				for(int j=0; j<neurons.get(i).size(); ++j)
				{
					neurons.get(i).get(j).writeWeights(f);
				}
			}
			
			f.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class TrainingSet
{
	int in, out;
	ArrayList<Training> trainings = new ArrayList<Training>();
	TrainingSet(int in, int out)
	{
		this.in = in;
		this.out = out;
	}
	void addTraining(Training training)
	{
		if(training.in.size() == in && training.out.size() == out)
			trainings.add(training);
	}
	ArrayList<Training> getTrainings()
	{
		return trainings;
	}
	void readFromFile(String fileName)
	{
		try
		{
			String line;
			FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null)
            {
            	String[] halves = line.split(" -> ");
            	String[] input = halves[0].split(" ");
            	String[] output = halves[1].split(" ");
            	ArrayList<Double> tIn = new ArrayList<Double>();
            	for(int i=0; i<input.length; ++i)
            	{
            		tIn.add(Double.valueOf(input[i]));
            	}
            	ArrayList<Double> tOut = new ArrayList<Double>();
            	for(int i=0; i<output.length; ++i)
            	{
            		tOut.add(Double.valueOf(output[i]));
            	}
            	Training training = new Training(tIn, tOut);
            	addTraining(training);
            }
            bufferedReader.close();
            fileReader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch(Exception e)
		{
			
		}
	}
	void mix()
	{
		Collections.shuffle(trainings);
	}
}

class Training
{
	ArrayList<Double> in = new ArrayList<Double>();
	ArrayList<Double> out = new ArrayList<Double>();
	Training(ArrayList<Double> in, ArrayList<Double> out)
	{
		this.in = in;
		this.out = out;
	}
	ArrayList<Double> getInput()
	{
		return in;
	}
	ArrayList<Double> getOutput()
	{
		return out;
	}	
}
