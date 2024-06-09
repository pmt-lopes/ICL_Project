package target;

public class GetField extends Instruction{
	
	public GetField(String arg1, String arg2) {
		op = "getfield";
		args = new String[] {arg1, arg2};
	}

}
