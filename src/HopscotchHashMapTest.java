import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

/**
 * 
 * HopscotchHashMapTest class.
 * 
 * @author Milu franz
 * @version 1.0
 */

public class HopscotchHashMapTest extends TestCase {

    private Map<Integer, Integer> hsHashMapTest;

    /**
     * Set up
     */
    protected void setUp() throws Exception {
        hsHashMapTest = new HopscotchHashMap<Integer, Integer>();
    }

    /**
     * Test HopscotchHashMap Default constructor.
     */
    public void testHopscotchHashMap() {
        assertNotNull(hsHashMapTest);
    }

    /**
     * Test HopscotchHashMap constructor with different capacity.
     */
    public void testHopscotchHashMapInt() {
        Map<Integer, Integer> hsHashMapTest2 = new 
                HopscotchHashMap<Integer, Integer>(
                20);
        assertNotNull(hsHashMapTest2);

        for (int i = 0; i < 20; i++) {
            hsHashMapTest2.put(i, i);
        }
        assertEquals(20, hsHashMapTest2.size());
    }

    /**
     * Test put set when all buckets are null
     */
    public void testPutFirstBucket() {
        assertEquals(null, hsHashMapTest.put(3, 21));
        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(3)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(13, 80));

        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(23, 100));

        assertEquals((Integer) 100,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(5)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(4, 200));
        assertEquals((Integer) 200,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(6)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(33, 400));

        assertEquals((Integer) 400,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(6)
                        .getValue());

        assertEquals(5, hsHashMapTest.size());
    }

    /**
     * Test put set when a bucket has been filled already
     */
    public void testPutFullBucket() {
        hsHashMapTest.put(1, 21);
        hsHashMapTest.put(11, 30);
        hsHashMapTest.put(21, 54);
        hsHashMapTest.put(31, 28);

        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(1)
                        .getValue());
        assertEquals((Integer) 30,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 54,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(3)
                        .getValue());
        assertEquals((Integer) 28,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getValue());
    }

    /**
     * Test put set when a bucket has been filled already
     */
    public void testPutFullBucket2() {
        assertEquals(null, hsHashMapTest.put(1, 21));
        assertEquals(null, hsHashMapTest.put(11, 30));
        assertEquals(null, hsHashMapTest.put(3, 54));
        assertEquals(null, hsHashMapTest.put(4, 28));
        assertEquals(null, hsHashMapTest.put(13, 80));
        assertEquals(null, hsHashMapTest.put(5, 72));

        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(1)
                        .getValue());
        assertEquals((Integer) 30,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 54,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(3)
                        .getValue());
        assertEquals((Integer) 28,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getValue());
        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(5)
                        .getValue());
        assertEquals((Integer) 72,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(6)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(23, 12));
        assertEquals((Integer) 12,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getValue());
        assertEquals((Integer) 28,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(7)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(21, 14));
        assertEquals((Integer) 14,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(3)
                        .getValue());
        assertEquals((Integer) 54,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(6)
                        .getValue());
        assertEquals((Integer) 72,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(8)
                        .getValue());
    }

    /**
     * Test put set when a bucket has been filled already
     */
    public void testPutFullBucket3() {
        assertEquals(null, hsHashMapTest.put(9, 21));
        assertEquals(null, hsHashMapTest.put(19, 30));
        assertEquals(null, hsHashMapTest.put(29, 55));
        assertEquals(null, hsHashMapTest.put(8, 34));

        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(9)
                        .getValue());
        assertEquals((Integer) 30,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(0)
                        .getValue());
        assertEquals((Integer) 55,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(1)
                        .getValue());
        assertEquals((Integer) 34,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(8)
                        .getValue());

        assertEquals(null, hsHashMapTest.put(18, 24));
        assertEquals((Integer) 24,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(9)
                        .getValue());
        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
    }

    /**
     * Test put set when a bucket has been filled already
     */
    public void testPutFullBucket4() {
        assertEquals(null, hsHashMapTest.put(21, 2));
        assertEquals(null, hsHashMapTest.put(11, 5));
        assertEquals(null, hsHashMapTest.put(3, 12));
        assertEquals(null, hsHashMapTest.put(14, 20));
        assertEquals(null, hsHashMapTest.put(24, 21));
        assertEquals(null, hsHashMapTest.put(15, 7));
        assertEquals(null, hsHashMapTest.put(25, 39));
        assertEquals(null, hsHashMapTest.put(8, 14));

        assertEquals(null, hsHashMapTest.put(31, 100));

        assertEquals((Integer) 100,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(3)
                        .getValue());
        assertEquals((Integer) 12,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(6)
                        .getValue());
        assertEquals((Integer) 14,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(9)
                        .getValue());
        
        assertEquals((Integer) 12, hsHashMapTest.put(3, 100));

    }
    
    /**
     * Test put set when a Neighborhood has been filled already
     */
    public void testPutFullNeighborhood() {
        assertEquals(null, hsHashMapTest.put(2, 21));
        assertEquals(null, hsHashMapTest.put(1, 30));
        assertEquals(null, hsHashMapTest.put(11, 54));
        assertEquals(null, hsHashMapTest.put(12, 28));
        assertEquals(null, hsHashMapTest.put(21, 80));

        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(5)
                        .getValue());
    }
    
    /**
     * Test put set when a Neighborhood has been filled already
     */
    public void testPutFullNeighborhood2() {
        assertEquals(null, hsHashMapTest.put(1, 21));
        assertEquals((Integer) 21, hsHashMapTest.put(1, 30));
        assertEquals(null, hsHashMapTest.put(11, 54));
        assertEquals(null, hsHashMapTest.put(12, 28));
        assertEquals(null, hsHashMapTest.put(21, 80));

        assertEquals((Integer) 30,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(1)
                        .getValue());
        assertEquals((Integer) 54,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 28,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(3)
                        .getValue());
        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getValue());
        
        assertEquals(null, hsHashMapTest.put(22, 79));
        assertEquals(null, hsHashMapTest.put(32, 15));
        assertEquals(null, hsHashMapTest.put(31, 100));
        
        assertEquals((Integer) 79, hsHashMapTest.get(22));
        assertEquals((Integer) 28, hsHashMapTest.get(12));

    }
    
    /**
     * Test replace a value
     */
    public void testReplacePut() {
        assertEquals(null, hsHashMapTest.put(2, 21));
        assertEquals(null, hsHashMapTest.put(1, 30));
        assertEquals((Integer) 30, hsHashMapTest.put(1, 54));
        
        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 54,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(1)
                        .getValue());
        
        assertEquals(null, hsHashMapTest.put(12, 8));
        assertEquals(null, hsHashMapTest.put(3, 25));
        assertEquals(null, hsHashMapTest.put(11, 30));
        
        assertEquals((Integer) 30,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(5)
                        .getValue());
        
        assertEquals((Integer) 30, hsHashMapTest.put(11, 80));
        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
    }
    
    /**
     * Test replace a value on the wrapping edge
     */
    public void testReplacePut2() {
        assertEquals(null, hsHashMapTest.put(9, 21));
        assertEquals(null, hsHashMapTest.put(19, 50));
        assertEquals(null, hsHashMapTest.put(29, 80));
        
        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(9)
                        .getValue());
        assertEquals((Integer) 50,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(0)
                        .getValue());
        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(1)
                        .getValue());
        
        assertEquals((Integer) 50, hsHashMapTest.put(19, 100));
        assertEquals((Integer) 100,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(0)
                        .getValue());
        
        assertEquals(3, hsHashMapTest.size());
    }

    /**
     * Test when neighborhood is completely full and ArrayList needs to be
     * resized
     */
    public void testResize() {
        assertEquals(null, hsHashMapTest.put(0, 21));
        assertEquals(null, hsHashMapTest.put(10, 30));
        assertEquals(null, hsHashMapTest.put(20, 54));
        assertEquals(null, hsHashMapTest.put(30, 28));
        assertEquals(null, hsHashMapTest.put(40, 80));

        assertEquals((Integer) 21,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(0)
                        .getValue());
        assertEquals((Integer) 54,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest)
                        .getEntry(1).getValue());
        assertEquals((Integer) 80,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(2)
                        .getValue());
        assertEquals((Integer) 30,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest)
                        .getEntry(10).getValue());
        assertEquals((Integer) 28,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest
                        ).getEntry(11).getValue());

    }
    
    /**
     * Test when table is completely full
     */
    public void testResize2() {
        assertEquals(null, hsHashMapTest.put(0, 0));
        assertEquals(null, hsHashMapTest.put(1, 1));
        assertEquals(null, hsHashMapTest.put(2, 2));
        assertEquals(null, hsHashMapTest.put(3, 3));
        assertEquals(null, hsHashMapTest.put(4, 4));
        assertEquals(null, hsHashMapTest.put(5, 5));
        assertEquals(null, hsHashMapTest.put(6, 6));
        assertEquals(null, hsHashMapTest.put(7, 7));
        assertEquals(null, hsHashMapTest.put(8, 8));
        assertEquals(null, hsHashMapTest.put(9, 9));
        
        assertEquals(null, hsHashMapTest.put(19, 19));
    }

    /**
     * Test Get value based on the key
     */
    public void testGet() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);
        hsHashMapTest.put(10, 28);
        hsHashMapTest.put(9, 30);
        hsHashMapTest.put(19, 53);

        assertEquals(6, hsHashMapTest.size());
        assertEquals((Integer) 22, hsHashMapTest.get(4));
        assertEquals((Integer) 28, hsHashMapTest.get(10));
        assertEquals((Integer) 30, hsHashMapTest.get(9));
        assertEquals((Integer) 53, hsHashMapTest.get(19));
        assertEquals(null, hsHashMapTest.get(null));
        assertEquals(null, hsHashMapTest.get(2));
    }

    /**
     * Test Get Entry
     */
    public void testGetEntry() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);

        assertEquals((Integer) 22,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getValue());
        assertEquals((Integer) 4,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(4)
                        .getKey());

        hsHashMapTest.put(6, 50);
        assertEquals((Integer) 50,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(6)
                        .getValue());

        hsHashMapTest.put(8, 76);
        assertEquals((Integer) 76,
                ((HopscotchHashMap<Integer, Integer>) hsHashMapTest).getEntry(8)
                        .getValue());
    }

    /**
     * Test if map is empty
     */
    public void testIsEmpty() {
        assertTrue(hsHashMapTest.isEmpty());

        hsHashMapTest.put(3, 21);
        assertFalse(hsHashMapTest.isEmpty());
    }

    /**
     * Test clear
     */
    public void testClear() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);
        assertEquals(3, hsHashMapTest.size());

        hsHashMapTest.clear();
        assertTrue(hsHashMapTest.isEmpty());

        boolean[] bitMap = ((HopscotchHashMap<Integer, Integer>) hsHashMapTest)
                .getBitmap(4);
        assertFalse(bitMap[0]);
        assertFalse(bitMap[1]);
    }

    /**
     * Test Remove
     */
    public void testRemove() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);
        assertEquals(3, hsHashMapTest.size());

        assertEquals((Integer) 22, hsHashMapTest.remove(4));
        assertEquals(2, hsHashMapTest.size());
        assertEquals(null, hsHashMapTest.remove(null));
        assertEquals(null, hsHashMapTest.remove(4));
    }

    /**
     * Test Remove
     */
    public void testRemove2() {
        hsHashMapTest.put(9, 21);
        hsHashMapTest.put(19, 30);
        hsHashMapTest.put(29, 55);
        hsHashMapTest.put(8, 34);
        hsHashMapTest.put(18, 24);

        assertEquals((Integer) 55, hsHashMapTest.remove(29));
        assertEquals(null, hsHashMapTest.get(29));
        boolean[] bitMap = ((HopscotchHashMap<Integer, Integer>) hsHashMapTest)
                .getBitmap(9);
        assertFalse(bitMap[0]);
        assertTrue(bitMap[1]);
        assertFalse(bitMap[2]);
        assertTrue(bitMap[3]);

    }

    /**
     * Test Contains Key
     */
    public void testContainsKey() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);

        assertTrue(hsHashMapTest.containsKey(4));
        assertTrue(hsHashMapTest.containsKey(3));
        assertFalse(hsHashMapTest.containsKey(6));
        assertFalse(hsHashMapTest.containsKey(null));
    }

    /**
     * Test Contains Key
     */
    public void testContainsKey2() {
        assertEquals(null, hsHashMapTest.put(9, 21));
        assertEquals(null, hsHashMapTest.put(19, 30));
        assertEquals(null, hsHashMapTest.put(29, 55));
        assertEquals(null, hsHashMapTest.put(8, 34));
        assertEquals(null, hsHashMapTest.put(18, 24));

        assertTrue(hsHashMapTest.containsKey(29));
        assertTrue(hsHashMapTest.containsKey(8));
        assertTrue(hsHashMapTest.containsKey(18));
    }

    /**
     * Test Contains value
     */
    public void testContainsValue() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);
        hsHashMapTest.put(8, 50);

        assertTrue(hsHashMapTest.containsValue(22));
        assertTrue(hsHashMapTest.containsValue(23));
        assertFalse(hsHashMapTest.containsValue(55));
        assertTrue(hsHashMapTest.containsValue(50));
        assertFalse(hsHashMapTest.containsValue(null));
        
        hsHashMapTest.put(4, 32);
        assertTrue(hsHashMapTest.containsValue(32));
        assertFalse(hsHashMapTest.containsValue(22));
    }

    /**
     * Test Put all
     */
    public void testPutAll() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);

        Map<Integer, Integer> hsHashMapTest2 = new 
                HopscotchHashMap<Integer, Integer>();
        hsHashMapTest2.putAll(hsHashMapTest);
        assertEquals(3, hsHashMapTest2.size());

    }

    /**
     * Test keySet()
     */
    public void testKeySet() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);

        Set<Integer> ks = hsHashMapTest.keySet();

        Set<Integer> expectedKs = new HashSet<Integer>();
        expectedKs.add(3);
        expectedKs.add(4);
        expectedKs.add(5);

        assertEquals(3, ks.size());
        assertTrue(expectedKs.equals(ks));
    }

    /**
     * Test values()
     */
    public void testValues() {
        hsHashMapTest.put(3, 21);
        hsHashMapTest.put(4, 22);
        hsHashMapTest.put(5, 23);

        ArrayList<Integer> vs = 
                (ArrayList<Integer>) hsHashMapTest.values();

        ArrayList<Integer> expectedVs = new ArrayList<Integer>();
        expectedVs.add(21);
        expectedVs.add(22);
        expectedVs.add(23);

        assertEquals(3, vs.size());
        assertTrue(expectedVs.equals(vs));
    }

    /**
     * Test entrySet()
     */
    /*
     * public void testEntrySet() { // How do I initialize a Set<Entry<K, V>? }
     */

}
