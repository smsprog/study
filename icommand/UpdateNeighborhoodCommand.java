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
		ICommand cmd,macroCmd;
		
		System.out.println("All neigh:"+allNeighborhoods+", sh="+this.sh);
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
			try {
				((Set<Ship>)allNeighborhoods[oldIdx]).remove(sh);
			} catch (Exception e) {
				oldIdx=newIdx;
			}
			i=0;
			for(Ship shFromNgh: (Set<Ship>)allNeighborhoods[newIdx]) {
				//q.add(new CheckCollisionCommand(sh, shFromNgh));
				cmdList.add(new CheckCollisionCommand(sh, shFromNgh));
				i++;
			}
			ICommand[] cmdArr=new ICommand[i];
			for(int j=0;j<cmdArr.length;j++)cmdArr[j]=cmdList.remove();
			
			macroCmd=new MacroCommand(cmdArr);
			try {
				cmd=IoC.Resolve("Scopes.Current", "Neigh"+newIdx); cmd.exec();
				System.out.println("UpdateNeighborhoodCommand.exec(): Neigh"+newIdx+" FOUND, replacing MacroCommand...");
				cmd=IoC.Resolve("IoC.Register", "MacroCommand", (IFunction)((arr) -> (macroCmd))); cmd.exec();
			} catch (Exception e) {
				System.out.println("UpdateNeighborhoodCommand.exec(): exception "+e);
				
				//System.out.println("UpdateNeighborhoodCommand.exec(): new MacroCommand has created");
				cmd=new ExecNeighMacroCommand("Neigh"+newIdx, macroCmd);
				//System.out.println("UpdateNeighborhoodCommand.exec(): new ExecNeighMacroCommand has created");
				q.add(cmd);
				//System.out.println("UpdateNeighborhoodCommand.exec(): new ExecNeighMacroCommand had queued");
			}
			
			((Set<Ship>)allNeighborhoods[newIdx]).add(sh);
			str=", neigh of "+sh+" has changed to Neigh"+newIdx+", "+i+" CheckCollisionCommand added";
		}
		System.out.println("Ship "+sh+" is in Neigh"+oldIdx+", "+sh.getProperty("position")+str);
	}
}


