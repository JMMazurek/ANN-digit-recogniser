import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * Pole wyœwietlaj¹ce wyœwietlaj¹ce podobieñstwo do danej cyfry
 * @author Jakub Mazurek
 *
 */
public class Label extends JLabel
{
	/**
	 * Konstruktor inicjalizuj¹cy
	 */
	Label()
	{
		super(); // konstruktor klasy JLabel
		setBackground(Color.WHITE);
		setOpaque(true); // brak przezroczystoœci
		setPreferredSize(new Dimension(100, 300));
		setAlignmentX(LEFT_ALIGNMENT); // wyrównanie tekstu do lewej
		clear(); // wyczyszczenie
	}
	/**
	 * Wyswietlenie podobieñstw do danych cyfr narysowanego znaku
	 * (prawdopodobieñstwo, ¿e narysowany znak to dana cyfra)
	 * @param list wyjœcie sieci neuronowej
	 */
	void setLabelText(ArrayList<Double> list)
	{
		StringBuilder builder = new StringBuilder();
		double sum = 0;
		for(int i=0; i<list.size(); ++i) // zsumowanie wartoœci wyjœcia
		{
			sum+=list.get(i);
		}
		builder.append("<html>");
		for(int i=0; i<list.size(); ++i) // wypisanie pradwopodobieñstw: (wartoœæ dla danej cyfry / suma wyjœæ) * 100%
		{
			builder.append(i + " " + (sum==0.0d ? "0" : Math.round(list.get(i)/sum*100)) + "%<br>");
		}
		builder.append("</html>");
		setText(builder.toString());
	}
	/**
	 * Ustawienie prawdopodobieñstw na 0%
	 */
	void clear()
	{
        ArrayList<Double> list = new ArrayList<Double>();
        for(int i=0; i<10; ++i) // wype³nienie tablicy zerami
        	list.add(0.0d);
        setLabelText(list);
	}
};
