import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;  

public class Testcase {    
	
	public static void main(String[] args) {
		Collection<String> c = new ArrayList<String>();
		c.add("lalala");
		c.add("lalala");
		removeLalala(c);
		System.err.println(c);
	}    
	
	private static void removeLalala(Collection<String> c) {
		//for(Iterator<String> i = c.iterator(); i.hasNext();) {
			Iterator<String> i = c.iterator();
			String s =  i.next();
			String s2 =  i.next();
			if(s.equals("lalala")) {
				c.remove(s);
			}
		//}
	}
}  