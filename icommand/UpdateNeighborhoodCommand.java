package icommand;

import java.util.Vector;
import imovable.*;
import uobject.*;
import java.util.Set;
import java.util.LinkedList;
import java.util.Queue;

public class UpdateNeighborhoodCommand implements ICommand {
	private Ship sh;
	Object[] allNeighborhoods;
	Queue<ICommand> q;
	
	public UpdateNeighborhoodCommand(Ship sh, Object[] allNeighborhoods, Queue<ICommand> q) throws Exception {
		this.sh=sh;
		this.allNeighborhoods=allNeighborhoods;
		this.q=q;
	}

	public void exec() throws Exception {
		Set<Ship> ngh;
		int oldIdx,newIdx,i;
		Vector pos;
		String str="";
		Queue<ICommand> cmdList=new LinkedList<>();
		
		System.out.println("All neigh:"+allNeighborhoods);
		for(oldIdx=0;oldIdx<allNeighborhoods.length;oldIdx++) {
			ngh=(Set<Ship>)allNeighborhoods[oldIdx];
			if(ngh.contains(sh))break;
		}
		
		pos=(Vector)sh.getProperty("position");
		if((double)pos.elementAt(0)>5.0 && (double)pos.elementAt(1)>5.0)
			newIdx=3;
		else if((double)pos.elementAt(0)>5.0 && (double)pos.elementAt(1)<=5.0)
			newIdx=1;
		else if((double)pos.elementAt(0)<=5.0 && (double)pos.elementAt(1)>5.0)
			newIdx=2;
		else
			newIdx=0;
		
		if(newIdx!=oldIdx) {
			((Set<Ship>)allNeighborhoods[oldIdx]).remove(sh);
			i=0;
			for(Ship shFromNgh: (Set<Ship>)allNeighborhoods[newIdx]) {
				//q.add(new CheckCollisionCommand(sh, shFromNgh));
				cmdList.add(new CheckCollisionCommand(sh, shFromNgh));
				i++;
			}
			ICommand[] cmdArr=new ICommand[i];
			for(int j=0;j<cmdArr.length;j++)cmdArr[j]=cmdList.remove();
			q.add(new MacroCommand(cmdArr));
			((Set<Ship>)allNeighborhoods[newIdx]).add(sh);
			str=", neigh of "+sh+" has changed to "+newIdx+", "+i+" CheckCollisionCommand added";
		}
		System.out.println("Neigh"+oldIdx+" contains "+sh+", "+sh.getProperty("position")+str);
	}
}