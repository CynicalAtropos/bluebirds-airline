package bluebirds.airline;

public class Pilot extends Person{
	private int pilotId;
	
	public Pilot(String name, String address, String phoneNumber, int p)
	{
		super(name,address,phoneNumber);
		pilotId = p;
	}
	
	public Pilot()
	{
		
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
