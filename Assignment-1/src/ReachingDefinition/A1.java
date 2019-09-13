package ReachingDefinition;

import java.util.*;

import soot.Local;
import soot.Pack;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.SootResolver;
import soot.Transform;
import soot.Unit;
import soot.UnitBox;
import soot.ValueBox;
import soot.options.Options;
import soot.tagkit.Tag;
import soot.jimple.Stmt;
import soot.jimple.AssignStmt;
import soot.jimple.toolkits.annotation.logic.LoopFinder;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.Body;

public class A1 {
	public String className;
	public SootClass abc;
	public HashMap<String, ArrayList<Unit>> Arr;
	public HashMap<String, Body> Bodys;
	
	public A1(String className) {
		this.className = className;
		this.abc = Scene.v().loadClassAndSupport(this.className);
		Options.v().setPhaseOption("jb", "use-original-names:true");
		this.Arr = new HashMap<String, ArrayList<Unit>>();
		this.Bodys = new HashMap<String, Body>();
		
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			Body b = e.getSource().getBody(e, "jb");
			Bodys.put(Name, b);
			ArrayList<Unit> Brr = new ArrayList<Unit>();
			for (Unit o : b.getUnits()) {
				Brr.add(o);
			}
			Arr.put(Name, Brr);
		}
	}
	
	public void printClassName() {
		System.out.println("Class Name: "+this.abc.getName());
//		System.out.println("Super Class: "+this.abc.getSuperclass());
	}
	
	public void printMethods() {
		System.out.print("Methods: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor() || e.isMain()) {
				continue;
			}
			System.out.print(e.getName().toString()+", ");
		}
		System.out.println();
	}
	
	public void printDataMembers() {
		System.out.print("Data Members: ");
		for (SootField var : this.abc.getFields()) {
			System.out.print(var.getSubSignature() + ", ");
		}
		System.out.println();
	}
	
	public void printFunctionArguments() {
		System.out.println("Function Arguments: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor() || e.isMain()) {
				continue;
			}
			System.out.print(e.getName().toString()+": ");
			for (Object type : e.getParameterTypes()) {
				System.out.print(type+", ");
			}
			System.out.println();
		}
	}
	
	public void printLoopCount() {
		System.out.println("Number of Loops: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			int count = 0;
			Body body = Bodys.get(Name);
			LoopFinder lp = new LoopFinder();
			lp.transform(body, "jb");
			count = lp.loops().size();
//			ArrayList<Unit> Brr = Arr.get(Name);
//			for (Unit o : Brr) {
//				boolean flag = o.toString().contains("goto") && o.toString().contains("branch");
//				if (flag) {
//					count++;
//				}
//				System.out.println(o.toString());
//			}
			
			System.out.println(e.getName().toString()+": "+count);
		}
	}
	
	public void printReturnStatement() {
		System.out.println("Return Statement: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			ArrayList<Unit> Brr = Arr.get(Name);
			String[] Crr;
			System.out.print(e.getName().toString()+": ");			
			for (Unit o : Brr) {
				boolean flag = o.toString().contains("return");
				if (flag) {
					 Crr = o.toString().split(" ");
					 if (Crr.length > 1) {
						 System.out.print("return ");
						 for (int i=1; i<Crr.length; i++) {
							 System.out.print(Crr[i]+"");
						 }
						 System.out.print(", ");
					 }
				}
			}
			System.out.println(e.getReturnType());
		}
	}
	
	public void printMethodsCallWithArguments() {
		System.out.println("Method Calls With Arguments: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			ArrayList<Unit> Brr = Arr.get(Name);
			System.out.print(e.getName().toString()+": ");
			for (Unit o : Brr) {
				String[] Crr = o.toString().split(" ");
				if(Crr[0].compareTo("virtualinvoke") == 0) {
					int index = o.toString().indexOf("<");
					for (int i=index; i<o.toString().length(); i++) {
						System.out.print(o.toString().charAt(i));
					}
					System.out.println();
//					System.out.println(o.toString());
				}
			}
			System.out.println();
		}
	}
	
	public void printLocalVariables() {
		System.out.println("Local Variables: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
//			System.out.println(this.Bodys.get(Name).getLocals());
//			System.out.println(e.retrieveActiveBody().getLocals().toString());
			ArrayList<Unit> Brr = Arr.get(Name);
			Set<String> Crr = new HashSet<String>();
			for (Unit o : Brr) {
				Stmt temp = (Stmt) o;
				Iterator<ValueBox> defIt = temp.getDefBoxes().iterator();
				while(defIt.hasNext())
				{
					ValueBox defBox = (ValueBox)defIt.next();
					if (defBox.getValue() instanceof Local) {
						String value = defBox.getValue().toString();
						if (!value.contains("$")) {
							Crr.add(value);
						}
					}
				}
			}
			System.out.print(e.getName().toString()+": ");
			for (String s : Crr) {
				System.out.print(s + ", ");
			}
			System.out.println();
		}
	}
	
	public void printUserDefinedMethods() {
		System.out.println("User Defined Method Call: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			System.out.print(e.getName().toString()+": ");
			ArrayList<Unit> Brr = Arr.get(Name);
			for (Unit o : Brr) {
				if (o.toString().contains("<"+this.abc.getPackageName()) && o.toString().contains("invoke")) {
					int index = o.toString().indexOf("<"+this.abc.getPackageName());
					for (int i=index; i<o.toString().length(); i++) {
						System.out.print(o.toString().charAt(i));
					}
					System.out.println();
				}
//				System.out.println(o.toString());
			}
			System.out.println();
		}
	}
	
	public void printLoopPossibilty() {
		System.out.println("Possible Loop: ");
		for (SootMethod e : this.abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			Body body = Bodys.get(Name);
			LoopFinder lp = new LoopFinder();
			lp.transform(body, "jb");
			System.out.print(Name+": ");
			for (Loop loopy : lp.loops()) {
				
//				loopy.getBackJumpStmt();
//				System.out.println(loopy.getBackJumpStmt().toString());
				List<Stmt> st = loopy.getLoopStatements();
				if (st.get(0).toString().contains("if") && st.get(0).toString().contains("goto")) {
					System.out.println("Possibly for loop");
				}
				else if (st.get(st.size()-1).toString().contains("if") && st.get(st.size()-1).toString().contains("goto")) {
					System.out.println("Possibly Do while loop");
				}
				else {
					System.out.println("Possibly while Loop");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length==0)
		{
			System.err.println("Usage: java Driver [options] classname");
			System.exit(0);
		}
		for (int i=0; i<args.length; i++) {
			A1 a1 = new A1(args[i]);
			a1.printClassName();
			a1.printMethods();
			a1.printDataMembers();
			a1.printFunctionArguments();
			a1.printLoopCount();
			a1.printReturnStatement();
			a1.printMethodsCallWithArguments();
			a1.printLocalVariables();
			a1.printUserDefinedMethods();
			a1.printLoopPossibilty();
			System.out.println();
			System.out.println();
		}
	}

}
