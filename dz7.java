import java.util.Vector;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

import uobject.*;
import icommand.*;
import irotatable.*;
import imovable.*;
import iburnable.*;

class m {
	static ICommand cmd;
	
    public static void main(String[] args) throws Exception {
		Code c1=new Code();
		cmd=new ExecQueueCommand(c1);
		cmd.exec();
		//for (Thread t : ThreadUtils.getAllThreads()) {
		//	System.out.println(t.getName() + ", " + t.isDaemon());
		//}
		Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t+": Is Alive " + t.isAlive()));
		
		
		//cmd=IoC.Resolve("IoC.Register", "X1", (IFunction)((x) -> (int)x[0]+1) ); cmd.exec(); if(cmd!=null)System.out.println("Test21 passed\n"); else System.out.println("Test21 FAILED\n");
		//cmd=IoC.Resolve("X1", 1 ); cmd.exec(); if(cmd!=null)System.out.println("Test23 passed\n"); else System.out.println("Test23 FAILED\n");

		
		//cmd=IoC.Resolve("Scopes.Current", "scope2"); cmd.exec(); if(cmd!=null)System.out.println("Test27 passed\n"); else System.out.println("Test27 FAILED\n");
		//cmd=IoC.Resolve("IoC.Register", "X1", (IFunction)((x) -> (int)x[0]+2) ); cmd.exec(); if(cmd!=null)System.out.println("Test22 passed\n"); else System.out.println("Test22 FAILED\n");
		//cmd=IoC.Resolve("X1", 1 ); cmd.exec(); if(cmd!=null)System.out.println("Test24 passed\n"); else System.out.println("Test24 FAILED\n");
		
		
		/*
		//e=test(0.0, 0.0, 90.0, 8, 1, 1, 0.0);	 					if(e==null)System.out.println("Test0 passed\n"); else System.out.println("Test0 FAILED, "+e+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0, 7.61577);	if(e==null && Math.abs((double)pos.elementAt(0)-5)<eps && Math.abs((double)pos.elementAt(1)-8)<eps && Math.abs(fc)<eps)System.out.println("===Test1 passed\n"); else System.out.println("===Test1 FAILED, "+e+" "+fc+"\n"); 
		e=test(12.0, 5.0, 10.0, (int)Math.pow(2, 19), -7, 3, 0.0, 1e-7);	if(e!=null && e.getClass().getSimpleName().equals("CommandException"))System.out.println("===Test1.1 passed: "+e+"\n"); else System.out.println("===Test1.1 FAILED, "+e+"\n"); 
		//e=test(Double.NaN, Double.NaN, 90.0, 8, 1, 1, 0.0);		if(e!=null)System.out.println("Test3 passed: "+e+"\n"); else System.out.println("Test3 FAILED, "+e+"\n"); 
		//e=test(0.0, 0.0, 90.0, 8, Double.NaN, Double.NaN, 0.0);	if(e!=null)System.out.println("Test4 passed: "+e+"\n"); else System.out.println("Test4 FAILED, "+e+"\n"); 
		e=test(0.0, 0.0, 0.0, 360, 1, 0, 90.0, 10.0);	if(e==null && Math.abs(alpha-90)<eps && Math.abs(v-1)<eps)System.out.println("===Test8.3 passed\n"); else System.out.println("===Test8.3 FAILED, "+e+"\n");
		e=test(0.0, 0.0, 0.0, 360, 3, 4, 0.0, 10.0);	if(e==null && Math.abs(alpha-0)<eps && Math.abs(v-5)<eps)System.out.println("===Test8.4 passed\n"); else System.out.println("===Test8.4 FAILED, "+e+"\n");
		*/
    }

	
}


