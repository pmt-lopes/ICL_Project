package ast;

public class ASTNew implements Exp{

	private String refName;

	public ASTNew(String e1) {
		this.refName = e1;
	}
	
	public String getName() {
		return refName;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
