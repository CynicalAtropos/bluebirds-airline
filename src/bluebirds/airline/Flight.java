package bluebirds.airline;

import java.time.LocalDate;

/**
 * Flight Class has 7 fields
 * flightCode, day , time, route, pilot, firstClass doubleArray, economyClass doubleArray
 * 
 * @author Matthew Sluder
 * @author Josh Whitt
 * @author Timothy Alligood
 */
public class Flight {
    /**
     * the flightCode
     */
	private String flightCode;
     /**
     * the day of the flight
     */
	private LocalDate day;
     /**
     * the time of the flight
     */
	private String time;
     /**
     * the flight's route
     */
	private String route;
    /**
     * the flight's pilot
     */
	private Pilot pilot;
     /**
     * the map of first class seats
     */
        private Reservation[][] firstClass;
     /**
     * the map of the economy seats
     */
	private Reservation[][] economyClass;
        
    /**
     * Creates customer with all parameters
     * @param f the flight code
     * @param d the day
     * @param t the time
     * @param r the route
     * @param p the pilot
     */
    public Flight(String f,LocalDate d, String t, String r, Pilot p)
	{
		flightCode = f;
		day = d;
		time = t;
		route = r;
		pilot = p;
                firstClass = new Reservation[2][2];
                economyClass = new Reservation[2][4];
	}
	
    /**
     * Empty constructor
     */
    public Flight(){
		firstClass = new Reservation[2][2];
                economyClass = new Reservation[2][4];
	}
    	/**
         * Prints the flight info
         * @return flight info
         */
	
	public String toString()
	{
		return "Flight Number: " + flightCode + " on " + day + " from " + route + " at " + time + " with " + pilot.getName() + " as the pilot.";
	}

    /**
     * Returns the flight code
     * @return the flightCode
     */
    public String getFlightCode() 
	{
		return flightCode;
	}

    /**
     * Sets the flight code
     * @param flightCode the flightCode
     */
    public void setFlightCode(String flightCode) 
	{
		this.flightCode = flightCode;
	}

    /**
     * Returns the day of the flight
     * @return the day
     */
    public LocalDate getDate() 
	{
		return day;
	}

    /**
     * Sets the day of the flight
     * @param date the day 
     */
    public void setDate(LocalDate date) 
	{
		this.day = date;
	}

    /**
     * Returns the time of the flight
     * @return the time
     */
    public String getTime() 
	{
		return time;
	}

    /**
     * Sets the time of the flight
     * @param time the flight
     */
    public void setTime(String time) 
	{
		this.time = time;
	}

    /**
     * Returns the flight's route
     * @return the route
     */
    public String getRoute()
	{
		return route;
	}

    /**
     * Sets the flight's route
     * @param route the route
     */
    public void setRoute(String route) 
	{
		this.route = route;
	}

    /**
     * Returns the flight's pilot
     * @return the pilot
     */
    public Pilot getPilot() 
	{
		return pilot;
	}

    /**
     * Sets the flight's pilot
     * @param pilot the pilot
     */
    public void setPilot(Pilot pilot) 
	{
		this.pilot = pilot;
	}
        
    /**
     * Returns the economy class seat map
     * @return the economy class array
     */
    public Reservation[][] getEconomyClass(){
            return this.economyClass;
        }
        
    /**
     * Sets the economy class seat map
     * @param res the economy class array
     */
    public void setEconomyClass(Reservation[][] res){
            this.economyClass = res;
        }
	
    /**
     * Returns the first class seat map
     * @return  the first class array
     */
    public Reservation[][] getFirstClass(){
            return this.firstClass;
        }
        
    /**
     * Sets the first class seat map
     * @param res the first class array
     */
    public void setFirstClass(Reservation[][] res){
            this.firstClass = res;
        }
	
	
	
	
	
}
