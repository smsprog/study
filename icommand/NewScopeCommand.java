package icommand;

import java.util.Dictionary;
import java.util.Hashtable;

class NewScopeCommand implements ICommand {
	String scopeName;
	
	public NewScopeCommand(String scopeName) throws Exception {
		this.scopeName=scopeName;
	}

	public void exec() throws Exception {
		Dictionary<String, IFunction> scope=new Hashtable<>();
		
		System.out.println("NewScopeCommand.exec(): "+scopeName);
		IoC.allScopes.put(scopeName, scope);
	}
}
