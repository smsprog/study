package icommand;

import java.util.Vector;
import imovable.*;
import uobject.*;
import java.util.Set;
import java.util.LinkedList;
import java.util.Queue;

public class UpdateNeighborhoodCommand implements ICommand {
	Ship sh;
	Neighborhoods allNgh;
	Queue<ICommand> q;
	
	public UpdateNeighborhoodCommand(Ship sh, Neighborhoods allNgh, Queue<ICommand> q) throws Exception {
		this.sh=sh;
		this.allNgh=allNgh;
		this.q=q;
	}

	public void exec() throws Exception {
		Set<Ship> ngh;
		int oldIdx,newIdx,i;
		String str="";
		Queue<ICommand> cmdList=new LinkedList<>();
		ICommand cmd,macroCmd;
		ICommand[] cmdArr;
		
		System.out.println("All neigh:"+allNgh.neighborhoods+", sh="+this.sh);
		for(oldIdx=0;oldIdx<allNgh.neighborhoods.length;oldIdx++) {
			ngh=(Set<Ship>)allNgh.neighborhoods[oldIdx];
			if(ngh.contains(sh))break;
		}
		
		newIdx=allNgh.getNeighborhoodIdx((Vector)sh.getProperty("position"));
		
		if(newIdx!=oldIdx) {
			try {
				((Set<Ship>)allNgh.neighborhoods[oldIdx]).remove(sh);
			} catch (Exception e) {
				oldIdx=newIdx;
			}
			
			cmdArr=prepareCmds4MacroByNgh(newIdx);
			macroCmd=new MacroCommand(cmdArr);
			
			try {
				cmd=IoC.Resolve("Scopes.Current", "Neigh"+newIdx); cmd.exec();
				System.out.println("UpdateNeighborhoodCommand.exec(): Neigh"+newIdx+" FOUND, replacing MacroCommand...");
				cmd=IoC.Resolve("IoC.Register", "MacroCommand", (IFunction)((arr) -> (macroCmd))); cmd.exec();
			} catch (Exception e) {
				System.out.println("UpdateNeighborhoodCommand.exec(): exception "+e);
				cmd=new ExecNeighMacroCommand("Neigh"+newIdx, macroCmd);
				q.add(cmd);
			}
			
			((Set<Ship>)allNgh.neighborhoods[newIdx]).add(sh);
			str=", neigh of "+sh+" has changed to Neigh"+newIdx+", "+cmdArr.length+" CheckCollisionCommand added";
		}
		System.out.println("Ship "+sh+" is in Neigh"+oldIdx+", "+sh.getProperty("position")+str);
	}
	
	ICommand[] prepareCmds4MacroByNgh(int nghIdx) throws Exception {
		Queue<ICommand> cmdList=new LinkedList<>();
		
		for(Ship shFromNgh: (Set<Ship>)this.allNgh.neighborhoods[nghIdx])cmdList.add(new CheckCollisionCommand(this.sh, shFromNgh));
		ICommand[] cmdArr=new ICommand[cmdList.size()];
		for(int j=0;j<cmdArr.length;j++)cmdArr[j]=cmdList.remove();
		return(cmdArr);
	}
}


