package target;

public class IfICmpLe extends Instruction{
	
	public IfICmpLe(String label) {
		op = "if_icmple";
		args = new String[] {label};
	}

}
