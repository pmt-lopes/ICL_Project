package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import Util.Pair;
import ast.*;
import target.*;

public class CodeGen implements Exp.Visitor<Void, Void>{
	
	BlockSeq blocks = new BlockSeq();

	@Override
	public Void visit(ASTInt i, Void v) {
		blocks.addInstruction(new SIPush(i.value) );
		return null;
	}

	@Override
	public Void visit(ASTAdd e, Void v) {
	    e.arg1.accept(this, null);
	    e.arg2.accept(this, null);
	    blocks.addInstruction(new IAdd());
	    return null;
	}

	@Override
	public Void visit(ASTSub e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new ISub());
		return null;
	}

	@Override
	public Void visit(ASTMult e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IMul());
		return null;
	}

	@Override
	public Void visit(ASTDiv e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IDiv());
		return null;
	}

	@Override
	public Void visit(ASTBool e, Void v) {
		blocks.addInstruction(new SIPush(e.value? 1 : 0) );
		return null;
	}

	@Override
	public Void visit(ASTAnd e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IAnd());
		return null;
	}

	@Override
	public Void visit(ASTOr e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IOr());
		return null;
	}

	@Override
	public Void visit(ASTEq e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IfICmpEq("L1"));
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new GoTo("L2"));
		blocks.addInstruction(new Label("L1"));
		blocks.addInstruction(new SIPush(1));
		blocks.addInstruction(new Label("L2"));
		blocks.addInstruction(new Nop());
		return null;
	}

	@Override
	public Void visit(ASTNEq e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IfICmpNe("L1"));
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new GoTo("L2"));
		blocks.addInstruction(new Label("L1"));
		blocks.addInstruction(new SIPush(1));
		blocks.addInstruction(new Label("L2"));
		blocks.addInstruction(new Nop());
		return null;
	}

	@Override
	public Void visit(ASTGr e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IfICmpGt("L1"));
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new GoTo("L2"));
		blocks.addInstruction(new Label("L1"));
		blocks.addInstruction(new SIPush(1));
		blocks.addInstruction(new Label("L2"));
		blocks.addInstruction(new Nop());
		return null;
	}

	@Override
	public Void visit(ASTGrE e, Void v) {
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		blocks.addInstruction(new IfICmpGe("L1"));
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new GoTo("L2"));
		blocks.addInstruction(new Label("L1"));
		blocks.addInstruction(new SIPush(1));
		blocks.addInstruction(new Label("L2"));
		blocks.addInstruction(new Nop());
		return null;
	}

	@Override
	public Void visit(ASTNeg e, Void v) {
		e.value.accept(this, null);
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new IfICmpEq("L1"));
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new GoTo("L2"));
		blocks.addInstruction(new Label("L1"));
		blocks.addInstruction(new SIPush(1));
		blocks.addInstruction(new Label("L2"));
		blocks.addInstruction(new Nop());
		return null;
	}

	@Override
	public Void visit(ASTId e, Void v) {
		blocks.fetch(e.arg1, e.getType());
		return null;
	}

	@Override
	public Void visit(ASTLet e, Void v) {
		int count = e.getBindings().size();
		Pair<Frame,CompEnv> p = blocks.beginScope(count);
		Frame f = p.getFirst();
		CompEnv newEnv = p.getSecond();
		for (Exp b : e.getBindings()) {
			//emit code to store bindings in new frame
		}
		blocks.endScope(f,newEnv);
		e.getBody().accept(this, null);
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
		return cg.blocks.block;
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
