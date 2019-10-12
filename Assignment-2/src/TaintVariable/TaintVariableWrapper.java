package TaintVariable;

import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.SootMethod;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class TaintVariableWrapper extends BodyTransformer {
	
	@Override
	protected void internalTransform(Body body, String phase, Map options) {
		// TODO Auto-generated method stub
		SootMethod sootMethod = body.getMethod();	
		UnitGraph g = new BriefUnitGraph(sootMethod.getActiveBody());
		TaintVariableAnalysis reach = new TaintVariableAnalysis(g);
	}
}
