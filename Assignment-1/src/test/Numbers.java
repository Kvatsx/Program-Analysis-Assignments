package test;

public class Numbers {
	static int x;
	Numbers () {}
	Numbers (int t)
	{
		x=t;
	}
	static void print()
	{
		while (x>0)
		{
			System.out.println(x);
			x--;
		}
		while (x>0)
		{
			System.out.println(x);
			x--;
		}
	}
	static void Testing() {
		do {
			System.out.println(x);
		} while(x>0);
	}
	static void forloop() {
		for (int i=0; i<10; i++) {
			System.out.println(i);
		}
	}
}