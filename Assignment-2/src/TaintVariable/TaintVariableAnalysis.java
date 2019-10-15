package TaintVariable;

import java.util.*;

import soot.Body;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.jimple.internal.ImmediateBox;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JReturnStmt;
import soot.jimple.internal.JimpleLocalBox;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.BackwardFlowAnalysis;

public class TaintVariableAnalysis extends BackwardFlowAnalysis<Unit, Set<String>> {
	
	Body b;
	Set<String> inval, outval, UserInput;
	boolean flag;
	public TaintVariableAnalysis(UnitGraph g)
	{
		super(g);
		UserInput = new HashSet<String>();
		b = g.getBody();
		flag = false;
		doAnalysis();
		Set<String> Result = new HashSet<String>();
		for (String e : UserInput) {
			for (String f : outval ) {
				if (e.equals(f)) {
					Result.add(f);
				}
			}
		}
		String Name = b.getMethod().getName();
		if (!Name.equals("<init>")) {
//			helperPrint(UserInput, "UserInput");
//			helperPrint(outval, "outval");
			System.out.println(Name + " " + Result.toString());
			System.out.println();			
		}
	}
	
	void helperPrint(Set<String> s, String Name) {
		System.out.print(Name + " {");
		for (String e : s) {
			if (e.charAt(0) != '$') {
				System.out.print(e + ", ");
			}
		}
		System.out.println("}");
	}
	
	@Override
	protected void flowThrough(Set<String> in, Unit unit, Set<String> out) {
		inval = in;
		outval = out;
		Stmt u = (Stmt)unit;
		outval.addAll(inval);
		
		//Gen operation
		
		// UserInput set containing parameter and scanner variables
		if (u.toString().contains("@parameter")) {
			UserInput.add(u.toString().split(" :=")[0]);
		}
		else if (u.toString().contains("java.util.Scanner")) {
			UserInput.add(u.toString().split(" =")[0]);
		}
		
		// Adding taint variable which affects Return Stmt
		if (u instanceof JReturnStmt) {
			for (ValueBox defBox: u.getUseBoxes()) {
				if (defBox.getValue() instanceof Local) {
					outval.add(defBox.getValue().toString());
				}
			}
		}

		// Adding taint variable which affects Print Stmt
		else if (u instanceof JInvokeStmt) {
			if (u.toString().contains("java.io.PrintStream")) {
				for (Value v: (List<Value>) u.getInvokeExpr().getArgs()) {
					if (v instanceof Local) {
						outval.add(v.toString());	
					}
				}
			}
		}
		
		else if (u instanceof AssignStmt) {
			Value Left = ((AssignStmt) u).getLeftOp();
			List Right = ((AssignStmt) u).getRightOp().getUseBoxes();
//			System.out.println(Left + "|" + ((AssignStmt) u).getRightOpBox().getValue().getUseBoxes().toString());
			if ( inval.contains(Left.toString())) {
//				System.out.println("Moving in");
				for (Object v : Right) {
//					System.out.println(v.getClass());
					if (v instanceof ImmediateBox) {
						ImmediateBox temp = (ImmediateBox) v;
						if (temp.getValue() instanceof Local) {
//							System.out.println("Yes");
							outval.add(temp.getValue().toString());	
						}
					}
				}
				if (Right.size() == 0) {
					outval.remove(Left.toString());
				}
				else {
					outval.add(Left.toString());
				}
			}
			if (u.toString().contains("java.lang.StringBuilder")) {
				for (Object v : Right) {
					if (v instanceof ImmediateBox) {
						ImmediateBox temp = (ImmediateBox) v;
						if (temp.getValue() instanceof Local) {
//							System.out.println("Yes");
							outval.add(temp.getValue().toString());	
						}
					}
				}
			}
		}
		
//		System.out.println("In: " + inval);
//		System.out.println("Unit: " + u.toString());
//		System.out.println("Out :" + outval);
//		System.out.println();
	}
	
	
	@Override
	protected void copy(Set<String> source, Set<String> dest) {
		dest.clear();
		dest.addAll(source);
	}
	
	@Override
	protected Set<String> entryInitialFlow() {
		return new HashSet<String>();
	}
	
	@Override
	protected void merge(Set<String> in1, Set<String> in2, Set<String> out) {
		out.clear();
		out.addAll(in1);
		out.addAll(in2);
	}
	
	@Override
	protected Set<String> newInitialFlow() {
		return new HashSet<String>();
	}
}
