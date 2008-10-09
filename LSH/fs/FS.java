package fs;

import java.util.Vector;

import util.SparseVector;

import lsh.LSHResult;

/**
 * Feature Selection
 * 
 * @author huicongwm
 * 
 */
public abstract class FS {
	Vector<LSHResult> input;

	Vector<SparseVector> data;

	int dimension;

	public Vector<Double> featureValue;

	public FS(Vector<LSHResult> _input, Vector<SparseVector> _data,
			int _dimension) {
		input = _input;
		data = _data;
		dimension = _dimension;
	}

	public abstract void selection();
}
