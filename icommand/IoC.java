package icommand;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;

public class IoC {
	static ThreadLocal< Dictionary<String, IFunction> > sc=new ThreadLocal<>();
	static Dictionary< String, Dictionary<String, IFunction> > allScopes=new Hashtable<>();

	public static void ShowScope() {
		Dictionary<String, IFunction> scope=sc.get();
		
		System.out.println("IoC.ShowScope()");
		Enumeration<String> k = scope.keys();
        while (k.hasMoreElements()) {
            String key = k.nextElement();
            System.out.println("Scope: key="+key+", value="+scope.get(key));
        }
	}

	public static <T> T Resolve(String cname, Object... objects) throws Exception {
		Class[] cArg=new Class[objects.length];
		IFunction f;
		ICommand cmd=null;
		Dictionary<String, IFunction> scope=new Hashtable<>();
		
		if (cname=="IoC.Register") {
			for (Object p: objects) {
				System.out.println("IoC.Register: "+p+" class="+p.getClass());
			}
			return((T)new RegisterInScopeCommand((String)objects[0], (IFunction)objects[1]));
		} else if(cname.equals("Scopes.New")) {
			System.out.println("IoC.Register: "+cname+" "+objects[0]);
			return((T)new NewScopeCommand((String)objects[0]) );
		} else if(cname.equals("Scopes.Current")) {
			System.out.println("IoC.Register: "+cname+" "+objects[0]);
			return((T)new SwitchScopeCommand((String)objects[0]) );
		} else { // call Function
			scope=sc.get();
			if(scope!=null) {
				f=scope.get(cname);
				if(f!=null)
					return((T)f.run(objects));		
			}
			System.out.println("IoC.Resolve: no such function "+cname);
		}
		return((T)cmd);
	}
}