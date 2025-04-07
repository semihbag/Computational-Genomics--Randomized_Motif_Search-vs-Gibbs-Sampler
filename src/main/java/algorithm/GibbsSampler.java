package algorithm;

import java.util.ArrayList;

public class GibbsSampler extends Algorithm {

	public GibbsSampler(int k, String name) {
		super(k, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateMotifs() {
		int r = (int)(Math.random() * (this.dna.size()));
		this.motifs.set(r, findMostProbableKMer(this.dna.get(r)));
	}
}
