
public interface Dominio<D> {
	
	public D inizializza(int size);
	public int h(D stato);
	public boolean goal(D stato);
	public int numAzioni(D stato);
	public D applica(D stato, int azione);
	public D copia(D stato);
	public boolean uguali(D i, D j);
}
