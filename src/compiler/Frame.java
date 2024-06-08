package compiler;

import java.util.ArrayList;
import java.util.List;

import types.Type;

public class Frame {
	
	int nFields;
	List<Type> types;
	int id;
	Frame prev;
	
	public Frame(int id, int nFields) {
		this.id = id;
		this.nFields = nFields;
		types = new ArrayList<Type>(nFields);
	}
	
	public int add(Type t) {
		types.add(t);
		return types.size();
	}

}
