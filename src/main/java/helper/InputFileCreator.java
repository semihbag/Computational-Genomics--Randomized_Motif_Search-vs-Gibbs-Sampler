package helper;

import java.util.ArrayList;
import java.util.Random;

public class InputFileCreator {
	
	public boolean createRandomInputFile (int k) {
		// k is kmer lenght
		// to do

		return false;
	}
	
	// hem dna string için hem de kmer üretmek için kullanacağız
	private String createRandomString(int k) {
		String chars= "AGCT";
		Random randomIndex= new Random();
		StringBuilder randomString= new StringBuilder(); 

		while (randomString.length() < k) { 
            int index = randomIndex.nextInt(chars.length());
            randomString.append(chars.charAt(index));
        }
		System.out.println(randomString.toString());
		return randomString.toString();
	}
	
	private String mutation(String kMer, int n) {
		//
		return null;
	}
	
	private String insertMutation(String dnaString, String mutatedKMer) {
		// 
		return dnaString;
	}
	
	
}
