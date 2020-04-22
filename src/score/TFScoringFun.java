package score;

import index.Index;

public class TFScoringFun implements TermScoringFun {

	@Override
	public void init(Index s) {
	}

	@Override
	public double scoreToken(String term, int freq) {
		return (double)freq; 
	}

}
