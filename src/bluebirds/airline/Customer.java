package bluebirds.airline;

import java.util.ArrayList;

/**
 * Customer class extends Person
 * has two fields customerId and reservation list
 * 
 * @author Matthew Sluder
 * @author Josh Whitt
 * @author Timothy Alligood
 */
public class Customer extends Person{
    
        /**
         * The auto generated unique customerId
         */
	private int customerId;
        
        /**
        * The customer's list of reservations
        */
        private ArrayList<Reservation> reservationList;

    /**
     * number to iterate customerId
     */
    public static int num = 100;
	
    /**
     * Customer takes in three parameters
     * and creates new arrayList of Reservations
     * @param name              the customer name
     * @param address           the customer address
     * @param phoneNumber       the customer phone number
     * 
     */
    public Customer(String name, String address, String phoneNumber)
	{
		super(name,address,phoneNumber);
		customerId = num;
                num++;
                reservationList = new ArrayList<Reservation>();
	}
	
    /**
     * Empty constructor
     */
    public Customer()
	{
            customerId = num;
            num++;
            
		
	}
	/**
         * Prints the Customer info
         * @return customer info
         */
	public String toString()
	{
		return super.toString() + " is a Customer with the Customer ID: " + customerId;
	}

    /**
     * Returns the customerId
     * @return  the customerId
     */
    public int getCustomerId() 
	{
		return customerId;
	}

    /**
     * Sets the customerId
     * @param customerId the customerId
     */
    public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}
        
    /**
     * Returns the customer's list of reservations
     * @return list of reservations
     */
    public ArrayList<Reservation> getReservationList(){
            return this.reservationList;
        }
        
    /**
     * Sets the customer's list of reservations
     * @param rList the list of reservations
     */
    public void setReservationList(ArrayList<Reservation> rList){
            this.reservationList = rList;
        }
	
    /**
     * Adds a reservation to the customer list
     * @param r  the new reservation to add
     */
    public void addRes(Reservation r) {
            this.reservationList.add(r);
        }
        
    /**
     * Prints all of the reservations for the customer
     */
    public void printRes(){
            System.out.println("\nReservations for " + this.name);
            for(int i = 0; i < this.reservationList.size(); i++){
                System.out.println(this.reservationList.get(i).toString());
            }
        }
	
}
