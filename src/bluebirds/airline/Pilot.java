package bluebirds.airline;

public class Pilot extends Person{
	private int pilotId;
        
        public static int num = 300;
	
	public Pilot(String name, String address, String phoneNumber)
	{
		super(name,address,phoneNumber);
		pilotId = num;
                num++;
	}
	
	public Pilot()
	{
            pilotId = num;
            num++;
	}
	
	public String toString()
	{
		return super.toString() + " is a Pilot with the Pilot ID: " + pilotId;
	}

	public int getPilotId() 
	{
		return pilotId;
	}

	public void setPilotId(int pilotId) 
	{
		this.pilotId = pilotId;
	}
	
	
	
}
