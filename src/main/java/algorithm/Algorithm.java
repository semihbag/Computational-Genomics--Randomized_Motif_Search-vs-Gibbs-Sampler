package algorithm;

import java.util.ArrayList;

public abstract class Algorithm {
	
	private int k;
	private ArrayList<String> dna;
	private ArrayList<String> motifs;
	private ArrayList<String> bestMotifs;
	private ArrayList<ArrayList<Integer>> counts;
	private ArrayList<ArrayList<Double>> profile;
	private boolean needNormalize = false;
	
	public Algorithm(int k) {
		this.k = k;
		setDna();
		
	}
	
	
	public abstract void setMotifs();
	public abstract void run();
	
	private void setDna() {
		// 
	}

	private String selectMotifFromLine(String line, int k) {
		// 
		return null;
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

	
}
