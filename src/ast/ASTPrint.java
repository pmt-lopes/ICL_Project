package ast;

public class ASTPrint implements Exp{

	public Exp value;

	public ASTPrint(Exp e) {
		value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
