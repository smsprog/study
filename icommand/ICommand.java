package icommand;

public interface ICommand {
	void exec() throws Exception;
}

class CommandException extends Exception {
	void CommandException() {
		
	}
}