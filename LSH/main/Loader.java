package main;

import java.util.Scanner;
import java.util.Vector;
import java.io.*;

import util.SparseVector;

public class Loader {
	public Vector<SparseVector> data = new Vector<SparseVector>();

	public void load(String file) {
		try {
			InputStream is = new FileInputStream(file);
			Scanner scanner = new Scanner(is);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				SparseVector sv = new SparseVector();
				sv.deserialize(line);
				data.add(sv);
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
