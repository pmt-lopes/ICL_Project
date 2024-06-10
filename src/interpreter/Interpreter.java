package interpreter;

import ast.*;
import symbols.Env;
import values.*;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements ast.Exp.Visitor<Value,Env<Value>>{

	public Interpreter() {

	}

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
		Value n1 = e.arg1.accept(this, env);
		Value n2 = e.arg2.accept(this, env);
		return new BoolValue(n1.equals(n2));
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
		List<Exp> bindings = e.getBindings();
		for(int i = 0; i < bindings.size(); i++) {
			Exp binding = bindings.get(i);
			String token = e.getTokens().get(i);
			Value val = binding.accept(this, env); // this is the bound value
			env.bind(token, val);
		}
		Exp body = e.getBody();
		Value v = body.accept(this, env); //body gets updates with current variable values (in theory...)
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
		//String id = ((ASTId) e.getName().accept(this, env)).toString();
		if(e.getValue() != null){
			//env.bind(e.getName(), e.getValue().accept(this, env));
			return e.getValue().accept(this, env);
		}
		return new VoidValue();
	}

	@Override
	public Value visit(ASTNew e, Env<Value> env) {
		//String id = e.getName(); // TODO: should verify if is string, maybe do .accept() on StringValue?
		//env.bind(id,new VoidValue());
		return new RefValue(new VoidValue());
	}

	@Override
	public Value visit(ASTNull e, Env<Value> env) {
		return new VoidValue();
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
		Value value = env.find(e.getName());
		
		if(value != null) {
			return value;
			//return error?
		}
		//return different error?
		return null; //throw "Unbound reference"
	}

	@Override
	public Value visit(ASTPrintln e, Env<Value> env) {
		String text = e.value;

		if(text != null) {
			//if(text instanceof String) {
				System.out.println(text);
			//}
			//return error?
		}
		//return different error?
		return new VoidValue();
	}

	@Override
	public Value visit(ASTPrint e, Env<Value> env) {
		String text = e.value;

		if(text != null) {//TODO: should i verify the type?
			//if(text instanceof String) {
			System.out.print(text);
			//}
			//return error?
		}
		//return different error?
		return new VoidValue();
	}
	
	@Override
	public Value visit(ASTSeq e, Env<Value> env) {
		Value v1 = e.arg1.accept(this, env);
		Value v2 = e.arg2.accept(this, env);
		List<Value> values = new ArrayList<>(2);
		values.add(v1);
		values.add(v2);
		//return new ValueList(values);
		return v2;
	}

	@Override
	public Value visit(ASTWhile e, Env<Value> env) {
		Exp condition = e.arg1;
		Exp body = e.arg2;

		//obtain truth value of condition
		BoolValue isTrue = (BoolValue) condition.accept(this, env);
		//System.out.println("IS COND TRUE? " + isTrue.getValue());
		Value result = null;
		System.out.println("DEBUG:IS WHILE CONDITION TRUE? " + isTrue.getValue());

		while(isTrue.getValue()){
			result = body.accept(this,env);
			if(body instanceof ASTRef) { // new value should be bound to var!
				ASTRef refBody = (ASTRef) body;
				env.bind(refBody.getName(), refBody.getValue().accept(this, env)); //bind new value to var in case of ASTRef
			}
			isTrue = (BoolValue) condition.accept(this, env); //update condition with regard to environment
			System.out.println("DEBUG:IS WHILE CONDITION TRUE? " + isTrue.getValue());
		}
		if(result != null){
			return result;
		}

		return new VoidValue();
	}

	@Override
	public Value visit(ASTIf e, Env<Value> env) {
		if(evalCondition(e.arg1, env)){
			return e.arg2.accept(this, env);
		}
		return e.arg3.accept(this, env);
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


}
