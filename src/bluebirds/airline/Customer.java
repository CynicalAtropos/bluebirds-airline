package bluebirds.airline;

import java.util.ArrayList;

public class Customer extends Person{
	private int customerId;
        private ArrayList<Reservation> reservationList;
        public static int num = 100;
	
	public Customer(String name, String address, String phoneNumber, ArrayList<Reservation> r)
	{
		super(name,address,phoneNumber);
		customerId = num;
                num++;
                reservationList = r;
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
	
        public void addRes(Reservation r) {
            this.reservationList.add(r);
        }
        
        public void printRes(){
            System.out.println("\nReservations for " + this.name);
            for(int i = 0; i < this.reservationList.size(); i++){
                System.out.println(this.reservationList.get(i).getFlight() + 
                        this.reservationList.get(i).getSeatNumber() +
                        this.reservationList.get(i).getCost());
            }
        }
	
}
