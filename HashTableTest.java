import java.util.*;
import java.io.*;
import java.util.Scanner;

/** class HashTableTest
 *  A class to test all the methods from HashTableBuilder class
 * @param filename: name of the file containing data
 */
public class HashTableTest {
  BufferedReader reader;
  HashTableBuilder table;
  int tableSize;

  public static void main(String[] args){
    HashTableTest test = new HashTableTest("datafile.txt");
    //test.table.print();

    try (Scanner scan = new Scanner(System.in)) {
      System.out.print("Enter keyword to query: ");
      String key = scan.nextLine();

      int index = test.table.find(key);
      if (index == -1) {
          System.out.println("No records associated with that keyword.");
      } else {
          Record head = test.table.hashTable[index].head;
          while(head.next != null){
            System.out.printf("Author: %s \nTitle: %s \n\n", head.author, head.title);
            head = head.next;
          }
      }

    }
  }

  HashTableTest(String filename){
	//Try to read and store data from the passed in file
    try {
      reader = new BufferedReader(new FileReader(filename));
      ArrayList<String> tempArray = new ArrayList<String>();
      FileData fd;

      while ((fd = this.readNextRecord()) != null) {
        for (int i = 0; i < fd.keywords.length; i++) {
          if (!tempArray.contains(fd.keywords[i])) {
            tempArray.add(fd.keywords[i]);
            tableSize++;
          }
        }
      }

      table = new HashTableBuilder(tableSize);
      reader = new BufferedReader(new FileReader(filename));

      /* READS DATAFILE.TXT INTO DATASTRUCTURE  */
      while ((fd = readNextRecord()) != null){
        for (int i = 0; i < fd.keywords.length; i++){
          table.insert(fd.keywords[i], fd);
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (reader!=null) reader.close();
      }
      catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  public FileData readNextRecord(){
		if(reader == null){
			System.out.println("Error: You must open the file first.");
			return null;
		}
		else{
			FileData readData;
			try{
				String data= reader.readLine();
				if(data == null) return null;
				readData = new FileData(Integer.parseInt(data), reader.readLine(), reader.readLine(), Integer.parseInt(reader.readLine()));

				for (int i=0; i<readData.keywords.length; i++) {
					readData.keywords[i] = reader.readLine();
				}

				String space = reader.readLine();
				if((space!=null) && (!space.trim().equals(""))){
					System.out.println("Error in file format");
					return null;
				}
			}
			catch(NumberFormatException e){
				System.out.println("Error Number Expected! ");
				return null;
			}
			catch(Exception e){
				System.out.println("Fatal Error: "+e);
				return null;
			}
			return readData;
		}
	}
}
