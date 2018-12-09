package container;

import java.io.IOException;

public class tester {
	
	public static void main (String[] arg) throws IOException {
	Utility u = new Utility();
	Chromosome c1 = u.loadParent("src/container/parent2.txt") ;
	System.out.println(c1.gene[0][1]);
	c1.gene[0][1] = 100;
	System.out.println(c1.gene[0][1]);
	u.printGene(c1);
	
	//u.writeToFile(c1, "afterGa.txt");
		}
	}
