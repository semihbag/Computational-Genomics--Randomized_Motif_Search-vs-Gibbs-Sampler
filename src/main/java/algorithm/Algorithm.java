package algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Algorithm {
	
	private int k;
	private ArrayList<String> dna = new ArrayList<>();
	private ArrayList<String> motifs = new ArrayList<>();
	private ArrayList<String> bestMotifs = new ArrayList<>();
	private int[][] counts;
	private ArrayList<ArrayList<Double>> profile;
	private boolean needNormalize = false;
	private int numberOfIteration;
	
	public Algorithm(int k) {
		this.k = k;
		this.numberOfIteration = 0;
		this.counts = new int[4][k];
		resetCounts();
		setDna();
	}
	
	
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

	
	// fill counts matrix by zero initially
	private void resetCounts() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < this.k; j++) {
				this.counts[i][j] = 0;
			}
		}
	}
	
	// select motif from a line randomly
	public String selectMotifFromLine(String line, int k) {
		int r = (int)(Math.random() * (line.length() + 1 - k));
		return line.substring(r, r + k);
	}

	
	// set motifs
	// RandomizedMotifSearch will use this in every iteration
	// GibbsSampler will use this in first only one time
	public void setMotifs() {
		this.motifs.clear();
		int len = this.dna.size();
		for (String line : dna) {
			motifs.add(this.selectMotifFromLine(line, this.k));
		}
	}
	
	
	// it counts the characters
	// i: index of char in kmers (columns)
	// j: index of kmers (rows)
	// t: tpye of char(A,C,T,G)
	// it sets needNormalize 
	private void updateCounts() {
		int i, j = 0;
		for (i = 0; i < this.k; i++) {
			for (String motif : this.motifs) {
				char nucleotide = motif.charAt(i);
				int index = getNucleotideIndex(nucleotide);
				this.counts[index][i]++;
			}
		}
		
		for (i = 0; i < 4; i++) {
			for (j = 0; j < this.k; j++) {
				if (this.counts[i][j] == 0) {
					needNormalize = true;
					break;
				}
			}
		}
	}
	
	private int getNucleotideIndex(char c) {
	    switch (c) {
	        case 'A': return 0;
	        case 'C': return 1;
	        case 'G': return 2;
	        case 'T': return 3;
	        default: return -1; 
	    }
	}
	
	
	// normalize the count matrix by adding 1 to every cells
	private void normalizeCounts() {
		int i, j = 0;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < this.k; j++) {
				this.counts[i][j]++;
			}
		}
		this.needNormalize = false;
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
