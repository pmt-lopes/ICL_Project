package target;

public class IfICmpEq extends Instruction{
	
	public IfICmpEq(String label) {
		op = "if_icmpeq";
		args = new String[] {label};
	}

}
