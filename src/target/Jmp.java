package target;

public class Jmp extends Instruction{
	
	public Jmp(String label) {
		op = "jmp";
		args = new String[] {label};
	}

}
