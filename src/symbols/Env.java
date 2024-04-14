package symbols;

import java.util.Hashtable;
import java.util.Map;

public class Env<T> {

	private Map<String,T> table;
	private Env<T> prev;

	public Env() {
		table = new Hashtable<>(20);
	}
	
	public void bind(String id, T val) {
	       table.put(id, val);
	}
	
	public T find(String id) {
		T value = table.get(id);
	    if(value == null) {
	    	if(prev != null) {
	    		return prev.find(id);
	    	} else {
	    		return null;
	    	}
	    }
	    else {
	    	return value;
	    }
	}
	
	public Env<T> beginScope() {
	    Env<T> e = new Env<T>();
	    e.prev = this;
	    return e;
	}
	
	public Env<T> endScope() {
	    return this.prev;
	}

}