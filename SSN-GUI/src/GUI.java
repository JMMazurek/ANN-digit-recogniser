import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Klasa zawiera ca³e GUI i komunikuje siê
 * bezpoœrednio z kontrolerem
 * @author Jakub Mazurek
 *
 */
public class GUI
{
	/** kontroler */
	Controller controller;
	 /** panel do rysowania znaku */
	DrawingPanel dPanel;
	/** panel do wyœwietlania sposobu interpretacji nawysowanego znaku */
	ShowingPanel sPanel;
	
	/** pole wyœwietlaj¹ce podobieñstwo do danej cyfry */
	Label label;
	/** przycisk do rozpoznania znaku */
	JButton rButton;
	/** przycisk do czyszczenia paneli */
	JButton cButton;
	/** przycisk do wyjœcia z programu */
	JButton eButton;
	/** przycik do wybrania pliku z konfiguracj¹ */
	JButton fButton;
	JButton wButton;
	JTextField tFile;
	JTextField tNumber;
	
	/**
	 * Konstruktor inicjalizuj¹cy GUI
	 */
	public GUI()
	{
		JFrame frame = new JFrame("Neurony");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = frame.getContentPane();
		
		dPanel = new DrawingPanel(); // inicjalizacja zawartoœci ramki
		sPanel = new ShowingPanel();
		
		label = new Label();
		
		rButton = new JButton("Rozpoznaj");
		cButton = new JButton("Wyczyœæ");
		fButton = new JButton("Wczytaj");
		eButton = new JButton("WyjdŸ");
		wButton = new JButton("Zapisz");
		initializeButtons();
		tFile = new JTextField("Nazwa pliku");
		tNumber = new JTextField("Narysowana cyfra");
		
		container.setLayout(new FlowLayout()); // ustawienie layoutu
		container.add(dPanel); // dodanie zawartoœci do kontenera
		container.add(sPanel);
		container.add(label);
		container.add(rButton);
		container.add(cButton);
		container.add(fButton);
		container.add(eButton);
		container.add(wButton);
		container.add(tFile);
		container.add(tNumber);
		container.setBackground(Color.GREEN); // ustawienie koloru t³a na zielony
		frame.setSize(690, 380);
		frame.setVisible(true);
	}
	/**
	 * Inicjalizacja przycisków
	 */
	void initializeButtons()
	{
		rButton.setEnabled(false); // zablokowanie przycisku
		rButton.addActionListener(new ActionListener()
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	controller.readSign(); // odczytanie narysowanego znaku
		    }
		});
		cButton.addActionListener(new ActionListener()
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        dPanel.clear(); // wyczyszczenie paneli
		        sPanel.clear();
		        label.clear(); // i pola tekstowego
		    }
		});
		fButton.addActionListener(new ActionListener()
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	controller.initialize(); // inicjalizacja kontrolera
		    }
		});
		eButton.addActionListener(new ActionListener()
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0); // wyjœcie
		    }
		});
		wButton.addActionListener(new ActionListener()
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	int number = Integer.parseInt(tNumber.getText());
		    	
				try
				{
					FileWriter f = new FileWriter(tFile.getText(), true);
					for(int i=0; i<10; ++i) // wype³nienie obrazu kwadratami o wielkoœci 30 x 30 o kolorze
						for(int j=0; j<7; ++j) // bia³ym dla false i czarnym dla true (w tablicy wype³nienia)
						{
							if(controller.getTable()[i][j])
								f.write("1 ");
							else
								f.write("0 ");
						}
					f.write("->");
					
					for(int i=0; i<10; i++)
						if(i == number)
							f.write(" 1");
						else
							f.write(" 0");
					f.write(System.getProperty( "line.separator" ));
					f.close();
				} catch (IOException ef)
				{
					// TODO Auto-generated catch block
					ef.printStackTrace();
				}
				
		    }
		});
	}
	/**
	 * Ustawiene kontrolera
	 * @param controller kontroler
	 */
	void setController(Controller controller)
	{
		this.controller = controller;
	}
}