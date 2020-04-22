
package index;

import io.DocSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import score.TermScoringFun;
import tokenizer.Tokenizer;

/**
 *
 * @author loofoong
 */
public class InvertedIndex extends Index{
    private HashMap<String,HashMap<Integer,Integer>> _index;//HashMap<String term>, HashMap<Integer docID, Integer term_freq>
    private HashMap<String,Integer> _docFreq;//HashMap<String term, Integer freq>
    
    public InvertedIndex(DocSource doc_source, Tokenizer tokenizer, TermScoringFun scoring) {
        super(doc_source, tokenizer, scoring);
        _index = new HashMap<String,HashMap<Integer,Integer>>();//initialize 
        _docFreq = new HashMap<String,Integer>();
    }

    @Override
    public void buildIndex() {// Index all files in DocSource
        ArrayList<String> _tokens = new ArrayList<String>();
        for(int i = 0; i < _docSource.getNumDocs(); i++){
            _tokens.addAll(_tokenizer.tokenize(_docSource.getDoc(i)));//build tokenlizer for the current doc
            for(String s : _tokens){
                if(!_index.containsKey(s)){
                    _index.put(s, new HashMap<Integer,Integer>());//if index does not contain token then make a new hashmap for the doc
                    _index.get(s).put(i, 1);//make the frequency 1
                    _docFreq.put(s, 1);
                }else{
                    if(_index.get(s).get(i)== null){//if contain token but no Hashmap
                        _index.get(s).put(i, 1);//make term frequncy ferquency be 1
                        _docFreq.put(s, _docFreq.get(s)+ 1);//add doc frequency by 1
                    }else{
                        int frequency = _index.get(s).get(i);
                        _index.get(s).put(i, frequency + 1);
                    }
                }
            }
            _tokens.clear();
        }
    }

    @Override
    public Integer getDocumentFreq(String term) {// Return document frequency of the term
        return _docFreq.get(term);
    }

    @Override
    public ArrayList<DocScore> getSortedSearchResults(String query) {// Return a ranked list of search results for the provided query
        //go through terms in tokenized query then go through documents, get freq of token in document, using TermScoringfun to score token and add to total score
        ArrayList<DocScore> result = new ArrayList<DocScore>();
        HashMap<Integer, Double> _docScore = new HashMap<Integer, Double>();
        TreeSet<SortedDocScore> _sortedScore = new TreeSet<SortedDocScore>();
        ArrayList<String> _tokens = new ArrayList<String>();
        _tokens.addAll(_tokenizer.tokenize(query));
        for (String s : _tokens){
            if (_index.get(s) == null){
                continue;
            }else{
                for(int i : _index.get(s).keySet()){
                    if(_docScore.containsKey(i)){
                        _docScore.put(i, _docScore.get(i) + _scoring.scoreToken(s, _index.get(s).get(i)));//set score
                    }else{//add the key
                        _docScore.put(i, _scoring.scoreToken(s, _index.get(s).get(i)));
                    }
                }
            }
        }
        for (int i : _docScore.keySet()){
            SortedDocScore ds = new SortedDocScore(_docScore.get(i), i, _docSource.getDoc(i));
            _sortedScore.add(ds);//sort score
        }
        result.addAll(_sortedScore);
        return result;//return the sorted arraylist
    }
    
}
 
