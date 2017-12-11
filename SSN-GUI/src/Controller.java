import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Kontroler zarz¹dzaj¹cy modelem oraz widokiem
 * @author Jakub Mazurek
 *
 */
public class Controller
{
	/** widok */
	private GUI view;
	/** model */
	private NeuralNetwork model;
	
	/**
	 * Konstruktor ustawiaj¹cy widok oraz model
	 * @param gui widok
	 * @param network model
	 */
	Controller(GUI gui, NeuralNetwork network)
	{
		view = gui;
		model = network;
		
		view.setController(this);
		model.setController(this);
	}
	/**
	 * Wybranie pliku z konfiguracj¹ oraz inicjalizacja
	 * modelu
	 */
	public void initialize()
	{
	    JFileChooser chooser = new JFileChooser();
	    chooser.setAcceptAllFileFilterUsed(false); // akceptacja tylko wybranego przeze mnie rozszerzenia
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Network configuration files (*.ncf)", "ncf");
	    chooser.setFileFilter(filter); // ustawienie filtra plików
	    int returnVal = chooser.showOpenDialog(null); // otworzenie okienka wyboru pliku
	    if(returnVal == JFileChooser.APPROVE_OPTION) // jeœli wybrano odpowiedni plik
	    {
			SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
			    	view.rButton.setEnabled(true); // odblokowanie przycisku rozpoznania
			    }
			});
	    	    
	    	ArrayList<Integer> list = new ArrayList<Integer>(); // lista neuronów w warstwach sieci neuronowej
			list.add(70);
			list.add(20);
			list.add(20);
			list.add(10);
			
			model.initialize(list, true); // inicjalizacja modelu
			model.setConnections(chooser.getSelectedFile().getAbsolutePath()); // stworzenie po³¹czeñ miêdzy neuronami o waga z podanego pliku
	    }
	}
	/**
	 * Stworzenie i zwrócenie tabeli wype³nienia kolorem czarnym
	 * poszczególnych czêœci obrazu 
	 * @return tabela wype³nienia kolorem czarnym poszczególnych czêœci obrazu
	 */
	public boolean[][] getTable()
	{
		BufferedImage image = view.dPanel.getImage();
		boolean[][] table = new boolean[10][7];
		for(int i=0; i<10; ++i) // przypisanie zawartoœci tablicy wartoœci false
			for(int j=0; j<7; ++j)
				table[i][j] = false;
		
		int minX=300, minY=300, maxX=-1, maxY=-1; // ustawienie "nieosi¹galnych" wartoœci
		int x, y;

		for(int i=0; i<image.getHeight(null); ++i) // znalezienie skrajnych wspó³rzêdnych czarnych pikseli
			for(int j=0; j<image.getHeight(null); ++j)
				if(image.getRGB(j, i) == Color.BLUE.getRGB())
				{
					if(j < minX)
						minX = j;
					if(j > maxX)
						maxX = j;
					if(i < minY)
						minY = i;
					if(i > maxY)
						maxY = i;
				}
		
		x=maxX-minX; // szerokoœæ narysowaniego znaku
		y=maxY-minY; // wysokoœæ narysowanego znaku
		if((double)x/y > 0.7d) // poprawa proporcji (dopasowanie wysokoœci)
		{
			y=(int)Math.ceil(x/0.7d);
			int delta =(int)Math.ceil((y-(maxY-minY))/2.0d);
			maxY+=delta;
			minY-=delta;
		}
		if((double)x/y < 0.7d) // poprawa proporcji (dopasowanie szerokoœci)
		{
			x=(int)Math.ceil(y*0.7d);
			int delta =(int)Math.ceil((x-(maxX-minX))/2.0d);
			maxX+=delta;
			minX-=delta;
		}

		for(int i=Math.max(minY, 0); i<=Math.min(maxY, 299); i++) // dla ka¿dego punktu o kolorze czarnym ustawienie wartoœci true
			for(int j=Math.max(minX, 0); j<=Math.min(maxX, 299); j++) // w tablicy wype³nieñ dla odpowieniego fragmentu obrazu
				if(image.getRGB(j, i) == Color.BLUE.getRGB())
					table[(int)Math.min(9, ((i-minY)/(double)y)*10.0d)][(int)Math.min(6, ((j-minX)/(double)x)*7.0d)]=true;

		return table;
	}
	/**
	 * Wczytanie oraz rozpoznanie narysowanego znaku
	 */
	public void readSign()
	{
		boolean[][] boolTable = getTable();
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		    	view.sPanel.updateTable(boolTable); // aktualizacja tablicy wype³enieñ w panelu do wyœwietlania interpretacji znaku
		    }
		});
		
		ArrayList<Double> table = new ArrayList<Double>();
		
		for(int i=0; i<10; ++i) // przygotowanie tablicy wejœæ dla sieci neuronowej
			for(int j=0; j<7; ++j)
				if(boolTable[i][j])
					table.add(1.0d);
				else
					table.add(0.0d);
		
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		    	view.label.setLabelText(model.getOutput(table)); // ustawienie zawartoœci pola tekstowego
		    }
		});
		
	}
}
