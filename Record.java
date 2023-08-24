

/** Class: Record
 *  Contains the definition to hold the id, title,
 *  author, and next of each record
 *  Fields:
 *  id: the id of the article
 *  title: the title of the article
 *  author: the author of the article
 *  next: 
 */
public class Record {
  int id;
  String title;
  String author;
  Record next;

  Record(int i, String t, String a, Record r) {
    this.id = i;
    this.title = t;
    this.author = a;
    this.next = r;
  }
}
