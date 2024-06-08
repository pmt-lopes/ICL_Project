package ast;

import types.Type;

public class ASTId implements Exp{
	public String arg1;
	public Exp arg2;
	private Type type;
	
	public ASTId(String arg1, Exp arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env)  {
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
