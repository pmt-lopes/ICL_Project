package ast;

public class ASTNew implements Exp{

	private Exp value;
	private Exp refName;

	public ASTNew(Exp e1) {
		this.refName = e1;
		this.value = null;
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
