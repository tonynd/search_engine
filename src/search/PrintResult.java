/*
	Tony Dinh (11148911)
	Josh Alpert (32631048)
	Geno Diaz (34812280)
	Sakorna Huot (88581556)
 */

package search;

import java.io.IOException;
import java.util.*;
import java.io.*;
import java.lang.String;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintResult extends HttpServlet 
{
	ArrayList<UrlInfo> printUrlInfo;
	String temp = "";
	
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
    	doGet(request, response);
    }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	{
		//Setting user's query to a string.
		String s = request.getParameter("searchQuery").toLowerCase();
		System.out.println("Search Query: " + s);
		request.setAttribute("userQuery", request.getParameter("searchQuery"));
		HashMap<String, HashMap<Integer, Double>> index = (HashMap<String, HashMap<Integer, Double>>) request.getSession().getAttribute("index2");
		File f = new File("/Users/tonydinh/Documents/workspace/search/WebContent/WEB-INF/classes/Index.txt");
		ArrayList<String> urlList = new ArrayList<String>();
		urlList = createUrlList(new File("/Users/tonydinh/Documents/workspace/search/WebContent/WEB-INF/classes/Url2.txt"));
		
		if(f.exists() && !f.isDirectory())
		{
			if (index == null)
			{
				index = new HashMap<String, HashMap<Integer, Double>>();
				createMapFromIndex(index, f);
				request.getSession().setAttribute("index2", index);
			}
		   
			else
			{
				System.out.println("Index HashMap Exist.");
			}
		}
	   
	   //Integer that decides how many URLs to display in the results.
	   int numOfTopUrls = 10;
	   ArrayList<String> queryList = new ArrayList<String>(Arrays.asList(s.split(" ")));
	   SortedSet<Map.Entry<Integer, Double>> queryResults = queryHash(queryList, index);
	   Iterator it = queryResults.iterator();
	 
	   printUrlInfo = new ArrayList<UrlInfo>();
	   File urlContent = new File("/Users/tonydinh/Documents/workspace/search/WebContent/WEB-INF/classes/UrlContent.txt");
	   for(int i = 0; i < numOfTopUrls; i++)
	   {
		   UrlInfo url = new UrlInfo();
		   
		   if(it.hasNext())
		   {
			   Entry<Integer, Double> q = (Entry<Integer, Double>) it.next();
			   int tempKey = q.getKey();
			   Scanner scan = new Scanner(urlContent);
			   boolean found = false;
			   while (!found)
			   {
				   String tempArr[] = scan.nextLine().split(" ");
				   if (Integer.parseInt(tempArr[0]) == tempKey)
				   {
					   url.setUrl(urlList.get(tempKey-1));
					   temp ="";
					   for (int j = 1; j < tempArr.length; j++)
					   {
						   temp += tempArr[j] + " ";
					   }
					   temp = temp.trim();
					   url.setText(temp);
					   found = true;
				   }
			   }
			   scan.close();
		   }
		   if (url.isValid())
			   printUrlInfo.add(url);
	   }
	  
	   request.setAttribute("printUrlInfo", printUrlInfo);
	 
	   request.getRequestDispatcher("/result.jsp").forward(request, response);
	}
	
	public static SortedSet<Map.Entry<Integer, Double>> queryHash(ArrayList<String> queryWords, HashMap<String, HashMap<Integer, Double>> index) 
	{
		Map<Integer,Double> queryValues = new TreeMap<Integer, Double>();
		SortedSet<Map.Entry<Integer, Double>> orderedList = new TreeSet<Map.Entry<Integer, Double>>();
		if(queryWords.size() == 0){
			return orderedList;
		}
		for (int i = 0; i < queryWords.size(); i++) {
			try{
				Iterator it = index.get(queryWords.get(i)).entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Integer, Double> pair = (Map.Entry)it.next();
					if (queryValues.containsKey(pair.getKey())) {
						double temp = queryValues.get(pair.getKey()) + pair.getValue();
						queryValues.put(pair.getKey(), temp);
					} else {
						queryValues.put(pair.getKey(), pair.getValue());
					}
				}
			}
			catch(Exception e){
				continue; 
			}
		}
		orderedList = entriesSortedByValues(queryValues);
		return orderedList;
	}
	
	static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) 
	{
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) 
	            {
	                int res = e2.getValue().compareTo(e1.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
	public static void createMapFromIndex(HashMap<String, HashMap<Integer, Double>> index, File indexFile)
	{
	    String token = "";
	    String temp = "";
	    String keyword = "";
	    int id = 0;
	    double tfidf = 0; 
	    boolean intflag = false;
	    boolean doubleflag = false;
	    boolean finishflag = false;
	    HashMap<Integer, Double> innerMap = new HashMap<Integer, Double>();
		try 
		{
			Scanner sc = new Scanner(indexFile);
			sc.useDelimiter("(?<=.)");
		    while(sc.hasNext()){
		    	temp = sc.next();
		    	//checks to see if the word is finished (all tfidfs found)
		    	if(finishflag){
		    		//if not it continues
			    	if(temp.compareTo(",") == 0){
			    		intflag = true;
			    		temp = sc.next();
			    	}else{
			    	//else goes to the next word and resets
			    		index.put(keyword, innerMap);
			    		innerMap = new HashMap<Integer, Double>();
			    		intflag = false;
			    	}
		    		doubleflag = false;
		    		finishflag = false;
		    	}
		    	//checks for the keyword and moves forward to id if ':' is found
		    	if((!intflag) && (!doubleflag)){
		    		if((temp.compareTo(":") == 0)){
		    			//raises flag so it knows to move to int
		    			keyword = token.trim();
		    			token = "";
		    			intflag = true;
		    		}else 
		    			token += temp;
		    	//checks til it finds the entire id, then looks for tfidf
		    	}else if ((intflag) && (!doubleflag)){
		    		if(temp.compareTo(":") == 0){
		    			//raises double flag once key is found
		    			id = Integer.parseInt(token);
		    			token = "";
		    			doubleflag = true;
		    		}else if (!(temp.compareTo("{") == 0)){
		    			if (!(temp.compareTo(" ") == 0))
		    				token+=temp;
		    		}
		    	}else {
		    		//finds tfidf
		    		if (!(temp.compareTo("}") == 0)){
		    			if (!(temp.compareTo(" ") == 0))
		    				token+=temp;
		    		}else{
		    			//raises finish flag to see if it needs to find more tfidfs
		    			tfidf = Double.parseDouble(token);
		    			token = "";
		    			finishflag = true;
		    			innerMap.put(id, tfidf);
		    		}
		    	}
		    }
		    sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> createUrlList(File input){
	    ArrayList<String> tokens = new ArrayList<String>();
	    String temp = "";
		try {
			Scanner sc = new Scanner(input);
			sc.useDelimiter("\n");
		    while(sc.hasNext()){
		    	temp = sc.next();
		    	tokens.add(temp.split(" ")[1]);
		    }
		    sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tokens;
	}
}