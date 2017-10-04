/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluebirds.airline;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tdambp
 */
public class BluebirdsAirlineDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner scan = new Scanner(System.in);
        
        ArrayList<Customer> customerAL = new ArrayList<Customer>();
        
        ArrayList<Flight> flightAL = new ArrayList<Flight>();
        
        ArrayList<Pilot> pilotAL = new ArrayList<Pilot>();
        
        ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
        
        while (true)
        {
        	int choice = menu();
        	if(choice == 0){
        		primeData(pilotAL, flightAL);
        	}
        	else if(choice == 1){
        		
        	}
        	else if(choice == 2){
        		
        	}
        	else if(choice == 3){
        		
        	}
        	else if(choice == 4){
        		
        	}
        	else if(choice == 5){
        		
        	}
        	else if (choice == 6){
        		
        	}
        	else if(choice == 7){
        		
        	}
        	else if(choice == 8){
        		
        	}
        	else if(choice == 9){
        		
        	}
        	else if(choice == 10){
        		
        	}
        	else if(choice == 11){
        		
        	}
        	else if(choice == 12){
                    
                }
        	else if(choice == 13){
        		
        	}
        	else if(choice == 14){
        		
        	}
        	else if(choice == 15){
        		System.out.println("\nGOOD BYE!!!");
        		System.exit(0);
        	}
        }  
    }
    
    public static void searchReservID(ArrayList<Reservation> reservations, Scanner scan)
    {
        System.out.println("What is the reservation number?");
        int resNum = scan.nextInt();
        
        boolean found = false;
        
        for(int i = 0; i < reservations.size(); i++)
        {
            if(resNum == reservations.get(i).getReservationNum())
            {
                found = true;
                System.out.println("We found that reservation:");
                System.out.println(reservations.get(i).toString());
                
            }
        }
        
        if(!found)
        {
            System.out.println("That reservation does not exist");
        }
    }
    
    public static void searchCustID(ArrayList<Customer> customers, Scanner scan)
    {
        System.out.println("What is the customer ID?");
        int custNum = scan.nextInt();
        boolean found = false;
        
        for(int i = 0; i < customers.size(); i++)
        {
            if(custNum == customers.get(i).getCustomerId())
            {
                found = true;
                System.out.println("We found that customer:");
                System.out.println(customers.get(i).toString());
            }
            
        }
        
        if(!found)
        {
            System.out.println("That customer does not exist");
        }
        
    }
       
    public static void primeData(ArrayList<Pilot> pilots, ArrayList<Flight> flights)
    {
        Pilot a = new Pilot("Chesley Sullenberger", "2801 Franklin Rd SW, Roanoke, VA 24014", "5403454434");
        Pilot b = new Pilot("Amelia Earhart", "15240 N 32nd St, Phoenix, AZ 85032", "6024937404");
        Pilot c = new Pilot("Han Solo", "3226 Brandon Ave SW, Roanoke, VA 24018","5403448200");
        Pilot d = new Pilot("Orville Wright","1919 W Deer Valley Rd, Phoenix, AZ 85027", "6237802330" );
        
        pilots.add(a);
        pilots.add(b);
        pilots.add(c);
        pilots.add(d);
        
        
        
        flights.add(new Flight("12RPAM", "November 12, 2017", "8:00 a.m.", "Roanoke to Phoenix", a));
        flights.add(new Flight("12PRAM", "November 12, 2017", "8:00 a.m.", "Phoenix to Roanoke", b));
        flights.add(new Flight("12PRPM", "November 12, 2017", "6:00 p.m.", "Phoenix to Roanoke", a));
        flights.add(new Flight("12RPPM", "November 12, 2017", "6:00 p.m.", "Roanoke to Phoenix", b));
        
        flights.add(new Flight("13RPAM", "November 13, 2017", "8:00 a.m.", "Roanoke to Phoenix", c));
        flights.add(new Flight("13PRAM", "November 13, 2017", "8:00 a.m.", "Phoenix to Roanoke", d));
        flights.add(new Flight("13PRPM", "November 13, 2017", "6:00 p.m.", "Phoenix to Roanoke", c));
        flights.add(new Flight("13RPPM", "November 13, 2017", "6:00 p.m.", "Roanoke to Phoenix", d));
        
        flights.add(new Flight("14RPAM", "November 14, 2017", "8:00 a.m.", "Roanoke to Phoenix", a));
        flights.add(new Flight("14PRAM", "November 14, 2017", "8:00 a.m.", "Phoenix to Roanoke", b));
        flights.add(new Flight("14PRPM", "November 14, 2017", "6:00 p.m.", "Phoenix to Roanoke", a));
        flights.add(new Flight("14RPPM", "November 14, 2017", "6:00 p.m.", "Roanoke to Phoenix", b));
        
        flights.add(new Flight("15RPAM", "November 15, 2017", "8:00 a.m.", "Roanoke to Phoenix", c));
        flights.add(new Flight("15PRAM", "November 15, 2017", "8:00 a.m.", "Phoenix to Roanoke", d));
        flights.add(new Flight("15PRPM", "November 15, 2017", "6:00 p.m.", "Phoenix to Roanoke", c));
        flights.add(new Flight("15RPPM", "November 15, 2017", "6:00 p.m.", "Roanoke to Phoenix", d));
        
        flights.add(new Flight("16RPAM", "November 16, 2017", "8:00 a.m.", "Roanoke to Phoenix", a));
        flights.add(new Flight("16PRAM", "November 16, 2017", "8:00 a.m.", "Phoenix to Roanoke", b));
        flights.add(new Flight("16PRPM", "November 16, 2017", "6:00 p.m.", "Phoenix to Roanoke", a));
        flights.add(new Flight("16RPPM", "November 16, 2017", "6:00 p.m.", "Roanoke to Phoenix", b));
        
        flights.add(new Flight("17RPAM", "November 17, 2017", "8:00 a.m.", "Roanoke to Phoenix", c));
        flights.add(new Flight("17PRAM", "November 17, 2017", "8:00 a.m.", "Phoenix to Roanoke", d));
        flights.add(new Flight("17PRPM", "November 17, 2017", "6:00 p.m.", "Phoenix to Roanoke", c));
        flights.add(new Flight("17RPPM", "November 17, 2017", "6:00 p.m.", "Roanoke to Phoenix", d));
        
        flights.add(new Flight("18RPAM", "November 18, 2017", "8:00 a.m.", "Roanoke to Phoenix", a));
        flights.add(new Flight("18PRAM", "November 18, 2017", "8:00 a.m.", "Phoenix to Roanoke", b));
        flights.add(new Flight("18PRPM", "November 18, 2017", "6:00 p.m.", "Phoenix to Roanoke", a));
        flights.add(new Flight("18RPPM", "November 18, 2017", "6:00 p.m.", "Roanoke to Phoenix", b));
        
        
        
        
        
        
    }
    
    public static int menu()
    {
    	Scanner scan = new Scanner(System.in);
    	System.out.println("\n0.  Prime the data.");
        System.out.println("1.  Book a Reservation.");
        System.out.println("2.  Search for Customer by their ID Number.");
        System.out.println("3.  Cancel a Reservation.");
        System.out.println("4.  Print Gross Income for Each Flight.");
        System.out.println("5.  Print Gross Income for a Specific Flight");
        System.out.println("6.  Print Each Pilots' Weekly Schedule.");
        System.out.println("7.  Find All Reservations Made Under a Specific Customer ID Number.");
        System.out.println("8.  Search for Reservation by Reservation Number.");
        System.out.println("9.  Search Canceled Reservations By Reservation Number or Customer Name.");
        System.out.println("10. Print Seat Layout for Specified Flight.");
        System.out.println("11. --");
        System.out.println("12. --");
        System.out.println("13. --");
        System.out.println("14. --");
        System.out.println("15. Exit."); 
        System.out.print("Choice: ");
        
        try{
        	int choice = scan.nextInt();
        	
        	if(choice < 0 || choice > 14){
            	System.out.println("\nThat is not a valid choice!"); 
            	return menu();
            }
            else{
            	return choice;
            }
        }
        catch(Exception t){
        	System.out.println("\nThat is not a valid choice!"); 
        	return menu();
        }
    }
    
    public static void grossIncomeEach(ArrayList<Flight> flights){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        for(int i = 0; i < flights.size(); i++)
            {
                
                System.out.println("Flight: " + flights.get(i).getFlightCode());
                //For Loop to read seat maps including getters and setters
                System.out.println("Gross Income: ");
            }
            
            
    }
    
    public static void grossIncomeSpec(ArrayList<Flight> flights){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("What is the flight code?");
        String flightCode = scan.nextLine().trim();
        boolean found = false;
        for(int i = 0; i < flights.size(); i++)
            {
                if(flightCode.equals(flights.get(i).getFlightCode())) {
                    found = true;
                    System.out.println("Matched Flight: ");
                    Flight f = flights.get(i);
                    //For Loop to read seat map
                    System.out.println("Gross Income: ");
                }
            }
            
            
    }
    
    public static void searchCanceledRes(ArrayList<Reservation> res){
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to search for canceled reservations by reservation number (1) or customer name (2)?");
        System.out.println("Choice (1|2): ");
        int choice = scan.nextInt();
        if(choice == 1){
            System.out.println("What is the reservation number?");
            int resNum = scan.nextInt();
            boolean found = false;
         
            for(int i = 0; i < res.size(); i++)
            {
                if(resNum == res.get(i).getReservationNum()) {
                    found = true;
                    System.out.println("We found that reservation:");
                    System.out.println(res.get(i).toString());
                }
            }
        } else if(choice == 2){
            System.out.println("What is the customer's name?");
            String name = scan.nextLine();
            boolean found = false;
         
            for(int i = 0; i < res.size(); i++)
            {
                if(res.get(i).getCustomer().getName().contains(name)) {
                    found = true;
                    System.out.println("\nMatching Reservation:\n");
                    System.out.println(res.get(i).toString());
                }
            }
            if(!found) {
                System.out.println("There are no matching reservations for the provided name.");
            }
        }
    }
    
    public static void printFlightSeats(ArrayList<Flight> flights){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the flight code: ");
        String flightCode = scan.nextLine();
    }
}
