import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * Pole wyświetlające wyświetlające podobieństwo do danej cyfry
 * @author Jakub Mazurek
 *
 */
public class Label extends JLabel
{
	/**
	 * Konstruktor inicjalizujący
	 */
	Label()
	{
		super(); // konstruktor klasy JLabel
		setBackground(Color.WHITE);
		setOpaque(true); // brak przezroczystości
		setPreferredSize(new Dimension(100, 300));
		setAlignmentX(LEFT_ALIGNMENT); // wyrównanie tekstu do lewej
		clear(); // wyczyszczenie
	}
	/**
	 * Wyswietlenie podobieństw do danych cyfr narysowanego znaku
	 * (prawdopodobieństwo, że narysowany znak to dana cyfra)
	 * @param list wyjście sieci neuronowej
	 */
	void setLabelText(ArrayList<Double> list)
	{
		StringBuilder builder = new StringBuilder();
		double sum = 0;
		for(int i=0; i<list.size(); ++i) // zsumowanie wartości wyjścia
		{
			sum+=list.get(i);
		}
		builder.append("<html>");
		for(int i=0; i<list.size(); ++i) // wypisanie pradwopodobieństw: (wartość dla danej cyfry / suma wyjść) * 100%
		{
			builder.append(i + " " + (sum==0.0d ? "0" : Math.round(list.get(i)/sum*100)) + "%<br>");
		}
		builder.append("</html>");
		setText(builder.toString());
	}
	/**
	 * Ustawienie prawdopodobieństw na 0%
	 */
	void clear()
	{
        ArrayList<Double> list = new ArrayList<Double>();
        for(int i=0; i<10; ++i) // wypełnienie tablicy zerami
        	list.add(0.0d);
        setLabelText(list);
	}
};
