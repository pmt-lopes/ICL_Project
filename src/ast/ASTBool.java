package ast;



public class ASTBool implements Exp{
	public boolean value;
	
	public ASTBool(boolean b) {
		this.value = b;
	}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env) {
			return v.visit(this, env);
	}

}
