package test;

import java.util.Scanner;

public class Tester2 {
	
	static int bar1(int x) {
		x = x * 5;
		return x;
	}
	
	static int bar2(int x) {
		Scanner sc1 = new Scanner(System.in);
		int y = sc1.nextInt();
		x = x * 5;
		return y;
	}
	
	static int foo(int x, int y) {
		int z = 5;
		int res, p;
		if (x > y) {
			p = bar1(z);
		} 
		else {
			p = bar2(5);
		}
		res = p;
		return res;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		int a, b, c, d, e;
		a = foo(n, k); //a is tainted
		b = foo(n, 1); //b is tainted
		c = foo(1, k); //c is tainted
		d = bar2(1); // d is tainted
		e = bar1(1); // e is untainted
		System.out.println(a + " " + b + " " + c + " " + d + " " + e);
	}
}