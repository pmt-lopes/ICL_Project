package compiler;

import java.util.ArrayList;
import java.util.List;

import Util.Pair;
import target.*;
import types.IntType;
import types.Type;

public class BlockSeq {
	
	List<Frame> frames;
	Frame currFrame;
	BasicBlock block;
	public CompEnv env;
	
	public BlockSeq() {
		frames = new ArrayList<Frame>();
		currFrame = null;
		block = new BasicBlock();
		env = new CompEnv();
	}
	
	
	public Pair<Frame,CompEnv> beginScope(int nFields) {
		Frame f = new Frame(frames.size(), nFields);
		f.prev = currFrame;
		currFrame = f;
		env = env.beginScope();
		frames.add(f);
		return new Pair<>(currFrame, env);
	}
	
	//maybe not necessary
	public void advanceToFrame(Frame f, CompEnv env) {
		this.currFrame = f;
		this.env = env;
	}
	
	public void endScope(Frame f, CompEnv env) {
		this.currFrame = currFrame.prev;
		this.env = env.endScope();
		frames.remove(f.id);
	}
	
	public void addInstruction(Instruction i) {
		block.addInstruction(i);
	}
	
	public void fetch(String id, Type t) {
		Pair<Integer, Integer> p = env.find(id);
		int depth = p.getFirst();
		int width = p.getSecond();
		/*
		 * aload 0
			getfield frame_1/sl Lframe_0;
			getfield frame_0/loc_0 I
		 */
		this.addInstruction(new ALoad(0));
		if(depth > 0) {
			for(int i = 0; i < depth; i++) {
				String frameSl = "mypackage/frame_" + (currFrame.id - i) + "/SL";
				String previousLink = "Lmypackage/frame_" + (currFrame.id - i - 1) + ";";
				this.addInstruction(new GetField(frameSl, previousLink));
			}
		}
		String frameLoc = "mypackage/frame_" + (currFrame.id - depth) + "/loc_" + width;
		String jvmType;
		//accepting only ints and booleans
		if(t instanceof IntType) {
			jvmType = "I";
		}
		else {
			jvmType = "Z";
		}
		this.addInstruction(new GetField(frameLoc, jvmType));
	}

}
