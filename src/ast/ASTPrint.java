package ast;

public class ASTPrint implements Exp{

	public String value;

	public ASTPrint(String e) {
		value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
