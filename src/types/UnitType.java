package types;

public class UnitType implements Type{
	
	private static final UnitType singleton = new UnitType();
	
	private UnitType() {}
	
	public static UnitType getInstance() {
		return singleton;
	}
	
	public String toString() {
		return null;
	}

}
