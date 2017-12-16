import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * HopscotchHashMap class.
 * 
 * @author Milu franz
 * @version 1.0
 * @param <K>
 *            key value
 * @param <V>
 *            value
 */

public class HopscotchHashMap<K, V> implements Map<K, V> {

    private static final int H = 4;
    private static final int DEFAULT_CAPACITY = 10;
    private int capacity = 10;
    private int modCount;

    private ArrayList<MapEntry> bucketArray;
    private boolean[][] bitmaps;

    /**
     * HopscotchHashMap Default constructor.
     */
    public HopscotchHashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * HopscotchHashMap constructor.
     * 
     * @param capacity
     *            int capacity
     */
    public HopscotchHashMap(int capacity) {
        this.capacity = capacity;

        bucketArray = new ArrayList<MapEntry>(
                Collections.nCopies(capacity, null));
        bitmaps = new boolean[capacity][];
        initBitMap();

    }

    /**
     * Initializes a BitMap with boolean values
     */
    private void initBitMap() {
        for (int i = 0; i < capacity; i++) {
            bitmaps[i] = new boolean[H];
        }
    }

    /**
     * Gets MapEntry
     * 
     * @param index
     *            to look for
     * @return MapEntry entry we are looking for
     */
    public MapEntry getEntry(int index) {
        return bucketArray.get(wrapIndex(index));
    }

    /**
     * Get Bitmap
     * 
     * @param index
     *            int
     * @return boolean [] boolean array
     */
    public boolean[] getBitmap(int index) {
        return bitmaps[index];
    }

    /**
     * Get Hash
     * 
     * @param key
     *            K to be hashed
     * @return int hash
     */
    private int getHash(K key) {
        int hash = Math.abs(key.hashCode() % capacity);
        return hash;
    }

    /**
     * Resize map
     */
    private void resize() {
        int prevCap = capacity;
        this.capacity = capacity * 2;

        // Save all entries in a temporary ArrayList
        ArrayList<MapEntry> tempBucketArray = new ArrayList<MapEntry>();
        for (int i = 0; i < prevCap; i++) {
            if (bucketArray.get(i) != null) {
                K key = bucketArray.get(i).getKey();
                V value = bucketArray.get(i).getValue();
                MapEntry temp = new MapEntry(key, value);
                tempBucketArray.add(temp);
            }
        }

        // Clean and initialize bucketArray and bitmaps with new capacity
        bucketArray = new ArrayList<MapEntry>(
                Collections.nCopies(capacity, null));
        bitmaps = new boolean[capacity][];
        initBitMap();

        for (int i = 0; i < tempBucketArray.size(); i++) {
            K key = tempBucketArray.get(i).getKey();
            V value = tempBucketArray.get(i).getValue();
            put(key, value);
        }
    }

    /**
     * Finds next available bucket
     * 
     * @param hash
     *            hash
     * @return int available bucket
     */
    private int findFreeBucket(int hash) {
        for (int i = hash; i < capacity; i++) {
            if (bucketArray.get(i) == null) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Index wrapper
     * 
     * @param index
     *            index that might need to be wrapped
     * @return int
     */
    private int wrapIndex(int index) {
        if (index == capacity) {
            return 0;
        }
        if (index > capacity) {
            return index - capacity;
        }
        return index;
    }

    /**
     * Swap values
     * 
     * @param hash
     *            index
     * @param freeBucket
     *            next bucket available
     * @param temp
     *            MapEntry to be swapped
     */
    private void swapBuckets(int hash, int freeBucket, MapEntry temp) {
        for (int i = freeBucket - 3; i < freeBucket; i++) {
            for (int j = 0; j < H; j++) {
                if (bitmaps[i][j] == true) {
                    // Change true value from bitmaps
                    bitmaps[i][j] = false;
                    // Set the entry stored in i in freeBucket
                    bucketArray.set(wrapIndex(freeBucket),
                            bucketArray.get(i + j));
                    bitmaps[i][freeBucket - i] = true;                  
                    bucketArray.set(i + j, temp);

                    if ((i + j) - hash < 5) {
                        bitmaps[hash][(i + j) - hash] = true;
                        modCount++;
                    } 
                    else 
                    {
                        swapBuckets(hash, (i + j), temp);
                    }

                    return;
                }
            }
        }
        
        resize();
        put(temp.getKey(), temp.getValue());
    }

    
    /**
     * Places value in correct index
     * Updates bitmap
     * 
     * @param hash int to look for previous value
     * @param i int bitmap
     * @param temp MapEntry to be entered
     */
    private void putValue(int hash, int i, int bitMap, MapEntry temp) {
        bucketArray.set(wrapIndex(i), temp);
        bitmaps[hash][bitMap] = true;
    }

    /**
     * Adds a MapEntry
     * 
     * @param key
     *            K value
     * @param value
     *            V value
     * @return V old value if any
     */
    @Override
    public V put(K key, V value) {
        V prevValue = null;
        int freeBucket = -1;
        // Get the hash
        int hash = getHash(key);
        // Create the new entry
        MapEntry temp = new MapEntry(key, value);
        
        if (containsKey(key)) {
            for (int i = hash; i <= hash + 3; i++) {
                int bitMap = 0;
                if (bucketArray.get(wrapIndex(i)).getKey() == key) {
                    prevValue = bucketArray.get(wrapIndex(i)).getValue();
                    putValue(hash, i, bitMap, temp);
                    return prevValue;
                }
                bitMap++;
            }            
        }

        // Insert new value in the bucketArray at the hash index
        if (bucketArray.get(hash) == null) {
            // No collision detected
            putValue(hash, hash, 0, temp);
            modCount++;
            return null;
        } 
        else 
        {
            // Collision detected. Find first free bucket
            freeBucket = findFreeBucket(hash);

            // There is no more buckets available after hash
            if (freeBucket == -1) {
                // check at the beginning of the list
                for (int i = 0; i < hash; i++) {
                    if (bucketArray.get(i) == null) {
                        freeBucket = capacity + i;
                        break;
                    }
                }
                // if no more buckets available at all
                if (freeBucket == -1) {
                    resize();
                    return put(key, value);
                }
            }

            // If free bucket is in neighborhood
            if (freeBucket - hash < 4) {
                int bitMapIndx = freeBucket - hash;
                putValue(hash, wrapIndex(freeBucket), bitMapIndx, temp);
                modCount++;
                return null;
            } 
            else 
            {
                boolean nighborhoodFull = true;
                for (int i = 0; i < H; i++) {
                    if (bitmaps[hash][i] == false) {
                        nighborhoodFull = false;
                        break;
                    }
                }

                if (nighborhoodFull) {
                    resize();
                    return put(key, value);
                }
                swapBuckets(hash, freeBucket, temp);
                return null;
            }
        }

    }

    /**
     * Gets a value based on the key
     * 
     * @param key
     *            Object key
     * @return V value that goes with key
     */
    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        
        // Get the hash
        int hash = Math.abs(key.hashCode() % capacity);
        
        for (int j = hash; j <= hash + 3; j++) {
            if (bucketArray.get(wrapIndex(j)) != null
                    && bucketArray.get(wrapIndex(j)).getKey() == key) {
                return bucketArray.get(wrapIndex(j)).getValue();
            }
        }
        // Didn't find a match
        return null;
    }

    /**
     * Returns size
     * 
     * @return int size
     */
    @Override
    public int size() {
        return modCount;
    }

    /**
     * Indicates if empty
     * 
     * @return boolean true or false
     */
    @Override
    public boolean isEmpty() {
        return modCount == 0;
    }

    /**
     * Removes all entries from map
     */
    @Override
    public void clear() {
        Collections.fill(bucketArray, null);
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < H; j++) {
                bitmaps[i][j] = false;
            }
        }
        modCount = 0;
    }

    /**
     * Removes a specific entry from map
     * 
     * @param key
     *            Object that needs to be deleted
     * @return V value that was removed
     */
    @Override
    public V remove(Object key) {
        if (key == null) {
            return null;
        }
        // Get the hash
        int hash = Math.abs(key.hashCode() % capacity);

        // Check that bucket is not null
        if (bucketArray.get(hash) == null) {
            return null;
        }

        // Search for the key in the neighborhood
        int bitMapIndex = 0;
        for (int j = hash; j <= hash + 3; j++) {
            if (bucketArray.get(wrapIndex(j)) != null
                    && bucketArray.get(wrapIndex(j)).getKey() == key) {
                // Set value to null
                V tempVal = bucketArray.get(wrapIndex(j)).getValue();
                bucketArray.set(wrapIndex(j), null);
                bitmaps[hash][bitMapIndex] = false;
                modCount--;
                return tempVal;
            }
            bitMapIndex++;
        }

        // Didn't find a match
        return null;
    }

    /**
     * Returns true if map contains key
     * 
     * @param key
     *            Object to look for
     * @return boolean true if found, false if not
     */
    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        // Get the hash
        int hash = Math.abs(key.hashCode() % capacity);

        // Check that bucket is not null
        if (bucketArray.get(hash) == null) {
            return false;
        }

        // Search for the key in the neighborhood
        for (int j = hash; j <= hash + 3; j++) {
            if (bucketArray.get(wrapIndex(j)) != null
                    && bucketArray.get(wrapIndex(j)).getKey() == key) {
                return true;
            }
        }

        // Didn't find a match
        return false;
    }

    /**
     * Returns true if map contains value
     * 
     * @param value
     *            Object to look for
     * @return boolean true if found, false if not
     */
    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }

        for (int i = 0; i < capacity; i++) {
            if (bucketArray.get(i) != null
                    && bucketArray.get(i).getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Copy all of the mappings from specified map to this map
     * 
     * @param other
     *            map to be copied
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> other) {
        int s = other.size();
        if (s > 0 && s < capacity) {
            if (isEmpty()) {
                for (Map.Entry<? extends K, ? extends V> e : other.entrySet()) {
                    K key = e.getKey();
                    V value = e.getValue();
                    put(key, value);
                }
            }
        }
    }

    /**
     * Returns a set view of the keys contained in this Map.
     * 
     * @return Set<K> set of keys
     */
    @Override
    public Set<K> keySet() {
        Set<K> ks = new HashSet<K>();
        if (!isEmpty()) {
            for (int i = 0; i < capacity; i++) {
                if (bucketArray.get(i) != null) {
                    K tempK = bucketArray.get(i).getKey();
                    ks.add(tempK);
                }
            }
            return ks;
        }
        return null;
    }
    /*
    final class KeyIterator extends HashIterator 
        implements Iterator<K> {
        public final K next() {
            return nextNode().key;
        }
    }
*/
    /**
     * Returns a set view of the values contained in this Map.
     * 
     * @return Collection<V> set of values
     */
    @Override
    public Collection<V> values() {
        Collection<V> vs = new ArrayList<V>();
        if (!isEmpty()) {
            for (int i = 0; i < capacity; i++) {
                if (bucketArray.get(i) != null) {
                    V tempV = bucketArray.get(i).getValue();
                    vs.add(tempV);
                }
            }
            return vs;
        }
        return null;
    }

    /**
     * Returns a set view of the mappings contained in this Map.
     * 
     * @return Set<Entry<K, V>>
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<Entry<K, V>>();

        if (!isEmpty()) {
            for (int i = 0; i < capacity; i++) {
                if (bucketArray.get(i) != null) {
                    MapEntry tempEntry = new MapEntry(
                            bucketArray.get(i).getKey(),
                            bucketArray.get(i).getValue());
                    entrySet.add(tempEntry);
                }
            }

            return entrySet;
        }

        return null;
    }

    /**
     * MapEntry class.
     */
    public class MapEntry implements Map.Entry<K, V> {

        private K key;
        private V value;

        /**
         * MapEntry constructor.
         * 
         * @param key
         *            K
         * @param value
         *            V
         */
        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns K value
         * 
         * @return K the key corresponding to this entry
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * returns V value
         * 
         * @return V the value corresponding to this entry
         */
        @Override
        public V getValue() {

            return value;
        }

        /**
         * Sets V value
         * 
         * @param avalue
         *            V value to be stored in this entry
         * @return V returns old value corresponding to the entry
         */
        @Override
        public V setValue(V avalue) {
            V oldValue = this.value;
            this.value = avalue;
            return oldValue;
        }
    }
}
