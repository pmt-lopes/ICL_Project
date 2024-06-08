package compiler;

import java.util.ArrayList;
import java.util.List;

import Util.Pair;
import target.BasicBlock;
import target.Instruction;
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
		
		//create jasmin file for frame_id class
		
		//add instructions for class declaration
		
		//add instructions for variable fields
		
		//add instructions for end of declaration
		
		return new Pair<>(currFrame, env);
	}
	
	public void advanceToFrame(Frame f, CompEnv env) {
		this.currFrame = f;
		this.env = env;
	}
	
	public void endScope(Frame f, CompEnv env) {
		this.currFrame = currFrame.prev;
		this.env = env.endScope();
	}
	
	public void addInstruction(Instruction i) {
		block.addInstruction(i);
	}
	
	public void fetch(String id, Type t) {
		//add instructions to call frame
		
		//add instructions to get element from frame
	}

}
