package bluebirds.airline;

public class Customer extends Person{
	private int customerId;
	
	public Customer(String name, String address, String phoneNumber, int c)
	{
		super(name,address,phoneNumber);
		customerId = c;
	}
	
	public Customer()
	{
		
		
	}
	
	public String toString()
	{
		return super.toString() + " is a Customer with the Customer ID: " + customerId;
	}

	public int getCustomerId() 
	{
		return customerId;
	}

	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}
	
	
}
