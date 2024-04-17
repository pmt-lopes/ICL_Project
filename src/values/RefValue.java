package values;

public class RefValue implements Value {
	private Value value;
	
	public RefValue(Value value) {
		this.value = value;
	}

	public Value getValue() {
		return value;
	}

	@Override
	public String toString() {
	    return "ref("+value.toString()+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof RefValue && value == ((RefValue)obj).getValue();
	}

	
}
