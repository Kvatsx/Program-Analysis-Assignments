package test;

import java.util.Scanner;

public class Tester {
	static int foo(int z) {
		int a;
		int b;
		//{z, a, b}
		int c = 11;
		//{z, a, b, c}
		int d = 5;
		//{z, a, b, c, d}
		System.out.println(z);
		//{z, a, b, c, d}
		a = z * d;
		//{z, a, b, c}
		b = z + c;
		//{z, a, b}
		z = a + b;
		//{z}
		return z;
		//{ }
	}
	public static void main(String [] args) {
		//{val}
		int val = foo(5);
		//{val}
		System.out.println(val);
		//{ }
	}
	
	static int zoo()
	{
		Scanner sc = new Scanner(System.in);
		int z;
		int a = sc.nextInt();
		int b = sc.nextInt();
		int c = 10;
		z = a+b+c;
		return z;
	}
	
	int bar1(int x) {
		int product, a;
		a = 10;
		product = x * a;
		return product;
	}
}