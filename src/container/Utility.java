package container;

import java.io.BufferedReader; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {

	
	int parent =700;
	int fitness = 699;
	/**
	 * load a Parent using this
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public void writeToFile (Chromosome c,String file) throws IOException {
		int length = c.getLength();
		int gene[][] = c.getGene();
		FileWriter fw = new FileWriter("src/container/"+file);
		for (int i = 0; i < length; i++) {
			fw.write(gene[i][0]+" "+gene[i][1]+" "+gene[i][2]+" "+gene[i][3]+ "\n");
		}
		fw.close();
		
	}
	
	public Chromosome loadParent(String file) throws IOException {
		int [][] list = new int [parent][3];
		BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    int i=0;
		    while ((line = br.readLine()) != null && i <parent) {
		    	list[i] = (LinetoIntconverter(line)); 
		    	//System.out.println(list[i][0]+" "+list[i][1]+" "+list[i][2]+" "+list [i][3]);
		    i++;
		    }
		    br.close();
		    Chromosome c = new Chromosome();
		    c.setGene(list);
		    c.setLength();
		    i=1;
		   // System.out.println(list[i][0]+" "+list[i][1]+" "+list[i][2]+" "+list [i][3]);
			//System.out.println(c.gene[i][0]+" "+c.gene[i][1]+" "+c.gene[i][2]+" "+c.gene[i][3]);
		    return c;
	}
	
	/**
	 * Load the trained Fitness that we got using this
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public int[][] loadTrainedFitness(String file) throws IOException {
		int [][] list = new int [fitness][3];
		BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    int i=0;
		    while ((line = br.readLine()) != null && i <fitness) {
		    	list[i] = (LinetoIntconverter(line));
		    	i++;
		    }
		    br.close();
			return list;
	}
	
	public int [] LinetoIntconverter(String s) {
		String [] temp = s.split(" ");
		int [] ret = new int[temp.length];
		for (int i=0; i < temp.length; i++) 
			ret[i] = Integer.parseInt(temp[i]);
		//System.out.println(ret[0]+" "+ret[1]+" "+ret[2]+" "+ret[3]);
		return ret;
		}
	
	public void printGene(Chromosome c) {
		int[][] gene = c.getGene();
		for (int i =0; i < c.len;i++)
		System.out.println(c.gene[i][0]+" "+c.gene[i][1]+" "+c.gene[i][2]+" "+c.gene[i][3]);
	}
	public void printGeneN5(Chromosome c) {
		int[][] gene = c.getGene();
		for (int i =100; i < 5;i++)
		System.out.println(c.gene[i][0]+" "+c.gene[i][1]+" "+c.gene[i][2]+" "+c.gene[i][3]);
	}
	
	
	public void printNestedArray(int [][] gene) {
		for (int i =0; i < gene.length ; i++) {
			System.out.println(gene[i][0]+" "+gene[i][1]+" "+ gene[i][2]+" "+gene[i][3]);
		}
	}
	
}
