package ReachingDefinition;

import java.util.*;
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
import soot.options.Options;
import soot.tagkit.Tag;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length==0)
		{
			System.err.println("Usage: java Driver [options] classname");
			System.exit(0);
		}
		SootClass abc=Scene.v().loadClassAndSupport("test.Tester");
		Options.v().setPhaseOption("jb", "use-original-names:true");
//		Pack jtp=PackManager.v().getPack("jtp");
//		jtp.add(new Transform("jtp.instrumenter", new ReachingDefinitionWrapper()));
//		SootResolver.v().resolveClass("java.lang.CloneNotSupportedException", SootClass.SIGNATURES);
//		Options.v().set_output_format(Options.output_format_jimple);
//		soot.Main.main(args);
//		
		
		// Name of the Class
		System.out.println("Class Name: "+abc.getName());
		
		// Printing Methods
		System.out.print("Methods: ");
		for (SootMethod e : abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor() || e.isMain()) {
				continue;
			}
			System.out.print(e.getName().toString()+", ");
		}
		System.out.println();
		
		// Print Data Members
		System.out.print("Data Members: ");
		for (SootField var : abc.getFields()) {
			System.out.print(var.getSubSignature() + ", ");
		}
		System.out.println();
		
		// Function Arguments
		System.out.println("Function Arguments: ");
		for (SootMethod e : abc.getMethods()) {
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
		ArrayList<List<Unit>> units = new ArrayList<List<Unit>>();
		// Number of loops
		System.out.println("Number of Loops: ");
		for (SootMethod e : abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			int count = 0;
			units.add((List<Unit>) e.getSource().getBody(e, "jb").getUnits());
			for (Unit o : e.getSource().getBody(e, "jb").getUnits()) {
				boolean flag = o.toString().contains("goto") && o.toString().contains("branch");
				if (flag) {
					count++;
				}
//				System.out.println(o.toString());
			}
			System.out.println(e.getName().toString()+": "+count);			
		}
		
		// Return Statement and Type
		System.out.println("Return Statements: ");
		for (SootMethod e : abc.getMethods()) {
			String Name = e.getName();
			if (Name.compareTo("<clinit>") == 0 || e.isConstructor()) {
				continue;
			}
			System.out.println(Name + ": "+e.getSource().getBody(e, "jb").getLocalCount() +e.getReturnType()); 
		}
		
	}
}
