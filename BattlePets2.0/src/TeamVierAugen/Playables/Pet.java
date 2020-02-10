/**
 * Authors: Lincoln Schroeder
 *
 * Commenter: Jared Hollenberger
 *
 * Purpose: The pet class stores the name and type of a pet.
 */
package TeamVierAugen.Playables;

public class Pet
{
    private String name;
    private PetTypes type;

    /**
     * Constructs the Pet class
     * @param name
     * @param type
     */
    public Pet(String name, PetTypes type)
    {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the pet's name.
     * @return string name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the pet's type.
     * @return pettype type
     */
    public PetTypes getType()
    {
        return type;
    }
}
