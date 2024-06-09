package target;

public class InvokeSpecial extends Instruction{
	
	public InvokeSpecial(String arg) {
		op = "invokespecial";
		args = new String[] {arg};
	}

}
