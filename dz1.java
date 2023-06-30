
class Eq {
	private double d;
	private static double eps=0.001;
	
    public double[] solve(double a, double b, double c) throws Exception { 		// ax^2+bx+c=0
		if(Math.abs(a)<eps) throw new Exception();
		d=b*b-4*a*c;
		if(d<0) return(new double[0]);
		if(Math.abs(d)<eps)d=0;
		a*=2;
		d=Math.sqrt(d)/a;
		b=b/a;
		return(new double[]{-b+d,-b-d});
    }  
}

class m {
	private static double[] xx;
	
    public static void main(String[] args) {
		Exception e;
		
		test(1,0,1); 	if(xx.length==0)System.out.println("Test3 passed"); else System.out.println("Test3 FAILED");
		test(1,0,-1); 	if(xx[0]==1 && xx[1]==-1)System.out.println("Test5 passed"); else System.out.println("Test5 FAILED");
		test(1,2,1); 	if(xx[0]==-1 && xx[1]==-1)System.out.println("Test7 passed"); else System.out.println("Test7 FAILED");
		e=test(0,2,1);	if(e!=null)System.out.println("Test9 passed"); else System.out.println("Test9 FAILED");
		test(1-1e-8,2,1); 	if(xx[0]==xx[1])System.out.println("Test7.1 passed"); else System.out.println("Test7.1 FAILED");
		test(1e-7,2,1);
    }
	
	public static Exception test(double a, double b, double c) {
		try {
			Eq eq=new Eq();
			xx=eq.solve(a,b,c);
			print_xx(); 
		} catch (Exception e) {
			System.out.println("Exception a=0");
			return(e);
		}
		return(null);
	}
	
	public static void print_xx () {
		if(xx.length>0)
			System.out.println(xx[0]+" "+xx[1]);
		else
			System.out.println("no roots");
	}
}
