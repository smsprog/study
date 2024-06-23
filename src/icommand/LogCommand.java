package icommand;

public class LogCommand implements ICommand {
	Exception e;

	public LogCommand(Exception e) throws Exception {
		this.e=e;
	}

	public void exec() throws Exception {
		System.out.println("LOGGER: "+e);
	}
}