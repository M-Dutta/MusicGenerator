package container;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MusicGA {
	
	/**
	 * Specifiers
	 * @author Mishuk
	 *
	 */
	
	static int maxGenerations = 20000;
	int maxChildren = 50; //Per generation
	int maxTopCandidates = 10;
			
	/***********************************/
	public class GAHolder {
		List<Chromosome> chromosomes ;
		List<Double> doubles;
		public GAHolder() {
		this.chromosomes = new ArrayList<Chromosome>();
		this.doubles = new ArrayList<Double>();
		}
		public GAHolder(List<Chromosome> chromosomes, List<Double> d ) {
			this.chromosomes = chromosomes;
			this.doubles = d;
		}
		
		public void insert(Chromosome c) {
			this.chromosomes.add(c);
			this.doubles.add(c.calculateFitness());
		}
		
		public void insertList(List<Chromosome> c) {
			for (Chromosome x : c) {
				this.chromosomes.add(x);
				this.doubles.add(x.calculateFitness());
			}
		}
		public Chromosome getBestFitnessChromosome() {
			int max = this.doubles.indexOf(Collections.max(doubles));
			return chromosomes.get(max);
		}
		public double getBestFitness() {
			return Collections.max(doubles);
		}
		public List<Chromosome> getTopN() {
			List<Chromosome> ch = new ArrayList<Chromosome>(this.chromosomes);
			List<Double> fit = new ArrayList<Double>(this.doubles);
			List<Chromosome> chMax = new ArrayList<Chromosome>();
			for (int i =0; i <maxTopCandidates ; i++) {
				int index = fit.indexOf(Collections.max(fit));
				chMax.add(ch.get(index));
				ch.remove(index); fit.remove(index);
			}
			return ch;
			}
		public List<Chromosome> getTop2() {
				List<Chromosome> ch = new ArrayList<Chromosome>(this.chromosomes);
				List<Double> fit = new ArrayList<Double>(this.doubles);
				List<Chromosome> chMax = new ArrayList<Chromosome>();
				int index = fit.indexOf(Collections.max(fit));
				chMax.add(ch.get(index));
				ch.remove(index); fit.remove(index);
				index =  fit.indexOf(Collections.max(fit));
				chMax.add(ch.get(index));
				ch.remove(index); fit.remove(index);
			return chMax;
			
			
		}
		
		
	}
	static int [][] trainedFitness;
	
	public int [] LinetoIntconverter(String s) {
		String [] temp = s.split(" ");
		int [] ret = new int[temp.length];
		for (int i=0; i < temp.length; i++) 
			ret[i] = Integer.parseInt(temp[i]);
		
		return ret;
		}
	
	
	Random r = new Random();
	
	//uniform crossover--each parent gets an equal chance at each note
	public Chromosome uniformCrossover(Chromosome parent1, Chromosome parent2){
		Chromosome child = new Chromosome(parent1.getGene(),trainedFitness);
		child.setLength();
		child.setTrainedFitness(trainedFitness);
		child.calculateFitness();
		
		for(int i=0; i<parent1.getLength() ;i++) {//go through each note 
			int x = r.nextInt(10);

			if(x<5)  //if less than 5 get from first parent			
				child.gene[i] = parent1.gene[i];
			
			else 		
				child.gene[i] = parent2.gene[i];	
		}
			return child;
	}
		public Chromosome mutator(Chromosome child, double probability, Chromosome Parent ) {
			
			boolean yes = false;
			if (Math.random() <=probability)
				yes = true;
			int b = r.nextInt(Parent.getLength()-72);
			for (int i =b; i < b+72; i++) {
				child.gene[i] = Parent.gene[i];
			}
			return child;
		}
		
		public Chromosome bitCrossover(Chromosome parent1,Chromosome parent2) {
			Chromosome child = new Chromosome();
			child.setGene(parent1.getGene());
			child.setLength();
			child.setTrainedFitness(trainedFitness);
			//child.calculateFitness();
			for (int i =0; i < child.getLength(); i++) {
				int x = r.nextInt(2);
				if (x==0)
					child.gene[i] = parent2.gene[i];
			}
			child.calculateFitness();
			return child;
			
		}

		
		public List<Chromosome> crossover(Chromosome parent1,Chromosome parent2) {
			parent1.setLength(); parent1.setTrainedFitness(trainedFitness);parent1.calculateFitness();
			parent2.setLength(); parent2.setTrainedFitness(trainedFitness);parent2.calculateFitness();
			GAHolder h = new GAHolder();
			Chromosome temp = parent2;
			for (int i=0; i<maxChildren; i++) {
				int a = r.nextInt(2);
				if (a ==0) temp = parent1;
				Chromosome child = mutator(bitCrossover(parent1, parent2),0.30,temp);
				child.setLength();
				child.setTrainedFitness(trainedFitness);
				child.calculateFitness();
				h.insert(child);
			}
			return h.getTopN();
		}
		
		/*
		 * randomly mate from top
		 */
		public List<Chromosome> randomShuffle(List<Chromosome> g) {
			List<Integer> ct=new ArrayList<Integer>(Arrays.asList(-1,-1));
			GAHolder h = new GAHolder();
			Random rand = new Random();
			
			int p1Loc=-1; int p2Loc=-1;
			for (int i=0; i< g.size(); i++) {
				while (ct.contains(p1Loc) || ct.contains(p2Loc)) {
				 p1Loc = rand.nextInt(g.size());
				 p2Loc = rand.nextInt(g.size());
				}
				h.insertList(crossover(g.get(p1Loc),g.get(p2Loc)));
				p1Loc=-1; p2Loc=-1;
			}
			
			//System.out.println(h.getBestFitness());
			return h.getTop2();
			
		}
	
		public List<Chromosome> runGAnonRecursive(List<Chromosome> c, int CurrentGeneration, int end) {
			for (int i =0; i < end;i++) {
				List<Chromosome> h = randomShuffle(c);
				c= h;
			}
			return c;
		}
		public List<Chromosome> runGA(List<Chromosome> c, int CurrentGeneration, int end) {
			List<Chromosome> h = randomShuffle(c);
			if (CurrentGeneration == end)
				return h;
			CurrentGeneration = CurrentGeneration+1;
			System.out.println(CurrentGeneration);
			return runGA(h,CurrentGeneration,end);
		}
		
		public static void printout(String file) {
			
		}
		public static void main(String [] arg) throws IOException {
			MusicGA music = new MusicGA();
			Utility u = new Utility();
			trainedFitness = u.loadTrainedFitness("src/container/fitness.txt");
			Chromosome c1 = u.loadParent("src/container/parent1.txt");
			Chromosome c2 = u.loadParent("src/container/parent2.txt");
			c1.setTrainedFitness(trainedFitness);
			c2.setTrainedFitness(trainedFitness);
			c1.calculateFitness();
			c2.calculateFitness();
			List<Chromosome> c = new ArrayList<Chromosome>();
			c.add(c1);c.add(c2);
			System.out.println("being");
			List<Chromosome> temp = new ArrayList<Chromosome>();
			for (int i =0; i <50; i++) {
				Chromosome cr =music.bitCrossover(c1, c2);
				temp.add(cr);
			}
			System.out.println("****");
			List<Chromosome> res =music.runGAnonRecursive(c, 0, 500);
			System.out.println(res.size());
			u.writeToFile(res.get(0), "ga1.txt");
		}

}

