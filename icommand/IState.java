package icommand;

public interface IState {
	IState handle() throws Exception;
	void setStateTo(IState to);
}