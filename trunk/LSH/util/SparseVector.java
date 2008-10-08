package util;

import java.io.*;
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
		int li = 0, ri = 0;
		for (; ri < line.length() && line.charAt(ri) != ' '; ++ri)
			;
		index = Integer.valueOf(line.substring(li, ri));

		for (; ri < line.length();) {
			int id;
			double value;
			for (li = ++ri; ri < line.length() && line.charAt(ri) != ' '; ++ri)
				;
			id = Integer.valueOf(line.substring(li, ri));

			for (li = ++ri; ri < line.length() && line.charAt(ri) != ' '; ++ri)
				;
			value = Double.valueOf(line.substring(li, ri));

			SparseCell sc = new SparseCell(id, value);
			vector.add(sc);
		}
		return ret;
	}
}
