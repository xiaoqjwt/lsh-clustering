package util;

import java.util.Scanner;
import java.util.Vector;
import java.io.*;

import lsh.LSH;
import lsh.LSHResult;

public class DataLoader {
	public Vector<SparseVector> data = new Vector<SparseVector>();

	public Vector<LSHResult> dataLSH = new Vector<LSHResult>();

	public int maxDimension = 0;

	public void load(String file) {
		try {
			InputStream is = new FileInputStream(file);
			Scanner scanner = new Scanner(is);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				SparseVector sv = new SparseVector();
				sv.deserialize(line);
				maxDimension = Math.max(maxDimension, sv.maxDimension);
				data.add(sv);
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public void load(String file, String labelFile) {
		try {
			InputStream is = new FileInputStream(file);
			Scanner scanner = new Scanner(is);
			InputStream lis = new FileInputStream(file);
			Scanner lscanner = new Scanner(lis);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				double label = lscanner.nextDouble();
				SparseVector sv = new SparseVector();
				sv.deserialize(line, label);
				maxDimension = Math.max(maxDimension, sv.maxDimension);
				data.add(sv);
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public void GenLSH(LSH function) {
		for (int i = 0; i < data.size(); ++i) {
			LSHResult result = function.hash(data.get(i));
			dataLSH.add(result);
		}
	}
}
