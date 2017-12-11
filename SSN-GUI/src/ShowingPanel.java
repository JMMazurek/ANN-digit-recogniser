import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Panel wyœwietlaj¹cy wype³nienia kolorem czarnym poszczególnych
 * czêœci obrazu, czyli w jaki sposób program interpretuje
 * narysowany przez nas znak
 * @author Jakub Mazurek
 *
 */
class ShowingPanel extends JPanel
{
	/** obraz na którym wyœwietlamy interpretacjê znaku */
	private BufferedImage image;
	/** grafika */
	private Graphics2D graphics;
	/** tablica wartoœci wype³nienia kolorem czarnym poszczególnych czêœci obrazu */
	private boolean[][] table;
	/**
	 * Konstruktor ustawiaj¹cy wartoœci tabeli wype³nienia na fa³sz
	 */
	ShowingPanel()
	{
		table = new boolean[10][7];
		for(int i=0; i<10; ++i) // przypisanie zawartoœci tablicy wartoœci false
		{
			for(int j=0; j<7; ++j)
				table[i][j] = false;
		}
		setPreferredSize(new Dimension(210, 300));
	}
	/**
	 * Inicjalizacja obrazu do wyœwietlenia
	 */
	void initializeGraphics()
	{
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB); // stworzenie nowego obrazu o wyniarach panelu
		graphics = (Graphics2D) image.getGraphics();
		clear(); // wyczyszczenie
	}
	/**
	 * Ustawienie wartoœci tabeli wype³nienia na fa³sz
	 */
	public void clear()
	{
		for(int i=0; i<10; ++i)  // przypisanie zawartoœci tablicy wype³nienia wartoœci false
		{
			for(int j=0; j<7; ++j)
				table[i][j] = false;
		}
	    repaint();
	}
	/**
	 * Wyœwietlenie wartoœci tabeli wype³nienia jako
	 * czarne i bia³e kwadraty
	 * @param g grafika
	 */
	protected void paintComponent(Graphics g)
	{
		if(image == null)  // inicjalizacja obrazu jeœli nie istnieje
			initializeGraphics();
		graphics.setPaint(Color.BLACK); // wybranie koloryu czarnego
		
		for(int i=0; i<10; ++i) // wype³nienie obrazu kwadratami o wielkoœci 30 x 30 o kolorze
			for(int j=0; j<7; ++j) // bia³ym dla false i czarnym dla true (w tablicy wype³nienia)
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
	 * Zauktualizowanie wartoœci tabeli wype³nienia
	 * @param table wartoœci tabeli wype³nienia
	 */
	public void updateTable(boolean table[][])
	{
		this.table = table;
		repaint();
	}
};