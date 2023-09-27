package icommand;

import java.util.Vector;

public class ExecNeighMacroCommand implements ICommand {
	String neigh;

	public ExecNeighMacroCommand(String neigh, ICommand macroCmd) throws Exception {
		this.neigh=neigh;
		ICommand cmd;
		
		cmd=IoC.Resolve("Scopes.New", this.neigh); cmd.exec();
		cmd=IoC.Resolve("Scopes.Current", this.neigh); cmd.exec();
		cmd=IoC.Resolve("IoC.Register", "MacroCommand", (IFunction)((arr) -> (macroCmd)) ); cmd.exec();
	}

	public void exec() throws Exception {
		ICommand cmd;
		
		cmd=IoC.Resolve("Scopes.Current", this.neigh); cmd.exec();
		cmd=IoC.Resolve("MacroCommand"); cmd.exec();
	}
}
