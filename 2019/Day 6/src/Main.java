import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> lines = new ArrayList<>();
	    try {
	        File myObj = new File("input.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          lines.add(data);
		        }
		        myReader.close();
	        myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	    System.out.println("Part 1");
	    final long startTime1 = System.nanoTime();
	    int answer1 = Part1.run(lines);	
	    final long endTime1 = System.nanoTime();
	    System.out.println("Answer: " + answer1);
		System.out.println("Runtime: " + (endTime1 - startTime1)/1000 + (char)956 + "s");
	    System.out.println("Part 2");
	    final long startTime2 = System.nanoTime();
	    int answer2 =  Part2.run(lines);
	    final long endTime2 = System.nanoTime();
	    System.out.println("Answer: " + answer2);
		System.out.println("Runtime: " + (endTime2 - startTime2)/1000 + (char)956 + "s");

	}

}
