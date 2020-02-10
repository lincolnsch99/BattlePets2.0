import TeamVierAugen.Damage;
public class TestDamage
{
   public static void main(String[] args)
   {
      // NOTE: The random damage should be positive number or zero.
      Damage d1 = new Damage(0.002,5.111);
      System.out.println(d1.getRandomDamage());
      System.out.println(d1.getConditionalDamage());
      d1.setRandomDamage(0);
      d1.setConditionalDamage(200);
      System.out.println(d1.getRandomDamage());
      System.out.println(d1.getConditionalDamage());
   }

}
