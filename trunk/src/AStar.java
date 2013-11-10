import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public final class AStar<T> {
	
	private PriorityQueue<Nodo> open = new PriorityQueue<Nodo>(3, new NodeComparator()); 
	private HashMap<T, Nodo> closed = new HashMap<T, Nodo>();
	private Dominio<T> dominio;
	
	public AStar(Dominio<T> dominio) 
	{
	    this.dominio = dominio;
	}
	
	public T search(T init)
	{
		Nodo initNode = new Nodo(init, null, 0);
		open.add(initNode);
		
		while(!open.isEmpty())
		{
			Nodo n = open.poll();
			if(closed.containsKey(n.stato))
			{
				System.out.println("incluso");
				continue;
			}
			
			if(dominio.goal(n.stato))
				return n.stato;
			
			closed.put(n.stato, n);
			
			for(int i = 0; i < dominio.numAzioni(n.stato); i++)
			{
				T child = dominio.copia(n.stato);
				child = dominio.applica(child, i);
				
				Nodo node = new Nodo(child, n, 1);
				open.add(node);
			}
		}
		return init;
	}
	
	private final class Nodo 
	{
		final int f, g;
		final Nodo padre;
		final T stato;
			
		private Nodo (T stato, Nodo padre, int costo)
		{
			this.stato = stato;
			this.g = (padre != null) ? padre.g + costo : costo;
			this.f =  g + dominio.h(stato);
			this.padre = padre;
		}
	}
	
	private final class NodeComparator implements Comparator<Nodo> 
	{
		public int compare(Nodo a, Nodo b) 
		{
			if (a.f == b.f) 
		    { 
		       if (a.g > b.g) return -1;
		       if (a.g < b.g) return 1;
		       return 0;
		    }
		    else 
		    {
		       if (a.f < b.f) return -1;
		       if (a.f > b.f) return 1;
		       return 0;
		    }
		    
			
		}
		
	}
}
