
package index;

/**
 *
 * @author loofoong
 */
public class SortedDocScore extends DocScore implements Comparable{

    public SortedDocScore(double score, int doc_id, String content) {
        super(score, doc_id, content);
    }
    
    public SortedDocScore(DocScore ds) {
        super(ds);
    }

    @Override
    public int compareTo(Object o) {
        //examples from project 4
//         public int compareTo(Object o) {
//        if (o instanceof ElementSet){
//            ElementSet e = (ElementSet) o;
//            return this._id - e._id;//CompareTo method must return negative number if current object is less than other object, positive number if current object is greater than other object and zero if both objects are equal to each other.
//        }else{
//            return 0;//if two objects are equal via equals() , there compareTo() must return zero otherwise if those objects are stored in SortedSet or SortedMap they will not behave properly.
//        }
//    }
        
        if(!(o instanceof DocScore)){
            return 1;
        }
        DocScore ds = (DocScore)o;
        if(this._score > (ds._score)){
            return -1;
        }
        if(this._score < (ds._score)){
            return 1; 
        }
        if(!(this._content.equals(ds._content))){
            return this._content.compareTo(ds._content);//return compare to of strings //function as equals????????
        }
        return 0;//objects are equal
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DocScore)){
            return false;
        }
            DocScore ds = (DocScore)o;
        return this._score == ds._score && this._content == ds._content;//check both score and content to see if they are equal
        }
    }
    
    

