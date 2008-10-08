package lsh;

import util.*;
import java.io.*;
import java.util.Scanner;

public abstract class LSH {

	public int dimension;

	/**
	 * Set the confiruation
	 * 
	 * @param conffile
	 * @return
	 */
	public boolean conf(String conffile) {
		boolean ret = true;

		try {
			InputStream fileIn = new FileInputStream(conffile);
			Scanner scanner = new Scanner(fileIn);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.length() == 0 || line.startsWith("#")) {
					continue;
				}
				String[] value = line.split("=");
				if (value.length != 2) {
					continue;
				}
				ret = setValue(value[0], value[1]);
				if (false == ret) {
					return ret;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			ret = false;
		}

		return ret;
	}

	boolean setValue(String lvalue, String rvalue) {
		boolean ret = false;
		if (lvalue.equals("dimension")) {
			dimension = Integer.valueOf(rvalue);
			return true;
		}
		return ret;
	}

	public abstract void initialize();

	/**
	 * Main Hash
	 * 
	 * @param sv
	 * @return
	 */
	public LSHResult hash(SparseVector sv) {
		LSHResult ret = new LSHResult();
		for (int i = 0; i < dimension; ++i) {
			Object obj = hashAt(sv, i);
			ret.put(obj);
		}
		return ret;
	}

	public abstract Object hashAt(SparseVector sv, int dim);
}
