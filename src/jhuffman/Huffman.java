package jhuffman;

import java.io.RandomAccessFile;

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
		// PROGRAMAR AQUI...
		System.out.println("Comprimiendo: "+filename);
		
		int[] concurrencia = new int[256];
		/*for (int i = 0; i < concurrencia.length; i++) {
		      concurrencia[i] = 0;
		}
		*/
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
		
		
	}
	
	public static void descomprimir(String filename)
	{
		// PROGRAMAR AQUI...
		System.out.println("Descomprimiendo: "+filename);
	}
}
