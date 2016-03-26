package org.depies.ml.ml4j;

import java.util.Arrays;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.Accumulation;

import org.nd4j.linalg.factory.Nd4j;

public class KNN {
	public static enum Distance {
		Euclidean("euclidean");
		String function;
		Distance(String f) {
			function = f;
		}
		
		public String getFuncStr() {
			return function;
		}
	}
	INDArray features, labels;
	int k = 25;
	public KNN() {
		//dist = new EuclideanDistance();
	}
	
	public void setDataset(INDArray features, INDArray labels) {
		this.features = features;
		this.labels = labels;
	}
	
	public void setDataset(INDArray dataset, int labelIndex) {
		INDArray[] datasetComponents = dataSetToComponents(dataset, labelIndex);
		INDArray features = datasetComponents[0], labels = datasetComponents[1];
		
		setDataset(features, labels);
	}
	
	
	public INDArray neighborhood(INDArray x, INDArray features, INDArray labels) {
		//acquire dataset/model
		//measure against each record
		//keep the closest k records
		//aggregate the k records
		
		int rows = features.rows();
		INDArray measures = Nd4j.create(rows,1);
		
		for (int i = 0; i < rows; i++) {
			Accumulation dist = Nd4j.getOpFactory().createAccum(Distance.Euclidean.getFuncStr(), x, features.getRow(i));
			INDArray summedDistance = Nd4j.getExecutioner().exec(dist, 1);
			measures.put(i, summedDistance);
		}
		INDArray mergedSet = Nd4j.hstack(features, labels, measures);
		mergedSet = Nd4j.sortRows(mergedSet, features.columns() + 1, true);
		int[] neighborhood = new int[k];
		for(int i =0; i < k; i++)
			neighborhood[i] = i;
		INDArray n = mergedSet.getRows(neighborhood);
		return n;
	}
	
	public static INDArray[] dataSetToComponents(INDArray dataset, int labelIndex) {
		INDArray features, labels;
		int[] featureColumns = new int[dataset.columns()];
		int addedIndex =0;
		for (int i =0; i < featureColumns.length; i++) {
			if (labelIndex != i) {
				featureColumns[addedIndex] = i;
				addedIndex++;
			}
		}
		features = dataset.getColumns(featureColumns);
		labels = dataset.getColumn(labelIndex);
		return new INDArray[] {features, labels};
	}
}
