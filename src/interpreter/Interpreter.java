package interpreter;

import ast.*;
import symbols.Env;
import values.*;

import java.util.ArrayList;
import java.util.List;

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
		return new BoolValue(n1.getValue() == n2.getValue());
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
	public Value visit(ASTNeg e, Env<Value> env) {
		BoolValue b = (BoolValue) e.value.accept(this, env);
		return new BoolValue(!b.getValue());
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

	@Override
	public Value visit(ASTId e, Env<Value> env) {
		String id = e.arg1;
		Value value = env.find(id);
		
		if(value == null) {
			//return error?
		}
		
		//if identifier points to reference
		if(value instanceof RefValue) {
			return ((RefValue) value).getValue();
		}
		
		return value;
	}
	
	@Override
	public Value visit(ASTRef e, Env<Value> env) {
		Value value = e.getValue().accept(this, env);
		return new RefValue(value);
	}

	@Override
	public Value visit(ASTAsg e, Env<Value> env) {
		String refName = e.refName;
		Value value = e.value.accept(this, env);
		Value refValue = env.find(refName);
		
	    if (refValue == null) {
	        Value newRef = new RefValue(value);
	        env.bind(refName, newRef);
	        return newRef;
	    }

	    if (refValue instanceof RefValue) {
	        ((RefValue) refValue).setValue(value);
	        return value; 
	    } else {
	        // return error?
	        return null;
	    }
		
	}

	@Override
	public Value visit(ASTDeref e, Env<Value> env) {
		String refName = e.getName();
		Value value = env.find(refName);
		
		if(value != null) {
			if(value instanceof RefValue) {
				return ((RefValue) value).getValue();
			}
			//return error?
		}
		//return different error?
		return null;
	}
	
	@Override
	public Value visit(ASTSeq e, Env<Value> env) {
		Value v1 = e.arg1.accept(this, env);
		Value v2 = e.arg2.accept(this, env);
		//not sure how to do this
		return null;
	}

	@Override
	public Value visit(ASTWhile e, Env<Value> env) {
		List<Value> resultValues = new ArrayList<>(); // Store values from each iteration

		while(evalCondition(e.arg1, env)) {
			Value result = e.arg2.accept(this, env);
			//not sure how to return all values
			if(result != null){
				resultValues.add(result);
			}
		}
		return new ValueList(resultValues);
	}
	
	private boolean evalCondition(Exp cond, Env<Value> env) {
		Value value = cond.accept(this, env);
		if( value instanceof BoolValue ) {
			return ((BoolValue) value).getValue();
		} else {
			//return error?
			return false; //todo:write exception to this
		}
	}
	
	public static Value interpret(Exp e) {
		Interpreter i = new Interpreter();
		Env<Value> env = new Env<Value>();
		return e.accept(i, env);
	}

	@Override
	public Value visit(ASTIf e, Env<Value> env) {
		// TODO Auto-generated method stub
		return null;
	}


}
