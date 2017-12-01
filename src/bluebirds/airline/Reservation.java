package bluebirds.airline;

/**
 *
 * @author Matt
 */
public class Reservation {
	
	private Flight flight;
	private Customer customer;
	private String seatNumber;
        private boolean firstClass;
	private int cost;
        
        private int reservationNum;
	
    /**
     *
     */
    public static int num = 200;
        
    /**
     *
     * @param f
     * @param c
     * @param s
     * @param fc
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
     *
     */
    public Reservation()
	{
            reservationNum = num;
            num++;
		
	}
        
        // for reading from a file

    /**
     *
     * @param f
     * @param c
     * @param s
     * @param fc
     * @param resNum
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
	
	public String toString()
	{
		return "The reservation " + reservationNum + " for " + customer.getName() + " of seat number " + seatNumber + " on " + flight.getFlightCode() + " which cost " + cost + ".";
	}

    /**
     *
     * @return
     */
    public Flight getFlight() {
		return flight;
	}

    /**
     *
     * @param flight
     */
    public void setFlight(Flight flight) {
		this.flight = flight;
	}

    /**
     *
     * @return
     */
    public Customer getCustomer() {
		return customer;
	}

    /**
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
		this.customer = customer;
	}
        
    /**
     *
     * @return
     */
    public boolean getFirstClass() {
		return firstClass;
	}

    /**
     *
     * @param firstClass
     */
    public void setFirstClass(boolean firstClass) {
		this.firstClass = firstClass;
	}

    /**
     *
     * @return
     */
    public String getSeatNumber() {
		return seatNumber;
	}

    /**
     *
     * @param seatNumber
     */
    public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

    /**
     *
     * @return
     */
    public int getCost() {
		return cost;
	}

    /**
     *
     * @param cost
     */
    public void setCost(int cost) {
		this.cost = cost;
	}
        
    /**
     *
     * @return
     */
    public int getReservationNum()
        {
            return reservationNum;
        }
        
    /**
     *
     * @param reservationNum
     */
    public void setReservationNum(int reservationNum)
        {
            this.reservationNum = reservationNum;
        }
	
	
	
}
