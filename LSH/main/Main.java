package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import util.DataLoader;
import util.Pair;

import fs.FS;
import fs.IGFS;
import lsh.LSH;
import lsh.LSHResult;
import lsh.MinHash;

public final class Main {

	static int selectNumber = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DataLoader loader = new DataLoader();
		//loader.load("test_data.txt");
		loader.load("test1.data", "test1.data.label");

		LSH lsh = new MinHash();
		lsh.conf("test.ini");
		lsh.initialize();

		loader.GenLSH(lsh);

		FS fs = new IGFS(loader.dataLSH, loader.data, lsh.dimension);
		fs.selection();

		Pair[] sp = new Pair[fs.featureValue.size()];
		for (int i = 0; i < fs.featureValue.size(); ++i) {
			sp[i] = new Pair(i, fs.featureValue.get(i));
		}
		Arrays.sort(sp, new Comparator<Pair>() {
			public int compare(Pair arg0, Pair arg1) {
				double v = ((Double) arg0.K2() - (Double) arg1.K2());
				return (0 == v) ? 0 : ((v > 0) ? 1 : -1);
			}
		});

		try {
			OutputStream os = new FileOutputStream("test.result");
			PrintStream ps = new PrintStream(os);
			for (int i = 0; i < loader.dataLSH.size(); ++i) {
				String label = "";
				for (int j = 0; j < selectNumber; ++j) {
					int id = (Integer) sp[j].K1();
					if (0 != j) {
						label += "-";
					}
					label += loader.dataLSH.get(i).get(id);
				}
				if (i != 0) {
					ps.println();
				}
				ps.print(label);
			}
			os.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
