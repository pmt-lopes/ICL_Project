package types;

import ast.*;
import symbols.Env;
import values.Value;

public class TypeChecker implements ast.Exp.Visitor<Type,Env<Type>>{

	@Override
	public Type visit(ASTInt i, Env<Type> env) {
		return IntType.getInstance();
	}

	@Override
	public Type visit(ASTAdd e, Env<Type> env) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ASTLet e, Env<Type> env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ASTRef e, Env<Type> env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ASTDeref e, Env<Type> env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ASTAsg e, Env<Type> env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ASTSeq e, Env<Type> env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ASTWhile e, Env<Type> env) {
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
	public Type visit(ASTReref e, Env<Type> env) {
		return null;
	}

	@Override
	public Type visit(ASTPrintln e, Env<Type> env) {
		return null;
	}

	@Override
	public Type visit(ASTPrint e, Env<Type> env) {
		return null;
	}

	@Override
	public Type visit(ASTNew e, Env<Type> env) {
		return null;
	}

	@Override
	public Type visit(ASTNull e, Env<Type> env) {
		return null;
	}

}
