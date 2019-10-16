package test;

import java.util.Scanner;

public class Tester2 {
static int foo() {
Scanner sc = new Scanner(System.in);
int z;
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
System.out.println(z);
return c;
}
public static void main(String[] args) {
int a = foo();
}
}