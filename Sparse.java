//Staunton Sample
//pa3
//CMPS 101
//Sparse.java

import java.io.*;
import java.util.Scanner;

class Sparse
{
	public static void main(String[] args) throws IOException
	{
		if (args.length < 2) 
		{
			throw new RuntimeException ("Please call 2 arguments with Sparse");
		}
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(args[1]);

		int size = in.nextInt();
		int aInt = in.nextInt();
		int bInt = in.nextInt();

		Matrix A = new Matrix (size);
		Matrix B = new Matrix (size);

		in.nextLine();
		for (int i = 1; i <= aInt; i++) 
		{
			int row = in.nextInt();
			int column = in.nextInt();
			double value = in.nextDouble();
			A.changeEntry(row,column,value);
		}
		in.nextLine();
		for (int i = 1; i <= bInt; i++)
		{
			int row = in.nextInt();
			int column = in.nextInt();
			double value = in.nextDouble();
			B.changeEntry(row,column,value);
		}
		out.println("A has "+aInt+" non-zero entries:");
		out.println(A);
		out.println("B has "+bInt+" non-zero entries:");
		out.println(B);
		out.println("(1.5)*A =");
		out.println(A.scalarMult(1.5));
		out.println("A+B =");
		out.println(A.add(B));
		out.println("A+A =");
		out.println(A.add(A));
		out.println("B-A =");
		out.println(B.sub(A));
		out.println("A-A =");
		out.println(A.sub(A));
		out.println("Transpose(A) =");
		out.println(A.transpose());
		out.println("A*B = ");
		out.println(A.mult(B));
		out.println("B*B = ");
		out.println(B.mult(B));
		
		in.close();
		out.close();
		return;
	}
	
}
