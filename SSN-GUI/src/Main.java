/**
 * G³ówna klasa programu
 * @author Jakub Mazurek
 *
 */
public class Main
{
	public static void main(String[] args)
	{
		/** widok */
		GUI view = new GUI();
		/** model */
		NeuralNetwork model = new NeuralNetwork();
		/** kontroler */
		Controller controller = new Controller(view, model);
	}
}
