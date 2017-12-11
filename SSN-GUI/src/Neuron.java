import java.util.ArrayList;
/**
 * Model sztucznego neuronu
 * @author Jakub Mazurek
 *
 */
public class Neuron
{
	/** suma warto�ci odebranych sygna��w */
	private double signal = 0;
	/** wsp�czynnik pochylenia krzywej sigmoidalnej */
	private double beta = 1.0d;
	/** neurony wysy�aj�ce do nas sygna� */
	private ArrayList<Connection> in = new ArrayList<Connection>();
	/** neurony odbiraj�ce nasz sygna� */
	private ArrayList<Connection> out = new ArrayList<Connection>();
	/**
	 * Funkcja simgoidalna unipolarna
	 * @param s otrzymany sygna�
	 * @return warto�� funkcji simgoidalnej dla otrzymanego sygna�u
	 */
	double sigmoidFunction(double s)
	{
		return 1/(1+Math.exp(-beta*s));
	}
	/**
	 * Pochodna funcji sigmoidalnej
	 * @param s otrzymany sygna�
	 * @return warto�� pochodnej funkcji sigmoidalnej dla otrzymanego sygna�u
	 */
	double dSigmoidFunction(double s)
	{
		return beta*(1-sigmoidFunction(s/beta))*sigmoidFunction(s/beta);
	}
	/**
	 * Fukcja aktywacji
	 * @return warto�� funkcji aktywacji dla otrzymanego sygna�u
	 */
	double dActivateFunction()
	{
		return dSigmoidFunction(signal);
	}
	/**
	 * Pochodna funkcji aktywacji
	 * @return warto�� pochodnej funkcji aktywacji dla otrzymanego sygna�u
	 */
	double activateFunction()
	{
		return sigmoidFunction(signal);
	}
	/**
	 * Odebranie przychodz�cego sygna�u i jego kumulacja
	 * @param s odebrany sygna�
	 */
	void receiveSignal(double s)
	{
		signal += s;
	}
	/**
	 * Zwr�cenie sumy warto�ci odebranych sygna��w
	 * @return suma warto�ci odebranych sygna��w
	 */
	double getSignal()
	{
		return signal;
	}
	/**
	 * Wys�anie sygna�u do wszystkich neuron�w odbieraj�cych nasze sygna�y
	 */
	void sendSignal()
	{
		for(Connection con: out)
		{
			con.out.receiveSignal(activateFunction()*con.getWeight());
		}
	}
	/**
	 * Dodanie po��czenia z neuronem wysy�aj�cym nam sygna�
	 * @param con po��czenie z neuronem wysy�aj�cym nam sygna�
	 */
	void addConnectionIn(Connection con)
	{
		in.add(con);
	}
	/**
	 * Dodanie po��czenia z neuronem obieraj�cym nasz sygna�
	 * @param con po��czenie z neuronem obieraj�cym nasz sygna�
	 */
	void addConnectionOut(Connection con)
	{
		out.add(con);
	}
	/**
	 * Resetowanie warto�ci otrzymanych sygna��w
	 */
	void resetSignal()
	{
		signal = 0;
	}
}
/**
 * Po��czenie mi�dzy dwoma neuronami
 * @author Qba050
 *
 */
class Connection
{
	Neuron in;
	Neuron out;
	double weight;
	/**
	 * Zwr�cenie wagi po��czenia
	 * @return waga po��czenia
	 */
	double getWeight()
	{
		return weight;
	}
	/**
	 * Ustawienie wagi po��czenia
	 * @param weight waga po��czenia
	 */
	void setWeight(double weight)
	{
		this.weight = weight;
	}
	/**
	 * Konstruktor, utoworzenie nowego po��czenia
	 * o ustalonej wadze mi�dzy dwoma neuronami
	 * @param in neuron odbieraj�cy sygna�
	 * @param out neuron wysy�aj�cy sygna�
	 * @param weight waga po��czenia
	 */
	public Connection(Neuron in, Neuron out, double weight)
	{
		this.in = in;
		this.out = out;
		this.weight = weight;
	}
}

/**
 * Neuron dodatkowy b�d�cy biasem
 * @author Qba050
 *
 */
class BiasNeuron extends Neuron
{
	double signal = 1;
	
	@Override
	/**
	 * Fukcja aktywacji o sta�ej warto�ci
	 * @return sta�a warto�� funkcji aktywacji
	 */
	double activateFunction()
	{
		return 1;
	}
	@Override
	/**
	 * Pochodna funkcji aktywacji o sta�ej warto�ci
	 * @return sta�a warto�� pochodnej funkcji aktywacji
	 */
	double dActivateFunction()
	{
		return 1;
	}
	@Override
	/**
	 * Ustawienie sygna�a na sta�� warto��
	 */
	void resetSignal()
	{
		signal = 1;
	}
}
/**
 * Neuron b�d�cy wej�ciem ca�ej sieci
 * @author Qba050
 *
 */
class InputNeuron extends Neuron
{
	@Override
	/**
	 * Funkcja aktywacji dla ustawionego z zewn�trz sygna�u
	 * @return ustawiony z zewn�trz sygna�
	 */
	double activateFunction()
	{
		return getSignal();
	}
}
