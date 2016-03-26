package org.depies.ml.ml4j;

import org.nd4j.linalg.api.ndarray.INDArray;

public class HeapSort {
	public static enum Orientation {
		Row, Column;
	}
	@FunctionalInterface
	public static interface Accessor {
		public double getValue(INDArray record);
	}
	Accessor accessor = (a) -> {return 0;};
	Orientation orient = Orientation.Row;
	INDArray data;
	public HeapSort(INDArray data, Accessor access) {
		accessor = access;
		this.data = data;
	}
	public int[] buildMaxHeap() {
		
	}
	
	private void heapify(int[] indexes, int heapedOffset) {
		for(int i = indexes.length-1; i > heapedOffset; i--) {
			int parentIndex = parentOfChildIndex(i);
			
		}
			
	}
	
	public int parentOfChildIndex(int childIndex) {
		return (childIndex - 1)/2;
	}
	public int leftChildIndex(int parentIndex) {
		return (parentIndex * 2);
	}
	public int rightChildIndex(int parentIndex) {
		return (parentIndex * 2) + 1;
	}
	
	private double getValue(int index) {
		INDArray d = (orient == Orientation.Row) ? data.getRow(index) : data.getColumn(index);
		return accessor.getValue(d);
	}
	
	private int[] indexify(INDArray data, Orientation orient) {
		int size = (orient == Orientation.Row) ? data.rows() : data.columns();
		int[] indexes  = new int[size];
		for (int i = 0; i < size; i++)
			indexes[i] = i;
		return indexes;
	}
}
