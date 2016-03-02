package com.Akoot.cthulhu.utils;

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
		super(fileName + ".cth");
	}

	public CthFile(File parent, String fileName)
	{
		super(parent, fileName + ".cth");
	}

	public void create()
	{
		try
		{
			this.createNewFile();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String getString(String key)
	{
		String s = String.valueOf(get(key));
		if(s.equals("null")) s = "";
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
		return i;
	}

	public List<Object> getList(String key)
	{
		List<Object> list = new ArrayList<Object>();
		try 
		{
			Scanner in = new Scanner(this);
			int i = 0;
			int listStart = 0;
			int listEnd = 0;

			while(in.hasNextLine())
			{
				i++;
				String ln = in.nextLine();
				if(!ln.startsWith("- ") && !ln.startsWith("#"))
				{
					if(ln.endsWith(":"))
					{
						String obj = ln.substring(0, ln.length() - 1);
						if(obj.equalsIgnoreCase(key))
						{
							listStart = i;
						}
					}
					else
					{
						if(listStart < i)
						{
							listEnd = i;
						}
					}
				}
			}
			in.close();

			in = new Scanner(this);
			i = 0;

			while(in.hasNextLine())
			{
				i++;
				String ln = in.nextLine();
				if(i > listStart && i < listEnd)
				{
					System.out.println("added " + ln);
					list.add(ln.substring(2));
				}
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + this.getPath());
		}
		return list;
	}

	public void set(String key, Object data, String comment)
	{
		set(key, data);
		setComment(key, comment);
	}

	public void set(String key, Object data)
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
		try 
		{
			List<String> lines = new ArrayList<String>();
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

			if(data instanceof List<?>)
			{
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) data;
				lines.add(line);
				for(Object obj: list)
				{
					lines.add("- " + obj);
				}
			}
			else
			{
				lines.add(line);
			}

			PrintWriter pw = new PrintWriter(this);
			for(String ln: lines)
			{
				pw.println(ln);
			}
			pw.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + this.getPath());
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

			PrintWriter pw = new PrintWriter(this);
			for(String ln: lines)
			{
				pw.println(ln);
			}
			pw.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + this.getPath());
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
			PrintWriter pw = new PrintWriter(this);
			for(String ln: lines)
			{
				pw.println(ln);
			}
			pw.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + file.getPath());
		}
	}

	public void addLine(String line)
	{
		try
		{
			List<String> lines = new ArrayList<String>();
			Scanner in = new Scanner(this);
			while(in.hasNextLine())
			{
				String ln = in.nextLine();
				lines.add(ln);
			}
			in.close();

			lines.add(line);

			PrintWriter pw = new PrintWriter(this);
			for(String ln: lines)
			{
				pw.println(ln);
			}
			pw.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found: " + this.getPath());
		}
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
				if(!ln.startsWith("#") && !ln.endsWith(":") && !ln.startsWith("- "))
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
			System.out.println("File not found: " + this.getPath());
		}
		return line;
	}

	public void addTo(String key, Object object)
	{
		if(!has(key))
		{
			List<Object> list = new ArrayList<Object>();
			set(key, list);
		}
		if(get(key) instanceof List<?>)
		{
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) get(key);
			list.add(object);
			set(key, list);
		}
	}

	public boolean has(String key, Object object)
	{
		if(get(key) instanceof List<?>)
		{
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) get(key);
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
		if(get(key) instanceof List<?>)
		{
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) get(key);
			List<Object> newList = new ArrayList<Object>();
			for(Object obj: list)
			{
				if(!obj.equals(object))
				{
					newList.add(obj);
				}
			}
			set(key, newList);
		}
	}

	public void remove(String key)
	{
		try
		{
			List<String> lines = new ArrayList<String>();
			Scanner in = new Scanner(this);
			if(get(key) instanceof List<?>)
			{
				while(in.hasNextLine())
				{
					String ln = in.nextLine();
					if(!ln.startsWith(key))
					{
						@SuppressWarnings("unchecked")
						List<Object> list = (List<Object>) get(key);
						for(Object obj: list)
						{
							if(!ln.equalsIgnoreCase("- " + obj))
							{
								lines.add(ln);
							}
						}
					}
				}
			}
			else
			{
				while(in.hasNextLine())
				{
					String ln = in.nextLine();
					if(!ln.startsWith(key))
					{
						lines.add(ln);
					}
				}
			}
			in.close();

			PrintWriter pw = new PrintWriter(this);
			for(String ln: lines)
			{
				pw.println(ln);
			}
			pw.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + this.getPath());
		}
	}
}
