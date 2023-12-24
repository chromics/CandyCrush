package test;

public class RunTimeTest {
    private static long startTime;

    public static void startTime(){
        startTime = System.nanoTime();
    }
    
    public static void endTime(){
        long endTime = System.nanoTime(); 
        long duration = endTime - startTime;       
        long durationInMillis = (endTime - startTime) / 1_000_000; // Convert nanoseconds to milliseconds
        System.out.println("\nDuration of Method: " + duration + " nanoseconds");
        System.out.println("Duration of Method: " + durationInMillis + " milliseconds\n");
    }
}
