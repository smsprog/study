package uobject;

import java.util.Vector;
import java.util.HashSet;
import java.util.Set;
import uobject.*;

public class Neighborhoods {
	public Object[] neighborhoods={null, null, null, null};
	public int size;
	int shift;
	double unit;

	public Neighborhoods(int size, int shift) throws Exception {
		this.unit=5.0;
		this.size=size;
		this.shift=shift % (int)this.unit;
		this.neighborhoods=new Object[this.size*this.size];
		for(int i=0;i<this.size*this.size;i++)this.neighborhoods[i]=new HashSet<Ship>();
	}
	
	public int getNeighborhoodIdx(Vector pos) {
		double border=unit+shift;
		
		if((double)pos.elementAt(0)>border && (double)pos.elementAt(1)>border)
			return(3);
		else if((double)pos.elementAt(0)>border && (double)pos.elementAt(1)<=border)
			return(1);
		else if((double)pos.elementAt(0)<=border && (double)pos.elementAt(1)>border)
			return(2);
		else
			return(0);
	}
	
	public void printNeighborhoods() {
		System.out.println("allNgh: "+this+" size: "+size+" shift: "+shift+" unit: "+unit);
		for(int i=0;i<neighborhoods.length;i++) {
			System.out.println("Neigh"+i+": "+((Set<Ship>)neighborhoods[i]) );
		}
	}
}
