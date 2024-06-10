package target;

public class InvokeVirtual extends Instruction{
	
	public InvokeVirtual(String arg) {
		op = "invokestatic";
		args = new String[] {arg};
	}

}
