package score;

import index.Index;

interface TermScoringFun {

	public abstract void init(Index s);
	public abstract double scoreToken(String term, int freq);
}
