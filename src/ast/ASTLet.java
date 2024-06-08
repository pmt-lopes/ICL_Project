package ast;

import java.util.List;

import types.Type;

public class ASTLet implements Exp{
	private List<Exp> bindings;
	private Exp body;
	private Type type;
	
	public void setBindings(List<Exp> b) {
		this.bindings = b;
	}
	
	public void setBody(Exp e) {
		this.body = e;
	}
	
	public List<Exp> getBindings(){
		return bindings;
	}
	
	public Exp getBody() {
		return body;
	}
	
	public ASTLet() {}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env)  {
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
