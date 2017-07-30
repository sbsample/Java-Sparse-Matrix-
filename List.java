//Staunton Sample
//pa3
//CMPS 101
//List.java

public class List
{
	private Node front;
	private Node back;
	private Node index;
	private int length;
	private int cursor;

	//List() creates List
	public List()
	{
		this.front = null;
		this.back = null;
		this.index = null;
		this.length = 0;
		this.cursor = -1;
	}
	//Node()
	//defines Node
	private class Node// Node class for Linked List
	{
		Object value;
		Node next;
		Node previous;
		// Node Constructor
		Node(Object value)// Node Constructor
		{
			this.value = value;
			next = null;
			previous = null;
		}

	}
// Access Procedures
	//length()
	//returns length
	public int length()
	{
		return length;
	}
	//index()
	//returns cursor
	public int index()
	{
		return cursor;
	}
	//front()
	//returns node at front of List
	public Object front()
	{

		{
			return front.value;
		}

	}
	//back ()
	//returns Node at back of list
	public Object back()
	{
		if (length == 0)
		{
			 throw new RuntimeException("Error! back() " +
				"called on an empty list.");
		}
		else
		{
			return back.value;
		}
	}
//Finds and returns element at given cursor location
	public Object get()
	{
		return index.value;
	}

	//equals()
	//returns true if Lists are equal
	public boolean equals(Object M)
	{
		List L = (List) M;
		Node thisFinder = this.front;
		Node lFinder =  L.front;

		if (L.length != length)
		{
			return false;
		}
		for (int i = 0; i < length; i++)
		{
			if (!lFinder.value.equals(thisFinder.value))
			{
				return false;
			}
			else
			{
				lFinder = lFinder.next;
				thisFinder = thisFinder.next;
			}

		}
		return true;
	}

//Manipulation procedures

	//clear()
	//clears list
	public void clear()
	{
		front = null;
		back = null;
		index = null;
		cursor = -1;
		length = 0;
	}
	// moveFront()
	// moves cursor to front
	public void moveFront()
	{
		if (length > 0)
		{
			cursor = 0;
			index = front;
		}

	}
	// moveBack()
	//moves cursor to back
	public void moveBack()
	{
		if (length > 0)
		{
			cursor = length - 1;
			index = back;
		}
	}
	// movePrev()
	// moves cursor back in list
	public void movePrev()
	{
		if (cursor > 0)
		{
			cursor -= 1;
			index = index.previous;

		}
		else if (cursor == 0)
		{
			cursor = -1;
			index = null;
		}
	}

	// moveNext()
	//moves cursor forward
	public void moveNext()
	{
		if (cursor >= 0 && cursor != length -1)
		{
			cursor += 1;
			index = index.next;
		}
		else if(cursor == length -1)
		{
			cursor = -1;
			index = null;
		}
	}

	//prepend()
	//adds item to front of list
	public void prepend(Object data)
	{
		Node n = new Node(data);
		if (front == null && back == null)
		{

			front = n;
			back = n;

		}
		else
		{
			n.next = front;
			front.previous = n;
			front = n;
			if (cursor >= 0)
			{
				cursor += 1;
			}

		}
		length += 1;


	}

	// append()
	//adds item to back of list
	public void append(Object data)
	{
		Node n = new Node(data);
		if (front == null && back == null)
		{
			front = n;
			back = n;
		}
		else
		{
			n.previous = back;
			back.next = n;
			back = n;

		}
		length += 1;
	}

	//insertBefore
	// inserts item before cursor
	public void insertBefore(Object data)
	{

		Node n = new Node(data);
		if (cursor > 0)
		{

			n.next = index;
			index.previous.next = n;
			n.previous = index.previous;
			index.previous = n;
			length += 1;
			cursor += 1;
		}
		else if (cursor == 0)
		{
			n.next = index;
			index.previous = n;
			front = n;
			length += 1;
			cursor += 1;
		}
		else
		{
			throw new RuntimeException("Error! insertBefore() " +
				"called on an undefined cursor.");
		}


	}

	// insertAfter()
	// inserts after cursor
	public void insertAfter(Object data)
	{
		Node n = new Node(data);
		if (cursor < length - 1 && cursor != -1)
		{

			n.previous = index;
			n.next = index.next;
			index.next.previous = n;
			index.next = n;
			length += 1;
		}
		else if (cursor == length -1)
		{
			n.previous = index;
			index.next = n;
			length += 1;
			back = n;
		}
		else
		{
			throw new RuntimeException("Error! insertAfter() " +
				"called on an undefined cursor.");
		}
	}
	//deleteFront()
	//deletes at front of list
	public void deleteFront()
	{

		if (length > 1 && cursor == -1)
		{
			front = front.next;
			front.previous = null;
			length -= 1;

		}
		else if (length > 1 && cursor != 0)
		{
			front = front.next;
			front.previous = null;
			length -= 1;
			cursor -= 1;

		}

		else if (length > 1 && cursor == 0)
		{
			front = front.next;
			front.previous = null;
			length -= 1;
			cursor = -1;
			index = null;
		}
		else if	(length == 1 && cursor == 0)
		{
			front = null;
			back = null;
			cursor = -1;
			index = null;
			length = 0;
		}


		else if (length == 1 && cursor == -1)
		{

			front = front.next;
			length = 0;

		}
	}

	//deleteBack()
	//deletes at Back or list
	public void deleteBack()
	{
		Node temp;
		if (length > 1 && cursor < length - 1)
		{
			temp = back;
			back = back.previous;
			back.next = null;
			length -= 1;

		}
		else if (length == 1 && cursor == 0)
		{
			front = null;
			back = null;
			cursor = -1;
			index = null;
			length = 0;
		}
		else if(length > 1 && cursor == length - 1)
		{
			temp = back;
			back = back.previous;
			cursor = -1;
			index = null;
			length -= 1;
		}
		else if (length == 1 && cursor == -1)
		{
			front = null;
			back = null;
			length = 0;

		}
	}

	// delete()
	//good ole delete
	public void delete()
	{
		if (index() != -1 && length() == 1)
    	{
      		clear();
      		return;
    	}
		Node temp;
		if (index == front)
		{
			temp = front;
			front = front.next;
			front.previous = null;
			temp.next = null;
			temp = null;
			index = null;
			cursor = -1;

		}
		else if (index == back)
		{
			temp = back;
			back = back.previous;
			back.next = null;
			temp.previous = null;
			temp = null;
			index = null;
			cursor = -1;
		}
		else
		{
			index.previous.next = index.next;
			index.next.previous = index.previous;
			index.next = null;
			index.previous =null;
			index = null;
			cursor = -1;
	    }
	    length -= 1;
	}

	// Other Methods
	// toString
	//Overides toString
	public String toString()
	{
		Node temp = front;
		String output = "";
		for (int i = 0; i < length; i++)
		{

			if ( i == length - 1 )
			{
				output += temp.value + "";
			}
			else
			{
				output += temp.value + " ";
			}
			temp = temp.next;
		}
		return output;
	}





}
