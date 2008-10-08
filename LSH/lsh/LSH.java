package lsh;

import util.*;

public abstract class LSH {
	/**
	 * Set the confiruation
	 * 
	 * @param conffile
	 * @return
	 */
	public abstract boolean conf(String conffile);

	/**
	 * Main Hash
	 * 
	 * @param sv
	 * @return
	 */
	public abstract LSHResult hash(SparseVector sv);
}
