package airplane_simulation;

public class Flight {
	
	private String flightCode;
	private String date;
	private String time;
	private String route;
	private Pilot pilot;
	
	public Flight(String f,String d, String t, String r, Pilot p)
	{
		flightCode = f;
		date = d;
		time = t;
		route = r;
		pilot = p;
	}
	
	public Flight(){
		
	}
	
	public String toString()
	{
		return "Flight Number: " + flightCode + " on " + date + " from " + route + " at " + time + " with " + pilot + " as the pilot.";
	}

	public String getFlightCode() 
	{
		return flightCode;
	}

	public void setFlightCode(String flightCode) 
	{
		this.flightCode = flightCode;
	}

	public String getDate() 
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date = date;
	}

	public String getTime() 
	{
		return time;
	}

	public void setTime(String time) 
	{
		this.time = time;
	}

	public String getRoute()
	{
		return route;
	}

	public void setRoute(String route) 
	{
		this.route = route;
	}

	public Pilot getPilot() 
	{
		return pilot;
	}

	public void setPilot(Pilot pilot) 
	{
		this.pilot = pilot;
	}
	
	
	
	
	
	
}
