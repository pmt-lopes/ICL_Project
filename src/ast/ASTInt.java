package ast;

import types.Type;

public class ASTInt implements Exp{
	public int value;
	private Type type;
	
	public ASTInt(int i) {
		this.value = i;
	}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env) {
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
