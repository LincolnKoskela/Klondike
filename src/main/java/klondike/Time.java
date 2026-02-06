package klondike;
import java.time.Duration;
import java.time.Instant;


/**
 * Simple stopwatch to detect seconds elapsed from hitting play
 * to exiting game or if game is won.
 * 
 * startTime -> when this run started
 * pasuedAccumulated -> all previous runs added together
 * running -> should time current increase
 */
public class Time {
    private Instant startTime;
    private boolean running;
    private Duration pausedAccumulated = Duration.ZERO;

    /**
     * Start the counter
     */
    public void start() {
        startTime = Instant.now();
        running = true;
    }

    public void freeze() {
        if (!running) return;

        pausedAccumulated = pausedAccumulated.plus(
            Duration.between(startTime, Instant.now())
        );
        running = false;
    }

    public void unFreeze() {
        if (running) return;

        startTime = Instant.now();
        running = true;
    }

    public void stop() {
        freeze();
    }

    public void reset() {
        startTime = null;
        pausedAccumulated = Duration.ZERO;
        running = false;
    }

    /**
     * Gets the elapsed time in seconds.
     * If still running, calculates from start until now.
     * @return elapsed time as a long
     * @throws IllegalStateException if the counter was never stated.
     */
    public long getElapsedTime() {
        if (startTime == null && pausedAccumulated.isZero()) {
            throw new IllegalStateException("Counter has not been started.");
        }

        Duration total = pausedAccumulated;

        if (running) {
            total = total.plus(Duration.between(startTime, Instant.now()));
        }

        return total.getSeconds();
    }

    public double getMinutes() {
        return getElapsedTime() / 60.0;
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
