
public class Customer extends Person{
	private int customerId;
        
        public static int num = 100;
	
	public Customer(String name, String address, String phoneNumber)
	{
		super(name,address,phoneNumber);
		customerId = num;
                num++;
	}
	
	public Customer()
	{
            customerId = num;
            num++;
		
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
