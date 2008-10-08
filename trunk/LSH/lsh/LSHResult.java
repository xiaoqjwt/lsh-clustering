package lsh;

import java.util.Vector;

public class LSHResult {
	private Vector<Object> resultVec;

	public void put(Object v) {
		resultVec.add(v);
	}

	public Object get(int id) {
		return resultVec.get(id);
	}

	public void set(int id, Object v) {
		resultVec.set(id, v);
	}
}
