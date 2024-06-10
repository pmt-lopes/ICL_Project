package target;

public class DotLabel extends Instruction {
	
	public DotLabel(String label) {
		op=".label";
		args = new String[]{ label } ; 
	}
}
