package target;

public class GoTo extends Instruction {
	
	public GoTo(String label) {
		op = "goto";
		args = new String[] {label};
	}
}
