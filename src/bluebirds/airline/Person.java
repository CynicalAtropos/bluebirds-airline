package airplane_simulation;

public abstract class Person {
	protected String name;
	
	protected String address;
	
	protected String phoneNumber;
	
	public Person(String n, String a, String p)
	{
		name = n;
		address = a;
		phoneNumber = p;
	}
	
	public Person()
	{
		
	}
	
	public String toString()
	{
		return name + " who lives at " + address + " and has the phone number: " + phoneNumber;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
