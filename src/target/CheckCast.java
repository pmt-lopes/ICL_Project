package target;

public class CheckCast extends Instruction{
	
	public CheckCast(String arg1) {
		op = "checkcast";
		args = new String[] {arg1};
	}

}
