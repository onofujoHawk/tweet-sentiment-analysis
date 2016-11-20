package Persistence;

import java.util.List;

import org.hibernate.HibernateException;

public interface HibernateQueryLayer 
{
	void persist(TweetSentimentPOJO pojo) throws HibernateException;
	List findAll();
	TweetSentimentPOJO findById(int id);
	void merge(TweetSentimentPOJO pojo) throws HibernateException;
	void delete(int id) throws HibernateException;
}
