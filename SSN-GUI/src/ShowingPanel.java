import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Panel wy�wietlaj�cy wype�nienia kolorem czarnym poszczeg�lnych
 * cz�ci obrazu, czyli w jaki spos�b program interpretuje
 * narysowany przez nas znak
 * @author Jakub Mazurek
 *
 */
class ShowingPanel extends JPanel
{
	/** obraz na kt�rym wy�wietlamy interpretacj� znaku */
	private BufferedImage image;
	/** grafika */
	private Graphics2D graphics;
	/** tablica warto�ci wype�nienia kolorem czarnym poszczeg�lnych cz�ci obrazu */
	private boolean[][] table;
	/**
	 * Konstruktor ustawiaj�cy warto�ci tabeli wype�nienia na fa�sz
	 */
	ShowingPanel()
	{
		table = new boolean[10][7];
		for(int i=0; i<10; ++i) // przypisanie zawarto�ci tablicy warto�ci false
		{
			for(int j=0; j<7; ++j)
				table[i][j] = false;
		}
		setPreferredSize(new Dimension(210, 300));
	}
	/**
	 * Inicjalizacja obrazu do wy�wietlenia
	 */
	void initializeGraphics()
	{
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB); // stworzenie nowego obrazu o wyniarach panelu
		graphics = (Graphics2D) image.getGraphics();
		clear(); // wyczyszczenie
	}
	/**
	 * Ustawienie warto�ci tabeli wype�nienia na fa�sz
	 */
	public void clear()
	{
		for(int i=0; i<10; ++i)  // przypisanie zawarto�ci tablicy wype�nienia warto�ci false
		{
			for(int j=0; j<7; ++j)
				table[i][j] = false;
		}
	    repaint();
	}
	/**
	 * Wy�wietlenie warto�ci tabeli wype�nienia jako
	 * czarne i bia�e kwadraty
	 * @param g grafika
	 */
	protected void paintComponent(Graphics g)
	{
		if(image == null)  // inicjalizacja obrazu je�li nie istnieje
			initializeGraphics();
		graphics.setPaint(Color.BLACK); // wybranie koloryu czarnego
		
		for(int i=0; i<10; ++i) // wype�nienie obrazu kwadratami o wielko�ci 30 x 30 o kolorze
			for(int j=0; j<7; ++j) // bia�ym dla false i czarnym dla true (w tablicy wype�nienia)
			{
				if(table[i][j])
					graphics.setColor(Color.BLACK);
				else
					graphics.setColor(Color.WHITE);
				graphics.fillRect(j*30, i*30, 30, 30);
			}
		g.drawImage(image, 0, 0, null);
	}
	/**
	 * Zauktualizowanie warto�ci tabeli wype�nienia
	 * @param table warto�ci tabeli wype�nienia
	 */
	public void updateTable(boolean table[][])
	{
		this.table = table;
		repaint();
	}
};