package test;

import java.util.ArrayList;

import index.DocScore;
import index.Index;
import tokenizer.SimpleTokenizer;
import tokenizer.Tokenizer;

/** A file to help you start testing.
 * 
 * Note that because classes are being used that have the same name but different
 * packages, we are not importing some classes, but rather referring to them by
 * their fully qualified package names to avoid ambiguity in whether the soln
 * or your packages are being used.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public class TestSearch {

	public final static int MAX_RESULTS = 100;
	
	// Test the above code
	public static void main(String[] args) {

		// Test tokenizer
		Tokenizer tok = new SimpleTokenizer();
		System.out.println("\nTokenize results: " + tok.tokenize("SoftBank is buying a chunk of Uber and it's state-of-the-art Taxi-hailing system for $10 billion"));
		
		// Build a simple search index with the basic classes given
		TestIndex(new soln.index.InvertedIndex(new io.StaticDocSource(), 
				  							   new tokenizer.SimpleTokenizer(), 
				  							   new score.TFScoringFun()));
		
		TestIndex(new soln.index.InvertedIndex(new soln.io.FileDocSource("files/Part1/awards_1994"), 
											   new soln.tokenizer.IndexingTokenizer(), 
											   new soln.score.TFIDFScoringFun()));

		TestIndex(new index.InvertedIndex(new io.FileDocSource("../Part1"), 
			                          new tokenizer.IndexingTokenizer(), 
						  new score.TFIDFScoringFun()));
	}

	public static void TestIndex(Index s) {
		
		// Build the search index
		long ms_start = System.currentTimeMillis();
		s.buildIndex();
		long ms_end = System.currentTimeMillis();
		System.out.println("\n>> Built " + s.getClass() + " index in " + (ms_end - ms_start) + " ms.");
		
		System.out.println("\n>> Index contents: " + s);
		
		// Do a few queries
		ms_start = System.currentTimeMillis();
		DoSearch(s, "Bitcoin");
		DoSearch(s, "billion");
		DoSearch(s, "computer equipment");
		//DoSearch(s, "at to of by");
		ms_end = System.currentTimeMillis();
		System.out.println("\n>> Completed searches in " + (ms_end - ms_start) + " ms.");
		System.out.flush(); // If doing a lot of printing, flush the buffer so we don't wait for output
	}

	// Simple search helper method
	public static void DoSearch(Index s, String query) {
		
		System.out.println("\n>> Query: '" + query + "'");
		
		ArrayList<DocScore> doc_scores = s.getSortedSearchResults(query);
		
		for (int i = 0; i < doc_scores.size() && i < MAX_RESULTS; i++)
			System.out.println("[Rank " + (i+1) + "]:" + doc_scores.get(i));
		
		if (doc_scores.size() > MAX_RESULTS)
			System.out.println("Results output truncated, total results = " + doc_scores.size());
	}
}
