package target;

public class IfICmpGt extends Instruction{
	
	public IfICmpGt(String label) {
		op = "if_icmpgt";
		args = new String[] {label};
	}

}
