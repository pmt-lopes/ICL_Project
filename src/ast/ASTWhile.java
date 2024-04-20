package ast;

public class ASTWhile implements Exp{
	
	public Exp arg1, arg2;
	
	public ASTWhile(Exp e1, Exp e2) {
		this.arg1 = e1;
		this.arg2 = e2;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		// TODO Auto-generated method stub
		return null;
	}

}
