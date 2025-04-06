package helper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
		int index;
		while (randomString.length() < k) { 
            index = randomIndex.nextInt(chars.length());
            randomString.append(chars.charAt(index));
        }
		System.out.println(randomString.toString());
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
				char mutaitedChar;
                do{
					mutaitedChar= createRandomChar();
					//karakteri değiştir dönen karakter!= current ise biter döngü
				}while(mutaitedChar == currentChar);
				sb.setCharAt(index, mutaitedChar);
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	private String insertMutation(String dnaString, String mutatedKMer) {
		// 
		return dnaString;
	}
	
	
}
