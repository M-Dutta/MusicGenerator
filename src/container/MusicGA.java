package container;

import java.io.IOException;
import java.util.ArrayList;

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
	static int maxChildren = 50; //Per generation
	int maxTopCandidates = 10;

	/***********************************/
	public class GAHolder {
		List<Chromosome> chromosomes ;
		List<Double> doubles;
		public GAHolder() {
		this.chromosomes = new ArrayList<>();
		this.doubles = new ArrayList<>();
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

		public List<Chromosome> getChromosomes() {
			return this.chromosomes;
		}
		public Chromosome getBestFitnessChromosome() {
			int max = this.doubles.indexOf(Collections.max(doubles));
			return chromosomes.get(max);
		}
		public double getBestFitness() {
			return Collections.max(doubles);
		}

		public List<Chromosome> getTopN(int n) {
			List<Chromosome> ch = new ArrayList<>(this.chromosomes);
			List<Double> fit = new ArrayList<>(this.doubles);
			List<Chromosome> chMax = new ArrayList<>();
			for (int i =0; i <n ; i++) {
				int index = fit.indexOf(Collections.min(fit));
				chMax.add(ch.get(index));
				ch.remove(index); fit.remove(index);
			}
			return chMax;
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
			double val = Math.random();
			if (val <=probability) {
					child = bitCrossover(child,Parent);
				}
			return child;
		}

		//Enhaced Uniform Crossover
		public Chromosome bitCrossover(Chromosome parent1,Chromosome parent2) {
			Chromosome child = new Chromosome();
			child.setGene(parent1.getGene());
			child.setLength();
			child.setTrainedFitness(trainedFitness);
			//child.calculateFitness();
			for (int i =0; i < child.getLength(); i++) {
				int x = r.nextInt(2);
				if (x==0)
					child.getGene()[i] = parent2.getGene()[i];
			}
			child.calculateFitness();
			return child;

		}


		public List<Chromosome> crossover(Chromosome parent1,Chromosome parent2) {
			parent1.setLength(); parent1.setTrainedFitness(trainedFitness);parent1.calculateFitness();
			parent2.setLength(); parent2.setTrainedFitness(trainedFitness);parent2.calculateFitness();
			GAHolder h = new GAHolder();
			Chromosome temp = parent2;
			//for (int i =0; i <maxChildren;i++) {
				int a = r.nextInt(2);
				if (a == 0) temp = parent1;
				Chromosome child = mutator(bitCrossover(parent1, parent2), 0.1, temp);
				//Chromosome child2 = mutator(bitCrossover(parent2, parent1),0.1,temp);
				child.setLength();
				child.setTrainedFitness(trainedFitness);
				child.calculateFitness();
				h.insert(child);
				//h.insert(child2);
			//}
			return h.getChromosomes();
		}

		/*
		 * randomly mate from top
		 */
		public List<Chromosome> randomShuffle(List<Chromosome> g) {

			List<Integer> ct1=new ArrayList<>();
			List<Integer> ct2=new ArrayList<>();
			GAHolder h = new GAHolder();
			Random rand = new Random();
/**
			for (int i=0; i< g.size()-1; i++) {
				for (int y =i+1 ;y<g.size(); y++) {
					List<Chromosome> childList = crossover(g.get(i), g.get(y));
					h.insertList(childList);
				}
			}
            */  h.insertList(g);
                for (int i=0; i< maxChildren; i++) {

 				int k =0 ;int l = 0;
 				while (k==l)
					 k = rand.nextInt(g.size());  l = rand.nextInt(g.size());
				h.insertList( crossover(g.get(k), g.get(l)));
    			}

			return h.getTopN(30);
		}

		public List<Chromosome> runGAnonRecursive(List<Chromosome> c, int CurrentGeneration, int end) {
			for (int i =0; i < end;i++) {
				List<Chromosome> h = randomShuffle(c);
				c= h;
				GAHolder hold = new GAHolder();
				hold.insertList(c);
				System.out.println(hold.getBestFitness());
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


		public static void main(String [] arg) throws IOException {

			int parents = 30;
			MusicGA music = new MusicGA();
			Utility u = new Utility();
			trainedFitness = u.loadTrainedFitness("src/container/fitness.txt");
			System.out.println(trainedFitness.length);
			List<Chromosome> c = new ArrayList<>();
			for (int i =1; i <parents;i++) {

                if (i != 10 && i !=11 && i!=24 && i!=26) {
                    Chromosome cl = u.loadParent("src/parents/".concat(String.valueOf(i)).concat(".txt"));
                    cl.setTrainedFitness(trainedFitness);
                    cl.setLength();
                    cl.calculateFitness();
                    c.add(cl);
                }
            }
			System.out.println("****");
			maxChildren =30+10 ;
			List<Chromosome> res =music.runGAnonRecursive(c, 0, 50);
			System.out.println(res.size());
			u.writeToFile(res.get(0), "ga1.txt");
			u.printGeneCut(res.get(0).getGene()[0]);
		}

}

