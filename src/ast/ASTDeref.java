package ast;

public class ASTDeref implements Exp{
	
	private Exp arg;
	
	public ASTDeref(Exp e) {
		this.arg = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		// TODO Auto-generated method stub
		return null;
	}

}
