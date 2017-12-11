import java.util.ArrayList;
/**
 * Model sztucznego neuronu
 * @author Jakub Mazurek
 *
 */
public class Neuron
{
	/** suma wartoœci odebranych sygna³ów */
	private double signal = 0;
	/** wspó³czynnik pochylenia krzywej sigmoidalnej */
	private double beta = 1.0d;
	/** neurony wysy³aj¹ce do nas sygna³ */
	private ArrayList<Connection> in = new ArrayList<Connection>();
	/** neurony odbiraj¹ce nasz sygna³ */
	private ArrayList<Connection> out = new ArrayList<Connection>();
	/**
	 * Funkcja simgoidalna unipolarna
	 * @param s otrzymany sygna³
	 * @return wartoœæ funkcji simgoidalnej dla otrzymanego sygna³u
	 */
	double sigmoidFunction(double s)
	{
		return 1/(1+Math.exp(-beta*s));
	}
	/**
	 * Pochodna funcji sigmoidalnej
	 * @param s otrzymany sygna³
	 * @return wartoœæ pochodnej funkcji sigmoidalnej dla otrzymanego sygna³u
	 */
	double dSigmoidFunction(double s)
	{
		return beta*(1-sigmoidFunction(s/beta))*sigmoidFunction(s/beta);
	}
	/**
	 * Fukcja aktywacji
	 * @return wartoœæ funkcji aktywacji dla otrzymanego sygna³u
	 */
	double dActivateFunction()
	{
		return dSigmoidFunction(signal);
	}
	/**
	 * Pochodna funkcji aktywacji
	 * @return wartoœæ pochodnej funkcji aktywacji dla otrzymanego sygna³u
	 */
	double activateFunction()
	{
		return sigmoidFunction(signal);
	}
	/**
	 * Odebranie przychodz¹cego sygna³u i jego kumulacja
	 * @param s odebrany sygna³
	 */
	void receiveSignal(double s)
	{
		signal += s;
	}
	/**
	 * Zwrócenie sumy wartoœci odebranych sygna³ów
	 * @return suma wartoœci odebranych sygna³ów
	 */
	double getSignal()
	{
		return signal;
	}
	/**
	 * Wys³anie sygna³u do wszystkich neuronów odbieraj¹cych nasze sygna³y
	 */
	void sendSignal()
	{
		for(Connection con: out)
		{
			con.out.receiveSignal(activateFunction()*con.getWeight());
		}
	}
	/**
	 * Dodanie po³¹czenia z neuronem wysy³aj¹cym nam sygna³
	 * @param con po³¹czenie z neuronem wysy³aj¹cym nam sygna³
	 */
	void addConnectionIn(Connection con)
	{
		in.add(con);
	}
	/**
	 * Dodanie po³¹czenia z neuronem obieraj¹cym nasz sygna³
	 * @param con po³¹czenie z neuronem obieraj¹cym nasz sygna³
	 */
	void addConnectionOut(Connection con)
	{
		out.add(con);
	}
	/**
	 * Resetowanie wartoœci otrzymanych sygna³ów
	 */
	void resetSignal()
	{
		signal = 0;
	}
}
/**
 * Po³¹czenie miêdzy dwoma neuronami
 * @author Qba050
 *
 */
class Connection
{
	Neuron in;
	Neuron out;
	double weight;
	/**
	 * Zwrócenie wagi po³¹czenia
	 * @return waga po³¹czenia
	 */
	double getWeight()
	{
		return weight;
	}
	/**
	 * Ustawienie wagi po³¹czenia
	 * @param weight waga po³¹czenia
	 */
	void setWeight(double weight)
	{
		this.weight = weight;
	}
	/**
	 * Konstruktor, utoworzenie nowego po³¹czenia
	 * o ustalonej wadze miêdzy dwoma neuronami
	 * @param in neuron odbieraj¹cy sygna³
	 * @param out neuron wysy³aj¹cy sygna³
	 * @param weight waga po³¹czenia
	 */
	public Connection(Neuron in, Neuron out, double weight)
	{
		this.in = in;
		this.out = out;
		this.weight = weight;
	}
}

/**
 * Neuron dodatkowy bêd¹cy biasem
 * @author Qba050
 *
 */
class BiasNeuron extends Neuron
{
	double signal = 1;
	
	@Override
	/**
	 * Fukcja aktywacji o sta³ej wartoœci
	 * @return sta³a wartoœæ funkcji aktywacji
	 */
	double activateFunction()
	{
		return 1;
	}
	@Override
	/**
	 * Pochodna funkcji aktywacji o sta³ej wartoœci
	 * @return sta³a wartoœæ pochodnej funkcji aktywacji
	 */
	double dActivateFunction()
	{
		return 1;
	}
	@Override
	/**
	 * Ustawienie sygna³a na sta³¹ wartoœæ
	 */
	void resetSignal()
	{
		signal = 1;
	}
}
/**
 * Neuron bêd¹cy wejœciem ca³ej sieci
 * @author Qba050
 *
 */
class InputNeuron extends Neuron
{
	@Override
	/**
	 * Funkcja aktywacji dla ustawionego z zewn¹trz sygna³u
	 * @return ustawiony z zewn¹trz sygna³
	 */
	double activateFunction()
	{
		return getSignal();
	}
}
