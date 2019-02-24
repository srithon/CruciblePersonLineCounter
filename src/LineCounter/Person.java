package LineCounter;

public class Person
{
	private int timesSpoken;
	private String name;
	
	public Person(String name)
	{
		this.name = name;
		timesSpoken = 0;
	}
	
	public synchronized void increment()
	{
		timesSpoken++;
	}
	
	public int getTimesSpoken()
	{
		return timesSpoken;
	}
	
	public String getData()
	{
		return name + " spoke " + timesSpoken + " times.";
	}
	
	public String toString()
	{
		return name;
	}
}