package TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Persistence.HibernateQueryFactory;
import Persistence.TweetSentimentPOJO;

public class HibernateQueryTest 
{
	public HibernateQueryTest()
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
    public void setUp() 
	{
    }

    @After
    public void tearDown() 
    {
    }

    @Test
    public void testInsertQuery()
    {
    	TweetSentimentPOJO entity = new TweetSentimentPOJO();
		entity.setId(65);
		entity.setMessage("We have nothing to do with this!");
		entity.setSentiment(2);
		entity.setTopic("Illuminati");
		
		Session currentSession = getSessionFactory().openSession();
		try 
		{
			currentSession.beginTransaction();
		    currentSession.saveOrUpdate(entity);
		    currentSession.getTransaction().rollback();
		    System.out.println("Insert query rolled back succesfully.");
		} catch (HibernateException e)
		{
			System.err.println("Insert query failed: " + e.getMessage());
			e.printStackTrace();
			currentSession.getTransaction().rollback();
		}
		
		assertNotNull(entity);
		assertEquals(2, entity.getSentiment());
    }
    
    @Test
    public void testGetByIdQuery()
    {
    	Session currentSession = getSessionFactory().openSession();
    	TweetSentimentPOJO tupla = (TweetSentimentPOJO) currentSession.createCriteria(TweetSentimentPOJO.class)
				.add(Restrictions.eq("id", 65))
				.uniqueResult();
    	Assert.assertTrue(tupla != null);
    	Assert.assertEquals(65, tupla.getId());
    	System.out.print(tupla.toString());
    }
    
    
    @Test
    public void testDeleteAndGetQuery()
    {
    	HibernateQueryFactory HQF = new HibernateQueryFactory();
    	HQF.delete(65);
    	List tuple = HQF.findAll();
    	if (!tuple.isEmpty())
    	{
    		for (Object pojo : tuple)
        	{
        		System.out.println(pojo.toString());
        	}
    	}
    }
    
    @Test
	public void testShutdown() 
    {
		getSessionFactory().close();
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

	private static SessionFactory getSessionFactory() 
	{
		return sessionFactory;
	}
    
	private static SessionFactory sessionFactory = buildSessionFactory();
}
