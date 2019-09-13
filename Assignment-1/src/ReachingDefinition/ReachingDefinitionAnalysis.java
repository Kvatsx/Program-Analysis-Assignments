package ReachingDefinition;

import java.util.Iterator;

import soot.Body;
import soot.Local;
import soot.Unit;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.ForwardFlowAnalysis;

public class ReachingDefinitionAnalysis extends ForwardFlowAnalysis {

	Body b;
	FlowSet inval, outval;
	public ReachingDefinitionAnalysis(UnitGraph g)
	{
		super(g);
		b = g.getBody();
		doAnalysis();
	}
	@Override
	protected void flowThrough(Object in, Object unit, Object out) {
		inval = (FlowSet)in;
		outval = (FlowSet)out;
		Stmt u = (Stmt)unit;
		System.out.println(u);
		System.out.println();
		inval.copy(outval);
		
		// Kill operation
		Iterator<ValueBox> defIt = u.getDefBoxes().iterator();
		while(defIt.hasNext())
		{
			ValueBox defBox = (ValueBox)defIt.next();

			if (defBox.getValue() instanceof Local) {
				Iterator inIt = inval.iterator();
				while (inIt.hasNext()) {
					Stmt s = (Stmt)inIt.next();
					Iterator sIt = s.getDefBoxes().iterator();
					while (sIt.hasNext()) {
						ValueBox oldDefBox = (ValueBox)sIt.next();
						if (oldDefBox.getValue() instanceof Local && oldDefBox.getValue().equivTo(defBox.getValue()))
							outval.remove(s);
					}
				}
			}
		}
		
		//Gen operation
		if (u instanceof AssignStmt)
			outval.add(u);
		
		if (u instanceof AssignStmt)
		{
			System.out.println("In: " + inval);
			System.out.println("Unit: " + u.toString());
			System.out.println("Out :" + outval);
			System.out.println();
		}
		
	}
	@Override
	protected void copy(Object source, Object dest) {
		FlowSet srcSet = (FlowSet)source;
		FlowSet	destSet = (FlowSet)dest;
		srcSet.copy(destSet);
		
	}
	@Override
	protected Object entryInitialFlow() {
		return new ArraySparseSet();
	}
	@Override
	protected void merge(Object in1, Object in2, Object out) {
		FlowSet inval1=(FlowSet)in1;
		FlowSet inval2=(FlowSet)in2;
		FlowSet outSet=(FlowSet)out;
		// May analysis
		inval1.intersection(inval2, outSet);
		System.out.println("In merge");
		System.out.println("inval1:"+inval1);
		System.out.println("inval2:"+inval2);
		System.out.println("Outval:"+outval);
		System.out.println();
		
	}
	@Override
	protected Object newInitialFlow() {
		return new ArraySparseSet();
	}

}
 