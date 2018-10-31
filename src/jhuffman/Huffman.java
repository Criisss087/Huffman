package jhuffman;

import java.io.RandomAccessFile;
import java.util.Comparator;
import jhuffman.util.SortedList;
import jhuffman.util.TreeUtil;
import jhuffman.ds.Node;

public class Huffman
{
	public static void main(String[] args)
	{
		String filename = args[0];
		if( filename.endsWith(".huf") )
		{
			descomprimir(filename);
		}
		else
		{
			comprimir(filename);
		}
	}

	public static void comprimir(String filename)
	{


		SortedList<Node> listaOrdConcurrencia = new SortedList<>();
		Comparator<Node> cmp = new CmpInteger();

		// PROGRAMAR AQUI...
		System.out.println("Comprimiendo: "+filename);

		int[] concurrencia = new int[256];

		//PASO 1: Armar un array con la cantidad de ocurrencias de cada caracter
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filename,"r");

			int c = raf.read();
			while( c>=0 )
			{
				concurrencia[c]++;
				System.out.print(c);
				c = raf.read();
			}

			raf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println(" ");
		//PASO 2 Armar una lista ordenada por concurrencia con el nodo de huffman
		for (int i = 0; i < concurrencia.length; i++) {
			if(concurrencia[i] > 0)
			{
				Node n = new Node();
				n.setC(i);
				n.setN(concurrencia[i]);
				n.setDer(null);
				n.setIzq(null);

				listaOrdConcurrencia.add(n,cmp);

			}
		}

		//PASO 3 Armar el arbol de huffman
		while(listaOrdConcurrencia.size() > 1)
		{
			Node n1 = new Node();
			Node n2 = new Node();
			Node n3 = new Node();
			n1 = listaOrdConcurrencia.pop();
			n2 = listaOrdConcurrencia.pop();

			n3.setN(n1.getN()+n2.getN());
			n3.setDer(n1);
			n3.setIzq(n2);

			listaOrdConcurrencia.add(n3,cmp);
		}

		Node root = listaOrdConcurrencia.pop();

		StringBuffer sb = new StringBuffer();
		TreeUtil ut = new TreeUtil(root);

		// primera hoja
		Node x = ut.next(sb);
		while( x!=null )
		{
			// muestro el codigo Huffman
			System.out.println((char)x.getC()+": "+sb+" Cant: "+x.getN());

			// siguiente hoja
			x = ut.next(sb);
		}




	}

	public static void descomprimir(String filename)
	{
		// PROGRAMAR AQUI...
		System.out.println("Descomprimiendo: "+filename);
	}

	static class CmpInteger implements Comparator<Node>
	{
		@Override
		public int compare(Node a, Node b)
		{
			Long i;
			i = a.getN() - b.getN();
			return i.intValue();
		}

	}

}

