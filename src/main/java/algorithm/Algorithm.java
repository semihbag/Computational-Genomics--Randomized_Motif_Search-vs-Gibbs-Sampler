package algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Algorithm {
	
	private int k;
	private ArrayList<String> dna = new ArrayList<>();
	private ArrayList<String> motifs;
	private ArrayList<String> bestMotifs;
	private ArrayList<ArrayList<Integer>> counts;
	private ArrayList<ArrayList<Double>> profile;
	private boolean needNormalize = false;
	private int numberOfIteration;
	
	public Algorithm(int k) {
		this.k = k;
		this.numberOfIteration = 0;
		setDna();
	}
	
	
	public abstract void setMotifs();
	public abstract void run();
	
	
	// when an algorithm created, set the dna lines from input file
	private void setDna() {
		try {
			File file = new File("../input.txt");
			Scanner s = new Scanner(file);
			
			while (s.hasNextLine()) {
				this.dna.add(s.nextLine());
			}
			s.close();
		}
		catch (Exception e) {
			System.err.println("An error occured in Algorithm/setDna()");
			System.out.println(e);
		}
	}

	
	// select motif from a line randomly
	public String selectMotifFromLine(String line, int k) {
		int r = (int)(Math.random() * (line.length() + 1 - k));
		return line.substring(r, r + k);
	}

	
	
	private void updateCounts() {
		// use this.motifs
		// update this.counts
		// set this.needNormalize
	}
	
	private void normalizeCounts() {
		// use this.counts
		// update this.counts matrix
		// set needNormalize flag false at the end
	}
	
	private void calculateProfile() {
		// use this.motifs
		// update this.profile matrix
	}
	
	private String findMostProbableKMer (String line, int k) {
		// use this.profile
		return null;
	}
	
	private int calculateScore(ArrayList<String> motifs) {
		//
		return 0;
	}

	private void addIteration() {
		setNumberOfIteration(getNumberOfIteration() + 1);
	}

	public int getNumberOfIteration() {
		return numberOfIteration;
	}

	public void setNumberOfIteration(int numberOfIteration) {
		this.numberOfIteration = numberOfIteration;
	}
	
	
	
}
