package InterTaintVariableAnalysis;

import java.util.*;

import soot.Body;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.SootMethod;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.jimple.internal.ImmediateBox;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JReturnStmt;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.BackwardFlowAnalysis;

public class IntraTaintAnalysis extends BackwardFlowAnalysis<Unit, Set<String>> {
	
	Body b;
	Set<String> inval, outval, UserInput, Parameters, Scan;
	boolean flag;
	SootMethod cMethod;
	Info info;
	HashMap<String, Info> summary;
	
	public IntraTaintAnalysis(UnitGraph g, HashMap<String, Info> Sum, SootMethod cm)
	{
		super(g);
		summary = Sum;
		cMethod = cm;
		UserInput = new HashSet<String>();
		Parameters = new HashSet<String>();
		Scan = new HashSet<String>();
		b = g.getBody();
		flag = false;
		doAnalysis();
		UserInput.addAll(Scan);
		Set<String> Result = new HashSet<String>();
		for (String e : UserInput) {
			for (String f : outval ) {
				if (e.equals(f)) {
					Result.add(f);
				}
			}
		}
		
		info = new Info();
		if (!cMethod.getName().equals("main")) {
			// 0->(), 1->(a, null), 2->(null, a), 3->(a, b), 4->(UserInput)
			List<String> list = new ArrayList<String>(Parameters);
			System.out.println(list.toString());
			if (Parameters.size() == 1) {
				if (Result.contains(list.get(0))){
					info.para0 = 1;
//					UserInput.remove(list.get(0));
				}
			}
			else {
				if (Result.contains(list.get(0)) && Result.contains(list.get(1))) {
					info.para0 = 1;
					info.para1 = 1;
//					UserInput.remove(list.get(0));
//					UserInput.remove(list.get(1));
				}
				else if (Result.contains(list.get(0))) {
//					UserInput.remove(list.get(0));
					info.para1 = 1;
				}
				else if (Result.contains(list.get(1))) {
//					UserInput.remove(list.get(1));
					info.para0 = 1;
				}
			}
			if (Scan.size() > 0) {
				info.userInput = 1;
			}
		}
		else {
			String Name = b.getMethod().getName();
			helperPrint(Result, Name);
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
//		System.out.println(u.toString());
		// UserInput set containing parameter and scanner variables
		if (u.toString().contains("@parameter")) {
			String para = u.toString().split(" :=")[0];
			UserInput.add(para);
			Parameters.add(para);
		}
		else if (u.toString().contains("java.util.Scanner") && u.toString().contains("virtualinvoke")) {
			outval.add(u.toString().split(" =")[0]);
			if (!cMethod.getName().equals("main")) {
				UserInput.add(u.toString().split(" =")[0]);
				Scan.add(u.toString().split(" =")[0]);
			}
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
//			System.out.println(u.toString());
//			System.out.println(Left + "|" + Right.toString());
			if ( inval.contains(Left.toString())) {
				
				if (u.containsInvokeExpr() && !u.getInvokeExpr().toString().contains("java.util.Scanner") && !u.getInvokeExpr().toString().contains("java.lang.StringBuilder")) {
					String Name = u.getInvokeExpr().getMethod().getName();
					Info temp = summary.get(Name);
					if (temp.userInput == 1) {
						Scan.add(Left.toString());
					}
					if (Right.size() == 0) {
						if (temp.userInput != 1) {
							outval.remove(Left.toString());
						}
					}
					else if (Right.size() == 1) {
						if (Right.get(0) instanceof ImmediateBox) {
							ImmediateBox tempI = (ImmediateBox) Right.get(0);
							if (tempI.getValue() instanceof Local) {
								if (temp.para0 == 1) {
									outval.add(tempI.getValue().toString());
									if (cMethod.getName().equals("main")) {
										Scan.add(Left.toString());
									}
								}
							}
						}
						else {
							if (temp.userInput != 1) {
								outval.remove(Left.toString());
							}
						}
					}
					else if (Right.size() == 2) {
						boolean fl = true;
						if (Right.get(0) instanceof ImmediateBox) {
							ImmediateBox temp1 = (ImmediateBox) Right.get(0);
							if (temp1.getValue() instanceof Local) {
								if (temp.para0 == 1) {
									fl = false;
									outval.add(temp1.getValue().toString());
									if (cMethod.getName().equals("main")) {
										Scan.add(Left.toString());
									}
								}
							}
						}
						if (Right.get(1) instanceof ImmediateBox) {
							ImmediateBox temp2 = (ImmediateBox) Right.get(1);
							if (temp2.getValue() instanceof Local) {
								if (temp.para1 == 1) {
									fl = false;
									outval.add(temp2.getValue().toString());
									if (cMethod.getName().equals("main")) {
										Scan.add(Left.toString());
									}
								}
							}
						}
						if (fl) {
							if (temp.userInput != 1) {
								outval.remove(Left.toString());
							}
						}
					}
				}
				else {
//					System.out.println("Moving in");
					for (Object v : Right) {
						if (v instanceof ImmediateBox) {
							ImmediateBox temp = (ImmediateBox) v;
							if (temp.getValue() instanceof Local) {
								outval.add(temp.getValue().toString());	
							}
						}
					}
					
					if (Right.size() == 0) {
						Value par = ((AssignStmt) u).getRightOp();
						if (par instanceof Local) {
							outval.add(par.toString());
						}
						else {
							outval.remove(Left.toString());							
						}
					}
					else {
						outval.add(Left.toString());
					}					
				}
			}
			if (u.toString().contains("java.lang.StringBuilder") || u.toString().contains("java.lang.String valueOf")) {
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
//		System.out.println("inval: "+inval.toString());
//		System.out.println("outval: "+outval.toString());
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

