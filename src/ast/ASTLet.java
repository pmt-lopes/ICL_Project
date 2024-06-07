package ast;

import java.util.ArrayList;
import java.util.List;

public class ASTLet implements Exp{
	private List<Exp> bindings = new ArrayList<>();
	private List<String> tokens = new ArrayList<>();
	private Exp body;

	public void addToken(String token){
		tokens.add(token);
	}
	public List<String> getTokens(){
		return tokens;
	}
	public void addBinding(Exp b) {
		this.bindings.add(b);
	}
	
	public void setBody(Exp e) {
		this.body = e;
	}
	
	public List<Exp> getBindings(){
		return bindings;
	}
	
	public Exp getBody() {
		return body;
	}
	
	public ASTLet() {}
	
	@Override
	public <T,E> T accept(Visitor<T,E> v, E env)  {
			return v.visit(this, env);
	}
}
