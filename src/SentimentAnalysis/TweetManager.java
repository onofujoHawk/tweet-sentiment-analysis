package SentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.tartarus.snowball.ext.englishStemmer;

import Stemmer.LancasterStemmer;
import Stemmer.PorterStemmer;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TweetManager 
{
	public static ArrayList getTweets(String topic)
	{
		TwitterFactory twitterFactory = new TwitterFactory(OAuth());
	    Twitter twitter = twitterFactory.getInstance();
		ArrayList tweetList = new ArrayList();
		try 
		{
			Query query = new Query(topic);
            QueryResult result;
            do 
            {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) 
                {
                	if (tweet.getLang().equalsIgnoreCase("en"))
                	{
                	  	messageToFilter = tweet.getText();
                    	cleaningTweet();
                    	tweetList.add(messageToFilter);
                	}
                }
            } while ((query = result.nextQuery()) != null);
		} catch (TwitterException e)
		{
			e.printStackTrace();
			System.out.println("Failed to search tweets: " + e.getMessage());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Failed to find stoplist: " + e.getMessage());
		}
		return tweetList;
	}
	
	private static Configuration OAuth() 
	{
		Properties prop = new Properties();
		try 
		{
			InputStream in = TweetManager.class.getClassLoader().getResourceAsStream("Twitter4j.properties");
			prop.load(in);
		} catch (IOException e) 
		{
			System.out.println(e.getMessage());
		} catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setIncludeEntitiesEnabled(true);
		cb.setDebugEnabled(Boolean.valueOf(prop.getProperty("debug")));
		cb.setOAuthConsumerKey(prop.getProperty("oauth.apiKey"));
		cb.setOAuthConsumerSecret(prop.getProperty("oauth.apiSecret"));
		cb.setOAuthAccessToken(prop.getProperty("oauth.accessToken"));
		cb.setOAuthAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret"));
		cb.setJSONStoreEnabled(true);
		return cb.build();
	}
	
	public static String cleaningTweet() throws IOException
	{
		String originalMessage = messageToFilter;
		String regexURLs = "https?://\\S+\\s?";
		if (messageToFilter.contains("https"))
		{
			 messageToFilter = messageToFilter.replaceAll(regexURLs, " ").trim();
		}
        String regexTags = "@[A-Za-z0-9_-]+";
        if (messageToFilter.contains("@")) 
        {
			messageToFilter = messageToFilter.replaceAll(regexTags, " ").trim();
		}
        if (messageToFilter.contains("RT"))
        {
        	messageToFilter = messageToFilter.replaceAll("RT", " ").trim();
        }
		loadStopList("config/Stopwords_en.txt");
		deleteStopWords();
    	porterStemmer();
    	if (!originalMessage.equals(messageToFilter))
    	{
    		return messageToFilter;
    	}
    	else 
    	{
    		messageToFilter = "";
    		messageToFilter = originalMessage;
    		return messageToFilter;
    	}
	}
	
	public static void loadStopList(String filePath) throws IOException
	{
		stopList = new HashSet<String>();
		BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
		String item;
		while (fileRead.ready()) 
		{
			item = fileRead.readLine();
			item = item.toLowerCase().trim();
			stopList.add(item);
		}
		fileRead.close();
	}
	
	public static void deleteStopWords()
	{
		String finalMessage="";
		messageToFilter = messageToFilter.replaceAll("(?!['’])\\p{Punct}", " ");
		String[] words = messageToFilter.split("\\s");
		for (int i = 0; i<words.length; i++)
		{
			String wordCompare = words[i].trim().toLowerCase();
			if (stopList.contains(wordCompare))
			{
				words[i] = "";
			}
			if (!(words[i].equalsIgnoreCase(""))) 
			{
				finalMessage = finalMessage.concat(words[i]+" ");
			}
		}
		int finalWS = finalMessage.length()-1;
		messageToFilter = "";
		if (!finalMessage.isEmpty())
		{
			finalMessage = finalMessage.substring(0, finalWS);
		}
		messageToFilter = finalMessage.trim();
	}
	
	public static String snowballStemmer()
	{
		englishStemmer englishStemmer = new englishStemmer();
		String[] singleWords = messageToFilter.split("\\s+");
		StringBuilder appendTerm = new StringBuilder();
		for (int i=0; i<singleWords.length; i++)
		{
			englishStemmer.setCurrent(singleWords[i].toLowerCase());
			boolean result = englishStemmer.stem();
			String stemmedTerm = englishStemmer.getCurrent();
		    if (!result) 
		    {
		    	appendTerm.append(singleWords[i].toLowerCase()).append(" ");
		    } else 
		    {
		    	if (stemmedTerm.endsWith("i"))
		    	{
		    		int index = stemmedTerm.lastIndexOf("i");
					stemmedTerm = stemmedTerm.substring(0, index) + "y" + stemmedTerm.substring(index+"i".length());
					appendTerm.append(stemmedTerm).append(" ");
		    	}
		    	appendTerm.append(stemmedTerm).append(" ");
		    }
		}
		messageToFilter = "";
	    return messageToFilter = appendTerm.toString().trim();
	}
	
	public static String lancasterStemmer()
	{
		LancasterStemmer lancasterStemmer = new LancasterStemmer();
		String[] singleWords = messageToFilter.split("\\s+");
		StringBuilder appendTerm = new StringBuilder();
        for (int i=0; i<singleWords.length; i++) 
        {
            String stemmedWord = lancasterStemmer.stem(singleWords[i]);
            appendTerm.append(stemmedWord).append(" ");
        }
        messageToFilter="";
        return messageToFilter = appendTerm.toString().trim();
	}
	
	public static String porterStemmer()
	{
		PorterStemmer porterStemmer = new PorterStemmer();
		String[] singleWords = messageToFilter.split("\\s+");
		StringBuilder appendTerm = new StringBuilder();
        for (int i=0; i<singleWords.length; i++) 
        {
            String stemmedWord = porterStemmer.stem(singleWords[i]);
            if (stemmedWord.endsWith("i"))
	    	{
	    		int index = stemmedWord.lastIndexOf("i");
				stemmedWord = stemmedWord.substring(0, index) + "y" + stemmedWord.substring(index+"i".length());
				appendTerm.append(stemmedWord).append(" ");
	    	} else
	    	{
	    		appendTerm.append(stemmedWord).append(" ");
	    	}
        }
        messageToFilter="";
        return messageToFilter = appendTerm.toString().trim();
	}
	
	private static HashSet<String> stopList;
	private static String messageToFilter;
	
}
