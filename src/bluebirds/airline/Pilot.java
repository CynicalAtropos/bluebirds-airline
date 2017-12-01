package bluebirds.airline;

/**
 * Pilot class extends Person
 * has one field-  pilot id
 * 
 * @author Matthew Sluder
 * @author Josh Whitt
 * @author Timothy Alligood
 */
public class Pilot extends Person{
    /**
     * The pilot's id number
     */
	private int pilotId;
        
    /**
     * the number to iterate pilotId
     */
    public static int num = 300;
	
    /**
     * Pilot has three parameters
     * @param name          the Pilot's name
     * @param address       the Pilot's address
     * @param phoneNumber   the Pilot's phone number
     */
    public Pilot(String name, String address, String phoneNumber)
	{
		super(name,address,phoneNumber);
		pilotId = num;
                num++;
	}
	
    /**
     * Empty constructor
     */
    public Pilot()
	{
            pilotId = num;
            num++;
	}
        /**
         * Prints the pilot info
         * @return pilot info
         */
	public String toString()
	{
		return super.toString() + " is a Pilot with the Pilot ID: " + pilotId;
	}

    /**
     * Returns the pilot id
     * @return pilotId
     */
    public int getPilotId() 
	{
		return pilotId;
	}

    /**
     * Sets the pilotId
     * @param pilotId pilotId
     */
    public void setPilotId(int pilotId) 
	{
		this.pilotId = pilotId;
	}
	
	
	
}
