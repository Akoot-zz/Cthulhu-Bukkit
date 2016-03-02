package com.Akoot.cthulhu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CthFile extends File
{
	private static final long serialVersionUID = 1L;

	public CthFile(String fileName)
	{
		super((fileName.contains(".") ? fileName : fileName + ".cth"));
	}

	public CthFile(File parent, String fileName)
	{
		super(parent, (fileName.contains(".") ? fileName : fileName + ".cth"));
	}

	private <T> void trace(T t)
	{
		System.out.println(t);
	}

	public void create()
	{
		try
		{
			this.createNewFile();
		} 
		catch (IOException e)
		{
			error(e);
		}
	}

	public String getString(String key)
	{
		String s = String.valueOf(get(key));
		return s;
	}

	public Boolean getBoolean(String key)
	{
		Boolean b = Boolean.valueOf(getString(key));
		return b;
	}

	public double getDouble(String key)
	{
		double d = Double.valueOf(getString(key));
		return d;
	}

	public int getInt(String key)
	{
		int i = Integer.valueOf(getString(key));
		if(Double.isNaN(i)) trace("key " + key + " is not a number");
		return i;
	}

	public List<Object> getList(String key)
	{
		List<Object> list = new ArrayList<Object>();
		//List<String> lines = read();
//		int found = 1;
//		for(String line: lines)
//		{
//			if(line.startsWith("- "))
//			{
//				if(lines.size() > found)
//				{
//					if(lines.get(lines.indexOf(line) - found).equals(key + ":"))
//					{
//						list.add(line.substring(2));
//						found++;
//						trace(line);
//					}
//				}
//			}
//		}
		return list;
	}

	private void write(List<String> lines)
	{
		try
		{
			PrintWriter pw = new PrintWriter(this);
			for(String ln: lines)
			{
				pw.println(ln);
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			error(e);
		}
	}

	public List<String> read()
	{
		return read("");
	}

	private List<String> read(String exclude)
	{
		List<String> lines = new ArrayList<String>();
		try
		{
			Scanner in = new Scanner(this);
			while(in.hasNextLine())
			{
				String ln = in.nextLine();
				if(!ln.matches(exclude) || exclude.isEmpty())
				{
					lines.add(ln);
				}
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			error(e);
		}
		return lines;
	}

	public void setList(String key, List<Object> data)
	{
		List<String> lines = read();
		if(!getList(key).isEmpty())
		{
			List<String> newLines = new ArrayList<String>();
			for(String line: lines)
			{
				//trace(line.substring(2));
				if(!line.startsWith(key + ":") && !getList(key).contains(line.substring(2)))
				{
					newLines.add(line);
				}
			}
			newLines.add(key + ":");
			for(Object o: data)
			{
				newLines.add("- " + o);
			}
			lines = newLines;
		}
		else
		{
			lines.add(key + ":");
			for(Object o: data)
			{
				lines.add("- " + o);
			}
		}
		write(lines);
	}

	@SuppressWarnings("unchecked")
	public void set(String key, Object data)
	{
		if(data instanceof ArrayList<?>)
		{
			setList(key, (ArrayList<Object>) data);
		}
		else
		{
			String line = key + ": ";
			if(data instanceof String)
			{
				String s = data.toString();
				line += "\"" + s + "\"";
			}
			else
			{
				line += data;
			}
			List<String> lines = read(key + ".*");

			lines.add(line);
			write(lines);
		}
	}

	public boolean has(String key)
	{
		return get(key) != null;
	}

	public void addComment(String comment)
	{
		addLine("# " + comment);
	}
	public void setComment(String key, String comment)
	{
		String line = key + ": ";
		String data = String.valueOf(get(key));
		if(data instanceof String)
		{
			String s = data.toString();
			line += "\"" + s + "\"";
		}
		else
		{
			line += data;
		}
		line += " #" + comment;
		try 
		{
			List<String> lines = new ArrayList<String>();
			lines.add(line);
			Scanner in = new Scanner(this);
			while(in.hasNextLine())
			{
				String ln = in.nextLine();
				if(!ln.startsWith(key))
				{
					lines.add(ln);
				}
			}
			in.close();
			write(lines);
		} 
		catch (FileNotFoundException e)
		{
			error(e);
		}
	}

	public void copyFromFile(File file)
	{
		try 
		{
			List<String> lines = new ArrayList<String>();
			Scanner in = new Scanner(file);
			while(in.hasNextLine())
			{
				String ln = in.nextLine();
				lines.add(ln);
			}
			in.close();
			write(lines);
		} 
		catch (FileNotFoundException e)
		{
			error(e);
		}
	}

	public void addLine(String line)
	{
		List<String> lines = new ArrayList<String>();
		lines = read();

		lines.add(line);
		write(lines);
	}

	public Object get(String key)
	{
		Object line = null;
		try 
		{
			Scanner in = new Scanner(this);
			while(in.hasNextLine())
			{
				String ln = in.nextLine();
				if(!ln.startsWith("#") && !ln.endsWith(":") && !ln.startsWith("- ") && ln.contains(":"))
				{
					String obj = ln.substring(0, ln.indexOf(":"));
					if(obj.equalsIgnoreCase(key))
					{
						String temp = ln.substring(ln.indexOf(":") + 2, ln.length());
						if(temp.contains("\""))
						{
							line = ln.substring(ln.indexOf(":") + 3, ln.lastIndexOf("\""));
						}
						else
						{
							line = ln.substring(ln.indexOf(":") + 2, ln.length());
						}
					}
				}
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			error(e);
		}
		return line;
	}

	public void addTo(String key, Object object)
	{
		List<Object> list = getList(key);
		list.add(object);
		setList(key, list);
	}

	public void replace(String key, Object object, Object replace)
	{
		if(has(key, object))
		{
			removeFrom(key, object);
		}
		addTo(key, replace);
	}

	public boolean has(String key, Object object)
	{
		if(getList(key) != null)
		{
			List<Object> list = getList(key);
			for(Object obj: list)
			{
				if(obj.equals(object))
				{
					return true;
				}
			}
		}
		return false;
	}

	public void removeFrom(String key, Object object)
	{
		if(getList(key) != null)
		{
			List<Object> list = new ArrayList<Object>();
			for(Object obj: getList(key))
			{
				if(!obj.equals(object))
				{
					list.add(obj);
				}
			}
			set(key, list);
		}
	}

	public void remove(String key)
	{
		try
		{
			List<String> lines = new ArrayList<String>();
			Scanner in = new Scanner(this);
			if(getList(key) != null)
			{
				while(in.hasNextLine())
				{
					String ln = in.nextLine();
					if(!ln.startsWith(key))
					{
						for(Object obj: getList(key))
						{
							if(ln.equalsIgnoreCase("- " + obj))
							{
								ln = "";
							}
						}
					}
					if(!ln.isEmpty()) lines.add(ln);
				}
			}
			else
			{
				lines = read(key + ".*");
			}
			in.close();
			write(lines);
		} 
		catch (FileNotFoundException e)
		{
			error(e);
		}
	}

	private void error(Exception e)
	{
		System.out.println("Error: " + e.getMessage());
	}
}
