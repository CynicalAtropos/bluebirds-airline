package bluebirds.airline;

public class Reservation {
	
	private Flight flight;
	private Customer customer;
	private String seatNumber;
        private boolean firstClass;
	private int cost;
        
        private int reservationNum;
	
        public static int num = 200;
        
        
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
		
	}
	
	public Reservation()
	{
            reservationNum = num;
            num++;
		
	}
        
        // for reading from a file
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
		return "The reservation " + reservationNum + " for " + customer.getName() + " of seat number " + seatNumber + " on " + flight + " which cost " + cost + ".";
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
        
        public boolean getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(boolean firstClass) {
		this.firstClass = firstClass;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
        
        public int getReservationNum()
        {
            return reservationNum;
        }
        
        public void setReservationNum(int reservationNum)
        {
            this.reservationNum = reservationNum;
        }
	
	
	
}
