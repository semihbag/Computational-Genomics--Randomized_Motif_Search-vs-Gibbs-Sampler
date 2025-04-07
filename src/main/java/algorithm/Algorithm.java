package algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.w3c.dom.css.CSSRule;

public abstract class Algorithm {
	
	protected int k;
	private double bestScore = Double.MAX_VALUE;
	private double totalScore = 0.0;
	private long duration;
	protected ArrayList<String> dna = new ArrayList<>();
	public ArrayList<String> motifs = new ArrayList<String>();
	private ArrayList<String> bestMotifs = new ArrayList<>();
	private int[][] counts;
	private double[][] profile;
	private boolean needNormalize = false;
	private int numberOfIteration;
	private int sameScoreCount = 0;
	private String consensus;
	private String name;
	private String result;
	
	public Algorithm(int k, String name) {
		this.k = k;
		this.name = name;
		this.numberOfIteration = 0;
		this.counts = new int[4][k];
		this.profile = new double[4][k];
		resetCounts();
		setDna();
		setMotifs();
	}
	
	
	public abstract void updateMotifs();
	
	public void run() {
		long startTime = System.nanoTime();

		updateBestMotifs();
		while (true) {
			updateCounts();
			calculateProfile();
			updateMotifs();
			
			double currentScore = calculateScore(bestMotifs);
			double newScore = calculateScore(motifs);
			
			totalScore += newScore;
			numberOfIteration++;
			
			if (newScore < currentScore) {
				bestScore = newScore;
				sameScoreCount = 0;
				updateBestMotifs();
			}
			else {
				sameScoreCount++;
			}
			
		    if (sameScoreCount >= 10) {
		        break;
		    }
		}
		
		long endTime = System.nanoTime();
		this.duration = endTime - startTime;
		this.result = printResults();
	}

	
	private void updateBestMotifs() {
		int i = 0;
		this.bestMotifs.clear();
		for (i = 0; i < this.motifs.size(); i++ ) {
			String motif = this.motifs.get(i);
			this.bestMotifs.add(motif);
		}
	}
	
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
	private String selectMotifFromLine(String line, int k) {
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
		resetCounts();
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
	
	private char getNucleotideFromIndex(int index) {
	    switch (index) {
	        case 0: return 'A';
	        case 1: return 'C';
	        case 2: return 'G';
	        case 3: return 'T';
	        default: return 'N'; // bilinmeyen durum için
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
	}
	
	
	// calculate profile matrix
	// according to needNormalize, 2x 
	private void calculateProfile() {
		int total = this.motifs.size();
		if (this.needNormalize) {
			normalizeCounts();
			total = total * 2;
		}
		
		int i, j, frequency = 0;
		double res = 0.0;
		
		for (i = 0; i < 4; i++) {
			for (j = 0; j < this.k; j++) {
				frequency = this.counts[i][j];
				res = (double) frequency / total;
				this.profile[i][j] = res;
			}
		}
		
		this.needNormalize = false;
	}
	
	
	
	protected String findMostProbableKMer(String line) {

		double bestProbabilit = 0.0;
		double probability = 1.0;
		String bestKMer = "";
		String kMer = "";
		char c = ' ';
		
		int i = 0;
		int j = 0;
		int index;
		int len = line.length() - this.k + 1;
		
		for(i = 0; i < len; i++) {
			kMer = line.substring(i, i + this.k);
			probability = 1.0;
			for(j = 0; j < this.k; j++) {
				c = kMer.charAt(j);
				index = getNucleotideIndex(c);
				probability =  probability * this.profile[index][j];
			}

			if (probability > bestProbabilit) {
				bestKMer = kMer;
				bestProbabilit = probability;
			}

		}
		
		return bestKMer;
	}
	
	
	// calculating the score
	private int calculateScore(ArrayList<String> motifs) {
        int t = motifs.size();
        int totalScore = 0;

        for (int i = 0; i < this.k; i++) {
            Map<Character, Integer> count = new HashMap<>();
            count.put('A', 0);
            count.put('C', 0);
            count.put('G', 0);
            count.put('T', 0);

            for (String motif : motifs) {
                char c = motif.charAt(i);
                count.put(c, count.get(c) + 1);
            }

            int maxCount = Collections.max(count.values());
            totalScore += (t - maxCount);
        }

        return totalScore;
	}

	public String getConsensusFromCounts() {
	    StringBuilder consensus = new StringBuilder();

	    for (int j = 0; j < k; j++) {
	        int maxCount = -1;
	        int maxIndex = -1;

	        for (int i = 0; i < 4; i++) {
	            if (counts[i][j] > maxCount) {
	                maxCount = counts[i][j];
	                maxIndex = i;
	            }
	        }

	        char nucleotide = getNucleotideFromIndex(maxIndex);
	        consensus.append(nucleotide);
	    }

	    this.consensus = consensus.toString(); // optional: saklamak için
	    return consensus.toString();
	}
	
	private String printMotifs() {
		String str = "";
		str += "==========Motifs==========\n";
		for (String motif : this.motifs) {
			str += motif + "\n";
		}
		str += "==========================\n";
		return str;
	}
	        
	public String printResults() {
		String res = "";
		res += "========================== k: " + this.k + " ==========================\n";
		res += "Algorithm Type: " + this.name + "\n";
		res += "Execution time: " + (duration / 1_000_000.0) + " ms\n";
		res += "Best Score: " + this.bestScore + "\n";
		res += "Avarage Score: " + (this.totalScore / numberOfIteration) + "\n";
		res += "Best Motif:\n";
		res += printMotifs();
		res += "Consensus String: " + getConsensusFromCounts() + "\n";
		
		return res;
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
