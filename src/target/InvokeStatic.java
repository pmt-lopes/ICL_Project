package target;

public class InvokeStatic extends Instruction{
	
	public InvokeStatic(String arg) {
		op = "invokestatic";
		args = new String[] {arg};
	}

}
