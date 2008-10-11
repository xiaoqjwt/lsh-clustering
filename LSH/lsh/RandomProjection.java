package lsh;

import java.util.Vector;

import util.Hyperplane;
import util.SparseVector;

public class RandomProjection extends LSH {

	int dataDimension;

	Vector<Hyperplane> parameter = new Vector<Hyperplane>();

	public boolean setValue(String lvalue, String rvalue) {
		boolean ret = super.setValue(lvalue, rvalue);
		if (true == ret) {
			return ret;
		}
		if (lvalue.equals("dataDimension")) {
			dataDimension = Integer.valueOf(rvalue);
			return true;
		}
		return ret;
	}

	@Override
	public Object hashAt(SparseVector sv, int dim) {
		Double v = parameter.get(dim).dot(sv);
		boolean ret = (v > 0);
		return ret;
	}

	@Override
	public void initialize() {
		for (int i = 0; i < dimension; ++i) {
			Hyperplane hp = new Hyperplane(dataDimension);
			parameter.add(hp);
		}
	}

	@Override
	public void deserialize(String file) {
		// TODO 自动生成方法存根
		
	}

	@Override
	public void serialize(String file) {
		// TODO 自动生成方法存根
		
	}
}
