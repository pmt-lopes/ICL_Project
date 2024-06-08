package ast;

import types.Type;

public class ASTAsg implements Exp{
	
	public String refName;
	public Exp value;
	private Type type;
	
	public ASTAsg(String s, Exp e) {
		this.refName = s;
		this.value = e;
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
