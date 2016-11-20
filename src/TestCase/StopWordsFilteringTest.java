package TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StopWordsFilteringTest 
{
	public StopWordsFilteringTest()
	{
		super();
	}
	
	@BeforeClass
	public static void setUpClass()
	{
	}
	
	@AfterClass
	public static void tearDownClass()
	{
	}
	
	@Before
    public void setUp() throws IOException 
	{
		stopList = new HashSet<String>();
		try 
		{
			loadStopList();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
    }

    @After
    public void tearDown() 
    {
    }
    
    @Test
    public void testStopWordsRemove()
    {
    	System.out.println("stopwords filtering\n");
    	String sentence1 = "I like going out to parties with friends or watching TV.";
    	String sentence2 = "Mrs Miller wants the entire house repainted.";
    	String sentence3 = "It is grossly unfair to suggest that the school was responsible for the accident.";
    	String sentence4 = "Eventually, a huge cyclone hit the entrance of my house.";
    	
    	ArrayList<String> sentences = new ArrayList<String>(Arrays.asList(sentence1, sentence2, sentence3, sentence4));
    	
    	for (String sentence : sentences)
    	{
    		String originalSentence = sentence;
    		String filteredSentence = "";
    		sentence = sentence.replaceAll("(?!['’])\\p{Punct}", " ");
    		String[] words = sentence.split("\\s");
    		for (int i = 0; i<words.length; i++)
    		{
    			String wordCompare = words[i].trim().toLowerCase();
    			if (stopList.contains(wordCompare))
    			{
    				words[i] = "";
    			}
    			if (!(words[i].equalsIgnoreCase(""))) 
    			{
    				filteredSentence = filteredSentence.concat(words[i]+" ");
    			}
    		}
    		int finalWS = filteredSentence.length()-1;
    		if (!filteredSentence.isEmpty())
    		{
    			filteredSentence = filteredSentence.substring(0, finalWS);
    		}
    		
    		Assert.assertNotEquals(originalSentence, filteredSentence);
    		System.out.println("Original sentence= "+originalSentence);
    		System.out.println("Filtered sentence= "+filteredSentence+"\r");
    	}
    }
    
    private void loadStopList() throws IOException
    {
		BufferedReader fileRead = new BufferedReader(new FileReader("config/Stopwords_en.txt"));
		String item;
		while (fileRead.ready()) 
		{
			item = fileRead.readLine();
			item = item.toLowerCase().trim();
			stopList.add(item);
		}
		fileRead.close();
    }
    
    private HashSet<String> stopList;
}
