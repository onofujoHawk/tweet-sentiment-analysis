package Persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

public class HibernateQueryFactory implements HibernateQueryLayer 
{

	@Override
	public void persist(TweetSentimentPOJO pojo)
	{
		Session currentSession = getSessionFactory().openSession();
		try 
		{
			currentSession.beginTransaction();
		    currentSession.saveOrUpdate(pojo);
		    currentSession.getTransaction().commit();
		    System.out.println("Query successfully executed.");
		} catch (HibernateException e)
		{
			System.err.println("Persist operation failed: " + e.getMessage());
			e.printStackTrace();
			currentSession.getTransaction().rollback();
		}
	}

	@Override
	public List findAll() 
	{
		List tuples = getSessionFactory().openSession()
				.createCriteria(TweetSentimentPOJO.class)
				.list();
		return (tuples.size() > 0) ? tuples : null;
	}

	@Override
	public TweetSentimentPOJO findById(int id) 
	{
		Session currentSession = getSessionFactory().openSession();
		TweetSentimentPOJO tuple = (TweetSentimentPOJO) currentSession.createCriteria(TweetSentimentPOJO.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		return (tuple != null) ? tuple : null;
	}

	@Override
	public void merge(TweetSentimentPOJO pojo) 
	{
		Session currentSession = getSessionFactory().openSession();
		try 
		{
			currentSession.beginTransaction();
		    currentSession.update(pojo);
		    currentSession.getTransaction().commit();
		    System.out.println("Query successfully executed.");
		} catch (HibernateException e)
		{
			System.err.println("Merge operation failed: " + e.getMessage());
			e.printStackTrace();
			currentSession.getTransaction().rollback();
		}
	}

	@Override
	public void delete(int id) throws HibernateException
	{
		Session currentSession = getSessionFactory().openSession();
		TweetSentimentPOJO tuple = (TweetSentimentPOJO) getSessionFactory().openSession()
				.createCriteria(TweetSentimentPOJO.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
		try 
		{
			currentSession.beginTransaction();
			currentSession.delete(tuple);
		    currentSession.getTransaction().commit();
		    System.out.println("Query successfully executed.");
		} catch (HibernateException e)
		{
			System.err.println("Delete operation failed: " + e.getMessage());
			e.printStackTrace();
			currentSession.getTransaction().rollback();
		}
	}
	
	public int getMaxId()
	{
		Criteria criteria = getSessionFactory().openSession()
				.createCriteria(TweetSentimentPOJO.class)
				.setProjection(Projections.max("id"));
		Integer resultQuery = (Integer) criteria.uniqueResult();
		if (resultQuery != null)
		{
			return resultQuery;
		} else 
		{
			return 0;
		}
	}
	
	private static SessionFactory buildSessionFactory() 
	{
		try 
		{
			if (getSessionFactory() == null) 
			{
				Configuration configuration = new Configuration()
						.configure(HibernateQueryFactory.class.getResource("/hibernate.cfg.xml"));
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} 
		catch (Throwable ex) 
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() 
	{
		return sessionFactory;
	}
	
	public EntityManager getEntityManager()
	{
		return entityManager;
	}
	
	public static void shutdown() 
	{
		getSessionFactory().close();
	}

	private static SessionFactory sessionFactory = buildSessionFactory();
	@PersistenceContext(unitName="SentimentUnit")
	private EntityManager entityManager;
}
