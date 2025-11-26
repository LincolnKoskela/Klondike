package klondike;
import java.time.Duration;
import java.time.Instant;


/**
 * Simple stopwatch to detect seconds elapsed from hitting play
 * to exiting game or if game is won
 */
public class Time {
    private Instant startTime;
    private Instant endTime;
    private boolean running;

    /**
     * Start the counter
     */
    public void start() {
        startTime = Instant.now();
        running = true;
        endTime = null;
    }

    /**
     * Stop the counter
     * @throws IllegalStateException if the counter was NOT started
     */
    public void stop() {
        if (!running) {
            throw new IllegalStateException("Counter has not been started.");
        }
        endTime = Instant.now();
        running = false;
    }

    /**
     * Gets the elapsed time in seconds.
     * If still running, calculates from start until now.
     * @return elapsed time as a long
     * @throws IllegalStateException if the counter was never stated.
     */
    public long getElapsedTime() {
        if (startTime == null) {
            throw new IllegalStateException("Counter has not been started.");
        }
        // if running, end = now | else end = endtime
        Instant end = running ? Instant.now() : endTime;
        return Duration.between(startTime, end).getSeconds();
    }

    public double getMinutes() {
        long seconds = getElapsedTime();
        return seconds / 60.0;
    }

    public static void main(String[] args) {
        try {
            Time counter = new Time();
            System.out.println("Starting counter...");
            counter.start();

            // simulate some stuff using thread object
            Thread.sleep(5000);

            counter.stop();
            System.out.println("Elapsed seconds: " + counter.getElapsedTime());
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
