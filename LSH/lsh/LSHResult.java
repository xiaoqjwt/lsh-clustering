package lsh;

import java.util.Vector;

public class LSHResult {
	private Vector<Object> resultVec = new Vector<Object>();

	public void put(Object v) {
		resultVec.add(v);
	}

	public Object get(int id) {
		return resultVec.get(id);
	}

	public void set(int id, Object v) {
		resultVec.set(id, v);
	}

	public String toString() {
		String ret = "***";
		for (int i = 0; i < resultVec.size(); ++i) {
			ret += " \t " + resultVec.get(i);
		}
		return ret;
	}
}
