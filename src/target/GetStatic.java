package target;

public class GetStatic extends Instruction{
	
	public GetStatic(String arg1, String arg2) {
		op = "getstatic";
		args = new String[] {arg1, arg2};
	}

}
