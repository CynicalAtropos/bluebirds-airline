package bluebirds.airline;

import java.util.ArrayList;

public class Customer extends Person{
	private int customerId;
        private ArrayList<Reservation> reservationList;
        public static int num = 100;
	
	public Customer(String name, String address, String phoneNumber)
	{
		super(name,address,phoneNumber);
		customerId = num;
                num++;
                reservationList = new ArrayList<Reservation>();
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
        
        public ArrayList<Reservation> getReservationList(){
            return this.reservationList;
        }
        
        public void setReservationList(ArrayList<Reservation> rList){
            this.reservationList = rList;
        }
	
        public void addRes(Reservation r) {
            this.reservationList.add(r);
        }
        
        public void printRes(){
            System.out.println("\nReservations for " + this.name);
            for(int i = 0; i < this.reservationList.size(); i++){
                System.out.println(this.reservationList.get(i).toString());
            }
        }
	
}
