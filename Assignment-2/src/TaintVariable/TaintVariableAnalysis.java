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
	Set<String> inval, outval;
	public TaintVariableAnalysis(UnitGraph g)
	{
		super(g);
		b = g.getBody();
		doAnalysis();
	}
	
	@Override
	protected void flowThrough(Set<String> in, Unit unit, Set<String> out) {
		inval = in;
		outval = out;
		Stmt u = (Stmt)unit;
		System.out.println(u);
//		System.out.println();
		outval.addAll(inval);
		
		// Kill operation
//		Iterator<ValueBox> defIt = u.getDefBoxes().iterator();
//		while(defIt.hasNext())
//		{
//			ValueBox defBox = (ValueBox)defIt.next();
//
//			if (defBox.getValue() instanceof Local) {
//				Iterator inIt = inval.iterator();
//				while (inIt.hasNext()) {
//					Stmt s = (Stmt)inIt.next();
//					Iterator sIt = s.getDefBoxes().iterator();
//					while (sIt.hasNext()) {
//						ValueBox oldDefBox = (ValueBox)sIt.next();
//						if (oldDefBox.getValue() instanceof Local && oldDefBox.getValue().equivTo(defBox.getValue()))
//							outval.remove(s);
//					}
//				}
//			}
//		}
		
		//Gen operation
		
		if (u instanceof JReturnStmt) {
			for (ValueBox defBox: u.getUseBoxes()) {
				if (defBox.getValue() instanceof Local) {
					outval.add(defBox.getValue().toString());
				}
			}
		}
		
		else if (u instanceof JInvokeStmt) {
			for (Value v: (List<Value>) u.getInvokeExpr().getArgs()) {
				outval.add(v.toString());
			}
		}
		
		else if (u instanceof AssignStmt) {
//			for (ValueBox defBox: u.getDefBoxes()) {
//				if (defBox.getValue() instanceof Local && !inval.contains(defBox.getValue().toString())) {
//					for (ValueBox useBox: u.getUseBoxes()) {
//						System.out.println("JInvoke2: "+useBox.getValue().toString());
//						System.out.println("Type: "+useBox.getValue().getType());
//						
//						if (useBox.getValue().getType().equals("java.util.Scanner")) {
//							System.out.println("JInvoke: "+useBox.toString());
//						}
//						else {
//							outval.add(u.get);
//						}
//					}
//				}
//			}
			Value Left = ((AssignStmt) u).getLeftOp();
			List Right = ((AssignStmt) u).getRightOp().getUseBoxes();
			System.out.println(Left + "|" + ((AssignStmt) u).getRightOpBox().getValue().getUseBoxes().toString());
			if ( inval.contains(Left.toString())) {
				System.out.println("Moving in");
				for (Object v : Right) {
					System.out.println(v.getClass());
					if (v instanceof JimpleLocalBox) {
						System.out.println("Yo");
					}
					else {
						ImmediateBox temp = (ImmediateBox) v;
						if (temp.getValue() instanceof Local) {
							System.out.println("Yes");
							outval.add(temp.getValue().toString());	
							outval.add(Left.toString());
						}
						else {
							System.out.println("No");
							outval.remove(Left.toString());
						}
					}
				}
				if (Right.size() == 0) {
					outval.remove(Left.toString());
				}
			}
		}
		
		System.out.println("In: " + inval);
		System.out.println("Unit: " + u.toString());
		System.out.println("Out :" + outval);
		System.out.println();
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
