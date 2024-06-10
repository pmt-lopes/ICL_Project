package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import Util.Pair;
import ast.*;
import target.*;
import types.*;

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
		CompEnv env = p.getSecond();
		/*
		 *  new frame_0
			dup
			invokespecial frame_0/<init>()V
			dup
			aload 0
			putfield frame_0/sl Lframe_prev
			astore 0
		 */
		String frameId = "mypackage/frame_" + f.id;
		blocks.addInstruction(new New(frameId));
		blocks.addInstruction(new Dup());
		blocks.addInstruction(new InvokeSpecial(frameId + "/<init>()V"));
		blocks.addInstruction(new Dup());
		blocks.addInstruction(new ALoad(0));
		String previousLink;
		if(f.id == 0) {
			previousLink = "Ljava/lang/Object;";
		}
		else {
			previousLink = "Lmypackage/frame_" + (f.id -1) + ";";
		}
		blocks.addInstruction(new PutField(frameId + "/SL", previousLink));
		blocks.addInstruction(new AStore(0));
		for (Exp b : e.getBindings()) {
			
			if(b instanceof ASTId) {
				
				ASTId i = (ASTId) b;
				Type t = i.getType();
				
				int index = f.add(t) -1;
				env.bind(i.arg1, index);
				/*
				 * aload 0
					[[ E1 ]]
					putfield frame_id/loc_0 type1
				 */
				blocks.addInstruction(new ALoad(0));
				
				i.arg2.accept(this, null);
				
				String jvmType = getJVMType(t);
				
				if(t instanceof RefType) {
					jvmType = "L" + jvmType;
				}
				
				String locIndex = "/loc_" + index;
				
				blocks.addInstruction(new PutField(frameId + locIndex, jvmType));
				
			} else { // ASTRef
				/*
				 *  new ref_int
					dup
					invokespecial ref_int/<init>()V
					dup
					sipush 1
					putfield ref_int/value II
					putfield frame_1/loc_0 Lref_int;
				 */
				ASTRef r = (ASTRef) b;
				RefType t = (RefType) r.getType();
				
				int index = f.add(t) -1;
				env.bind(r.getName(), index);
				
				String refName = t.toString();
				
				blocks.addInstruction(new New(refName));
				blocks.addInstruction(new Dup());
				blocks.addInstruction(new InvokeSpecial(refName + "/<init>()V"));
				blocks.addInstruction(new Dup());
				
				//new ref ASTRef
				//putRefValue(f, env, r.getValue());
				
				//Assume there is no ref_ref
				r.getValue().accept(this, null);
				
				String refValue = refName + "/value";
				String valueType = getJVMType(t.getInner());
				
				if(t.getInner() instanceof RefType) {
					valueType = "L" + valueType;
				}
				
				blocks.addInstruction(new PutField(refValue, valueType));
				
				String locIndex = "/loc_" + index;
				String jvmType = getJVMType(t);
				jvmType = "L" + jvmType;
				
				blocks.addInstruction(new PutField(frameId + locIndex, jvmType));
				

				try {
					createRefClass(t);
				}
				catch(FileNotFoundException ex) {
					
				}
			}
		}
		try {
			createFrameClass(f);
		}
		catch(FileNotFoundException ex) {
			
		}
		e.getBody().accept(this, null);
		/*
		 * aload 0
			checkcast frame_id
			getfield frame_id/SL Lframe_prev;
			astore 0
		 */
		blocks.addInstruction(new ALoad(0));
		blocks.addInstruction(new CheckCast(frameId));
		blocks.addInstruction(new GetField(frameId + "/SL", previousLink));
		blocks.addInstruction(new AStore(0));
		blocks.endScope(f,env);
		return null;
	}

	@Override
	public Void visit(ASTRef e, Void env) {
		//references are compiled by the let bindings;
		//not in the let body
		return null;
	}

	@Override
	public Void visit(ASTDeref e, Void env) {
		blocks.fetch(e.getName(), e.getType());
		return null;
	}

	@Override
	public Void visit(ASTAsg e, Void env) {
		blocks.fetch(e.refName, e.getType());
		e.accept(this, null);
		String jvmType = getJVMType(e.value.getType());
		String refLoc = "ref_" + (jvmType == "I"? "int": "bool") + "/value";
		blocks.addInstruction(new PutField(refLoc, jvmType));
		return null;
	}

	@Override
	public Void visit(ASTSeq e, Void env) {
		// i hope it's not more complicated
		e.arg1.accept(this, null);
		e.arg2.accept(this, null);
		return null;
	}

	@Override
	public Void visit(ASTWhile e, Void env) {
		blocks.addInstruction(new DotLabel("start"));
		e.arg1.accept(this, null);
		blocks.addInstruction(new SIPush(0));
		blocks.addInstruction(new IfICmpEq("end")); //if expression equals false, end
		e.arg2.accept(this, null);
		blocks.addInstruction(new GoTo("start"));
		blocks.addInstruction(new DotLabel("end"));
		return null;
	}	

	@Override
	public Void visit(ASTIf e, Void env) {
		e.arg1.accept(this, null);
		blocks.addInstruction(new SIPush(0));
		
		boolean hasElse = e.arg3 != null;
		if(hasElse) {
			blocks.addInstruction(new IfICmpEq("else")); //if false jump to else
		} else {
			blocks.addInstruction(new IfICmpEq("endif"));
		}
		
		e.arg2.accept(this, null); //then 
		
		if(hasElse) {
			blocks.addInstruction(new Label("else"));
			e.arg3.accept(this, null); //else
		}
		
		blocks.addInstruction(new Label("endif"));
		return null;
	}
	
	@Override
	public Void visit(ASTPrint e, Void env) {
		e.value.accept(this, null);
		/*
		 * getstatic java/lang/System/out Ljava/io/PrintStream;
		 * invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
			invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
		 */
		blocks.addInstruction(new GetStatic("java/lang/System/out", "Ljava/io/PrintStream;"));
		blocks.addInstruction(new InvokeStatic("java/lang/String/valueOf(I)Ljava/lang/String;"));
		blocks.addInstruction(new InvokeVirtual("java/io/PrintStream/print(Ljava/lang/String;)V"));
		return null;
	}

	@Override
	public Void visit(ASTPrintln e, Void env) {
		e.value.accept(this, null);
		/*
		 * getstatic java/lang/System/out Ljava/io/PrintStream;
		 * invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
			invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
		 */
		blocks.addInstruction(new GetStatic("java/lang/System/out", "Ljava/io/PrintStream;"));
		blocks.addInstruction(new InvokeStatic("java/lang/String/valueOf(I)Ljava/lang/String;"));
		blocks.addInstruction(new InvokeVirtual("java/io/PrintStream/println(Ljava/lang/String;)V"));
		return null;
	}
	
	public static BasicBlock codeGen(Exp e, Void ev) {
		CodeGen cg = new CodeGen();
		e.accept(cg, ev);
		return cg.blocks.block;
	}
	
	private static void createFrameClass(Frame f) throws FileNotFoundException {
		String frameId = "frame_" + f.id;
		String previousType;
		
		if(f.id == 0) {
			previousType = "Ljava/lang/Object";
		} else {
			previousType = "Lmypackage/frame_" + (f.id - 1);
		}
		
		String frameContent = String.format("""
                .class public mypackage/%s
                .super java/lang/Object
                .field public SL %s;
                """, frameId, previousType);
		
		String s2 = """
		.method public <init>()V
     aload_0
     invokenonvirtual java/lang/Object/<init>()V
     return
	 .end method
				""";
		
		StringBuilder sb = new StringBuilder(frameContent);
		
		for(int i = 0; i < f.types.size(); i++) {
			Type t = f.types.get(i);
			String jvmType = getJVMType(t);
			if(t instanceof RefType) {
				jvmType = "L" + jvmType;
			}
			sb.append(String.format(".field public loc_%d %s%n", i, jvmType));
		}
		
		sb.append(s2);
		String filename = frameId + ".j";
		PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
	}
	
	private static void createRefClass(Type type) throws FileNotFoundException {
		String className = "mypackage/" + type.toString(); //ref_int or ref_bool
		RefType t = (RefType) type;
		String valueType = getJVMType(t.getInner());
		if(t instanceof RefType) {
			valueType = "L" + valueType;
		}
		String content = String.format("""
					.class %s
					.super java/lang/Object
					.field public value %s
					.method public <init>()V
					aload_0
					invokespecial java/lang/Object/<init>()V
					return
					.end method					          
			 	  """, className, valueType);
		StringBuilder sb = new StringBuilder(content);
		String filename = className + ".j";
		PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
	}
	
	//this was made for ref_ref_ but that syntax is not implemented
	private void putRefValue(Frame f, CompEnv env, Exp e) {
		if(e instanceof ASTRef) { // new new 0
			ASTRef r = (ASTRef) e;
			RefType t = (RefType) r.getType();
			String refName = t.toString();
			
			blocks.addInstruction(new New(refName));
			blocks.addInstruction(new Dup());
			blocks.addInstruction(new InvokeSpecial(refName + "/<init>()V"));
			blocks.addInstruction(new Dup());
			
			putRefValue(f, env, r.getValue());
			
			String refValue = refName + "/value";
			String valueType = getJVMType(t.getInner());
			
			if(t.getInner() instanceof RefType) {
				valueType = "L" + valueType;
			}
			
			blocks.addInstruction(new PutField(refValue, valueType));
			

			try {
				createRefClass(t);
			}
			catch(FileNotFoundException ex) {
				
			}
			
		} else if (e instanceof ASTId){ // this is for the case x = new(y) not sure if it will be
			ASTId i = (ASTId) e;		// made valid because getting the value of y seems difficult
			Pair<Integer, Integer> p = env.find(i.arg1);  // so it's not finished for now
			int depth = p.getFirst();
			int width = p.getSecond();
			Frame fr = f;
			while(depth > 0) {
				fr = fr.prev;
				depth--;
			}
			Type t = fr.getType(width);
			String ts = t.toString();
			if(ts.startsWith("ref_")) {
				
			} else {
				e.accept(this, null);
			}
		} else {
			e.accept(this, null);
		}
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
	
	private static String getJVMType(Type t) {
		
		String jvmType;
		if(t instanceof IntType) {
			jvmType = "I";
		}
		else if (t instanceof RefType){
			RefType t2 = (RefType) t;
			jvmType = t2.toString();
		}
		else {
			jvmType = "Z";
		}
		
		return jvmType;
	}
	
	public static void writeToFile(Exp e, Void ev, String filename) throws FileNotFoundException {
	    StringBuilder sb = genPreAndPost(codeGen(e, ev));
	    PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
		
	}

}
