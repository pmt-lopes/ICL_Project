package types;

public class RefType implements Type {

    private Type refOf;
	
    public RefType(Type t) { this.refOf = t; }

    public Type getInner() { return refOf; }
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

    public String toString() {
	return "ref_"+refOf.toString();
    }
}
