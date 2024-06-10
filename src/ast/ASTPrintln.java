package ast;

public class ASTPrintln implements Exp{

	public String value;
	private final String EMPTY = "";

	public ASTPrintln(String e) {
		value = e;
	}

	@Override
	public <T, E> T accept(Visitor<T, E> v, E env) {
		return v.visit(this, env);
	}

}
