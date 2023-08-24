/** Class: Element
 *  Contains the definition to hold the keyword and 
 *  record associated with that keyword
 *  Fields:
 *  keyword: the keyword associated with this element
 *  head: 
 */

class Element { 
  String keyword;
  Record head;

  Element(String k, Record r){
    keyword = k;
    head = r;
  }
}
