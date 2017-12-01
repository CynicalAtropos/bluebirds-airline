package bluebirds.airline;

/**
 * Person class has three fields
 * name, address, phone
 * 
 * @author Matthew Sluder
 * @author Josh Whitt
 * @author Timothy Alligood
 */
public abstract class Person {

    /**
     * The person's name
     */
    protected String name;
   /**
     * The person's address
     */
    protected String address;
    /**
     * The person's phone number
     */
    protected String phoneNumber;
	
    /**
     * Person takes in three parameters
     * @param n the person's name
     * @param a the person's address
     * @param p the person's phoneNumber
     */
    public Person(String n, String a, String p)
	{
		name = n;
		address = a;
		phoneNumber = p;
	}
	
    /**
     * Empty Constructor
     */
    public Person()
	{
		
	}
        /**
         * Prints the person's info
         * @return person info
         */
	public String toString()
	{
		return name + " who lives at " + address + " and has the phone number: " + phoneNumber;
	}

    /**
     * Returns the Person's Name
     * @return Person name
     */
    public String getName() 
	{
		return name;
	}

    /**
     * Sets the Person's name
     * @param name  the Person's name
     */
    public void setName(String name) 
	{
		this.name = name;
	}

    /**
     * Returns the Person's Address
     * @return the person's address
     */
    public String getAddress() 
	{
		return address;
	}

    /**
     * Sets the address
     * @param address the person's address
     */
    public void setAddress(String address) 
	{
		this.address = address;
	}

    /**
     * Returns the phone number
     * @return the person's phoneNumber
     */
    public String getPhoneNumber() 
	{
		return phoneNumber;
	}

    /**
     * Sets the phoneNumber
     * @param phoneNumber  the person's phone number
     */
    public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
