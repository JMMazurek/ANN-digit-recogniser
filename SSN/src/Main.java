import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		ArrayList<Integer> layers = new ArrayList<Integer>();
		layers.add(70);
		layers.add(20);
		layers.add(20);
		layers.add(10);
		
		NeuralNetwork net = new NeuralNetwork(layers, true);
		
		net.trainingSet.readFromFile("input.txt");
		
		long start = System.currentTimeMillis();
		long end;
		for(int i=0; i<20000; ++i)
		{
			net.trainNetwork();
			if(i%200 == 0)
			{
				end = System.currentTimeMillis();
				System.out.println(i/200+"% left "+((double)(end-start)/(60000.0f))*(100-(i/200))+" min");
				start = end;
			}
		}
		
		net.writeWeigths("Wagi.ncf");
	}
}
