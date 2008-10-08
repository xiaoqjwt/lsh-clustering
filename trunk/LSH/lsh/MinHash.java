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
		Random rand = new Random();
		for (int i = 0; i < dimension; ++i) {
			while (true) {
				long v1 = rand.nextLong();
				long v2 = rand.nextLong();
				Pair p = new Pair(v1, v2);
				if (!parameter.contains(p)) {
					parameter.add(p);
					break;
				}
			}
		}
	}

	@Override
	public LSHResult hash(SparseVector sv) {
		LSHResult ret = new LSHResult();
		for (int i = 0; i < dimension; ++i) {
			Object obj = hashAt(sv, i);
			ret.put(obj);
		}
		return ret;
	}

	@Override
	public Object hashAt(SparseVector sv, int dim) {
		long ret = Long.MAX_VALUE;
		Pair par = parameter.get(dim);
		for (int i = 0; i < sv.vector.size(); ++i) {
			if (bignumber) {
				BigInteger mul = new BigInteger("" + par.K1());
				mul.multiply(new BigInteger("" + sv.vector.get(i).id));
				mul.add(new BigInteger("" + par.K2()));
				mul.mod(new BigInteger("" + modL));
				ret = mul.longValue();
			} else {
				ret = (sv.vector.get(i).id * (Long) par.K1() + (Long) par.K2())
						% modL;
			}
		}
		return ret;
	}
}
