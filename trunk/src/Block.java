/* Classe che rappresenta un tassello del gioco */
public class Block {
	
	int x = 0, y = 0;  // Coordinate correnti
	int dx, dy;        // Coordinate di destinazione
	public int label;  // Testo del tassello (Numero)
	
	// Costruttore
	public Block(int lab, int y, int x, int size)
	{
		label = lab;
		this.x = x;
		this.y = y;
		
		dx = (label - 1) % size;
		dy = (label - 1) / size;
	}
	
	/* Restituisce la distanza di manatthan tra il tassello
	 * e la sua destinazione */
	public int distanza()
	{
		
		int distx = Math.abs(x - dx);
		int disty = Math.abs(y - dy);
	
		return distx + disty;

	}
}
