package ast;

public class ASTPrintln implements Exp{

	public Exp value;

	public ASTPrintln(Exp e) {
		value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
