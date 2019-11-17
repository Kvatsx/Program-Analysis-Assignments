package InterTaintVariableAnalysis;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import soot.Scene;
import soot.Pack;
import soot.PackManager;
import soot.SootClass;
import soot.SootResolver;
import soot.Transform;
import soot.options.Options;
import soot.Body;
import soot.BodyTransformer;
import soot.SceneTransformer;
import soot.SootMethod;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

public class Driver{
	static HashMap<String, Info> Summary = new HashMap<String, Info>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Demo");
		if(args.length==0)
		{
			System.err.println("Usage: java Driver [options] classname");
			System.exit(0);
		}
		final String classname=args[0];
		Options.v().set_keep_line_number(true);
		Options.v().setPhaseOption("jb", "use-original-names:true");
		
		List<String> argsList = new ArrayList<String>(Arrays.asList(args));
		argsList.addAll(Arrays.asList(new String[]{"-w","-no-bodies-for-excluded", "-main-class", classname, classname}));

		PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTransform", new SceneTransformer(){
			@Override
	 		protected void internalTransform(String phaseName, Map options) {
				CHATransformer.v().transform();
	 	        SootClass a = Scene.v().getSootClass(classname);
	 	        SootMethod src = Scene.v().getMainClass().getMethodByName("main");    //root of cg
	 			CallGraph cg = Scene.v().getCallGraph();   //call graph generated
	 			Stack<SootMethod> stack = new GetCallGraph().obtaincallgraph(cg,src);	 //call graph sorted topologically
	 			Iterator<SootMethod> value = stack.iterator(); 
	 			System.out.println("Stack Size: "+stack.size());
	 			while (value.hasNext()) { 
	 				System.out.println("Node: " + value.next().getName().toString()); 
	 			} 
	 			buildsummary(stack);
	 		}
		}));
		System.out.println("Hello");
		System.out.println("Class Name: " + classname);
		PackManager.v().getPack("jtp").add(new Transform("jtp.instrumenter", new BodyTransformer()
 	    {
			protected void internalTransform(Body body, String phase, Map options)
			{		
				if(body.getMethod().getName().equals("main"))
				{
					SootMethod sm=body.getMethod();
					IntraTaintAnalysis analysis = new IntraTaintAnalysis((new ExceptionalUnitGraph(body)), Summary,sm);						
				}			
			}
	    }));
		args = argsList.toArray(new String[0]);
		Options.v().set_output_format(Options.output_format_jimple);
		soot.Main.main(args);
	}
	
	public static void buildsummary(Stack<SootMethod> stack) {    	   	
	    while(!stack.isEmpty()) 
	    {
	    	SootMethod sm = stack.pop();
	    	IntraTaintAnalysis analysis = new IntraTaintAnalysis(new ExceptionalUnitGraph(sm.getActiveBody()), Summary,sm);
	    	Summary.put(sm.getName(), analysis.info);
	    	System.out.println(analysis.info);
	    }
	}

}
