package test;

import java.util.Scanner;

//import java.util.Scanner;
//
//public class Tester1 {
//	static int bar(int x) {
//		x = x * 5;
//		return x;
//	}
//	static int foo(int x, int y) {
//		int z = 5;
//		int res;
//		int p = bar(z);
//		int q = bar(x);
//		res = p * q;
//		return res;
//	}
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		int n = sc.nextInt();
//		int k = sc.nextInt();
//		int a,b,c,d,e;
//		a = foo(n, k);
//		b = foo(n,1);
//		c = foo(1,k);
//		d = foo(1,10);
//		e = bar(n);
//		System.out.println(a+" "+b+" "+c+" "+d+" "+e);
//	}
//}

//(a, 28) : Tainted
//(b, 29) : Tainted
//(c, 30) : Untainted
//(d, 30) : Untainted
//(e, 31) : Tainted



public class Tester1 {

    static int bar(int y,int t) {
    int x=5,z;
    z=x*y;
    return z; //z depends on y 
    }
    static int foo() {

        Scanner sc = new Scanner(System.in);
        int z,t;
        int a = sc.nextInt();
        int b = sc.nextInt();
        if (a > b) {
            a = 10;
        } else {
            b = 11;
        }
        int c = 10;
        while(a>5)
        {
            a--;
            c = 10 + c;
        }
        z = a + b + c;
        if (a>b)
        return z; // depends on a and b
        else
        return c; //untainted
    }
    static int sum() {
        int z,t=5;    
        Scanner sc = new Scanner(System.in);
        int a = 10;
        int b = sc.nextInt();
        if (a > b)
            t = t+1;
        z = a + t;
        return z; // z is untainted
        
    }
    static int sum2(int a, int b) {
        int c;
        c = a+b;
        return c; // c depends on a and b
    }    

    public static void main(String[] args) {
        int t = 5,y = 10;
        Scanner sc = new Scanner(System.in);
        int inp = sc.nextInt();
        int a = foo();   //tainted
        int b = bar(inp,t); // tainted
        int c = sum();    //untainted
        int d = sum2(t,y); // untainted
        int e = bar(t,inp); //untainted
        System.out.println(a+" "+b+ " "+ c+" "+d+" "+e);
    }

}