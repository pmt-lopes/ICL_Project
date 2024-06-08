package ast;

import types.Type;

public class ASTDeref implements Exp{
	
	private String refName;
	private Type type;
	
	public ASTDeref(String e) {
		this.refName = e;
	}
	
	public String getName() {
		return this.refName;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type t) {
		this.type = t;
	}

}
