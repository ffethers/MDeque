package project3;
import java.util.Iterator;

/**
 * This class represents a custom data structure called an MDeque. It operates
 * like a doubly-linked list implementation of a queue, but keeps track of the
 * middle element in addition to a head and tail. In the case of an even length,
 * the middle is considered to be at position size/2 + 1
 * 
 * There is no limit on the amount of elements in the structure, and it does not
 * accept null as an element.
 * 
 * Javadoc page:
 * https://cs.nyu.edu/~joannakl/cs102_f22/projects/project3/project3/MDeque.html
 * 
 * @author Finn Fetherstonhaugh
 *
 * @param <E>
 */

public class MDeque<E> extends Object implements Iterable<E> {

	/**
	 * This class is an implementation of the Iterator<E> interface, and is
	 * instantiated by the MDeque iterator() method. It visits each item in the
	 * MDeque starting at the head and ending at the tail.
	 * 
	 * @author finn
	 *
	 */
	private class Itr implements Iterator<E> { // CITATION: code from LinkedList 3 presentation slides (slide 24)
		// node that will increment and visit each element
		private Node current = head;

		// Determines if current will return null or not
		public boolean hasNext() {
			return current != null;
		}

		// returns the data that current stores and increments current forward
		public E next() {
			E tmp = current.data;
			current = current.next;
			return tmp;
		}

	}

	/**
	 * This class is an implementation of the Iterator<E> interface, and is
	 * instantiated by the MDeque reverseIterator() method. It visits each item in
	 * the MDeque starting at the tail and ending at the head.
	 * 
	 * @author finn
	 *
	 */
	private class ReverseItr implements Iterator<E> { // CITATION: code from LinkedList 3 presentation slides (slide 24)
		// node that will increment and visit each element
		private Node current = tail;

		// Determines if current will return null or not
		public boolean hasNext() {
			return current != null;
		}

		// returns the data that current stores and increments current forward
		public E next() {
			E tmp = current.data;
			current = current.previous;
			return tmp;
		}
	}

	/**
	 * This class represents a Node stores a data element as well as the memory
	 * address of the next and previous element of the list.
	 * 
	 * @author finn
	 *
	 */
	private class Node {
		// variable to data
		private E data;

		// Node to store next element
		private Node next;

		// Node to store previous element
		private Node previous;

		// Node is instantiated without a next or previous
		public Node(E data) {
			this.data = data;
			this.next = null;
			this.previous = null;
		}
	}

	// Each MDeque stores a head, tail, middle, and size variable
	private Node head;
	private Node tail;
	private Node middle;
	private int size;

	// MDeque is instantiated with all pointers pointing to null and size at 0
	public MDeque() {
		this.head = null;
		this.tail = null;
		this.middle = null;
		this.size = 0;
	}

	// Retrieves the back element of this mdeque.
	public E peekBack() {
		// if MDeque is empty, return null
		if (size == 0) {
			return null;
		}
		return this.tail.data;
	}

	// Retrieves the first element of this mdeque.
	public E peekFront() {
		// if MDeque is empty, return null
		if (size == 0) {
			return null;
		}
		return this.head.data;
	}

	// Retrieves the middle element of this mdeque.
	public E peekMiddle() {
		// if MDeque is empty, return null
		if (size == 0) {
			return null;
		}
		return this.middle.data;
	}

	// Retrieves and removes the last element of this mdeque.
	public E popBack() {
		// if MDeque is empty, return null
		if (this.size() == 0) {
			return null;
		}
		// if MDeque has 1 element, return the data and set all pointers to null,
		// increment size
		if (this.size() == 1) {
			E tmp = this.head.data;
			head = null;
			tail = null;
			middle = null;
			size--;
			return tmp;
		}
		// set the tail to the 2nd to last element and remove the back element and
		// return its data
		E tmp = this.tail.data;
		Node n = tail.previous;
		n.next = null;
		tail = n;
		// move the middle left once if the original size was even
		if (size % 2 == 0) {
			middle = middle.previous;
		}
		// increment size
		size--;
		return tmp;
	}

	// Retrieves and removes the first element of this mdeque.
	public E popFront() {
		// if MDeque is empty, return null
		if (this.size() == 0) {
			return null;
		}
		// if MDeque has 1 element, return the data and set all pointers to null,
		// increment size
		if (this.size() == 1) {
			E tmp = this.head.data;
			head = null;
			tail = null;
			size--;
			return tmp;
		}
		// set the head to the 2nd element and remove the front element and return its
		// data
		E tmp = this.head.data;
		Node n = head.next;
		n.previous = null;
		head = n;
		// moves the middle right by one if the original size is odd
		if (size % 2 != 0) {
			middle = middle.next;
		}
		// increment size
		size--;
		return tmp;
	}

	// Retrieves and removes the middle element of this mdeque.
	public E popMiddle() {
		E tmp = null;
		// if MDeque is empty, return null
		if (this.size() == 0) {
			return null;
		}
		// if MDeque has 1 element, return the data and set all pointers to null,
		// increment size
		else if (this.size() == 1) {
			tmp = this.head.data;
			head = null;
			tail = null;
			middle = null;
		}
		// if MDeque has 2 elements, return the 2nd element's data and set all pointers
		// to the remaining element,
		// increment size
		else if (this.size() == 2) {
			tmp = this.tail.data;
			head.next = null;
			tail = head;
			middle = head;
		}
		// Otherwise, return the middle value and set the middle to the next or previous
		// value, depending on if the
		// list is an odd or even length
		else {
			tmp = peekMiddle();
			if (size % 2 == 0) {
				middle.previous.next = middle.next;
				middle.next.previous = middle.previous;
				middle = middle.previous;

			} else {
				middle.previous.next = middle.next;
				middle.next.previous = middle.previous;
				middle = middle.next;
			}
		}
		// increment size
		size--;
		return tmp;
	}

	// Inserts the specified item at the back of this MDeque.
	public void pushBack(E item) {
		// If the argument provided is null, throws an exception
		if (item == null) {
			throw new IllegalArgumentException("Error: Cannot push a null value");
		}
		Node n = new Node(item);
		// if the MDeque is empty, inserts item at index 0 and sets all pointers to
		// index 0
		if (head == null && tail == null) {
			head = n;
			tail = n;
			middle = n;

			head.next = null;
			tail.next = null;
			middle.next = null;
			head.previous = null;
			tail.previous = null;
			middle.previous = null;

		}
		// if the MDeque has 1 element, inserts the item after the first item, sets
		// head, middle, and tail accordingly
		else if (head == tail) {
			n.previous = head;
			n.next = null;
			head.next = n;
			tail = n;
			middle = tail;
		}
		// otherwise, insert the item at the back and increment the tail pointer to the
		// new element
		else {
			n.previous = tail;
			n.next = null;
			tail.next = n;
			tail = n;
			// if the size starts at an odd value, increment the middle right one
			if (size % 2 != 0) {
				middle = middle.next;
			}
		}
		// increment size
		size++;

	}

	// Inserts the specified item at the front of this mdeque.
	public void pushFront(E item) {
		// If the argument provided is null, throws an exception
		if (item == null) {
			throw new IllegalArgumentException("Error: Cannot push a null value");
		}
		Node n = new Node(item);
		// if the MDeque is empty, inserts item at index 0 and sets all pointers to
		// index 0
		if (head == null && tail == null) {
			head = n;
			tail = n;
			middle = n;

			head.next = null;
			tail.next = null;
			head.previous = null;
			tail.previous = null;
			middle.next = null;
			middle.next = null;

		}
		// if the MDeque has 1 element, inserts the item before the first item, sets
		// head, middle, and tail accordingly
		else if (head == tail) {
			n.next = tail;
			n.previous = null;
			tail.previous = n;
			head = n;
			middle = tail;
		}
		// otherwise, insert the item at the front and increment the head pointer to the
		// new element
		else {
			n.next = head;
			n.previous = null;
			head.previous = n;
			head = n;
			// if the size starts at even value, increment the middle left one
			if (size % 2 == 0) {
				middle = middle.previous;
			}
		}
		// increment size
		size++;
	}

	// Inserts the specified item in the middle of this mdeque
	public void pushMiddle(E item) {
		Node n = new Node(item);

		// If the argument provided is null, throws an exception
		if (item == null) {
			throw new IllegalArgumentException("Error: Cannot push a null value");
		}
		// if the MDeque is empty, inserts item at index 0 and sets all pointers to
		// index 0
		if (head == null && tail == null) {
			head = n;
			tail = n;
			middle = n;

			head.next = null;
			tail.next = null;
			middle.next = null;
			head.previous = null;
			tail.previous = null;
			middle.previous = null;
		}
		// if the MDeque has 1 element, inserts the item after the first item, sets
		// head, middle, and tail accordingly
		else if (head == tail) {
			n.previous = head;
			n.next = null;
			head.next = n;
			tail = n;
			middle = tail;
		}
		
		// if size is 2, put the new node between the head and tail and assign middle to it
		else if (size == 2) {
			n.next = tail;
			n.previous = middle.previous;
			middle = n;
			head.next = middle;
			tail.previous = middle;
		}
		// otherwise, if the size is even, insert the before the middle and set it as
		// the new middle
		// if the size is odd, insert the after the middle and set it as the new middle
		else {
			if (size % 2 == 0) {
				Node tmp = middle.previous;
				n.next = middle;
				n.previous = middle.previous;
				middle.previous = n;
				tmp.next = n;
				middle = n;

			} else {
				Node tmp = middle.next;
				n.next = middle.next;
				n.previous = middle;
				middle.next = n;
				tmp.previous = n;
				middle = n;
			}
		}
		// increment size
		size++;
	}

	// Returns a new instance of the Itr class
	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	// Returns a new instance of the reverseItr class
	public Iterator<E> reverseIterator() {
		return new ReverseItr();
	}

	// Returns the current size
	public int size() {
		return size;
	}

	// Creates a string representation of the MDeque (wrapper method)
	@Override
	public String toString() {
		// tmp keeps track of the original head, so it can be reset after the method
		// runs
		Node tmp = head;
		String s = "[";
		s += recString(this);
		s += "]";
		head = tmp;
		return s;
	}

	// Creates a string representation of the MDeque (recursive method)
	public String recString(MDeque<E> m) {
		// base case: head has traversed past every element
		if (head == null) {
			return "";
		}
		// when head reaches last element, returns the head value without adding a comma
		if (head == tail) {
			String s = peekFront().toString();
			head = head.next;
			s += recString(m);
			return s;
		}
		// returns the value of head and increments it one to the right
		else {
			String s = peekFront().toString() + ", ";
			head = head.next;
			s += recString(m);
			return s;
		}
	}

	// reverses the elements of the current MDeque
	public void reverseMdeque() {
		// New forwards and backward iterators
		Iterator<E> itr = this.iterator();
		Iterator<E> reverseItr = this.reverseIterator();

		int counter = 0;

		int oddOrEven = (size % 2 == 0) ? size / 2 : size / 2 + 1;

		// while loop stops at the middle element, depending on odd or even size
		while (counter < oddOrEven) {
			// points to the outer two elements
			Node forward = ((Itr) itr).current;
			Node backward = ((ReverseItr) reverseItr).current;

			// tmp variables to keep track of next and previous for each element
			Node forwardNext = forward.next;
			Node forwardPrevious = forward.previous;
			Node backwardNext = backward.next;
			Node backwardPrevious = backward.previous;

			// increments current
			itr.next();
			reverseItr.next();

			// swaps the next and previous values of the two elements
			forward.next = forwardPrevious;
			forward.previous = forwardNext;
			backward.next = backwardPrevious;
			backward.previous = backwardNext;

			counter++;
		}

		// shifts the middle forward one if the size is even
		if (size % 2 == 0) {
			middle = middle.next;
		}

		// swaps head and tail values
		Node tmp = head;
		head = tail;
		tail = tmp;

	}
}
