package InterTaintVariableAnalysis;

public class Info {
	int para0, para1, userInput;
	public Info() {
		para0 = 0;
		para1 = 0;
		userInput = 0;
	}
	
	@Override
	public String toString() {
		String Results = Integer.toString(para0) + " " + Integer.toString(para1) + " " + Integer.toString(userInput);  
		return Results;
	}
}
