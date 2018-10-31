package jhuffman;

import java.io.RandomAccessFile;
import java.util.Comparator;
import jhuffman.util.SortedList;
import jhuffman.util.TreeUtil;
import jhuffman.ds.Node;
import jhuffman.util.BitReader;
import jhuffman.util.BitWriter;

public class Huffman
{
	public static void main(String[] args)
	{
		String filename = args[0];
		if( filename.endsWith(".huff") )
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

		long cant_codificados = 0;
		SortedList<Node> listaOrdConcurrencia = new SortedList<>();
		Comparator<Node> cmp = new CmpInteger();

		// PROGRAMAR AQUI...
		System.out.println("Comprimiendo: "+filename);

		int[] concurrencia = new int[256];
		String[] cod_huff = new String[256];

		//PASO 1: Armar un array con la cantidad de ocurrencias de cada caracter
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filename,"r");

			int c = raf.read();
			while( c>=0 )
			{
				concurrencia[c]++;
				//System.out.print(c);
				cant_codificados++;
				c = raf.read();
			}

			raf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		//System.out.println(" ");
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
		/*
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
		*/
		
		System.out.println("Cantidad de caracteres codificados: "+cant_codificados);
		
		/*
		1. Al inicio contiene el árbol Huffman, representado por t registros cuya estructura se explica más abajo.
		2. Luego contiene 8 bytes (long) indicando cuantos caracteres fueron codificados; es decir: la longitud, 
		en bytes, del archivo original.
		3. Finalmente contiene la información codificada y comprimida.

		-- 1 byte con el carácter (o valor numérico) que representa la hoja del árbol.
		-- 1 byte indicando la longitud del código Huffman que se le asignó al carácter.
		-- h bytes que contienen los bits (caracteres 1 o 0) que componen el código Huffman del carácter.
		*/
		
		try
		{	
			String filename_huff = filename+".huff";
			BitWriter arch = new BitWriter(filename_huff);
		
			StringBuffer sb = new StringBuffer();
			TreeUtil ut = new TreeUtil(root);

			// primera hoja
			Node x = ut.next(sb);
			while( x!=null )
			{	
				arch.writeBit(x.getC());
				arch.writeBit(sb.length());
				
				cod_huff[x.getC()] = sb.toString();
				
				for(int i=0; i < sb.length(); i++) {
					//int a = Character.getNumericValue(sb.charAt(i));
					arch.writeBit(sb.charAt(i));
				}
					
				// siguiente hoja
				x = ut.next(sb);
			}
			
			arch.writeLong(cant_codificados);
			
			
			System.out.println("Lista de codigos huff: ");
			for(int i=0; i < cod_huff.length; i++) {
				if(cod_huff[i]!=null)
				{
					System.out.println("Letra: "+(char)i+" Codigo: "+cod_huff[i]);
				}
					
			}
			
			
			try
			{
				RandomAccessFile raf = new RandomAccessFile(filename,"r");

				int c = raf.read();
				while( c>=0 )
				{
					String s = cod_huff[c];
					
					for(int i = 0; i < s.length() ;i++)
					{
						//int a = Character.getNumericValue(s.charAt(i));
						arch.writeBit(s.charAt(i));
					}

					c = raf.read();
				}

				raf.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			arch.close();
			System.out.println("Compresión Exitosa!!");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public static void descomprimir(String filename)
	{
		System.out.println("Descomprimiendo: "+filename);
		
		try
		{	
			BitReader arch = new BitReader(filename);
			
			String[] cod_huff = new String[256];
			
			int pos = arch.readBit();
			while(!arch.eof())
			{				
				
				while(pos> 0 && pos<256)
				{
					//System.out.println("int: "+(int)pos+" char: "+(char)pos);
										
					int length = arch.readBit();
					//System.out.println(" pos char: "+(char)pos+" length int: "+(int)length+" lengt char: "+(char)length);
					StringBuffer sb = new StringBuffer();
					
					for(int i=0; i<length ; i++)
					{
						int c = arch.readBit();
						sb.append((char)c);
						//System.out.println((char)c);
					}
					
					//System.out.println("pos int: "+(int)pos+" pos char: "+(char)pos);
					cod_huff[pos] = sb.toString();
					System.out.println((char)pos+" "+cod_huff[pos]);
					pos = arch.readBit();
					System.out.println(pos);
				}
				
				long cantCaract = arch.readLong();
				
			}
			
			
			System.out.println("Lista de codigos huff: ");
			for(int i=0; i < cod_huff.length; i++) {
				System.out.println("Letra: "+(char)i+" Codigo: "+cod_huff[i]);
			}
					
			arch.close();
			System.out.println("Descompresion Exitosa!!");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
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

