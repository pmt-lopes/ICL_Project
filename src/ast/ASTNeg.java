package ast;

import types.Type;

public class ASTNeg implements Exp{
	
	public Exp value;
	private Type type;
	
	public ASTNeg(Exp e) {
		value = e;
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
