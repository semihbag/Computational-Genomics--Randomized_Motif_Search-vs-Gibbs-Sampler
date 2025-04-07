package algorithm;

public class RandomizedMotifSearch extends Algorithm{

	public RandomizedMotifSearch(int k, String name) {
		super(k, name);
	}

	@Override
	public void updateMotifs() {
		int i = 0;
		for (i = 0; i < this.motifs.size(); i++) {
			this.motifs.set(i, findMostProbableKMer(this.dna.get(i)));
		}
	}
	
}
