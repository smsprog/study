package icommand;

import java.util.Queue;

public class ExceptionHandler {
	public static ICommand Handle(Exception e, ICommand cmd, Queue<ICommand> q) throws Exception {
						//q.add(new RepeatCommand(q, cmd));
		String eName=e.getClass().getSimpleName();
		String cmdName=cmd.getClass().getSimpleName();
		
		System.out.println("Handle EXCEPTION: "+eName+" "+cmdName);
		if(cmdName.equals("MoveCommand")) {
			System.out.println("+command in queue: "+cmdName);
			return(new AddCommand(q, new RetryCommand(cmd)));
//		else if(cmdName.equals("RetryCommand"))
//			return(new AddCommand(q, new SecondRetryCommand(cmd)));
		}else if(cmdName.equals("CheckFuelCommand") || cmdName.equals("MacroCommand") || cmdName.equals("MoveAndBurnFuelCommand")) {
			throw e;
		} else {
			System.out.println("+command in queue: LogCommand");
			return(new AddCommand(q, new LogCommand(e)));
		}
	}
}