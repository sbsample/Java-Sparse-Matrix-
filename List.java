public class List
{
	private Node front;
	private Node back;
	private Node index;
	private int length;
	private int cursor;

	public List()
	{
		this.front = null;
		this.back = null;
		this.index = null;
		this.length = 0;
		this.cursor = -1; 
	}

	private class Node// Node class for Linked List 
	{
		Object value;
		Node next;
		Node previous;

		Node(Object value)// Node Constructor
		{
			this.value = value;
			next = null;
			previous = null;
		}

	}
// Access Procedures

	public int length()
	{
		return length;
	}

	public int index()
	{
		return cursor;	
	}

	public Object front()
	{
		
		{
			return front.value;	
		}
		
	}

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
		if (length == 0)
		{
			throw new RuntimeException("Error! get() " + 
				"called on an empty list.");
		}
		else
		{	
			return index.value;
		}
	
	}

	public boolean equals(Object M)
	{
		List L = (List) M;
		Node thisFinder = this.front;
		Node lFinder = (Node) L.front;
		if (L == null)
		{
			throw new RuntimeException("Error! equals() " + 
				"called on an empty list.");
		}
		if (L.length != length)
		{
			return false;
		}
		for (int i = 0; i < length; i++)
		{
			if (lFinder.value != thisFinder.value)
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


	public void clear()
	{
		front = null;
		back = null;
		index = null;
		cursor = -1;
		length = 0;
	}

	public void moveFront()
	{
		if (length > 0)
		{
			cursor = 0;
			index = front;
		} 
	}

	public void moveBack()
	{
		if (length > 0)
		{
			cursor = length - 1;
			index = back;
		} 
	}

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

	public void delete()
	{
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
