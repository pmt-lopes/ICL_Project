package target;

public class ALoad extends Instruction {
	
	public ALoad(int n) {
		op="aload";
		args = new String[]{ Integer.toString(n) } ; 
	}
}
