import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
/**
 * Panel w którym rysujemy cyfrê
 * @author Jakub Mazurek
 *
 */
class DrawingPanel extends JPanel
{
	/** narysowany przez nas obraz */
	private BufferedImage image;
	/** grafika */
	private Graphics2D graphics;
	/** stare wspó³rzêdne kursora*/
	private int oX, oY;
	/** nowe wspó³rzêdne kursora */
	private int nX, nY;
	
	/**
	 * Konstruktor inicjalizuj¹cy panel
	 * tworz¹cy s³uchaczy
	 */
	DrawingPanel()
	{
		setPreferredSize(new Dimension(300, 300));
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{	
				oX = e.getX(); // stare wspó³rzêdne = wspó³rzêdne klikniêcia
				oY = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				nX = e.getX(); // nowe wspó³rzêdne = wspó³rzêdne klikniêcia
				nY = e.getY();
				
				graphics.drawLine(oX, oY, nX, nY); // narysowanie linii
				repaint();
				
				oX = nX; // stare wspó³rzêdne = nowe wspó³rzêdne
				oY = nY;
			}
		});
	}
	/**
	 * Inicjalizacja obrazu do rysowania
	 */
	void initializeGraphics()
	{
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB); // nowy obraz o wymiarach panelu
		graphics = (Graphics2D) image.getGraphics();
		clear(); // wyczyszczenie obrazu
	}
	/**
	 * Czyszczenie ca³ego obrazu na bia³o
	 */
	public void clear()
	{
		graphics.setPaint(Color.WHITE); // wybranie koloru bia³ego
		graphics.fillRect(0, 0, getWidth(), getHeight()); // wype³nienie ca³ego obrazu kolorem
	    graphics.setPaint(Color.BLUE); // powrót do koloru czarnego
	    repaint();
	}
	/**
	 * Rysowanie naszego obrazu
	 * oraz jego inicjalizacja gdy nie istnieje
	 */
	protected void paintComponent(Graphics g)
	{
		if(image == null) // inicjalizacja obrazu jeœli nie istnieje
			initializeGraphics();
		g.drawImage(image, 0, 0, null); // narysowanie obrazu
	}
	/**
	 * Zwrócenie narysowanego przez nas obrazu
	 * @return narysowany przez nas obraz
	 */
	public BufferedImage getImage()
	{
		return image;
	}
};