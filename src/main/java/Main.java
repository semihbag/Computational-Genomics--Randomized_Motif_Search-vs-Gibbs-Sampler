import java.util.ArrayList;

import algorithm.GibbsSampler;
import algorithm.RandomizedMotifSearch;
import helper.InputFileCreator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InputFileCreator ic = new InputFileCreator();

		try {
			int[] listK  = {8,9,10};
			
			for (int i = 0; i < listK.length; i++) {
				System.out.println("========================================================================\n");
				ic.createRandomInputFile(listK[i]);
				
				RandomizedMotifSearch r = new RandomizedMotifSearch(listK[i], "RandomizedMotifSearch");
				GibbsSampler g = new GibbsSampler(listK[i], "GibbsSampler");
				
				r.run();
				System.out.println(r.printResults());
				g.run();
				System.out.println(g.printResults());
				
			}
			

		} catch (Exception e) {
			System.out.println(e);
		}

		int k = 9;


		

		
	}

}
