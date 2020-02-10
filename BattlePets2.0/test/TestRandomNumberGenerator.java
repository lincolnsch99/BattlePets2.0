import TeamVierAugen.RandomNumberGenerator;
public class TestRandomNumberGenerator
{
   public static void main(String[] args)
   {
      //NOTE: Random class will generate pseudorandom numbers.
      //It means you will get identical number if you use a same instance.
      //Use non-default constructor(seeds) to get different numbers.
      RandomNumberGenerator r1 = new RandomNumberGenerator();
      System.out.println(r1.getNextRandom());
      RandomNumberGenerator r2 = new RandomNumberGenerator();
      System.out.println(r2.getNextRandom());
      //Use non-default constructor(seeds) to get different numbers.
      RandomNumberGenerator r3 = new RandomNumberGenerator(1);
      System.out.println(r3.getNextRandom());
      System.out.println(r3.getSeed());
      RandomNumberGenerator r4 = new RandomNumberGenerator(99);
      System.out.println(r4.getNextRandom());
      System.out.println(r4.getSeed());
   }
}
