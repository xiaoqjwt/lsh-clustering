package lsh;

import java.math.BigInteger;
import java.util.Random;
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
}
