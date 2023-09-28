package icommand;

import java.util.Vector;
import imovable.*;
import uobject.*;
import java.util.Set;
import java.util.LinkedList;
import java.util.Queue;

public class UpdateNeighborhoodCommand implements ICommand {
	Ship sh;
	Neighborhoods[] allNgh;
	Queue<ICommand> q;
	int macroSize=0;
	
	public UpdateNeighborhoodCommand(Ship sh, Neighborhoods[] allNgh, Queue<ICommand> q) throws Exception {
		this.sh=sh;
		this.allNgh=allNgh;
		this.q=q;
	}

	public void exec() throws Exception {
		Set<Ship> ngh;
		int[] oldIdx=new int[this.allNgh.length];
		int[] newIdx=new int[this.allNgh.length];
		String str="";
		Queue<ICommand> cmdList=new LinkedList<>();
		ICommand cmd;
		ICommand macroCmd;
		ICommand[] cmdArr;
		
		for(int k=0;k<allNgh.length;k++)
			System.out.println("All neigh:"+allNgh[k].neighborhoods+", sh="+this.sh);

		// find all old neighs of ship
		for(int k=0;k<allNgh.length;k++)
			for(oldIdx[k]=0;oldIdx[k]<allNgh[k].neighborhoods.length;oldIdx[k]++) {
				ngh=(Set<Ship>)allNgh[k].neighborhoods[oldIdx[k]];
				if(ngh.contains(sh))break;
			}
		
		// find all new neighs of ship
		for(int k=0;k<allNgh.length;k++)
			newIdx[k]=allNgh[k].getNeighborhoodIdx((Vector)sh.getProperty("position"));
		
		// set new neighs for all changed ones
		for(int k=0;k<allNgh.length;k++) {
			if(newIdx[k]!=oldIdx[k]) {
				try {
					((Set<Ship>)allNgh[k].neighborhoods[oldIdx[k]]).remove(sh);
				} catch (Exception e) {
					oldIdx[k]=newIdx[k];
				}
				
				cmdArr=prepareCmds4MacroByNgh(allNgh[k], newIdx[k]);
				macroCmd=new MacroCommand(cmdArr);
				
				try {
					cmd=IoC.Resolve("Scopes.Current", this.allNgh[k]+"_"+newIdx[k]); cmd.exec();
					System.out.println("UpdateNeighborhoodCommand.exec(): "+this.allNgh[k]+"_"+newIdx[k]+" FOUND, replacing MacroCommand...");
					//cmd=IoC.Resolve("IoC.Register", "MacroCommand", (IFunction)((arr) -> (macroCmd))); cmd.exec();
					this.reg(macroCmd);
				} catch (Exception e) {
					System.out.println("UpdateNeighborhoodCommand.exec(): exception "+e);
					cmd=new ExecNeighMacroCommand(this.allNgh[k]+"_"+newIdx[k], macroCmd);
					q.add(cmd);
				}
				
				((Set<Ship>)allNgh[k].neighborhoods[newIdx[k]]).add(sh);
				str=", neigh of "+sh+" has changed to "+this.allNgh[k]+"_"+newIdx[k]+", "+this.macroSize+" CheckCollisionCommand added";
			}
			System.out.println("Ship "+sh+" is in "+this.allNgh[k]+"_"+oldIdx[k]+", "+sh.getProperty("position")+str);
		}
	}
	
	void reg(ICommand macroCmd) throws Exception {
		ICommand cmd=IoC.Resolve("IoC.Register", "MacroCommand", (IFunction)((arr) -> (macroCmd))); cmd.exec();
	}
	
	ICommand[] prepareCmds4MacroByNgh(Neighborhoods allNgh, int nghIdx) throws Exception {
		CheckCollisionCommand hdl=null,hdlNext=null;
		
		
		for(Ship shFromNgh: (Set<Ship>)allNgh.neighborhoods[nghIdx]) {
			hdl=new CheckCollisionCommand(this.sh, shFromNgh, hdlNext);
			hdlNext=hdl;
		}
		this.macroSize=((Set<Ship>)allNgh.neighborhoods[nghIdx]).size();
		ICommand[] cmdArr=new ICommand[Math.min(this.macroSize, 1)];
		if(cmdArr.length>0)cmdArr[0]=hdl;
		return(cmdArr);
	}
}


