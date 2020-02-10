/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Commenter: Zexin Liu
 *
 * Purpose: A singleton class to generate a series of random number.
 */
package TeamVierAugen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomNumberGenerator
{
    private static double RANGE_MAX_EXCLUSIVE = 5.0;
    private static double RANGE_MIN_INCLUSIVE = 0.0;

    private static int DEFAULT_SEED = 0;
    private Random randomList;
    private int seed;

    private static RandomNumberGenerator randomNumberGenerator;
	 
    /**
     * Default private costructor.
	  * Use a default seed.
     */
    private RandomNumberGenerator()
    {
        seed = DEFAULT_SEED;
        randomList = new Random(seed);
    }
	 
    /**
     * Get a singleton RandomNumberGenerator instance.
     * @return an instance.
     */
    public static RandomNumberGenerator getInstance()
    {
        if(randomNumberGenerator != null)
            return randomNumberGenerator;
        else
        {
            randomNumberGenerator = new RandomNumberGenerator();
            return randomNumberGenerator;
        }
    }

    /**
     * Get another random number.
     * @return another random number.
     */
    public double getNextRandom()
    {
        return round(randomList.nextDouble() * (RANGE_MAX_EXCLUSIVE - RANGE_MIN_INCLUSIVE) + RANGE_MIN_INCLUSIVE,
                2);
    }

    /**
     * Set a user-defined seed to generate random number.
     * @param seed is user defined seed.
     */
    public void setSeed(int seed)
    {
        this.seed = seed;
        this.randomList = new Random(seed);
    }

    /**
     * Round up a passing double number in given places.
	  * If the given places is negative then throw IllegalArgumentException().  
     * @param value is the double number to be rounded.
	  * @param places is the number of decimal places.
     * @return rounded number.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
