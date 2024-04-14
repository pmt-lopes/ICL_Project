package ast;

public class ASTGr implements Exp{
	public Exp arg1;
	public Exp arg2;
	
	public ASTGr(Exp arg1, Exp arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env) {
			return v.visit(this, env);
	}
}
