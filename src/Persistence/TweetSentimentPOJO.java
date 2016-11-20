package Persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TWEET_SENTIMENT")
public class TweetSentimentPOJO implements Serializable
{
	
	public TweetSentimentPOJO() 
	{
		super();
	}

	public TweetSentimentPOJO(int id, String message, int sentiment, String topic) 
	{
		super();
		this.id = id;
		this.message = message;
		this.sentiment = sentiment;
		this.topic = topic;
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name="ID_VALUE", nullable=false, unique=true)
	public int getId()
	{
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
	
	/**
	 * @return the message
	 */
	@Column(name="MESSAGE", length=140)
	public String getMessage() 
	{
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * @return the sentiment
	 */
	@Column(name="SENTIMENT_VALUE", nullable=false)
	@NotNull
	public int getSentiment()
	{
		return sentiment;
	}
	
	/**
	 * @param sentiment the sentiment to set
	 */
	public void setSentiment(int sentiment) 
	{
		this.sentiment = sentiment;
	}
	

	/**
	 * @return the topic
	 */
	@Column(name="TOPIC", length=140)
	public String getTopic() 
	{
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) 
	{
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "TweetSentimentPOJO [id=" + id + ", " + (message != null ? "message=" + message + ", " : "")
				+ "sentiment=" + sentiment + ", " + (topic != null ? "topic=" + topic : "") + "]";
	}

	private static final long serialVersionUID = 1734026963653343610L;
	private int id;
	private String message;
	private int sentiment;
	private String topic;
}
