package icommand;

import java.util.Dictionary;
import icommand.IoC;

public class RegisterInScopeCommand implements ICommand {
	String cname;
	IFunction f;
	
	public RegisterInScopeCommand(String cname, IFunction f) throws Exception {
		this.cname=cname;
		this.f=f;
	}

	public void exec() throws Exception {
		Dictionary<String, IFunction> scope;
		
		System.out.println("RegisterInScopeCommand.exec(): "+cname);
		scope=IoC.sc.get(); if(scope==null)throw new Exception("No scope defined");
		scope.put((String)cname, (IFunction)f);
	}
}