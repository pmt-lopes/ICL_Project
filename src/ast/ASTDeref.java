package ast;

public class ASTDeref implements Exp{
	
	private Exp refName;
	
	public ASTDeref(Exp e) {
		this.refName = e;
	}
	
	public Exp getName() {
		return this.refName;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
