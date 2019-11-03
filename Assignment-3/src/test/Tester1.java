package test;

import java.util.Scanner;

public class Tester1 {
	static int bar(int x) {
		x = x * 5;
		return x;
	}
	static int foo(int x, int y) {
		int z = 5;
		int res;
		int p = bar(z);
		int q = bar(x);
		res = p * q;
		return res;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		int a,b,c,d,e;
		a = foo(n, k);
		b = foo(n,1);
		c = foo(1,k);
		d = foo(1,10);
		e = bar(n);
		System.out.println(a+" "+b+" "+c+" "+d+" "+e);
	}
}

//(a, 28) : Tainted
//(b, 29) : Tainted
//(c, 30) : Untainted
//(d, 30) : Untainted
//(e, 31) : Tainted