package ast;

public class ASTRef implements Exp{
	
	private Exp value;
	private Exp refName;
	
	public ASTRef(Exp e1, Exp e2) {
		this.refName = e1;
		this.value = e2;
	}
	
	public Exp getName() {
		return refName;
	}
	
	public Exp getValue() {
		return value;
	}
	
	public void setValue(Exp e) {
		this.value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
