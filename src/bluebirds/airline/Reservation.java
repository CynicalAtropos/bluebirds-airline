package bluebirds.airline;

/**
 * Reservation class has fields flight, customer
 * seatNumber, firstClass, cost, and resNum
 * 
 * @author Matthew Sluder
 * @author Josh Whitt
 * @author Timothy Alligood
 */
public class Reservation {
	
    /**
     * the flight
     */
	private Flight flight;
    /**
     * the customer
     */
	private Customer customer;
    /**
     * the seat number
     */
	private String seatNumber;
    /**
     * true for firstClass false if economy
     */
        private boolean firstClass;
    /**
     * the cost
     */
	private int cost;
    /**
     * the reservation number
     */
        private int reservationNum;
	
    /**
     * used to iterate for the reservation number
     */
    public static int num = 200;
        
    /**
     * Creates new Reservation with all parameters
     * @param f the flight
     * @param c the customer
     * @param s the seatNumber
     * @param fc the seatType 
     */
    public Reservation(Flight f, Customer c, String s, boolean fc)
	{
		flight = f;
		customer = c;
		seatNumber = s;
		if (fc)
                {
                    cost = 850;
                }
                else
                {
                    cost = 450;
                }
                reservationNum = num;
                num++;
                firstClass = fc;
		
	}
	
    /**
     * Empty Constructor
     */
    public Reservation()
	{
            reservationNum = num;
            num++;
		
	}

    /**
     * Reads the reservation from a file
     * @param f the flight 
     * @param c the customer
     * @param s the seatNumber
     * @param fc the seatType
     * @param resNum the Reservation number
     */
        public Reservation(Flight f, Customer c, String s, boolean fc, int resNum)
        {
            flight = f;
            customer = c;
            seatNumber = s;
            if (fc)
            {
                cost = 850;
            }
            else
            {
                cost = 450;
            }
            
            reservationNum = resNum;
            
        }
    /**
     * Returns the reservation info
     * @return the reservation info
     */
	public String toString()
	{
		return "The reservation " + reservationNum + " for " + customer.getName() + " of seat number " + seatNumber + " on " + flight.getFlightCode() + " which cost " + cost + ".";
	}

    /**
     * Returns the flight 
     * @return the flight 
     */
    public Flight getFlight() {
		return flight;
	}

    /**
     * Sets the flight
     * @param flight the flight
     */
    public void setFlight(Flight flight) {
		this.flight = flight;
	}

    /**
     * Returns the customer
     * @return the customer
     */
    public Customer getCustomer() {
		return customer;
	}

    /**
     * Sets the customer
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
		this.customer = customer;
	}
        
    /**
     * Returns the seatType
     * @return true or false for seatType
     */
    public boolean getFirstClass() {
		return firstClass;
	}

    /**
     * Sets the seatType
     * @param firstClass true or false for seatType
     */
    public void setFirstClass(boolean firstClass) {
		this.firstClass = firstClass;
	}

    /**
     * Returns the seatNumber
     * @return the seatNumber
     */
    public String getSeatNumber() {
		return seatNumber;
	}

    /**
     * Sets the seatNumber
     * @param seatNumber the seat number
     */
    public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

    /**
     * Returns the cost
     * @return the cost
     */
    public int getCost() {
		return cost;
	}

    /**
     * Sets the cost
     * @param cost the cost
     */
    public void setCost(int cost) {
		this.cost = cost;
	}
        
    /**
     * Returns the Reservation number
     * @return the reservation number
     */
    public int getReservationNum()
        {
            return reservationNum;
        }
        
    /**
     * Sets the reservation number
     * @param reservationNum the reservation number
     */
    public void setReservationNum(int reservationNum)
        {
            this.reservationNum = reservationNum;
        }
	
	
	
}
