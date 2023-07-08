package icommand;

public class RetryCommand implements ICommand {
	ICommand cmd;

	public RetryCommand(ICommand cmd) throws Exception {
		this.cmd=cmd;
	}

	public void exec() throws Exception {
		cmd.exec();
	}
}