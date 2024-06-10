package ast;

public class ASTNull implements Exp{

	public ASTNull() {
	}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env)  {
			return v.visit(this, env);
	}
}
