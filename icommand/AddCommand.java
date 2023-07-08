package icommand;

import java.util.Queue;

public class AddCommand implements ICommand {
	ICommand cmd;
	Queue<ICommand> q;

	public AddCommand(Queue<ICommand> q, ICommand cmd) throws Exception {
		this.q=q;
		this.cmd=cmd;
	}

	public void exec() throws Exception {
		q.add(cmd);
	}
}