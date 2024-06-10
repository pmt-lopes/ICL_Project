package types;

public class BoolType implements Type{
	
	private static final BoolType singleton = new BoolType();
	
	private BoolType() {}
	
	public static BoolType getInstance() {
		return singleton;
	}
	
	public String toString() {
		return "bool";
	}

}
