import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Definisco il frame principale nel quale verranno inseriti i vari elementi
class FrameP extends JFrame{
	
	// Definizione dei vari elementi di cui il frame � composto
	JPanel c;
	JLabel spiega = new JLabel("Seleziona la grandezza del quadrato");
	JLabel intro = new JLabel("Benvenuto in Quadrato Magico");
	
	// Bottoni maschere
	JButton inizia = new JButton("Inizia gioco");
	JButton risolvi = new JButton("Risolvi");
	JButton chiudi = new JButton ("Chiudi");
	JButton restart = new JButton ("Ricomincia");
	JRadioButton scelta1 = new JRadioButton("Lato 3x3");
	JRadioButton scelta2 = new JRadioButton("Lato 4x4");
	ButtonGroup gruppo = new ButtonGroup();
	
	// Pannelli utilizzati nella struttura principale
	JPanel titoli = new JPanel();
	JPanel bottoni1 = new JPanel();
	JPanel bottoni2 = new JPanel();
	JPanel bottoni3 = new JPanel();
	JPanel bottoni4  = new JPanel();
	
	// Definizione del modello
	Modello mod;
	QuadratoMagico g;

	public FrameP(){
	
	FrameStart();
	
	}
	
	
	// Parte relativa alla prima maschera visualizzata, nella quale poter scegliere il tipo di griglia ed iniziare a giocare
	private void FrameStart(){
		
		
		// Costruzione della prima maschera
		c = (JPanel)this.getContentPane();
		this.setSize(349,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());
		
		intro.setHorizontalAlignment(SwingConstants.CENTER);
		intro.setFont(new Font("Serif", Font.PLAIN, 25));
		spiega.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Ascoltatore per eseguire le azioni
		inizia.addActionListener(new StartGame());

		gruppo.add(scelta1);
		gruppo.add(scelta2);
		
		titoli.setLayout(new BorderLayout());
		titoli.setSize(300,200);
		
		titoli.add(intro, BorderLayout.NORTH);
		
		bottoni1.setLayout(new BorderLayout());
		
		bottoni1.add(spiega, BorderLayout.NORTH);
		bottoni1.add(scelta1, BorderLayout.WEST);
		bottoni1.add(scelta2, BorderLayout.EAST);
		
		inizia.setEnabled(true);
		risolvi.setEnabled(false);
		chiudi.setEnabled(false); 
		restart.setEnabled(false); 
		
		
		bottoni2.setLayout(new FlowLayout());
		bottoni2.add(inizia);
		
		c.add(titoli, BorderLayout.NORTH);
		c.add(bottoni1, BorderLayout.CENTER);
		c.add(bottoni2, BorderLayout.SOUTH);

		this.setVisible(true);

		
	}
	
	
	// Parte relativa alla schermata dedicata alla visualizzazione della prima griglia non risolta
	private void FrameGame() {
		
		
		// Definizione della dimensione della griglia
		int size = 3;
		
		if(scelta1.isSelected()){
            size = 3;
        }
        else if(scelta2.isSelected()){
            size = 4;
        }
		
		
		mod = new Modello();
		g = mod.inizializza(size);
		
		c.removeAll();
		
		// Costruzione dell'interfaccia a schermo
		c = (JPanel)this.getContentPane();
		this.setSize(350,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());
		
		JLabel desc = new JLabel("Quadrato magico " + g.size + "x" + g.size);
		desc.setHorizontalAlignment(SwingConstants.CENTER);
		desc.setFont(new Font("Serif", Font.PLAIN, 25));
		
		c.add(desc, BorderLayout.NORTH);
				
		JPanel griglia = new JPanel();
		
		griglia.setLayout(new GridLayout(g.size,g.size));

		// Popolazione della griglia
		for(int i = 0; i < g.size; i++){
			
			for(int j = 0; j < g.size; j++){
				
				JLabel val = new JLabel("" + g.blocchi[i][j].label);
				val.setHorizontalAlignment(SwingConstants.CENTER);
				val.setFont(new Font("Arial", Font.PLAIN, 25));
				val.setForeground(Color.DARK_GRAY);
				val.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.LIGHT_GRAY));
				
				griglia.add(val);
				
			}
			
		}
		
		c.add(griglia, BorderLayout.CENTER);
		JLabel h = new JLabel("H = " + g.h());
		h.setFont(new Font("Serif", Font.PLAIN, 25));

		inizia.setEnabled(false);
		risolvi.setEnabled(true);
		chiudi.setEnabled(false); 
		restart.setEnabled(false); 
		
		bottoni2.removeAll();
		
		bottoni3.add(risolvi);
		
		// Ascoltatore per attivare la risoluzione del problema
		risolvi.addActionListener(new SolveGame());
		
		
		c.add(h, BorderLayout.EAST);
		c.add(bottoni3, BorderLayout.SOUTH);

 		this.setVisible(true);
		
		
	}
		
	
	// Maschera relativa alla visualizzazione della soluzione del problema
	private void FrameSolved(){
		
		AStar<QuadratoMagico> as = new AStar<QuadratoMagico>(mod);
		
		long start = System.currentTimeMillis();
		
		// Risoluzione del problema 
		QuadratoMagico solution = as.search(g);
				
		long end = System.currentTimeMillis();
		
		c.removeAll();
		
		
		// Costruzione della maschera 
		c = (JPanel)this.getContentPane();
		this.setSize(351,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());
		
		JLabel desc = new JLabel("Quadrato magico " + g.size + "x" + g.size + " RISOLTO");
		desc.setHorizontalAlignment(SwingConstants.CENTER);
		desc.setFont(new Font("Serif", Font.PLAIN, 25));
		
		c.add(desc, BorderLayout.NORTH);
				
		JPanel griglia = new JPanel();
		
		griglia.setLayout(new GridLayout(g.size,g.size));
		
		// Popolazione della griglia
		for(int i = 0; i < g.size; i++){
			
			for(int j = 0; j < g.size; j++){
								
				JLabel sol = new JLabel("" + solution.blocchi[i][j].label);
				
				sol.setHorizontalAlignment(SwingConstants.CENTER);
				sol.setFont(new Font("Arial", Font.PLAIN, 25));
				sol.setForeground(Color.DARK_GRAY);
				sol.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.LIGHT_GRAY));
				
				griglia.add(sol);
				
			}
			
		}
		
		
		c.add(griglia, BorderLayout.CENTER);
		
		
		//JLabel h = new JLabel("H = " + solution.h());
		//h.setFont(new Font("Serif", Font.PLAIN, 20));
		
		JLabel g = new JLabel("Mosse eseguite = " + solution.mosseEseguite);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		

		int total = (int)(end-start);
		
		JLabel t = new JLabel("Tempo = "+ total + " ms");
		t.setFont(new Font("Serif", Font.PLAIN, 20));
		
		inizia.setEnabled(false);
		risolvi.setEnabled(false);
		chiudi.setEnabled(true); 
		restart.setEnabled(true); 
		
		bottoni3.removeAll();
		
		bottoni4.setLayout(new FlowLayout());
		bottoni4.add(chiudi);
		bottoni4.add(restart);
		
		// Ascoltatori per poter ripartire con il gioco oppure chiudere
		restart.addActionListener(new StartGame());
		chiudi.addActionListener(new ExitGame());


		JPanel result = new JPanel(); 
		result.setLayout(new GridLayout(4,0));
		
		//result.add(h);
		result.add(g);
		result.add(t);
		
		
		c.add(result, BorderLayout.EAST);
		c.add(bottoni4, BorderLayout.SOUTH);
		
		
 		this.setVisible(true);


	}
	
	
	// Azione che corrisponde all'scoltatore Start Game
	class StartGame implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		if (button.getText()=="Inizia gioco"){
			
			// Permette di procedere con l'attivazione del gioco
			FrameGame();
			inizia.removeActionListener(this);
			
		} else {
		
		if (button.getText() == "Ricomincia"){
						
			
			c.removeAll();
			
			// Permette con il resel del gioco e l'avvio di una nuova partita
			FrameStart();
			restart.removeActionListener(this);
						
			
		}
		}
		}
	}
	
	// Azione che corrisponde all'scoltatore Solve Game
	class SolveGame implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		if (button.getText()=="Risolvi"){
			
			
			// Effettuo la risoluzione del gioco
			risolvi.removeActionListener(this);
			
			c.remove(bottoni4);
			
			FrameSolved();
			
		}
		}
	}

	
	// Azione per la chiusura della finestra, corrisponde all'ascoltatore Exit Game
	class ExitGame implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			if (button.getText()=="Chiudi"){
				
				// Chiude il gioco
				System.exit(0);
				
			}
			}
		
	}
	
	
	
}





public class GraficaFinestra{

	// Main per l'avvio di una partita
	public static void main(String args[])

	{

		
		JFrame mainWindow = new FrameP();
		

	}
}