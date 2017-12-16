# Hopscotch-Hashing
A reordering scheme that can be used with the open addressing method for collision resolution in hash tables. </br >
The entries in HopscotchHashMap will be stored in an array, as in linear probing, but collisions are handled differently.</br >
In a HopscotchHashMap, each bucket has a neighborhood of size H, and any entry that hashes to a given bucket will be found somewhere in its neighborhood. </br >

In our case, H = 4, and the table size will be 10 by default.</br > 

This means that a key that hashes to bucket 3 will always be found in one of the four buckets starting at 3, i.e. buckets 3-6.</br >

If a key hashed to bucket 5, then it would be found somewhere in buckets 5-8.</br >

Collisions are handled in the same way as in linear probing, as long as an empty bucket can be found in the neighborhood of the natural bucket for a key.</br >
For example, if we insert the key 3 into a table of size 10, it hashes to bucket 3 (we’ll ignore the values that go with the keys for now because they don’t affect the hashing process).</br >
Since that bucket is empty, we have no collision.  If we then insert the key 13, it also hashes to bucket 3.</br >
Since the bucket is occupied, we search forward through the table, looking for an empty bucket.</br >
Bucket 4 is available, and it’s within the neighborhood of bucket 3, so we put the new entry into that slot.

