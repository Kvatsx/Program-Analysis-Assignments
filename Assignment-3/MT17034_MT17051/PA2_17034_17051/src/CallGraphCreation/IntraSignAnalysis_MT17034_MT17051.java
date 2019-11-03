package CallGraphCreation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import polyglot.parse.VarDeclarator;
import soot.Body;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.JastAddJ.AddExpr;
import soot.coffi.parameter_annotation;
import soot.jimple.AssignStmt;
import soot.jimple.BinopExpr;
import soot.jimple.Expr;
import soot.jimple.IdentityRef;
import soot.jimple.IdentityStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.NegExpr;
import soot.jimple.ReturnStmt;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.tagkit.Tag;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.ForwardFlowAnalysis;

public class IntraSignAnalysis_MT17034_MT17051 extends ForwardFlowAnalysis {

	//class members
	Set<String> subSet=new HashSet<>();
	//HashMap<String,Set<String>> hashStore =new HashMap<>(); 
	FlowSet inval, outval;
	Body b;
	SootMethod currSootmethod;
	String analysisOfSign;
	Boolean print;
	
	
	//constructor
	public IntraSignAnalysis_MT17034_MT17051(UnitGraph graph, String sign, SootMethod method) 
	{
		super(graph);	
		b = graph.getBody();
		currSootmethod=method;
		analysisOfSign=sign;
	//	print=printoption;
		doAnalysis();
	}
	public IntraSignAnalysis_MT17034_MT17051(UnitGraph graph) 
	{
		super(graph);
		doAnalysis();
	}

	@Override 
	protected void flowThrough(Object in, Object unit, Object out) {
		//declarations and castings
		inval = (FlowSet) in;
		outval = (FlowSet) out;
		Stmt u = (Stmt) unit;
		
		if(u instanceof IdentityStmt)
		{
			
			if(!currSootmethod.getName().equals("main"))
			{
				
				if(u.toString().contains(" := @parameter"))
				{				
					inval.add(b.getParameterLocal(0).toString()+"="+analysisOfSign);		
				}
			}
			else
			{
				if(u.toString().contains(" := @parameter"))
				{			
					int numparams=b.getMethod().getParameterCount();

					for(int i=0;i<=numparams-1;i++)
					{
						
					}	
				}
			}
		}		
		inval.copy(outval);
		
		//KILL 				
		if(u instanceof AssignStmt)
		{
			
		}

		if(u instanceof ReturnStmt)
		{
			
		}
		//For ASSIGNMENT STATEMENTS ONLY
		if (u instanceof AssignStmt)
		{
			
		}
	}

	@Override
	protected void copy(Object source, Object dest) {
		FlowSet srcSet = (FlowSet) source;
		FlowSet destSet = (FlowSet) dest;
		srcSet.copy(destSet);

	}

	@Override
	protected Object entryInitialFlow() 
	{

		ArraySparseSet as=new ArraySparseSet();
		System.out.println("Function: ' " + currSootmethod.getName().toString() +"'s ' summary for '"+analysisOfSign +"' sign" );		
		System.out.println();
		return as;
	}

	@Override
	protected Object newInitialFlow() {
		return new ArraySparseSet();
	}

	@Override
	protected void merge(Object in1, Object in2, Object out) {

	}
	

}
