import org.limewire.collection.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DomainManager {
    PatriciaTrie<String, String> trie ;
	
	public DomainManager(){
	    trie = new PatriciaTrie<String, String>(new CharSequenceKeyAnalyzer());
		try{
			FileInputStream fstream = new FileInputStream("src/domain_classifications.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String domain, category, cur_cat_list;
			String[] domainCategory;
			while ((strLine = br.readLine()) != null)   {
				if (strLine.length() == 0 || strLine.charAt(0) == ' ')
					break;
				domainCategory = getDomainCategory(strLine);
				domain = domainCategory[0];
				category = domainCategory[1];
				if (domain != null && category != null){
					if (trie.get(domain) == null){
						trie.put(domain, category+" ");
					}else {
						cur_cat_list = trie.get(domain);
						if (cur_cat_list.indexOf(" "+category+" ") < 0 && (cur_cat_list.indexOf(category+" ") != 0))
							// makes sure categories for each domain remains unique
							trie.put(domain, trie.get(domain) + " "+category+" ");
					}
				}
			}
			in.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public String[] getDomainCategory(String strLine){
		String[] URLCategory = {null, null};
		int equalIndex = strLine.indexOf("=");
		if (equalIndex > 0 && equalIndex < strLine.length()-1){
			URLCategory[0] = cleanDomain(strLine.substring(0, equalIndex));
			URLCategory[1] = strLine.substring(equalIndex+1,strLine.length());
		}
		return URLCategory;
	}
	
	// This function can evolve over time to address more corner cases in URL inputs
	public String cleanDomain(String url){
		if (url.charAt(url.length()-1) == '/')
			return url.substring(0,url.length()-1);
		return url;
	}
	
	public String[] lookupCategories(String domain){
		return trie.get(domain).split(" ");
	}
	
	public static void main(String[] args){
        DomainManager DM = new DomainManager();
        String[] categories; 
        String[] testDomains = new String[]{
        		"cnn.com", "nba.com", "nfl.com", "test.com",
        		"google.com", "xyz.com", "espn.com", "match.com", 
        		"nba.com"
        		};

        for (int i=0; i< testDomains.length; i++){
            categories = DM.lookupCategories(testDomains[i]);
            System.out.println(testDomains[i] + " -> " + Arrays.toString(categories));        	
        }    
	}
}
