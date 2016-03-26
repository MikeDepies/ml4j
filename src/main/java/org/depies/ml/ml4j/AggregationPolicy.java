package org.depies.ml.ml4j;

import java.util.Arrays;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Should refactor this into an interface and each implementation handles a
 * specific policy.
 * @author depie_000
 *
 */
public class AggregationPolicy {
	/**
	 * Rough implementation of calculating majority rules
	 * @param set
	 * @param labelIndex
	 * @param measureIndex
	 * @return
	 */
	public int majorityRules(INDArray set, int labelIndex, int measureIndex) {
		INDArray labelColumn = set.getColumn(labelIndex);
		int maxClassification = (int) (double) labelColumn.maxNumber() + 1;
		System.out.println(maxClassification);
		int[] classCount = new int[maxClassification];
		for (int i =0; i < set.rows(); i++)
			classCount[labelColumn.getInt(i)] += 1;
		System.out.println(Arrays.toString(classCount));
		int maxRep =0;
		int maxRepIndex = 0;
		for(int i =0; i < classCount.length; i++)
			if (classCount[i] > maxRep) {
				maxRep = classCount[i];
				maxRepIndex = i;
			}
				
		return maxRepIndex;
	}
}
