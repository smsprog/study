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
import tank_operations_imovable.*;

import AutoGenerated.*;

class dz6 {
	static double eps=0.0001;
	static Vector pos;
	static double alpha, fc, v;
	static ICommand cmd;
	
    public static void main(String[] args) throws Exception {
		
		Ship ship1=new Ship(1, 1, 0.0, 360, 0.0);
		cmd=IoC.Resolve("Scopes.New", "scope1"); cmd.exec(); if(cmd!=null)System.out.println("Test15 passed\n"); else System.out.println("Test15 FAILED\n");
		cmd=IoC.Resolve("Scopes.Current", "scope1"); cmd.exec(); if(cmd!=null)System.out.println("Test17 passed\n"); else System.out.println("Test17 FAILED\n");
		
		AutoGenerated_new.IoCRegister();
		
		IMovable ad=IoC.Resolve("Adapter", IMovable.class.getSimpleName(), ship1);
		ad.setVelocity(2, 3);
    }
}


