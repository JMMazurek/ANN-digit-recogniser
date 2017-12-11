import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Neuron
{
	private double signal = 0;
	private double beta = 1.0d;
	private double delta = 0;
	private double learningSpeed = 0.2d;
	private ArrayList<Connection> in = new ArrayList<Connection>();
	private ArrayList<Connection> out = new ArrayList<Connection>();
	
	double getLearningSpeed()
	{
		return learningSpeed;
	}
	double sigmoidFunction(double s)
	{
		//return Math.tanh(s*beta);
		return 1/(1+Math.exp(-beta*s));
	}
	double dSigmoidFunction(double s)
	{
		//return beta*(1-Math.pow(Math.tanh(s/beta), 2.0d));
		return beta*(1-sigmoidFunction(s/beta))*sigmoidFunction(s/beta);
	}
	double dActivateFunction()
	{
		return dSigmoidFunction(signal);
	}
	double activateFunction()
	{
		return sigmoidFunction(signal);
	}
	void receiveSignal(double s)
	{
		signal += s;
	}
	double getSignal()
	{
		return signal;
	}
	void receiveDelta(double delta)
	{
		this.delta += delta;
	}
	double getDelta()
	{
		return delta;
	}
	
	void sendSignal()
	{
		for(Connection con: out)
		{
			con.out.receiveSignal(activateFunction()*con.getWeight());
		}
	}
	void sendDelta()
	{
		for(Connection con: in)
		{
			con.in.receiveDelta(getDelta()*con.getWeight());
		}
	}
	void fixWeights()
	{
		for(int i=0; i<in.size(); ++i)
		{
			in.get(i).setWeight(in.get(i).getWeight()+getLearningSpeed()*getDelta()*in.get(i).in.activateFunction()*dActivateFunction());
		}
	}
	void writeWeights(FileWriter f)
	{
		for(int i=0; i<out.size(); ++i)
		{
			try
			{
				f.write(String.valueOf(out.get(i).getWeight()));
				f.write(System.getProperty( "line.separator" ));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void addConnectionIn(Connection con)
	{
		in.add(con);
	}
	void addConnectionOut(Connection con)
	{
		out.add(con);
	}
	void resetSignal()
	{
		signal = 0;
	}
	void resetDelta()
	{
		delta = 0;
	}
	void setDelta(double delta)
	{
		this.delta = delta;
	}
}

class Connection
{
	Neuron in;
	Neuron out;
	double weight;
	double getWeight()
	{
		return weight;
	}
	void setWeight(double weight)
	{
		this.weight = weight;
	}
	public Connection(Neuron in, Neuron out, double weight)
	{
		this.in = in;
		this.out = out;
		this.weight = weight;
	}
}


class BiasNeuron extends Neuron
{
	double signal = 1;
	@Override
	double activateFunction()
	{
		return 1;
	}
	@Override
	double dActivateFunction()
	{
		return 1;
	}
	@Override
	void resetSignal()
	{
		signal = 1;
	}
}

class InputNeuron extends Neuron
{
	@Override
	double activateFunction()
	{
		return getSignal();
	}
}
