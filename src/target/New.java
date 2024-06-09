package target;

public class New extends Instruction{
	
	public New(String arg) {
		op = "new";
		args = new String[] {arg};
	}

}
