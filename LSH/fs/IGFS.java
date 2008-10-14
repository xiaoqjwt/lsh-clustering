package fs;

import java.util.HashMap;
import java.util.Vector;

import util.Pair;
import util.SparseVector;

import lsh.LSHResult;

/**
 * Information Gain Feature Selection
 * 
 * @author huicongwm
 * 
 */
public class IGFS extends FS {

	public IGFS(Vector<LSHResult> _input, Vector<SparseVector> _data,
			int _dimension) {
		super(_input, _data, _dimension);
		allCount = _input.size();
		featureValue = new Vector<Double>();
	}

	@Override
	public void selection() {
		for (int dim = 0; dim < dimension; ++dim) {
			double entropy = 0;
			countInit();

			for (int i = 0; i < allCount; ++i) {
				Object value = input.get(i).get(dim);
				double label = data.get(i).label;
				if (!valueCount.containsKey(value)) {
					valueCount.put(value, 0);

					HashMap<Double, Integer> labelCount = new HashMap<Double, Integer>();
					labelCount.put(label, 0);
					valueLabelCount.put(value, labelCount);
				} else {
					if (!valueLabelCount.get(value).containsKey(label)) {
						valueLabelCount.get(value).put(label, 0);
					}
				}

				valueCount.put(value, valueCount.get(value) + 1);
				int c = valueLabelCount.get(value).get(label) + 1;
				valueLabelCount.get(value).put(label, c);

			}

			for (Object value : valueCount.keySet()) {
				double vCount = valueCount.get(value);
				HashMap<Double, Integer> labelCount = valueLabelCount
						.get(value);
				double semipr = 0;
				for (Integer lCount : labelCount.values()) {
					double pr = 1.0 * lCount / vCount;
					semipr += -pr * Math.log(pr) / Math.log(2.0);
				}
				entropy += semipr * vCount / allCount;
			}

			featureValue.add(entropy);
		}
	}

	private void countInit() {
		valueCount = new HashMap<Object, Integer>();
		valueLabelCount = new HashMap<Object, HashMap<Double, Integer>>();
	}

	private HashMap<Object, Integer> valueCount;

	private HashMap<Object, HashMap<Double, Integer>> valueLabelCount;

	private Integer allCount;

}
