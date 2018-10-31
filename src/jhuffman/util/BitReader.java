package jhuffman.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BitReader
{
	private RandomAccessFile raf = null;
	
	public BitReader(String filename)
	{
		// programar aqui	
		try
		{				
			this.raf = new RandomAccessFile(filename,"r");					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public int readBit()
	{
		// programar aqui
		int a;
		
		try
		{
			a = this.raf.readByte();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return a;
	}
	
	public long readLong()
	{
		long a;
		
		try
		{
			a = this.raf.readLong();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return a;
	}
	
	public boolean eof() 
	{
		// programar aqui
		
		try
		{
			long pos = this.raf.getFilePointer();
			int a = this.raf.read();
			if(a<0)
				return true;
			else
				this.raf.seek(pos);
			
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			//throw new RuntimeException(e);
			return true;
		}
		
		return false;
	}
		
	public void close()
	{
		// programar aqui		
	}
}
