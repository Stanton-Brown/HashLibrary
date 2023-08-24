//import javax.lang.model.util.Elements;

/** Class: HashTableBuilder
 *  This class contains the definition to create a hash table. Each element within the hash table is a keyword that
 *  points to a linked list containing all records associated with that keyword. It uses quadratic probing to avoid collisions. 
 *  It provides methods for createNewElement, insert, find, getNextQuadProbIndex, convertStringToInt,
 *  nextPrime, and print.
 *  Fields:
 *  Element[]: 
 *  tableSize: this size of the hashtable
 */
class HashTableBuilder {
  Element [] hashTable;
  int tableSize;
  
  /** HashTableBuilder
   *  Constructor 
   * @param numKeyWords: The number of keywords in the data file
   */
  HashTableBuilder(int numKeyWords) {
    tableSize = nextPrime(numKeyWords * 2);
    hashTable = new Element[tableSize];
  }
  
  /** createNewElement
   *  Method to create a new element 
   * @param keyword: the keyword associated with the new element
   * @param recordToAdd: record associated with the particular keyword
   * @return The new element created 
   */
  private Element createNewElement(String keyword, Record recordToAdd) {
    return new Element(keyword, recordToAdd);
  }
  
  /** insert
   *  Method to insert new keywords into hash table and link any records associated with that keyword
   * @param keyword: The keyword to add to the hash table
   * @param fd: The data read in from the file 
   */
  void insert(String keyword, FileData fd) {
    Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
    int index = find(keyword);
    if (index == -1)
      insert(keyword, recordToAdd);
    else {
      //System.out.printf("%s: %d\n", keyword, index);
      recordToAdd.next = hashTable[index].head;
      hashTable[index].head = recordToAdd;
    }
  }
  
  /** insert
   *  Overloaded method to insert new keywords into the hashtable when the keyword does not already exist
   * @param keyword: the keyword to be inserted
   * @param recordToAdd: the record that is associated with this keyword
   */
  private void insert(String keyword, Record recordToAdd) {
    int key = convertStringToInt(keyword);
    //System.out.printf("%d %d\n", key, tableSize);
    int index = key % tableSize;
    int  probe = 1;
    while (hashTable[index] != null) {
      System.out.printf("%s conflicts with %s at index: %d\n", keyword, hashTable[index].keyword, index);
      index = getNextQuadProbIndex(key, probe++);
    }
    hashTable[index] = createNewElement(keyword, recordToAdd);
  }
  
  /** find
   *  Method to find an element of the hash table
   * @param keyword: the keyword to search for
   * @return The index of the keyword if found, otherwise -1
   */
  int find(String keyword) {
    int key = convertStringToInt(keyword);
    //System.out.printf("%d %d\n", key, tableSize);
    int index = key % tableSize;
    int  probe = 1;
    while (hashTable[index] != null) {
      if (hashTable[index].keyword.compareTo(keyword) == 0){
        return index;
      }
        
      index = getNextQuadProbIndex(key, probe++);
    }
    return -1;
  }
  
  /** getNextQuadProbIndex
   *  Helper method to help find a location for elements in hash table while minimizing collisions (Ch. 27.4.2)
   * @param key: The key that will eventually be inserted into the hash table in a separate method
   * @param probe: a value to help reduce collisions in the hash table
   * @return The recommended index to insert the keyword
   */
  private int getNextQuadProbIndex(int key, int probe) {
    return ((key % tableSize) + (int) Math.pow(probe, 2)) % tableSize;
  }
  
  /** convertStringToInt
   *  Method to convert each key word into an integer and obtain the hash code (Ch. 27.3.2)
   *  It does this by summing up every Unicode value of each character in the String. 
   * @param keyword: the keyword to convert to integer
   * @return The summation of each Unicode character value in the string
   */
  private int convertStringToInt(String keyword) {
    int sum = 0;
    for (int i=0; i < keyword.length(); i++)
      sum += (int) keyword.charAt(i);
    return sum;
  }
  
  /** nextPrime
   *  Helper method to help scale up the size of hash table
   * @param num: The starting number from which to search for the next prime.
   * @return the next prime number 
   */
  private int nextPrime(int num) {
      num++;
      for (int i = 2; i < num; i++) {
         if(num % i == 0) {
            num++;
            i = 2;
         } 
         else {
            continue;
         }
      }
      return num;
   }
  
  /** print
   *  Method to print all elements associated with hash table to the console
   */
   public void print() {
     int printWrap = 0;
     System.out.print("\n\n\n");
     for (int index = 0; index < tableSize; index++) {
       if (hashTable[index] != null) {
         System.out.printf("index [%d]: %s (E ---> int): %d\n", index, hashTable[index].keyword, convertStringToInt(hashTable[index].keyword));
         Record rec = hashTable[index].head;
         //System.out.printf("%d|%s|%s\n ---> ", rec.id, rec.author, rec.title);
         System.out.print("\t\t");
         while(rec != null) {
           if (++printWrap % 3 == 0) {
             System.out.println();
             System.out.print("\t\t");
           }
           //System.out.printf("\t%s\n",r.title);
           System.out.printf("%d|%s|%s ---> ", rec.id, rec.author, rec.title);
           rec = rec.next;
           //printWrap++;
         }
         System.out.println("null\n");
         printWrap = 0;

       }
     }
   }
}
