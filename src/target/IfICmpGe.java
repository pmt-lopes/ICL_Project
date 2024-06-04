package target;

public class IfICmpGe extends Instruction{
	
	public IfICmpGe(String label) {
		op = "if_icmpge";
		args = new String[] {label};
	}

}
