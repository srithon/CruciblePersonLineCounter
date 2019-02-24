package LineCounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TextAccessor
{	
	private HashMap<String, Person> people;
	private BufferedReader reader;
	private Stack<String> lines;
	private boolean stop;
	private Thread lineHandlerThread1, lineHandlerThread2;
	private int exceptions;
	
	public TextAccessor(String pathToPDF, String[] characterNames)
	{
		people = new HashMap<String, Person>();
		lines = new Stack<String>();
		stop = false;
		exceptions = 0;
		
		for (String characterName : characterNames)
		{
			people.put(characterName.toLowerCase(), new Person(characterName));
		}
		
		try
		{
			reader = new BufferedReader(new FileReader(pathToPDF));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			
			System.out.println("\nFILE NOT FOUND!!");
			
			return;
		}
		
		Thread lineReader = new Thread(() -> readLines());
		
		lineReader.start();
		
		try
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e)
		{
			
		}
		
		handleLines();
	}
	
	private void handlePerson(String characterName)
	{
		System.out.println(characterName);
		
		try
		{
			people.get(characterName.toLowerCase()).increment();
		}
		catch (NullPointerException e)
		{
			exceptions++;
			
			System.out.println("NULL IN CHECKPERSON : " + characterName);
		}
	}
	
	private void handleString(String line)
	{
		int indexOf = line.indexOf(":");
		
		if (indexOf != -1 && Character.isUpperCase(line.charAt(0)))
		{ //179 errors -> 119 -> 101 errors because Francis is a character
			System.out.println(line);
			
			line = line.substring(0, indexOf);
			
			indexOf = line.indexOf(",");
			
			if (indexOf != -1)
			{
				line = line.substring(0, indexOf).trim();
			}
			else
			{
				indexOf = line.indexOf("-");
				
				if (indexOf != -1)
				{
					line = line.substring(0, indexOf).trim();
				}
			}
			
			handlePerson(line);
		}
	}
	
	private boolean handleLinesAfterReadingFinished()
	{
		String currentLine;
		
		try
		{
			currentLine = lines.pop();
		}
		catch (EmptyStackException e)
		{
			System.out.println("EMPTY STACK");
			return false;
		}
		
		handleString(currentLine);
		
		return true;
	}
	
	private void handleLines()
	{
		lineHandlerThread1 = new Thread(new lineHandler());
		//lineHandlerThread2 = new Thread(new lineHandler());
		
		lineHandlerThread1.start();
		//lineHandlerThread2.start();
		new lineHandler().run();
	}
	
	private void readLines()
	{
		String currentLine = "";
		
		try
		{
			currentLine = reader.readLine();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		while (currentLine != null)
		{
			try
			{
				lines.push(currentLine);
				
				currentLine = reader.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("STOPPING");
		
		stop = true;
	}
	
	public void test()
	{
		for (int i = 0; i < 10; i++)
		{
			try
			{
				System.out.println(reader.readLine());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void printAll()
	{
		for (Person x : people.values())
		{
			System.out.println(x.getData());
		}
	}
	
	public void joinThreads() throws InterruptedException
	{
		lineHandlerThread1.join();
		//lineHandlerThread2.join();
	}
	
	public int getNumExceptions()
	{
		return exceptions;
	}
	
	public void printSortedMapBasedOnTimesSpoken()
	{
		List<Person> sorted = new ArrayList<Person>();
		
		for (Person p : people.values())
		{
			sorted.add(p);
		}
		
		Collections.sort(sorted, new Comparator<Person>()
				{
					@Override
					public int compare(Person a, Person b)
					{
						if (a.getTimesSpoken() > b.getTimesSpoken())
						{
							return 1;
						}
						else if (a.getTimesSpoken() < b.getTimesSpoken())
						{
							return -1;
						}
						
						return 0;
					}
				});
		
		Collections.reverse(sorted);
		
		System.out.println(sorted);
	}
	
	private class lineHandler implements Runnable
	{
		public void run()
		{
			String line;
			
			while (!stop)
			{
				try
				{
					line = lines.pop();
					
					handleString(line);
				}
				catch (Exception e)
				{
					
				}
			}
			
			while(handleLinesAfterReadingFinished());
			
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
