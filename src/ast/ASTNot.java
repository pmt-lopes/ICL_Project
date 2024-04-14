package ast;

public class ASTNot implements Exp{
	public Exp value;
	
	public ASTNot(Exp arg1) {
		this.value = arg1;
	}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env) {
			return v.visit(this, env);
	}
}