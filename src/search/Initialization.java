package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.lang.String;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Initialization extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
    {
    	doGet(request, response);
    }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
    {
		HashMap<String, HashMap<Integer, Double>> index = (HashMap<String, HashMap<Integer, Double>>) request.getSession().getAttribute("index2");
		File f = new File("/Users/tonydinh/Documents/workspace/search/WebContent/WEB-INF/classes/Index.txt");
		if(f.exists() && !f.isDirectory())
		{
			if (index == null)
			{
				System.out.println("No Index HashMap.\nCreating Index HashMap.");
				index = new HashMap<String, HashMap<Integer, Double>>();
				createMapFromIndex(index, f);
				request.getSession().setAttribute("index2", index); 
			}
		}
		request.getRequestDispatcher("/main.jsp").forward(request, response);
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
		    	if(finishflag)
		    	{
		    		//if not it continues
			    	if(temp.compareTo(",") == 0)
			    	{
			    		intflag = true;
			    		temp = sc.next();
			    	}
			    	else
			    	{
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
		    		}
		    		else 
		    			token += temp;
		    	//checks til it finds the entire id, then looks for tfidf
		    	}
		    	else if ((intflag) && (!doubleflag))
		    	{
		    		if(temp.compareTo(":") == 0)
		    		{
		    			//raises double flag once key is found
		    			id = Integer.parseInt(token);
		    			token = "";
		    			doubleflag = true;
		    		}
		    		else if (!(temp.compareTo("{") == 0))
		    		{
		    			if (!(temp.compareTo(" ") == 0))
		    				token+=temp;
		    		}
		    	}
		    	else 
		    	{
		    		//finds tfidf
		    		if (!(temp.compareTo("}") == 0))
		    		{
		    			if (!(temp.compareTo(" ") == 0))
		    				token+=temp;
		    		}
		    		else
		    		{
		    			//raises finish flag to see if it needs to find more tfidfs
		    			tfidf = Double.parseDouble(token);
		    			token = "";
		    			finishflag = true;
		    			innerMap.put(id, tfidf);
		    		}
		    	}
		    }
		    sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
