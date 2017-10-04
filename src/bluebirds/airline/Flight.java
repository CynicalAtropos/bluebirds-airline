package bluebirds.airline;

import java.time.LocalDate;

public class Flight {
	
	private String flightCode;
	private LocalDate day;
	private String time;
	private String route;
	private Pilot pilot;
        private Reservation[][] firstClass;
	private Reservation[][] peasantClass;
        
	public Flight(String f,LocalDate d, String t, String r, Pilot p)
	{
		flightCode = f;
		day = d;
		time = t;
		route = r;
		pilot = p;
                firstClass = new Reservation[2][2];
                peasantClass = new Reservation[2][4];
	}
	
	public Flight(){
		firstClass = new Reservation[2][2];
                peasantClass = new Reservation[2][4];
	}
	
	public String toString()
	{
		return "Flight Number: " + flightCode + " on " + day + " from " + route + " at " + time + " with " + pilot + " as the pilot.";
	}

	public String getFlightCode() 
	{
		return flightCode;
	}

	public void setFlightCode(String flightCode) 
	{
		this.flightCode = flightCode;
	}

	public LocalDate getDate() 
	{
		return day;
	}

	public void setDate(LocalDate date) 
	{
		this.day = date;
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
        
        public Reservation[][] getPeasantClass(){
            return this.peasantClass;
        }
        
        public void setPeasantClass(Reservation[][] res){
            this.peasantClass = res;
        }
	
        public Reservation[][] getFirstClass(){
            return this.firstClass;
        }
        
        public void setFirstClass(Reservation[][] res){
            this.firstClass = res;
        }
	
	
	
	
	
}
