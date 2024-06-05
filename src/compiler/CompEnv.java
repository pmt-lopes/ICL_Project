package compiler;

import java.util.Hashtable;
import java.util.Map;
import Util.Pair;

public class CompEnv {
	
	private Map<String,Integer> table;
	private CompEnv prev;
	
	public CompEnv() {
		table = new Hashtable<>(20);
	}
	
	public void bind(String id, Integer val) {
	       table.put(id, val);
	}
	
	private Pair<Integer,Integer> findIt(String id) {
		Integer width = null;
		CompEnv env = this;
		int depth = -1;
		while (width == null && env != null){
			width = env.table.get(id);
			env = this.prev;
			depth++;
		}
		return new Pair<>(depth,width);
	}
	
	public Pair<Integer,Integer> find(String id) {
		return findIt(id);
	}

	public CompEnv beginScope() {
	    CompEnv e = new CompEnv();
	    e.prev = this;
	    return e;
	}
	
	public CompEnv endScope() {
	    return this.prev;
	}
	
}
