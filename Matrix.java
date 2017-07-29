public class Matrix
{
	private int nnz;
	private int size;
	private List [] matrixArray;

	//Matrix constructor
	// list Objects contained in an array.
	public Matrix(int size)
	{
		this.size = size;
		nnz = 0;
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
		return nnz;
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
			for (int i = 1; i <= this.size; i++)
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
				else
				{
					this.matrixArray[i].moveFront();
					otherMatrix.matrixArray[i].moveFront();

					while(same == true && this.matrixArray[i].index() >= 0)
					{

						Entry thisEntry = (Entry)this.matrixArray[i].get();
						Entry otherEntry = (Entry)otherMatrix.matrixArray[i].get();
						if(!thisEntry.equals(otherEntry))
						{
							same = false;
						}
						this.matrixArray[i].moveNext();
						otherMatrix.matrixArray[i].moveNext();
					}
				}
			}
		}

		return same;
	}

	//////////Manipulation procedures

	// makeZero()
	// Sets matrix to zero
	public void makeZero()
	{
		for (int i = 0; i <= size; i ++)
		{
			if (matrixArray[i] == null)
			{
				continue;
			}
			else
			{
				matrixArray[i].clear();
			}
		}
		nnz = 0;
	}
	// copy()
	// iterates this Matrix and returns the copy
	public Matrix copy()
	{	Matrix copyMatrix = new Matrix(size);
		if (matrixArray == null)
		{
			copyMatrix = null;
		}
		else if (size == 0)
		{
			return copyMatrix;
		}
		else
		{

			for (int i = 1; i <= size ; i++) 
			{
				matrixArray[i].moveFront();
				while (this.matrixArray[i].index() >= 0)
				{
					Entry thisEntry = (Entry)this.matrixArray[i].get();
					Entry copyEntry = thisEntry;
					copyMatrix.matrixArray[i].append(copyEntry);
					this.matrixArray[i].moveNext();
					copyMatrix.nnz++;

				}	
			}
		}
		return copyMatrix;
	}

	// changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x)
	{
		Entry thisEntry = null;
		if (matrixArray[i].length() > 0)
		{
			matrixArray[i].moveFront();
			thisEntry = (Entry) matrixArray[i].get();
			while (matrixArray[i].index() >= 0 && thisEntry.column < j )
			{
				matrixArray[i].moveNext();
				if (matrixArray[i].index() != -1)
				{
					thisEntry = (Entry) matrixArray[i].get();
				}
				else
				{
					break;
				}
			}
		}
		Entry entryChange = new Entry(j, x);
		if (matrixArray[i].index() == -1 )
		{
			if (x != 0)
			{

				matrixArray[i].append(entryChange);
				nnz++;

			}
		}
		else if (thisEntry.column == j)
		{
			if (x == 0)
			{
				matrixArray[i].delete();
				nnz--;
			}
			else
			{
				thisEntry.value = x;
				
			}
		}
		else if (thisEntry.column > j && x != 0)
		{
			matrixArray[i].insertBefore(entryChange);
		}

		
	} 

	Matrix scalarMult(double x)
	{
		Matrix scalarMatrix = new Matrix(size);
		if (x == 0.0 || size == 0)
		{
			return scalarMatrix;
		}
		for (int i = 1; i <= size; i++)
		{
			if (matrixArray[i] == null)
			{
				continue;
			}
			else
			{
				matrixArray[i].moveFront();
				while (matrixArray[i].index() >= 0)
				{
					Entry thisEntry = (Entry) matrixArray[i].get();
					Entry scalarEntry = new Entry(thisEntry.column, thisEntry.value * x);
					scalarMatrix.matrixArray[i].append(scalarEntry);	
					matrixArray[i].moveNext(); 
					scalarMatrix.nnz++;

				}

			}
		}

		return scalarMatrix;
	}

	//transpose()
	//
	public Matrix transpose()
	{
		Matrix transMatrix = new Matrix(size);
		for(int i = 0; i <= size; i++)
		{
			matrixArray[i].moveFront();
			while( matrixArray[i].index() >=0)
			{
				Entry transEntry = (Entry) matrixArray[i].get();
				int temp = transEntry.column - 1; 
				transMatrix.matrixArray[temp].append(new Entry(i + 1, transEntry.value));
				matrixArray[i].moveNext();
			}
		}
		transMatrix.nnz = this.nnz;
		return transMatrix;
	}

	//add()
	// returns a new Matrix that is the sum of this Matrix with M
	public Matrix add(Matrix M)
	{
		boolean addSwitch = true; 
		return addOrSub( M, addSwitch);
		
	}
 
 	//sub()
 	// returns a new Matrix that is the difference of this Matrix with M
	public Matrix sub(Matrix M)
	{
		boolean subSwitch = false;
		return addOrSub(M, subSwitch);
	}

	public Matrix mult(Matrix M)
	{
		M = M.transpose();
		Matrix multMatrix = new Matrix(size);
		for (int i = 1; i <= size; i++)
		{
			for (int j = 1; j <= size; j++)
			{
				multMatrix.changeEntry(i, j, dot(this.matrixArray[i], M.matrixArray[j]));
			}
		}
		return multMatrix;
	}


	// Helper functions

	// addOrSub()
	// does the computing for add() and sub()
	public Matrix addOrSub(Matrix B, boolean someBool)
	{
		Matrix asMatrix = new Matrix(size); 
		if (someBool == false)
		{
			B = B.scalarMult(-1);
		}
		if (this.size != B.size)
		{
			throw new RuntimeException("Error! add() or sub() " + 
				"Matrices not of equal size.");
		}
		else if (this.equals(B))
		{
			asMatrix = this.scalarMult(2);
		}
		else
		{
			

			for(int i = 0; i <= size; i++)
			{
				matrixArray[i].moveFront();
				B.matrixArray[i].moveFront();
				while( matrixArray[i].index() >=0 || B.matrixArray[i].index() >=0)
				{	
					Entry asEntry = null;
					Entry bEntry = null;
					Entry thisEntry = null;
					if (matrixArray[i].index() != -1)
					{
						thisEntry = (Entry) matrixArray[i].get();
					}
					if (B.matrixArray[i].index() != -1)
					{
						bEntry = (Entry) B.matrixArray[i].get();
					}		
					if (matrixArray[i].index() == -1)
					{
						while (B.matrixArray[i].index() >= 0)
						{
							asMatrix.matrixArray[i].append(bEntry);
							B.matrixArray[i].moveNext();
							asMatrix.nnz++;
						}
					}
					else if (B.matrixArray[i].index() == -1)
					{
						while (matrixArray[i].index() >= 0)
						{
							asMatrix.matrixArray[i].append(thisEntry);
							matrixArray[i].moveNext();
							asMatrix.nnz++;
						}
					}
					else if (thisEntry.column > bEntry.column)
					{
						
						asMatrix.matrixArray[i].append(bEntry);
						B.matrixArray[i].moveNext(); 
						asMatrix.nnz++;
					}
					else if (thisEntry.column < bEntry.column)
					{
						asMatrix.matrixArray[i].append(thisEntry);
						matrixArray[i].moveNext();
						asMatrix.nnz++; 
					}
					else
					{	//if add == true then the two Entry values will be summed, else subtract
						
						double result = thisEntry.value + bEntry.value;
						if (result != 0)
						{
							asEntry = new Entry(thisEntry.column,result);
							asMatrix.matrixArray[i].append(asEntry);	
						}
						asMatrix.nnz++;
						matrixArray[i].moveNext();
						B.matrixArray[i].moveNext();
					}

				}
			}

		}
		return asMatrix;		
	}

	// dot()
	// does the dot dirty

	private double dot(List A, List B)
	{
		double sum = 0;
		B.moveFront();
		A.moveFront();
		while(B.index() >= 0 && A.index() >= 0)
		{
			Entry bEntry = (Entry)(B.get());
			Entry thisEntry = (Entry)(A.get()); 
			if(thisEntry.column == bEntry.column)
			{
				sum += bEntry.value * thisEntry.value; 
				A.moveNext();
				B.moveNext();
			}
			else if(thisEntry.column < bEntry.column)
			{
				sum = 0;
				A.moveNext();
			}
			else
			{	
				sum = 0;
				B.moveNext();
			}
		}
		return sum; 
	}

	public String toString() 
	{
		String finalStr = "";
		for(int i = 0; i <= size; i++) 
		{
			if(matrixArray[i].length() < 1) 
			{
				continue;
			}
			finalStr = finalStr + (i+1)+":"+matrixArray[i].toString()+"\n";
		}
		return finalStr;
	}




}

