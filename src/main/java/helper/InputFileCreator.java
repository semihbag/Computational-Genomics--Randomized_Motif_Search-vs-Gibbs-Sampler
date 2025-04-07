package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class InputFileCreator {
	
	public String kmerFinal;
	
	public boolean createRandomInputFile (int k) throws IOException {
		// k is kmer lenght
		// to do
		File file = new File("src/main/java/input.txt");
		FileWriter writer = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		String nineMer= createRandomString(k); //create key with given k lenght

		String coloredKMer = "\033[31m" + nineMer + "\033[0m"; // Red color
		System.out.println("Generated kmer: " + nineMer);
		
		kmerFinal = "Generated kmer: " + coloredKMer;
		for(int i=0; i<10; i++){  //for 10 lines
			String line= createRandomString(500);  // lenght of line is 500
			String mutatedStr= mutation(nineMer,4); //key mutated for every line
			String a = insertMutation(line, mutatedStr);  //insert mutatedkey to actually line
			bufferedWriter.write(a);
			bufferedWriter.newLine();
		}

		bufferedWriter.close();
        return file.exists();
	}
	
	// hem dna string için hem de kmer üretmek için kullanacağız
	public String createRandomString(int k) { 
		StringBuilder randomString= new StringBuilder(); 
		while (randomString.length() < k) { 
            randomString.append(createRandomChar());
        }
		return randomString.toString();
	}
	
	private char createRandomChar() {
		String chars = "AGCT";
		Random random = new Random();
		return chars.charAt(random.nextInt(chars.length()));
	}
	
	public String mutation(String kMer, int n) {
		//
		//nextInt(kmer.lengt)

		StringBuilder sb = new StringBuilder(kMer);
		Random randomIndex= new Random();
		Set<Integer> markedIndexes = new HashSet<>(); //degisen index tekrar degismesin diye hashset kullanildi
		int index; 
		while(markedIndexes.size()<n){  
			index = randomIndex.nextInt(kMer.length());
			if(markedIndexes.add(index)){  //eger yeni bir index geldi ise yani yeni bir konum belirlendi
				char currentChar= kMer.charAt(index);
				char mutatedChar;
                do{
					mutatedChar= createRandomChar();
					//karakteri değiştir dönen karakter!= current ise biter döngü
				}while(mutatedChar == currentChar);
				sb.setCharAt(index, mutatedChar);
			}
		}

		return sb.toString();
	}
	
	private String insertMutation(String dnaString, String mutatedKMer) {
		StringBuilder sb = new StringBuilder(dnaString);
		Random randomIndex= new Random();
		int index = randomIndex.nextInt(dnaString.length()-mutatedKMer.length());

		// String coloredKMer = "\033[31m" + mutatedKMer + "\033[0m"; // Red color
		// sb.insert(index, coloredKMer);
		//System.out.println(sb.toString());
		
		sb.insert(index, mutatedKMer);
		return sb.toString();
	}
	
	
}
