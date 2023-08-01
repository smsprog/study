package icommand;

import java.util.Dictionary;

class SwitchScopeCommand implements ICommand {
	String scopeName;
	
	public SwitchScopeCommand(String scopeName) throws Exception {
		this.scopeName=scopeName;
	}

	public void exec() throws Exception {
		Dictionary<String, IFunction> newScope;
		
		System.out.println("SwitchScopeCommand.exec(): "+scopeName);
		newScope=IoC.allScopes.get(scopeName);
		if(newScope==null)throw new Exception("SwitchScopeCommand.exec(): no such scope "+scopeName);
		IoC.sc.set(newScope);
	}
}
