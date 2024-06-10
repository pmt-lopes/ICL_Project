package ast;

import types.Type;

public interface Exp {
	
	public interface Visitor<T,E> {
		
		public T visit(ASTInt i, E env);
		public T visit(ASTAdd e, E env);
		public T visit(ASTSub e, E env);
		public T visit(ASTMult e, E env);
		public T visit(ASTDiv e, E env);
		public T visit(ASTBool e, E env);
		public T visit(ASTAnd e, E env);
		public T visit(ASTOr e, E env);
		public T visit(ASTEq e, E env);
		public T visit(ASTNEq e, E env);
		public T visit(ASTGr e, E env);
		public T visit(ASTGrE e, E env);
		public T visit(ASTNeg e, E env);
		public T visit(ASTId e, E env);
		public T visit(ASTLet e, E env);
		public T visit(ASTRef e, E env);
		public T visit(ASTDeref e, E env);
		public T visit(ASTAsg e, E env);
		public T visit(ASTSeq e, E env);
		public T visit(ASTWhile e, E env);
		public T visit(ASTIf e, E env);
	}
	
	public <T,E> T accept(Visitor<T,E> v, E env);
	
	public Type getType();
	
	public void setType(Type t);

}
