import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * Pole wy�wietlaj�ce wy�wietlaj�ce podobie�stwo do danej cyfry
 * @author Jakub Mazurek
 *
 */
public class Label extends JLabel
{
	/**
	 * Konstruktor inicjalizuj�cy
	 */
	Label()
	{
		super(); // konstruktor klasy JLabel
		setBackground(Color.WHITE);
		setOpaque(true); // brak przezroczysto�ci
		setPreferredSize(new Dimension(100, 300));
		setAlignmentX(LEFT_ALIGNMENT); // wyr�wnanie tekstu do lewej
		clear(); // wyczyszczenie
	}
	/**
	 * Wyswietlenie podobie�stw do danych cyfr narysowanego znaku
	 * (prawdopodobie�stwo, �e narysowany znak to dana cyfra)
	 * @param list wyj�cie sieci neuronowej
	 */
	void setLabelText(ArrayList<Double> list)
	{
		StringBuilder builder = new StringBuilder();
		double sum = 0;
		for(int i=0; i<list.size(); ++i) // zsumowanie warto�ci wyj�cia
		{
			sum+=list.get(i);
		}
		builder.append("<html>");
		for(int i=0; i<list.size(); ++i) // wypisanie pradwopodobie�stw: (warto�� dla danej cyfry / suma wyj��) * 100%
		{
			builder.append(i + " " + (sum==0.0d ? "0" : Math.round(list.get(i)/sum*100)) + "%<br>");
		}
		builder.append("</html>");
		setText(builder.toString());
	}
	/**
	 * Ustawienie prawdopodobie�stw na 0%
	 */
	void clear()
	{
        ArrayList<Double> list = new ArrayList<Double>();
        for(int i=0; i<10; ++i) // wype�nienie tablicy zerami
        	list.add(0.0d);
        setLabelText(list);
	}
};
