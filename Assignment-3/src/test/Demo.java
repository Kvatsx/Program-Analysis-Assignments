package test;

import java.util.Scanner;

public class Demo {

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