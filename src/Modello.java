public class Modello implements Dominio<QuadratoMagico> {
	
	//   ---Tabella del gioco nello stato di GOAL
	QuadratoMagico goal;

	public QuadratoMagico copia(QuadratoMagico state)
	{
		return state.copy();
	}

	public int h(QuadratoMagico state) 
	{
		return state.h();
	}
	
	public QuadratoMagico inizializza(int size) 
	{
		goal = new QuadratoMagico(size, true);
		return new QuadratoMagico(size, false);
	}
	
	public boolean goal(QuadratoMagico state) 
	{
		if(state.hashCode() == goal.hashCode())
			return true;
		return false;
	}	
	
	public int numAzioni(QuadratoMagico state)
	{
		return state.numMosse;
	}
	
	public QuadratoMagico applica(QuadratoMagico stato, int azione)
	{	
		stato.muovi(stato.mosse[azione]);
		stato.nMosse();
		return stato;
	}
	
	public boolean uguali(QuadratoMagico a, QuadratoMagico b)
	{
		if(a.hashCode() == b.hashCode())
			return true;
		return false;
	}
}
	
