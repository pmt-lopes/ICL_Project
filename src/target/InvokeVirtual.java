package target;

public class InvokeVirtual extends Instruction{
	
	public InvokeVirtual(String arg) {
		op = "invokevirtual";
		args = new String[] {arg};
	}

}
