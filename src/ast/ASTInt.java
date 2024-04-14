package ast;

public class ASTInt implements Exp{
	public int value;
	
	public ASTInt(int i) {
		this.value = i;
	}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env) {
			return v.visit(this, env);
	}

}
