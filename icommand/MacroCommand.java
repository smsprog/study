package icommand;

import java.util.Vector;

public class MacroCommand implements ICommand {
	protected Vector<ICommand> cmds;

	public MacroCommand(ICommand... commands) throws Exception {
		cmds=new Vector<ICommand>();
		for (ICommand c: commands)
			cmds.add(c);
	}

	public void exec() throws CommandException {
		for(int i = 0; i < cmds.size(); i++)
			try {
				System.out.println("CMD(macro)="+cmds.get(i).getClass().getSimpleName());
				cmds.get(i).exec();
			} catch (Exception e) {
				throw new CommandException();
			}
	}
}
