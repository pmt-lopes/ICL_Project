package ast;

public class ASTAsg implements Exp{
	
	public String refName;
	public Exp value;
	
	public ASTAsg(String s, Exp e) {
		this.refName = s;
		this.value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
