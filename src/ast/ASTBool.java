package ast;

import types.Type;

public class ASTBool implements Exp{
	public boolean value;
	private Type type;
	
	public ASTBool(boolean b) {
		this.value = b;
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
