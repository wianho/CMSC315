import java.util.*;

public class Exercise {
    /** Main method */
    public static void main(String[] args) {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Enter integer keys, input ends with a negative value: ");
        int key = input.nextInt();
        while (key >= 0) {
            map.put(key, key);
            key = input.nextInt();
        }

        System.out.print("Enter key1: ");
        int key1 = input.nextInt();
        System.out.println("Is " + key1 + " in the map? " + map.containsKey(key1));

        System.out.print("Enter key2: ");
        int key2 = input.nextInt();
        System.out.println("Is " + key2 + " in the map? " + map.containsKey(key2));

        System.out.println("The map size is " + map.size());

        map.remove(2);
        System.out.println("After removing key 2 from the map, is key 2 in the map? " + map.containsKey(2));
        System.out.println("The map size is " + map.size());
    }
}

interface MyMap<K, V> {
    /** Remove all of the entries from this map */
    public void clear();

    /** Return true if the specified key is in the map */
    public boolean containsKey(K key);

    /** Return true if this map contains the specified value */
    public boolean containsValue(V value);

    /** Return a set of entries in the map */
    public java.util.Set<Entry<K, V>> entrySet();

    /** Return the first value that matches the specified key */
    public V get(K key);

    /** Return true if this map contains no entries */
    public boolean isEmpty();

    /** Return a set consisting of the keys in this map */
    public java.util.Set<K> keySet();

    /** Add an entry (key, value) into the map */
    public V put(K key, V value);

    /** Remove the entries for the specified key */
    public void remove(K key);

    /** Return the number of mappings in this map */
    public int size();

    /** Return a set consisting of the values in this map */
    public java.util.Set<V> values();

    /** Define inner class for Entry */
    public static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }
}

class MyHashMap<K, V> implements MyMap<K, V> {
    // Define the default hash table size.
    private static int DEFAULT_INITIAL_CAPACITY = 4;

    // Define the maximum hash table size. 1 << 30 is same as 2^30
    private static int MAXIMUM_CAPACITY = 1 << 30;

    // Current hash table capacity.
    private int capacity;

    // Define default load factor
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.5f;

    // Specify a load factor used in the hash table
    private float loadFactorThreshold;

    // The number of entries in the map
    private int size = 0;

    // Hash table is an array with each cell that is a linked list
    MyMap.Entry<K, V>[] table;

    /** Construct a map with the default capacity and load factor */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    /** Construct a map with the specified initial capacity and
     * default load factor */
    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactorThreshold) {
        this.capacity = initialCapacity;
        this.loadFactorThreshold = loadFactorThreshold;
        table = (MyMap.Entry<K, V>[]) new MyMap.Entry[capacity]; // Explicit cast
    }

    /** Remove all of the entries from this map */
    public void clear() {
        size = 0;
        removeEntries();
    }

    /** Return true if the specified key is in the map */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /** Return true if this map contains the specified value */
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length; i++)
            if (table[i] != null && table[i].value.equals(value))
                return true;

        return false;
    }

    /** Return a set of entries in the map */
    public java.util.Set<MyMap.Entry<K, V>> entrySet() {
        java.util.Set<MyMap.Entry<K, V>> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++)
            if (table[i] != null)
                set.add(table[i]);

        return set;
    }

    /** Return true if this map contains no entries */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return a set consisting of the keys in this map */
    public java.util.Set<K> keySet() {
        java.util.Set<K> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++)
            if (table[i] != null)
                set.add(table[i].key);

        return set;
    }

    /** Remove the element for the specified key */
    public void remove(K key) {
        int i = hash(key.hashCode());

        while (table[i] != null && (table[i].key == null || !table[i].key.equals(key)))
            i = (i + 1) % table.length;

        if (table[i] != null && table[i].key.equals(key)) {
            // A special marker Entry(null, null) is placed for the deleted entry
            table[i] = new MyMap.Entry<K, V>(null, null);
            size--;
        }
    }

    /** Return the number of mappings in this map */
    public int size() {
        return size;
    }

    /** Return a set consisting of the values in this map */
    public java.util.Set<V> values() {
        java.util.Set<V> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++)
            if (table[i] != null)
                set.add(table[i].value);

        return set;
    }

    /** Hash function */
    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    /** Ensure the hashing is evenly distributed */
    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /** Remove all entries from the table */
    private void removeEntries() {
        for (int i = 0; i < capacity; i++)
            table[i] = null;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        java.util.Set<Entry<K, V>> set = entrySet(); // Get entries
        capacity <<= 1; // Double capacity
        table = (MyMap.Entry<K, V>[]) new MyMap.Entry[capacity]; // Explicit cast
    
        size = 0; // Clear size
    
        for (Entry<K, V> entry : set) {
            put(entry.getKey(), entry.getValue()); // Store to new table
        }
    }

    @Override
    /** Return a string representation for this map */
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i].key != null)
                builder.append(table[i].toString());
        }

        return builder.append("]").toString();
    }

    /** Return the first value that matches the specified key */
    public V get(K key) {
        int i = hash(key.hashCode()); // Get the hashed index
        int initialIndex = i; // Remember the initial index to avoid infinite loops

        while (table[i] != null) {
            if (table[i].key != null && table[i].key.equals(key)) {
                return table[i].value; // Key found, return value
            }
            i = (i + 1) % capacity; // Move to the next index (linear probing)
            if (i == initialIndex) {
                break; // We've looped back to the starting index, key not found
            }
        }
        return null; // Key not found
    }

    /** Add an entry (key, value) into the map */
    public V put(K key, V value) {
        if (size >= capacity * loadFactorThreshold) {
            rehash(); // Double the capacity if the load factor threshold is exceeded
        }

        int i = hash(key.hashCode()); // Get the hashed index
        int initialIndex = i; // Remember the initial index to avoid infinite loops

        // Linear probing to find the right position for the key
        while (table[i] != null && table[i].key != null) {
            if (table[i].key.equals(key)) {
                V oldValue = table[i].value;
                table[i].value = value; // Update the value if the key exists
                return oldValue; // Return the old value
            }
            i = (i + 1) % capacity; // Move to the next index (linear probing)
            if (i == initialIndex) {
                break; // We've looped back to the starting index
            }
        }

        // If a null or a special marker (Entry(null, null)) is found, insert the key-value pair
        table[i] = new MyMap.Entry<>(key, value);
        size++;
        return null; // Return null if it is a new insertion
    }
}