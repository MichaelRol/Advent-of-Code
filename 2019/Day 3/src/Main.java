import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		List<String> wire1 = new ArrayList<String>();
		List<String> wire2 = new ArrayList<String>();
	    try {
	        File myObj = new File("input.txt");
	        Scanner myReader = new Scanner(myObj);
	        String data = myReader.nextLine();
	        wire1 = Arrays.asList(data.split(","));
	        data = myReader.nextLine();
	        wire2 = Arrays.asList(data.split(","));
	        myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	    System.out.println("Part 1");
	    final long startTime1 = System.nanoTime();
	    int answer1 =  Part1.run(wire1, wire2);
	    final long endTime1 = System.nanoTime();
	    System.out.println("Answer: " + answer1);
		System.out.println("Runtime: " + (endTime1 - startTime1)/1000 + (char)956 + "s");
	    System.out.println("Part 2");
	    final long startTime2 = System.nanoTime();
	    int answer2 =  Part2.run(wire1, wire2);
	    final long endTime2 = System.nanoTime();
	    System.out.println("Answer: " + answer2);
		System.out.println("Runtime: " + (endTime2 - startTime2)/1000 + (char)956 + "s");

	}
}
