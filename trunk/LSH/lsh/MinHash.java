package lsh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import util.Pair;
import util.SparseVector;

public class MinHash extends LSH {

	long modL;

	boolean bignumber = false;

	Vector<Pair> parameter = new Vector<Pair>();

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
		if (lvalue.equals("bignumber")) {
			bignumber = (rvalue.equals("on"));
			return true;
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
		for (int i = 0; i < dimension; ++i) {
			while (true) {
				int v1 = rand.nextInt();
				int v2 = rand.nextInt();
				Pair p = new Pair(v1, v2);
				if (!parameter.contains(p)) {
					parameter.add(p);
					break;
				}
			}
		}
	}

	@Override
	public Object hashAt(SparseVector sv, int dim) {
		long ret = Long.MAX_VALUE;
		long value;
		Pair par = parameter.get(dim);
		for (int i = 0; i < sv.vector.size(); ++i) {
			if (bignumber) {
				BigInteger mul = new BigInteger("" + par.K1());
				mul.multiply(new BigInteger("" + sv.vector.get(i).id));
				mul.add(new BigInteger("" + par.K2()));
				mul.mod(new BigInteger("" + modL));
				mul.add(new BigInteger("" + modL));
				mul.mod(new BigInteger("" + modL));
				value = mul.longValue();
			} else {
				value = (((sv.vector.get(i).id).longValue()
						* (Integer) par.K1() + (Integer) par.K2())
						% modL + modL)
						% modL;
			}
			if (value < ret) {
				ret = value;
			}

		}
		return ret;
	}

	@Override
	public void deserialize(String file) {
		try {
			InputStream is = new FileInputStream(file);
			Scanner scanner = new Scanner(is);
			for (int i = 0; i < dimension; ++i) {
				long k1 = scanner.nextLong();
				long k2 = scanner.nextLong();
				Pair p = new Pair(k1, k2);
				parameter.add(p);
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
				ps.print(parameter.get(i).K1() + " " + parameter.get(i).K2());
			}
			os.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
