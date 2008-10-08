package util;

import java.util.Random;
import java.util.Vector;

public class Hyperplane {
	private int dimension;

	public Vector<Double> X = new Vector<Double>();

	public Double w;

	public Hyperplane(int d) {
		dimension = d;
		randomGen();
	}

	public void randomGen() {
		Random rand = new Random(System.currentTimeMillis());
		w = 0.0;
		for (int i = 0; i < dimension; ++i) {
			X.add(rand.nextDouble());
		}
	}

	public double dot(SparseVector data) {
		double ret = w;
		for (int i = 0; i < data.vector.size(); ++i) {
			ret += X.get(data.vector.get(i).id) * data.vector.get(i).value;
		}
		return ret;
	}
}
