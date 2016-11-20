package SentimentAnalysis;

import java.util.ArrayList;
import java.util.List;

import Persistence.HibernateQueryFactory;
import Persistence.TweetSentimentPOJO;

public class WhatToThink 
{
	public static void main(String[] args) 
	{
        String topic = "Happy";
        ArrayList<String> tweets = TweetManager.getTweets(topic);
        SentimentAnalysis.init();
        if (!tweets.isEmpty())
        {
        	for(String tweet : tweets) 
            {
                System.out.println(tweet + " : " +  SentimentAnalysis.findSentiment(tweet));
                int id = getMaxIdQuery();
                saveQuery(id++, tweet, SentimentAnalysis.findSentiment(tweet), topic);
            }
            System.out.println("\r\rAll data for topic= { "+topic+" }\r");
            findAllQuery();
            HibernateQueryFactory.shutdown();

        }
	}
	
	public static void saveQuery(int id, String message, int sentiment, String topic)
	{
		HibernateQueryFactory hqf = new HibernateQueryFactory();
		TweetSentimentPOJO entity = new TweetSentimentPOJO();
		entity.setId(id);
		entity.setMessage(message);
		entity.setSentiment(sentiment);
		entity.setTopic(topic);
		hqf.persist(entity);
	}
	
	public static void findAllQuery()
	{
		HibernateQueryFactory hqf = new HibernateQueryFactory();
		List tuples = hqf.findAll();
		for (Object tuple : tuples)
		{
			System.out.println(tuple.toString());
		}
	}
	
	public static int getMaxIdQuery()
	{
		HibernateQueryFactory hqf = new HibernateQueryFactory();
		return hqf.getMaxId();
	}
}
