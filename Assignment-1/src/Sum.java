import java.io.*;
import java.util.*;

public class Sum { 
	  
    // Overloaded sum(). 
    // This sum takes two int parameters 
    public int Sum(int x, int y) 
    { 
        return (x + y); 
    } 
  
    // Overloaded sum(). 
    // This sum takes three int parameters 
    public int Sum(int x, int y, int z) 
    { 
        return (x + y + z); 
    } 
  
    // Overloaded sum(). 
    // This sum takes two double parameters 
    public double Sum(double x, double y) 
    { 
        return (x + y); 
    } 
    
    public String HelloWorld() {
    	String var = "HelloWorld!";
    	System.out.println(var);
    	return "Be Negative";
    }
  
    // Driver code 
    public static void main(String args[]) 
    { 
        Sum s = new Sum(); 
        System.out.println(s.Sum(10, 20)); 
        System.out.println(s.Sum(10, 20, 30)); 
        System.out.println(s.Sum(10.5, 20.5));
        System.out.println(s.HelloWorld());
    } 
} 