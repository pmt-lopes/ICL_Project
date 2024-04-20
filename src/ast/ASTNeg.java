package ast;

public class ASTNeg implements Exp{
	
	public Exp value;
	
	public ASTNeg(Exp e) {
		value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
