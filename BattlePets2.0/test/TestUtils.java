import TeamVierAugen.Utils;

public class TestUtils
        {
           public static void main(String[] args)
           {
              System.out.println(Utils.convertEnumString("HUMAN"));
              System.out.println(Utils.convertEnumString("human"));
              System.out.println(Utils.convertEnumString("human_being"));
              System.out.println(Utils.convertEnumString("HUMAN_being"));
              System.out.println(Utils.convertEnumString("HUMAN=being"));
           }

        }
