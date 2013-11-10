
public class QuadratoMagico {
	
	int size;  // dimensione del gioco
	Block blocchi[][] = new Block[size][size];  // array 2-dimensionale
	int xE, yE;  //coordinate della casella vuota (xEmpty, yEmpty)
	int[] mosse;
	int numMosse;
	int mosseEseguite;
	
	public QuadratoMagico(int size, boolean goal)
	{
		this.size = size;
		blocchi = new Block[size][size];
		
		// Costruisci la tabella di gioco 
		for(int i = 0; i < size; i++)  
			for(int j = 0; j < size; j++) 
				blocchi[i][j] = new Block(size*i+j+1, i, j, size);
		
		// Assegna l'ultima casella come vuota
		blocchi[size-1][size-1].label = 0;
		xE = yE = size -1;
		
		// Mischia le tessere se non creo lo stato goal
		if(!goal)
			mischia(100);
		nMosse();
		mosseEseguite = 0;
	}
	
	// scambia due caselle - scambia il label e le destinazioni tra le due
	public void scambia(int ax, int ay, int bx, int by)
	{
		int temp_label = blocchi[ay][ax].label; 
		int temp_dx = blocchi[ay][ax].dx;
		int temp_dy = blocchi[ay][ax].dy;
		
		blocchi[ay][ax].label = blocchi[by][bx].label;
		blocchi[ay][ax].dx = blocchi[by][bx].dx;
		blocchi[ay][ax].dy = blocchi[by][bx].dy;
		
		blocchi[by][bx].label = temp_label;
		blocchi[by][bx].dx = temp_dx;
		blocchi[by][bx].dy = temp_dy;
	}
	
	public void nMosse()
	{
		numMosse = 0;
		mosse = new int[4];
		if(xE > 0)
		{
			mosse[numMosse] = 0;
			numMosse++;
		}	
		if(yE > 0)
		{
			mosse[numMosse] = 1;
			numMosse++;
		}
		if(xE < size - 1)
		{
			mosse[numMosse] = 2;
			numMosse++;
		}
		if(yE < size - 1)
		{
			mosse[numMosse] = 3;
			numMosse++;
		}
	}
	
	
	/*muove uno dei 4 tasselli adiacenti a quello vuoto, se possibile.
	/ m = 0 destra
	/ m = 1 giu
	/ m = 2 sinistra
	/ m = 3 su  */
	public boolean muovi(int m)
	{
		switch(m)
		{
		case 0:
			if(xE > 0)
			{
				scambia(xE, yE, xE-1, yE);
				xE = xE-1;
				mosseEseguite++;
				return true;
			}
			break;
		case 1:
			if(yE > 0)
			{
				scambia(xE, yE, xE, yE-1);
				yE = yE-1;
				mosseEseguite++;
				return true;
			}
			break;
		case 2:
			if(xE < size - 1)
			{
				scambia(xE, yE, xE+1, yE);
				xE = xE+1;
				mosseEseguite++;
				return true;
			}
			break;
		case 3:
			if(yE < size - 1)
			{
				scambia(xE, yE, xE, yE+1);
				yE = yE+1;
				mosseEseguite++;
				return true;
			}
			break;
		}
		return false;
	}
	
	// mischia per n volte le tessere
	public void mischia(int n)
	{
		for(int i = 0; i < n; i++)
			muovi((int) (Math.random()*4));
		
	}
	
	// calcola la funzione euristica h()
	public int h()
	{
		int manatthan = 0;
		for(int i=0; i<size; i++)
		      for(int j=0; j<size; j++)
		    	  if(blocchi[i][j].label != 0)
		    	  {
		    		  manatthan += blocchi[i][j].distanza();
		    	  }
		return manatthan;
	}
	
	//restituisce unat size copia dell'oggetto Otto
	public QuadratoMagico copy()
	{
		QuadratoMagico newOtto = new QuadratoMagico(size, false);
		newOtto.xE = xE;
		newOtto.yE = yE;
		Block newBlocchi[][] = new Block[size][size];
		for(int i = 0; i < size; i++)   
			for(int j = 0; j < size; j++)
			{
				Block b = blocchi[i][j];
				newBlocchi[i][j] = new Block(b.label, b.y, b.x, size);
			}
		newOtto.blocchi = newBlocchi;
		newOtto.nMosse();
		newOtto.mosseEseguite = mosseEseguite;
		
		
		return newOtto;
	}

	public int hashCode()
	{
		int h = 0;
		for(int i = 0; i < size; i++)   
			for(int j = 0; j < size; j++)
				h = h*3 + blocchi[i][j].label;
		return h;
	}
}
	
	
