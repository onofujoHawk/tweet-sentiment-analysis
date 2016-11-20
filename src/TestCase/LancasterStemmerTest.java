package TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Stemmer.LancasterStemmer;

public class LancasterStemmerTest 
{
	public LancasterStemmerTest() 
	{
		super();
	}
	
	@BeforeClass
	public void setUpClass()
	{
	}
	
	@AfterClass
	public void tearDownClass()
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
    public void testStem()
    {
    	System.out.println("lancaster stemmer\n");
        String[] words = {"consign", "consigned", "consigning", "consignment",
            "consist", "consisted", "consistency", "consistent", "consistently",
            "consisting", "consists", "consolation", "consolations", "consolatory",
            "console", "consoled", "consoles", "consolidate", "consolidated",
            "consolidating", "consoling", "consolingly", "consols", "consonant",
            "consort", "consorted", "consorting", "conspicuous", "conspicuously",
            "conspiracy", "conspirator", "conspirators", "conspire", "conspired",
            "conspiring", "constable", "constables", "constance", "constancy",
            "constant", "knack", "knackeries", "knacks", "knag", "knave",
            "knaves", "knavish", "kneaded", "kneading", "knee", "kneel",
            "kneeled", "kneeling", "kneels", "knees", "knell", "knelt", "knew",
            "knick", "knif", "knife", "knight", "knightly", "knights", "knit",
            "knits", "knitted", "knitting", "knives", "knob", "knobs", "knock",
            "knocked", "knocker", "knockers", "knocking", "knocks", "knopp",
            "knot", "knots"
        };

        String[] expResult = {"consign", "consign", "consign", "consign",
            "consist", "consist", "consist", "consist", "consist", "consist",
            "consist", "consol", "consol", "consol", "consol", "consol",
            "consol", "consolid", "consolid", "consolid", "consol", "consol",
            "consol", "conson", "consort", "consort", "consort", "conspicu",
            "conspicu", "conspir", "conspir", "conspir", "conspir", "conspir",
            "conspir", "const", "const", "const", "const", "const", "knack",
            "knackery", "knack", "knag", "knav", "knav", "knav", "knead",
            "knead", "kne", "kneel", "kneel", "kneel", "kneel", "kne", "knel",
            "knelt", "knew", "knick", "knif", "knif", "knight", "knight",
            "knight", "knit", "knit", "knit", "knit", "kniv", "knob", "knob",
            "knock", "knock", "knock", "knock", "knock", "knock", "knop",
            "knot", "knot"
        };
        
        LancasterStemmer instance = new LancasterStemmer();
        for (int i=0; i<words.length; i++) 
        {
            String result = instance.stem(words[i]);
            Assert.assertEquals(expResult[i], result);
        }
    }
}
