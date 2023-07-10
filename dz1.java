
class Eq {
	private double d;
	private static double eps=0.001;
	
    public double[] solve(double a, double b, double c) throws Exception { 		// ax^2+bx+c=0
		if(Math.abs(a)<eps || Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c)) throw new Exception();
		d=b*b-4*a*c;
		if(d<-eps) return(new double[0]);
		if(Math.abs(d)<eps)d=0;
		a*=2;
		d=Math.sqrt(d)/a;
		b=b/a;
		return(new double[]{-b+d,-b-d});
    } 
	public double getD() {
		return(d);
	}
}

class m {
	private static double[] xx;
	
    public static void main(String[] args) {
		Exception e;
		
		e=test(1,0,1); 			if(xx.length==0)System.out.println("===Test3 passed"); else System.out.println("===Test3 FAILED");
		e=test(1,0,-1); 		if(xx[0]==1 && xx[1]==-1)System.out.println("===Test5 passed"); else System.out.println("===Test5 FAILED");
		e=test(1,2,1); 			if(xx[0]==-1 && xx[1]==-1)System.out.println("===Test7 passed"); else System.out.println("===Test7 FAILED");
		e=test(0,2,1);			if(e!=null)System.out.println("===Test9 passed"); else System.out.println("===Test9 FAILED");
		e=test(1-1e-8,2,1); 	if(xx[0]==xx[1])System.out.println("===Test7.1 passed"); else System.out.println("===Test7.1 FAILED");
		e=test(1+1e-8,2,1); 	if(xx[0]==xx[1])System.out.println("===Test7.2 passed"); else System.out.println("===Test7.2 FAILED");
		e=test(1e-8,2,1); 		if(e!=null)System.out.println("===Test9.1 passed"); else System.out.println("===Test9.1 FAILED");
		e=test(1,Double.NaN,Double.NaN); if(e!=null)System.out.println("===Test10 passed"); else System.out.println("===Test10 FAILED");
    }
	
	public static Exception test(double a, double b, double c) {
		try {
			Eq eq=new Eq();
			xx=eq.solve(a,b,c);
			if(xx.length>0)
				System.out.println("Roots: "+xx[0]+" "+xx[1]+" D="+eq.getD());
			else
				System.out.println("No roots."+" D="+eq.getD());
		} catch (Exception e) {
			System.out.println("Exception a=0");
			return(e);
		}
		return(null);
	}
}
