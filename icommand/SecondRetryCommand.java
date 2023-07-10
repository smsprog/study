package icommand;

public class SecondRetryCommand implements ICommand {
	ICommand cmd;

	public SecondRetryCommand(ICommand cmd) throws Exception {
		this.cmd=cmd;
	}

	public void exec() throws Exception {
		cmd.exec();
	}
}
