package main;

import lsh.LSH;
import lsh.LSHResult;
import lsh.MinHash;

public final class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Loader loader = new Loader();
		loader.load("test_data.txt");
		LSH lsh = new MinHash();
		lsh.conf("test.ini");
		lsh.initialize();
		for (int i = 0; i < loader.data.size(); ++i) {
			LSHResult result = lsh.hash(loader.data.get(i));
			System.out.println(result);
		}
	}

}
