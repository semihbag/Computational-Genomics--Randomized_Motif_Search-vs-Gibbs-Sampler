package algorithm;

public class RandomizedMotifSearch extends Algorithm{

	public RandomizedMotifSearch(int k) {
		super(k);
		System.out.println("RandomizedMotifSearch has been created");
	}

	@Override
	public void updateMotifs() {
		int i = 0;
		for (i = 0; i < this.motifs.size(); i++) {
			this.motifs.set(i, findMostProbableKMer(this.motifs.get(i)));
		}
	}
	
}
