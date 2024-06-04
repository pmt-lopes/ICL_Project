package target;

public class IfICmpNe extends Instruction{
	
	public IfICmpNe(String label) {
		op = "if_icmpne";
		args = new String[] {label};
	}

}