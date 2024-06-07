package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import ast.*;
import target.*;

public class CodeGen implements Exp.Visitor<Void, Void>{
	
	BasicBlock block = new BasicBlock();

	@Override
	public Void visit(ASTInt i, Void ev) {
		block.addInstruction(new SIPush(i.value) );
		return null;
	}

	@Override
	public Void visit(ASTAdd e, Void env) {
	    e.arg1.accept(this, env);
	    e.arg2.accept(this, env);
	    block.addInstruction(new IAdd());
	    return null;
	}

	@Override
	public Void visit(ASTSub e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new ISub());
		return null;
	}

	@Override
	public Void visit(ASTMult e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IMul());
		return null;
	}

	@Override
	public Void visit(ASTDiv e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IDiv());
		return null;
	}

	@Override
	public Void visit(ASTBool e, Void env) {
		block.addInstruction(new SIPush(e.value? 1 : 0) );
		return null;
	}

	@Override
	public Void visit(ASTAnd e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IAnd());
		return null;
	}

	@Override
	public Void visit(ASTOr e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IOr());
		return null;
	}

	@Override
	public Void visit(ASTEq e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IfICmpNeq("L1"));
		block.addInstruction(new SIPush(1));
		block.addInstruction(new Jmp("L2"));
		block.addInstruction(new Label("L1", "sipush 0"));
		block.addInstruction(new Label("L2", "nop"));
		return null;
	}

	@Override
	public Void visit(ASTNEq e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IfICmpNe("L1"));
		block.addInstruction(new SIPush(1));
		block.addInstruction(new Jmp("L2"));
		block.addInstruction(new Label("L1", "sipush 0"));
		block.addInstruction(new Label("L2", "nop"));
		return null;
	}

	@Override
	public Void visit(ASTGr e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IfICmpLe("L1"));
		block.addInstruction(new SIPush(1));
		block.addInstruction(new Jmp("L2"));
		block.addInstruction(new Label("L1", "sipush 0"));
		block.addInstruction(new Label("L2", "nop"));
		return null;
	}

	@Override
	public Void visit(ASTGrE e, Void env) {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IfICmpGe("L1"));
		block.addInstruction(new SIPush(1));
		block.addInstruction(new Jmp("L2"));
		block.addInstruction(new Label("L1", "sipush 0"));
		block.addInstruction(new Label("L2", "nop"));
		return null;
	}

	@Override
	public Void visit(ASTNeg e, Void env) {
		block.addInstruction(new SIPush(0));
		e.value.accept(this, env);
		block.addInstruction(new IfICmpNeq("L1"));
		block.addInstruction(new SIPush(1));
		block.addInstruction(new Jmp("L2"));
		block.addInstruction(new Label("L1", "sipush 0"));
		block.addInstruction(new Label("L2", "nop"));
		return null;
	}

	@Override
	public Void visit(ASTId e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ASTLet e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ASTRef e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ASTDeref e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ASTAsg e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ASTSeq e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ASTWhile e, Void env) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static BasicBlock codeGen(Exp e, Void ev) {
		CodeGen cg = new CodeGen();
		e.accept(cg, ev);
		return cg.block;
	}
	
	
	private static StringBuilder genPreAndPost(BasicBlock block) {
		String preamble = """
					  .class public mypackage/Demo
					  .super java/lang/Object 
					  .method public <init>()V
					     aload_0
					     invokenonvirtual java/lang/Object/<init>()V
					     return
					  .end method
					  .method public static main([Ljava/lang/String;)V
					   .limit locals 10
					   .limit stack 256
					   ; setup local variables:
					   ;    1 - the PrintStream object held in java.lang.out
					  getstatic java/lang/System/out Ljava/io/PrintStream;					          
				   """;
		String footer = 
				"""
				invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
				invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
				return
				.end method
				""";
		StringBuilder sb = new StringBuilder(preamble);
		block.build(sb);
		sb.append(footer);
		return sb;
			
	}
	
	public static void writeToFile(Exp e, Void ev, String filename) throws FileNotFoundException {
	    StringBuilder sb = genPreAndPost(codeGen(e, ev));
	    PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
		
	}

}
