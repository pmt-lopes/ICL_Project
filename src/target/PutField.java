package target;

public class PutField extends Instruction{
	
	public PutField(String arg1, String arg2) {
		op = "putfield";
		args = new String[] {arg1, arg2};
	}

}
