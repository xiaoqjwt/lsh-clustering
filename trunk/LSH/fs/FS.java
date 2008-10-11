package fs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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

	public void serialize(String file) {
		try {
			OutputStream os = new FileOutputStream(file);
			PrintStream ps = new PrintStream(os);
			for (int i = 0; i < dimension; ++i) {
				if (i != 0) {
					ps.println();
				}
				ps.print(featureValue.get(i));
			}
			os.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
