import algorithm.RandomizedMotifSearch;
import helper.InputFileCreator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("heeellllooğ world");
		InputFileCreator i = new InputFileCreator();
		int k = 9;

		try {
			i.createRandomInputFile(k);


		} catch (Exception e) {
			System.out.println(e);
		}
		RandomizedMotifSearch r = new RandomizedMotifSearch(k);
		r.run();
		
	}

}
