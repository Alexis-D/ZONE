/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import zone.utils.Item;
import zone.utils.Prop;

/**
 *
 * @author Desclaux Christophe <christophe@zouig.org>
 */
public class RDFDatabaseTest {
    
    public RDFDatabaseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }




    /**
     * Test of addItem method, of class RDFDatabase.
     */
    @Test
    public void testAddItem() {
        System.out.println("addItem");
        String uri="http://testURI.com/#MyURI";
        Item item = new Item(uri);
        item.addProp(new Prop("http://purl.org/rss/1.0/title","le titre",true));
        
        
        RDFDatabase.addItem(item);
        RDFDatabase.print();
        assertEquals(true,RDFDatabase.ItemURIExist(uri));
        RDFDatabase.deleteItem(uri);
        
        RDFDatabase.print();
        assertEquals(false,RDFDatabase.ItemURIExist(uri));
        RDFDatabase.addItem(item);
        assertEquals(true,RDFDatabase.ItemURIExist(uri));
        RDFDatabase.deleteItem(uri);
        assertEquals(false,RDFDatabase.ItemURIExist(uri));
        //fail("The test case is a prototype.");
    }

}
