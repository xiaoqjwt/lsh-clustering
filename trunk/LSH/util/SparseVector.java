package util;

import java.util.Vector;

public class SparseVector {
	public Vector<SparseCell> vector;

	public int index;

	public String deserialize() {
		String ret = "";
		ret += index;
		for (int i = 0; i < vector.size(); ++i) {
			int id = vector.get(i).id;
			double value = vector.get(i).value;
			ret += " " + id + " " + value;
		}
		return ret;
	}

	public boolean serialize(String line) {
		boolean ret = true;
		return ret;
	}
}
