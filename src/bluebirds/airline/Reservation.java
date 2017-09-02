package bluebirds.airline;

public class Reservation {
	
	private Flight flight;
	private Customer customer;
	private String seatNumber;
	private int cost;
	
	public Reservation(Flight f, Customer c, String s, int co)
	{
		flight = f;
		customer = c;
		seatNumber = s;
		cost = co;
		
	}
	
	public Reservation()
	{
		
	}
	
	public String toString()
	{
		return "The reservation for " + customer + " of seat number" + seatNumber + " on " + flight + " which cost " + cost;
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
	
	
	
}
