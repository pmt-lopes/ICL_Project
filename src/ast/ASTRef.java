package ast;

public class ASTRef implements Exp{
	
	private Exp left, right;
	
	public ASTRef(Exp e1, Exp e2) {
		this.left = e1;
		this.right = e2;
	}
	
	public void get() {
		
	}
	
	public void set() {
		
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		// TODO Auto-generated method stub
		return null;
	}

}
