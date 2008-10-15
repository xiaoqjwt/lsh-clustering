package lsh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import util.Pair;
import util.SparseVector;

public class RandomPermutationMinhash extends LSH {

	long modL;

	Vector<Vector<Long>> parameter = new Vector<Vector<Long>>();

	@Override
	public boolean setValue(String lvalue, String rvalue) {
		boolean ret = super.setValue(lvalue, rvalue);
		if (true == ret) {
			return ret;
		}
		if (lvalue.equals("modL")) {
			modL = Long.valueOf(rvalue);
			return true;
		}
		return ret;
	}

	@Override
	public Object hashAt(SparseVector sv, int dim) {
		Long ret = Long.MAX_VALUE;
		for (int i = 0; i < sv.vector.size(); ++i) {
			int id = sv.vector.get(i).id;
			Long v = parameter.get(dim).get(id);
			if (v < ret) {
				ret = v;
			}
		}
		return ret;
	}

	@Override
	public void initialize() {
		if (this.loadParameter) {
			deserialize(parameterFile);
		} else {
			randomGenParameter();
			serialize(parameterFile);
		}
	}

	private void randomGenParameter() {
		Random rand = new Random(System.currentTimeMillis());
		for (int dim = 0; dim < dimension; ++dim) {
			Vector<Long> permu = new Vector<Long>();
			for (int i = 0; i < modL; ++i) {
				Long ni;
				do {
					ni = ((rand.nextLong() % modL) + modL) % modL;
				} while (permu.contains(ni));
				permu.add(ni);
			}
			parameter.add(permu);
		}
	}

	@Override
	public void deserialize(String file) {
		try {
			InputStream is = new FileInputStream(file);
			Scanner scanner = new Scanner(is);
			for (int i = 0; i < dimension; ++i) {
				Vector<Long> sp = new Vector<Long>();
				for (int j = 0; j < modL; ++j) {
					Long v = scanner.nextLong();
					sp.add(v);
				}
				parameter.add(sp);
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	@Override
	public void serialize(String file) {
		try {
			OutputStream os = new FileOutputStream(file);
			PrintStream ps = new PrintStream(os);
			for (int i = 0; i < parameter.size(); ++i) {
				if (0 != i) {
					ps.println();
				}
				for (int j = 0; j < modL; ++j) {
					if (0 != j) {
						ps.print(" ");
					}
					ps.print(parameter.get(i).get(j));
				}
				ps.println();
			}
			os.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

}
