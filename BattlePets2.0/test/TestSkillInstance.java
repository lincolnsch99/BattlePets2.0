import TeamVierAugen.Skills.Skills;
import TeamVierAugen.Skills.SkillInstance;

public class TestSkillInstance
{
   public static void main(String[] args)
   {
      SkillInstance s1 = new SkillInstance(Skills.PAPER_CUT);
      SkillInstance s2 = new SkillInstance(Skills.ROCK_THROW);
      SkillInstance s3 = new SkillInstance(Skills.SCISSORS_POKE);
      s1.setRechargeTime(5);
      s2.setRechargeTime(1);
      s3.setRechargeTime(-2);
      System.out.println(s1.getRechargeTime());
      System.out.println(s2.getRechargeTime());
      System.out.println(s3.getRechargeTime());
      s1.decrementRecharge();
      s2.decrementRecharge();
      s3.decrementRecharge();
      System.out.println(s1.isOnCooldown());
      System.out.println(s2.isOnCooldown());
      System.out.println(s3.isOnCooldown());


   }

}
