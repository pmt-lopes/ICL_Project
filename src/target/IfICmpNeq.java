package target;

public class IfICmpNeq extends Instruction{
	
	public IfICmpNeq(String label) {
		op = "if_icmpneq";
		args = new String[] {label};
	}

}
