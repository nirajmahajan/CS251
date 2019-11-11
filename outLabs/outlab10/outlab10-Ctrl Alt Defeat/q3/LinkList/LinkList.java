/*!
* This class implements the data structure LinkList,
* which consists of sequential nodes containing information,
* and links to the previous and the next nodes
*/    
public class LinkList<T> {
 
    private Node<T> front;
    private Node<T> rear;
     
    /*!
    * \param item The element to be inserted at the front of the LinkList
    * Inserts the element to the front of the LinkList,
    * links the old front node to the new one
    * and updates the front node of LinkList
    * Prints the element being added to the front
    */
    public void insertFront(T item){
        //! add element at the beginning of the queue
        System.out.println("adding at front: "+item);
        Node<T> nd = new Node<T>();
        nd.setValue(item);
        nd.setNext(front);
        if(front != null) front.setPrev(nd);
        if(front == null) rear = nd;
        front = nd;
    }
     
    /*!
    * \param item The element to be inserted at the rear of the LinkList
    * Inserts the element to the rear of the LinkList,
    * links the old rear node to the new one
    * and updates the rear node of LinkList
    * Prints the element being added to the rear
    */
    public void insertRear(T item){
        //! add element at the end of the queue
        System.out.println("adding at rear: "+item);
        Node<T> nd = new Node<T>();
        nd.setValue(item);
        nd.setPrev(rear);
        if(rear != null) rear.setNext(nd);
        if(rear == null) front = nd;
         
        rear = nd;
    }
     
    /*!
    * Removes the element at the front of the LinkList,
    * links the new front node to null
    * and updates the front node of LinkList
    * to the second node in the old List
    * Prints the element being removed from the front
    */
    public void removeFront(){
        if(front == null){
            System.out.println("Deque underflow!! unable to remove.");
            return;
        }
        //! remove an item from the beginning of the queue
        Node<T> tmpFront = front.getNext();
        if(tmpFront != null) tmpFront.setPrev(null);
        if(tmpFront == null) rear = null;
        System.out.println("removed from front: "+front.getValue());
        front = tmpFront;
    }
     
    /*!
    * Removes the element at the rear of the LinkList,
    * links the new rear node to null
    * and updates the rear node of LinkList
    * to the second last node in the old List
    * Prints the element being removed from the rear
    */
    public void removeRear(){
        if(rear == null){
            System.out.println("Deque underflow!! unable to remove.");
            return;
        }
        //! remove an item from the beginning of the queue
        Node<T> tmpRear = rear.getPrev();
        if(tmpRear != null) tmpRear.setNext(null);
        if(tmpRear == null) front = null;
        System.out.println("removed from rear: "+rear.getValue());
        rear = tmpRear;
    }
     
    /*!
    *  The main driver code for building the LinkList
    */
    public static void main(String a[]){
        LinkList<Integer> deque = new LinkList<Integer>();
        deque.insertFront(34);
        deque.insertFront(67);
        deque.insertFront(29);
        deque.insertFront(765);
        deque.removeFront();
        deque.removeFront();
        deque.removeFront();
        deque.insertRear(43);
        deque.insertRear(83);
        deque.insertRear(84);
        deque.insertRear(546);
        deque.insertRear(356);
        deque.removeRear();
        deque.removeRear();
        deque.removeRear();
        deque.removeRear();
        deque.removeFront();
        deque.removeFront();
        deque.removeFront();
    }
}

/*!
 * The Node class.
 * This class defines the node structure
 * which consists of a value, the next node
 * and the previous node in reference to itself
 * Each node defines each element of LinkList
 * All the variables are private, so public functions 
 * are made to fetch or set those variables
*/ 
class Node<T>{
     
    private Node<T> prev;
    private Node<T> next;
    private T value;
     
    public Node<T> getPrev() {
        return prev;
    }
    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
    public Node<T> getNext() {
        return next;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
}