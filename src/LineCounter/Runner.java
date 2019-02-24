package LineCounter;

import java.io.File;

public class Runner
{
	public static void main(String[] args)
	{
		String[] characterNames = new String[]
				{
					"Proctor",
					"Danforth", 
					"Parris", 
					"Hale", 
					"Elizabeth", 
					"Abigail", 
					"Mary Warren", 
					"Giles", 
					"Putnam", 
					"Tituba", 
					"Mrs. Putnam", 
					"Cheever", 
					"Herrick", 
					"Rebecca", 
					"Francis", 
					"Mercy", 
					"Hathorne", 
					"Betty", 
					//"Sarah Good", 
					"Susanna",
					"Nurse",
					"Hopkins"
					
					/*
					 *
					"Proctor",
					"Nurse", 
					"Parris", 
					"Hale", 
					"Abigail", 
					"Elizabeth", 
					"Susanna", 
					"Hathorne", 
					"Herrick", 
					"Putnam", 
					"Danforth", 
					"Mary Warren", 
					"Tituba", 
					"Betty", 
					"Mrs. Putnam", 
					"Cheever", 
					"Rebecca", 
					"Giles", 
					//"Sarah Good", 
					"Hopkins",
					"Mercy",
					"Francis"
					 */
				};
		//[Proctor, Danforth, Parris, Hale, Elizabeth, Abigail, Mary Warren, Giles, Putnam, Tituba, Mrs. Putnam, Cheever, Herrick, Rebecca, Francis, Mercy, Hathorne, Betty, Susanna, Nurse, Hopkins]
		
		double start = System.nanoTime();
		
		TextAccessor a = new TextAccessor("D:" + File.separator + "Data" + File.separator + "Dev" +
				File.separator + "Eclipse2018-2019" + File.separator + "CruciblePersonLineCounter" + File.separator + "src" +
				File.separator + "LineCounter" + File.separator + "CrucibleTextNoWhiteSpace.txt", characterNames);
		
		try
		{
			a.joinThreads();
		}
		catch (InterruptedException e)
		{
			
		}
		
		double end = System.nanoTime();
		
		System.out.println("Duration - " + (end - start) / 1000000000.0);
		
		a.printAll();
		
		System.out.println("\n" + a.getNumExceptions() + " errors.");
		
		//a.printSortedMapBasedOnTimesSpoken();
	}
}
