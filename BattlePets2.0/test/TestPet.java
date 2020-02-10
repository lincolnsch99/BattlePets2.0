import TeamVierAugen.Playables.Pet;
import TeamVierAugen.Playables.PetTypes;
public class TestPet
{
   public static void main(String[] args)
   {
      Pet p1=new Pet();
      System.out.println(p1.getName());
      System.out.println(p1.getType());

      Pet p2=new Pet("Jason",PetTypes.INTELLIGENCE);
      System.out.println(p2.getName());
      System.out.println(p2.getType());
      p2.setName("Jodan");
      p2.setType(PetTypes.POWER);
      System.out.println(p2.getName());
      System.out.println(p2.getType());


   }

}
