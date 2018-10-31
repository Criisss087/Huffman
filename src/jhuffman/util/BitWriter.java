package jhuffman.util;

import java.io.RandomAccessFile;

public class BitWriter
{
	private RandomAccessFile raf = null;
	
	public BitWriter(String filename)
	{				
		try
		{				
			this.raf = new RandomAccessFile(filename,"rw");					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	public void writeBit(int bit)
	{
		// programar aqui		
		try
		{				
			this.raf.writeByte(bit);				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void flush()
	{
		// programar aqui		
	}
	
	public void close()
	{
		try
		{				
			this.raf.close();					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
			
	}
	
	public void writeLong(long l)
	{
		try
		{				
			this.raf.writeLong(l);				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
