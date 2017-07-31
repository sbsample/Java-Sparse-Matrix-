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

    // equals()
    // tests whether List Object is the as called object
    public boolean equals(Object otherObject)
    {
      Entry entry = (Entry) otherObject;
      if (entry.column == this.column && entry.value == this.value)
      {
        return true;
      }

        return false;

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

    Matrix otherMatrix = (Matrix) X;
    if (otherMatrix.getSize() != this.getSize())
    {
      return false;
    }
    for (int i = 1; i <= size; i++)
    {
      if (!matrixArray[i].equals(otherMatrix.matrixArray[i]))
      {
        return false;
      }
    }
    return true;
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
    Entry entryChange = new Entry(j, x);
    if (matrixArray[i].index() == -1 && matrixArray[i].length() == 0 )
    {
      if (x != 0)
      {

        matrixArray[i].append(entryChange);
        nnz++;
      }
      return; // return always
    }
    matrixArray[i].moveFront();
    Entry thisEntry = (Entry) matrixArray[i].get();
    while (matrixArray[i].index() >= 0 && thisEntry.column != j ) // including j
    {
      thisEntry = (Entry) matrixArray[i].get();
      if (thisEntry.column == j)
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
        return;
      }
      else if (thisEntry.column > j ) // x == 0 inside
      {
        if (x == 0) return;
        matrixArray[i].insertBefore(entryChange);
        nnz++;
        return;
      }


      matrixArray[i].moveNext();
    }
    if (x == 0) return;
    matrixArray[i].append(entryChange);
    nnz++;
  }
  // scalarMult()
  // performs scalar multiplication
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
      for(matrixArray[i].moveFront(); matrixArray[i].index()>=0; matrixArray[i].moveNext())
      {
        Entry transEntry = (Entry) matrixArray[i].get();
 		int newColumn = transEntry.column;
		transMatrix.changeEntry(newColumn, i, transEntry.value);
      }
    }

    return transMatrix;
  }

  //add()
  // returns a new Matrix that is the sum of this Matrix with M
  public Matrix add(Matrix M)
  {

    if (this == M) return scalarMult(2); // edge case where adding to self
    Matrix newMatrix = new Matrix (size);
    for (int i = 1; i <= size; i++) // 1 to n
    {
      matrixArray[i].moveFront();
      M.matrixArray[i].moveFront();
      Entry entry1 = matrixArray[i].index() >= 0 ? // only if it exists
        (Entry)(matrixArray[i].get()) : null;
      Entry entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
        (Entry)(M.matrixArray[i].get()) : null;
      while (entry1 !=null || entry2 != null)
      {
        if (entry1 == null)
        {
          newMatrix.changeEntry(i, entry2.column, entry2.value);
          M.matrixArray[i].moveNext();
          entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
            (Entry)(M.matrixArray[i].get()) : null;
        }
        else if (entry2 == null)
        {
          newMatrix.changeEntry(i, entry1.column, entry1.value);
          matrixArray[i].moveNext();
          entry1 = matrixArray[i].index() >= 0 ? // only if it exists
            (Entry)(matrixArray[i].get()) : null;
        }
        else
        {
          if (entry1.column == entry2.column)
          {
            newMatrix.changeEntry(i, entry1.column, entry1.value + entry2.value);
            matrixArray[i].moveNext();
            M.matrixArray[i].moveNext();
            entry1 = matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(matrixArray[i].get()) : null;
            entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(M.matrixArray[i].get()) : null;
          }
          else if (entry1.column > entry2.column)
          {
            newMatrix.changeEntry(i, entry2.column, entry2.value);
            M.matrixArray[i].moveNext();
            entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(M.matrixArray[i].get()) : null;
          }
          else
          {
            newMatrix.changeEntry(i, entry1.column, entry1.value);
            this.matrixArray[i].moveNext();
            entry1 = matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(matrixArray[i].get()) : null;
          }
        }
      }
    }
    return newMatrix;
  }

  //sub()
  // returns a new Matrix that is the difference of this Matrix with M
  public Matrix sub(Matrix M)
  {

    if (this == M) return scalarMult(0); // edge case where subtracting to self
    Matrix newMatrix = new Matrix (size);
    for (int i = 1; i <= size; i++) // 1 to n
    {
      matrixArray[i].moveFront();
      M.matrixArray[i].moveFront();
      Entry entry1 = matrixArray[i].index() >= 0 ? // only if it exists
        (Entry)(matrixArray[i].get()) : null;
      Entry entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
        (Entry)(M.matrixArray[i].get()) : null;
      while (entry1 !=null || entry2 != null)
      {
        if (entry1 == null)
        {
          newMatrix.changeEntry(i, entry2.column, -1 * entry2.value); // sub 2nd item
          M.matrixArray[i].moveNext();
          entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
            (Entry)(M.matrixArray[i].get()) : null;
        }
        else if (entry2 == null)
        {
          newMatrix.changeEntry(i, entry1.column, entry1.value);
          matrixArray[i].moveNext();
          entry1 = matrixArray[i].index() >= 0 ? // only if it exists
            (Entry)(matrixArray[i].get()) : null;
        }
        else
        {
          if (entry1.column == entry2.column)
          {
            newMatrix.changeEntry(i, entry1.column, entry1.value - entry2.value);
            matrixArray[i].moveNext();
            M.matrixArray[i].moveNext();
            entry1 = matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(matrixArray[i].get()) : null;
            entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(M.matrixArray[i].get()) : null;
          }
          else if (entry1.column > entry2.column)
          {
            newMatrix.changeEntry(i, entry2.column, -1 * entry2.value); // sub 2nd item
            M.matrixArray[i].moveNext();
            entry2 = M.matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(M.matrixArray[i].get()) : null;
          }
          else
          {
            newMatrix.changeEntry(i, entry1.column, entry1.value);
            this.matrixArray[i].moveNext();
            entry1 = matrixArray[i].index() >= 0 ? // only if it exists
              (Entry)(matrixArray[i].get()) : null;
          }
        }
      }
    }
    return newMatrix;
  }
  //mult()
  //performs multiplication on two Matrices
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
  // public Matrix addOrSub(Matrix B, boolean someBool)
  // {
  //   Matrix asMatrix = new Matrix(size);
  //   if (someBool == false)
  //   {
  //     B = B.scalarMult(-1);
  //   }
  //   if (this.size != B.size)
  //   {
  //     throw new RuntimeException("Error! add() or sub() " +
  //         "Matrices not of equal size.");
  //   }
  //   else if (this.equals(B))
  //   {
  //     asMatrix = this.scalarMult(2);
  //   }
  //   else
  //   {
  //     for(int i = 0; i <= size; i++)
  //     {
  //       matrixArray[i].moveFront();
  //       B.matrixArray[i].moveFront();

  //       while( matrixArray[i].index() >=0 || B.matrixArray[i].index() >=0)
  //       {
  //         Entry thisEntry = (Entry) matrixArray[i].get();
  //         Entry bEntry = (Entry) B.matrixArray[i].get();

  //         if (thisEntry == null)
  //         {
  //           asMatrix.changeEntry(i, bEntry.column, bEntry.value);
  //           B.matrixArray[i].moveNext();

  //         }
  //         else if (bEntry == null)
  //         {
  //           asMatrix.changeEntry(i, thisEntry.column, thisEntry.value);;
  //           matrixArray[i].moveNext();

  //         }
  //         else
  //         {
  //           if (thisEntry.column > bEntry.column)
  //           {

  //             asMatrix.changeEntry(i, bEntry.column, bEntry.value);
  //             B.matrixArray[i].moveNext();
  //           }
  //           else if (thisEntry.column < bEntry.column)
  //           {
  //             asMatrix.changeEntry(i, thisEntry.column, thisEntry.value);
  //             matrixArray[i].moveNext();
  //           }
  //           else
  //           {	//if add == true then the two Entry values will be summed, else subtract

  //             double result = thisEntry.value + bEntry.value;
  //             asMatrix.changeEntry(i, thisEntry.column, result);
  //             matrixArray[i].moveNext();
  //             B.matrixArray[i].moveNext();
  //           }
  //         }

  //       }
  //     }

  //   }
  //   return asMatrix;
  // }

  // dot()
  // does the dot dirty

  private double dot(List A, List B)
  {
    double sum = 0;
    B.moveFront();
    A.moveFront();
    while(B.index() != -1 && A.index() != -1)
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

        A.moveNext();
      }
      else
      {

        B.moveNext();
      }
    }
    return sum;
  }
  // toString()
  // returns strings
  public String toString()
  {
    String finalStr = "";
    for (int i = 1; i <= size; ++i)
    {
      if ( matrixArray[i].length() != 0 )
      {
        finalStr += String.valueOf(i) + ": ";
        finalStr += matrixArray[i].toString() + "\n";
      }
    }
    return finalStr;
  }




}

