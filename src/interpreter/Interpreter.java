package interpreter;

import ast.*;
import symbols.Env;
import values.*;

public class Interpreter implements ast.Exp.Visitor<Value,Env<Value>>{

	@Override
	public Value visit(ASTInt i, Env<Value> env) {
		return new IntValue(i.value);
	}

	@Override
	public Value visit(ASTAdd e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new IntValue(n1.getValue() + n2.getValue());
	}

	@Override
	public Value visit(ASTSub e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new IntValue(n1.getValue() - n2.getValue());
	}

	@Override
	public Value visit(ASTMult e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new IntValue(n1.getValue() * n2.getValue());
	}

	@Override
	public Value visit(ASTDiv e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new IntValue(n1.getValue() / n2.getValue());
	}

	@Override
	public Value visit(ASTBool e, Env<Value> env) {
		return new BoolValue(e.value);
	}

	@Override
	public Value visit(ASTAnd e, Env<Value> env) {
		BoolValue n1 = (BoolValue) e.arg1.accept(this, env);
		BoolValue n2 = (BoolValue) e.arg2.accept(this, env);
		return new BoolValue(n1.getValue() && n2.getValue());
	}

	@Override
	public Value visit(ASTOr e, Env<Value> env) {
		BoolValue n1 = (BoolValue) e.arg1.accept(this, env);
		BoolValue n2 = (BoolValue) e.arg2.accept(this, env);
		return new BoolValue(n1.getValue() || n2.getValue());
	}

	@Override
	public Value visit(ASTEq e, Env<Value> env) {
		BoolValue n1 = (BoolValue) e.arg1.accept(this, env);
		BoolValue n2 = (BoolValue) e.arg2.accept(this, env);
		return new BoolValue(n1.getValue() == n2.getValue());
	}

	@Override
	public Value visit(ASTNEq e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new BoolValue(n1.getValue() != n2.getValue());
	}

	@Override
	public Value visit(ASTGr e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new BoolValue(n1.getValue() > n2.getValue());
	}

	@Override
	public Value visit(ASTGrE e, Env<Value> env) {
		IntValue n1 = (IntValue) e.arg1.accept(this, env);
		IntValue n2 = (IntValue) e.arg2.accept(this, env);
		return new BoolValue(n1.getValue() >= n2.getValue());
	}

	@Override
	public Value visit(ASTNot e, Env<Value> env) {
		BoolValue b = (BoolValue) e.value.accept(this, env);
		return new BoolValue(!b.getValue());
	}

	@Override
	public Value visit(ASTId e, Env<Value> env) {
		String n1 = e.arg1;
		return env.find(n1);
	}

	@Override
	public Value visit(ASTLet e, Env<Value> env) {
		env = env.beginScope();
		for(Exp i : e.getBindings()) {
			ASTId id = (ASTId) i;
			String n = id.arg1;
			Exp exp = id.arg2;
			Value v = exp.accept(this, env);
			env.bind(n, v);
		}
		Exp body = e.getBody();
		Value v = body.accept(this, env);
		env = env.endScope();
		return v;
	}
	
	public static Value interpret(Exp e) {
		Interpreter i = new Interpreter();
		Env<Value> env = new Env<Value>();
		return e.accept(i, env);
	}

}
