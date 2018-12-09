package container;

public class Chromosome extends Utility {
	
	double fitness;
	int[][] gene;
	int[][] trainedFitness;
	int len;
	public Chromosome() {	
	}
	
	public Chromosome(int [][] a,int [][] trainedFitness) {
		this.trainedFitness = trainedFitness;
		this.gene = a;
		this.fitness = -1;
		this.len = a.length; //need to manually set it every time while initializing
	}

	public double calculateGeneFitness() {
		double actualFitness = 0;
		double note = 0.5;
		double velocity = 0.25;
		double time =0.2;
		int length = trainedFitness.length;
		int[][] geneFitness = geneDifference(this.gene);
		if (geneFitness.length!= trainedFitness.length) {
			System.out.println("\n*****\nWARNING. Check GeneFitness and TrainedFitness Length. "
					+ "\n*****Truncating for now\n*****");
			System.out.println("GeneFitness Length ="+ geneFitness.length);
			System.out.println("GeneFitness Length ="+ geneFitness.length+"\n*****\n*****");
			}
		int [][] geneAndTrainedDifference = arrayOfArraySubtraction(geneFitness, trainedFitness);		
		for ( int i =0; i <length; i++ ) {
			actualFitness += 
					(double) (geneAndTrainedDifference[i][1])*note + (double) (geneAndTrainedDifference[i][2])*velocity+
					(double) (geneAndTrainedDifference[i][3])*time;
			
		}
		return (double) actualFitness/length;
		
	}
	public int getLength() {
		return this.len;
	}
	
	public double calculateFitness() {
		this.fitness = calculateGeneFitness();
		return fitness;
	}
	public double getFitness() {
		//calculateFitness()
		return fitness;	
	}
	
	public int[][] getGene() {
	return gene;	
	}
	
	public void setFitness(double x) {
		this.fitness = x;
	}
	
	public void setGene(int [][] x) {
		this.gene = x;
	}
	
	public void setLength() {
		this.len = gene.length;
	}
	
	public void setTrainedFitness( int[][] t) {
		this.trainedFitness = t;
	}
	/**
	 * Helpers
	 * @param a
	 * @return
	 */
	
	
	public int[][] geneDifference( int [][] a) {
		int length  = a.length;
		int [][] ret = new int [length-1][a[0].length];
		for (int i =1 ;i <length-1; i++ )  {
			ret[i] = arraySubtraction(a[i-1],a[i]);
		}
		return ret;
		
	}
	public int[][] arrayOfArraySubtraction(int[][] a, int[][] b) {
		int [][] ret = new int[a.length][a[0].length];
		int length = a.length;
		for (int i =0 ;i <length; i++ ) 
			ret[i] = arraySubtraction(a[i],b[i]);
		return ret;
		
	}
	public int[] arraySubtraction(int [] a, int [] b) {
		int length = a.length;
		int ret[] = new int [length];
		for (int i =0; i <length; i++)
			ret[i] = Math.abs(a[i]-b[i]);
		return ret;
	}
	
	
	
	


}
