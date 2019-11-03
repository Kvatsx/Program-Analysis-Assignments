package InterTaintVariableAnalysis;

import java.util.Iterator;
import java.util.Stack;

import soot.MethodOrMethodContext;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;

public class GetCallGraph
{	
	Stack<SootMethod> callstack=new Stack<SootMethod>();	

	public Stack<SootMethod> obtaincallgraph(CallGraph cg, SootMethod src)
	{
		//System.out.println("src: "+src);
		 Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
		 
	 	if(!targets.hasNext())
			 return callstack;
	       while (targets.hasNext()) {
	    	  
	           SootMethod tgt = (SootMethod)targets.next();
	                   
	           if(!tgt.isJavaLibraryMethod())
	           {
//	        	  System.out.println(src + " may call " + tgt.getName());
	        	  if(callstack.contains(tgt))
	        		   callstack.remove(tgt);

	        	  
	        	  callstack.add(tgt);
	        	  obtaincallgraph(cg,tgt);
	        	  }
	           }
		  
	       return callstack;
		 
	}
}

