package test;
import test.Parent;

public class Tester extends Parent {
	int c = 0;
	Tester() {
		super();
	}
	
	static int status = 1;
	static Parent p = new Parent ();
	
	static void print () {
		for (int i=0; i<5; i++)
			System.out.println("Print "+i);
	}
	
	static int show(int a, float f) {
		int c;
		if (a > 1)
			c = 2 * a;
		else
			c = a * -1;
		System.out.println("f:" + f);
		return c;
	}
	
	public static void main(String [] args) {
		p.display(1, 11);
		p.print("child");
		int b = show (1, 1);
		print();
		System.out.println("Returned" + b);
	}
}

//import test.Numbers;
//public class Tester extends Numbers {
//	/**
//	* @param args
//	*/
//	int num;
//	Tester()
//	{
//		super();
//	}
//	static void show(int a)
//	{
//		Numbers n=new Numbers(a);
//		n.print();
//	}
//	public static void main(String [] args) {
//	
//		int i = 0;
//		
//		while (i < 5)
//		{
//			show(i);
//			i++;
//		}
//	}
//}