
public class Main {

	public static void main(String[] args) {
		
		int lowerBound = 124075;
		int upperBound = 580769;
	    System.out.println("Part 1");
	    final long startTime1 = System.nanoTime();
	    int answer1 =  Part1.run(lowerBound, upperBound);
	    final long endTime1 = System.nanoTime();
	    System.out.println("Answer: " + answer1);
		System.out.println("Runtime: " + (endTime1 - startTime1)/1000 + (char)956 + "s");
		
	    System.out.println("Part 2");
	    final long startTime2 = System.nanoTime();
	    int answer2 =  Part2.run(lowerBound, upperBound);
	    final long endTime2 = System.nanoTime();
	    System.out.println("Answer: " + answer2);
		System.out.println("Runtime: " + (endTime2 - startTime2)/1000 + (char)956 + "s");
	}

}
