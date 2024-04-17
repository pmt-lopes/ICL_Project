package types;

public class IntType implements Type {

	public static final IntType singleton = new IntType();
	
	private IntType() {}
	
	public static IntType getInstance() {
		return singleton;
	}
	
	public String toString() {
		return null;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		return this == obj;
//	}

}
