package ast;

public class ASTDeref implements Exp{
	
	private String refName;
	
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

}
