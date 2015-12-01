package scripts.AdvancedWalking.Tests.Core.Dynamic;

import org.junit.Before;
import org.junit.Test;
import scripts.AdvancedWalking.Core.Dynamic.Bag;

import static org.junit.Assert.*;

/**
 * @author Laniax
 */
public class BagTest {

    private Bag _bag;

    @Before
    public void initialize() {
        _bag = new Bag();
    }

    @Test
    public void testIsEmpty() throws Exception {

        assertTrue(_bag.isEmpty());
        assertEquals(0, _bag.getCount());

    }

    @Test
    public void testGetCount() throws Exception {

        _bag.add("key1", "object1");
        _bag.add("key2", false);
        _bag.add("key3", new Object());

        assertEquals(3, _bag.getCount());
    }

    @Test
    public void testAdd() throws Exception {

        int preCount = _bag.getCount();

        assertTrue(_bag.add("new", new Object()));

        assertEquals(preCount + 1,  _bag.getCount());
        assertNotNull(_bag.get("new"));
    }

    @Test
    public void testGet() throws Exception {

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();

        _bag.add("key1", obj1);
        _bag.add("key2", obj2);
        _bag.add("key3", obj3);

        Object res1 = _bag.get("key1");
        Object res2 = _bag.get("key2");
        Object res3 = _bag.get("key3");

        assertEquals(obj1, res1);
        assertEquals(obj2, res2);
        assertEquals(obj3, res3);

        assertNull( _bag.get("non-existing-key"));
    }

    @Test
    public void testGetDefaultValue() throws Exception {

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();

        _bag.add("key1", obj1);
        _bag.add("key2", obj2);
        _bag.add("key3", obj3);

        Object res1 = _bag.get("key1", new String());
        Object res2 = _bag.get("key2", new String());
        Object res3 = _bag.get("key3", new String());

        assertEquals(obj1, res1);
        assertEquals(obj2, res2);
        assertEquals(obj3, res3);

        String str = new String();
        Object res4 = _bag.get("non-existing-key", str);

        assertEquals(str, res4);

        assertNotNull(_bag.get("non-existing-key2", new Object()));
    }

    @Test
    public void testRemove() throws Exception {

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();

        _bag.add("key1", obj1);
        _bag.add("key2", obj2);
        _bag.add("key3", obj3);

        assertEquals(3, _bag.getCount());

        assertTrue(_bag.remove("key2"));

        assertEquals(2, _bag.getCount());

        assertFalse(_bag.remove("key10"));

        assertEquals(2, _bag.getCount());
    }
}