public class Matrix
{
	private int nonZero;
	private int size;
	private List [] matrixArray;

	//Matrix constructor
	// list Objects contained in an array.
	public Matrix(int size)
	{
		this.size = size;
		nonZero = 0;
		matrixArray = new List[size + 1];
		for (int i = 0; i <= size; i++)
		{
			matrixArray[i] = new List();
		}
	}

	private class Entry
	{
		int column;
		double value;

		// Entry Object and Contructors

		Entry(int column, double value)
		{
			this.column = column;
			this.value = value;
		}

		//equals()
		// tests whether List Object is the as called object
		public boolean equals(Object otherObject)
		{
			Entry entry = (Entry) otherObject;
			if (entry.column == this.column && entry.value == this.value)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		//toString()
		// prints Entry Object, overrides object toString
		public String toString()
		{
			return ( "(" + column +", "+ value +")" );
		}
	}

	// Acess functions

	//getsize()
	//returns size of the matrix
	public int getSize()
	{
		return this.size;
	}

	// getNNZ()
	// returns the number of non-zeros in the matrix
	public int getNNZ()
	{
		return nonZero;
	}

	// equals()
	// does the argument equal the the existing object
	public boolean equals(Object X)
	{	
		boolean same = true;
		Matrix otherMatrix = (Matrix) X;
		if (otherMatrix.getSize() != this.getSize())
		{
			same = false;	
		}
		else if (otherMatrix.getNNZ() != this.getNNZ())
		{
			same = false;
		}
		else
		{
			for (int i = 1; i < this.size; i++)
			{
				if (same == false)
				{
					break;
				}
				else if (otherMatrix.matrixArray[i].length() != this.matrixArray[i].length())
				{
					same = false;
					break;
				} 
				else if ( otherMatrix.matrixArray[i].equals(this.matrixArray[i]) )
				{
					same = false;
				}
			}
		}

		return same;	
	}
}
