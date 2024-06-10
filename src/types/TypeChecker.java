package types;

import ast.*;
import interpreter.Interpreter;
import symbols.Env;
import values.BoolValue;
import values.RefValue;
import values.Value;
import values.VoidValue;

import java.util.List;

public class TypeChecker implements ast.Exp.Visitor<Type,Env<Type>>{

	@Override
	public Type visit(ASTInt i, Env<Type> env) {
		return IntType.getInstance();
	}

	@Override
	public Type visit(ASTAdd e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 instanceof IntType && t1 == t2) {
			return t1;
		}
		//error?
		return new VoidType();

	}

	@Override
	public Type visit(ASTSub e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == IntType.getInstance() && t1 == t2) {
			return t1;
		} else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTMult e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == IntType.getInstance() && t1 == t2) {
			return t1;
		} else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTDiv e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == IntType.getInstance() && t1 == t2) {
			return t1;
		} else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTBool e, Env<Type> env) {
		return BoolType.getInstance();
	}

	@Override
	public Type visit(ASTAnd e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance() && t1 == t2) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTOr e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance() && t1 == t2) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTEq e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance() && t1 == t2) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTNEq e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance() && t1 == t2) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTGr e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance() && t1 == t2) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTGrE e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance() && t1 == t2) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTNeg e, Env<Type> env) {
		Type t1 = e.value.accept(this, env);
		if(t1 == BoolType.getInstance()) {
			return t1;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTId e, Env<Type> env) {
		String id = e.arg1;
		Type t1 = env.find(id);
		Type t2 = e.arg2.accept(this, env);

		//if identifier points to reference
		if(t1 instanceof RefType &&
				(t2 instanceof IntType || t2 instanceof BoolType)) {
			return t1;
		}

		return null;
	}

	@Override
	public Type visit(ASTLet e, Env<Type> env) {
		List<Exp> bindings = e.getBindings();
		if(!bindings.isEmpty() && !e.getTokens().isEmpty()){
			Exp binding = bindings.get(0);
			String token = e.getTokens().get(0);
			Type t = binding.accept(this, env); // this is the bound type
			env.bind(token, t);
			Exp body = e.getBody();
			Type bodyType = body.accept(this, env);
			if(!(t instanceof VoidType || bodyType instanceof VoidType))
				return body.accept(this, env);
		}
		return null;
	}

	@Override
	public Type visit(ASTRef e, Env<Type> env) {
		if(e.getValue() != null){
			Type t = e.getValue().accept(this, env);
			if(t instanceof RefType)
				return e.getValue().accept(this, env);
		}
		return null;
	}

	@Override
	public Type visit(ASTDeref e, Env<Type> env) {
		Type t = env.find(e.getName());

		if(t instanceof IntType || t instanceof BoolType
				|| t instanceof RefType) {
			return t;
			//return error?
		}
		//return different error?
		return null; //throw "Unbound reference"
	}

	@Override
	public Type visit(ASTAsg e, Env<Type> env) {
		String refName = e.refName;
		Type valType = e.value.accept(this, env);
		Type refType = env.find(refName);

		if ((refType == null || refType instanceof RefType)
				&& (valType instanceof IntType || valType instanceof BoolType)
		) {
			return new RefType(valType);
		}
		return null;

	}

	@Override
	public Type visit(ASTSeq e, Env<Type> env) {
		Type t = e.arg2.accept(this, env);
		if ((t instanceof IntType || t instanceof BoolType)
		) {
			return t;
		}
		return null;
	}

	@Override
	public Type visit(ASTWhile e, Env<Type> env) {
		Exp condition = e.arg1;
		Exp body = e.arg2;

		if(condition instanceof BoolType){
			Type bType = body.accept(this,env);
			if(bType instanceof IntType || bType instanceof BoolType) { // new value should be bound to var!
				return bType;
			}
		}

		return null;
	}

	@Override
	public Type visit(ASTIf e, Env<Type> env) {
		Type t1 = e.arg1.accept(this, env);
		Type t2 = e.arg2.accept(this, env);
		if(t1 == BoolType.getInstance()){
			return t2;
		}
		else {
			//error?
			return null;
		}
	}

	@Override
	public Type visit(ASTPrintln e, Env<Type> env) {
		return new VoidType();
	}

	@Override
	public Type visit(ASTPrint e, Env<Type> env) {
		return new VoidType();
	}

	@Override
	public Type visit(ASTNew e, Env<Type> env) {
		return new RefType(null);
	}

	@Override
	public Type visit(ASTNull e, Env<Type> env) {
		return new VoidType();
	}

	public static String typecheck(Exp e) {
		TypeChecker i = new TypeChecker();
		Env<Type> env = new Env<Type>();
		return e.accept(i, env).toString();
	}

}
