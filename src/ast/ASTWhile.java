package ast;

import types.Type;

public class ASTWhile implements Exp{
	
	public Exp arg1, arg2;
	private Type type;
	
	public ASTWhile(Exp e1, Exp e2) {
		this.arg1 = e1;
		this.arg2 = e2;
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
