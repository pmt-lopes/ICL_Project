package target;

public class AStore extends Instruction {
	
	public AStore(int n) {
		op="astore";
		args = new String[]{ Integer.toString(n) } ; 
	}
}
