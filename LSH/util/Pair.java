package util;

public class Pair {
	Object ok1, ok2;

	public Pair(Object k1, Object k2) {
		ok1 = k1;
		ok2 = k2;
	}

	public Object K1() {
		return ok1;
	}

	public Object K2() {
		return ok2;
	}

	public int hashCode() {
		String s = "" + ok1 + "_" + ok2;
		return s.hashCode();
	}
}
