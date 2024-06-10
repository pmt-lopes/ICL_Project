package types;

public class VoidType implements Type{

	private static final VoidType singleton = new VoidType();
	public static final String VOID = "()";

	VoidType() {}
	
	public static VoidType getInstance() {
		return singleton;
	}
	
	public String toString() {
		return VOID;
	}

}
