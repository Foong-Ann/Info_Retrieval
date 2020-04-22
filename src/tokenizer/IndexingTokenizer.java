
package tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexingTokenizer implements Tokenizer{
    		// Note: can see following page for special characters in Java Regex:
		//       https://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
    
    @Override
    public ArrayList<String> tokenize(String s) {
        ArrayList<String> ret = new ArrayList<String>();//create a Arraylist for return
        Pattern p = Pattern.compile("\\w[-\\w]*");
        Matcher m = p.matcher(s);//copy from SimpleTokenizer
        while (m.find()) {
                ret.add(m.group().toLowerCase());//lowercases all tokens
			// Equivalently we could also say: ret.add(s.substring(m.start(), m.end()));
        }
	return ret;
    }
    //test
    public static void main(String[] args){
        String s = "SoftBank is buying a chunk of Uber and it's state-of-the-art hailing system";
        IndexingTokenizer IT = new IndexingTokenizer();
        System.out.println(IT.tokenize(s));
    }
}
