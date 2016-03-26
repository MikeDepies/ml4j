package org.depies.ml.ml4j;

import java.util.Arrays;

import org.deeplearning4j.datasets.fetchers.IrisDataFetcher;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.rng.DefaultRandom;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        IrisDataFetcher fetcher = new IrisDataFetcher();
        fetcher.fetch(150);
        DataSet ds = fetcher.next();
        KNN knn = new KNN();
        INDArray features = ds.getFeatures();//knn.dataSetToComponents(dataset, 6)[0];
        INDArray labels = ds.getLabels();//dataset.getColumn(6);
        System.out.println(Arrays.toString(labels.shape()));
        System.out.println(binaryToCategory(labels));
        INDArray neighborhood = knn.neighborhood(ds.getFeatures().getRow(80), features, binaryToCategory(labels));
        System.out.println(neighborhood);
        int labelForNeighborhood = new AggregationPolicy().majorityRules(neighborhood, 4, 5);
        System.out.println(labelForNeighborhood);
        
        INDArray test = Nd4j.rand(25, 1, 0, 10, new DefaultRandom());
        System.out.println(test);
        System.out.println(categoryToBinary(test, (int) (double) test.maxNumber()));
        System.out.println(binaryToCategory(categoryToBinary(test, (int) (double) test.maxNumber())));
    }
    public static INDArray categoryToBinary(INDArray category, int maxCategory) {
    	if (!category.isColumnVector())
    		throw new RuntimeException();
    	int rows = category.rows();
    	INDArray binaryArray = Nd4j.zeros(rows, maxCategory + 1);
    	for(int i =0; i < rows; i++)
    		binaryArray.put(i, category.getInt(i), 1);
    	return binaryArray;
    }
    
    public static INDArray binaryToCategory(INDArray binary) {
    	int rows = binary.rows();
    	int cols = binary.columns();
    	INDArray categoryArray = Nd4j.zeros(rows,  1);
    	
    	for(int i =0; i < rows; i++)
    		for(int c=0; c < cols; c++) {
    			int value = binary.getInt(i, c); 
    			if (value == 1) {
    				categoryArray.put(i, 0, c);
    				break;
    			}
    		}
    	return categoryArray;
    }
}
