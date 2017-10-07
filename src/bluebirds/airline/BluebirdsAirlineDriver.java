/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluebirds.airline;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tdambp
 */
public class BluebirdsAirlineDriver {

    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
        ArrayList<Customer> customerAL = new ArrayList<Customer>();

        ArrayList<Flight> flightAL = new ArrayList<Flight>();

        ArrayList<Pilot> pilotAL = new ArrayList<Pilot>();

        ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
        
        ArrayList<Reservation> canceledResAL = new ArrayList<Reservation>();
        
        while (true)
        {
        	int choice = menu();
        	if(choice == 0){
                        pilotAL = primePilots(pilotAL);
                        flightAL = primeFlights(pilotAL, flightAL);
                        customerAL = primeCustomers(customerAL);
                        reservationAL = primeReservations(reservationAL, flightAL, customerAL);
        	}
        	else if(choice == 1){
        		selectFlight(flightAL,customerAL);
        	}
        	else if(choice == 2){
        		searchCustID(customerAL);
        	}
        	else if(choice == 3){
        		cancelRes(reservationAL, canceledResAL);
        	}
        	else if(choice == 4){
        		grossIncomeEach(flightAL);
        	}
        	else if(choice == 5){
        		grossIncomeSpec(flightAL);
        	}
        	else if (choice == 6){
        		printSchedule(flightAL);
        	}
        	else if(choice == 7){
        		printRes(customerAL);
        	}
        	else if(choice == 8){
        		searchReservID(reservationAL);
        	}
        	else if(choice == 9){
        		searchCanceledRes(reservationAL);
        	}
        	else if(choice == 10){
        		printFlightSeats(flightAL);
        	}
        	else if(choice == 11){
        		System.out.println("\nGOOD BYE!!!");
        		System.exit(0);
        	}
        }  
    }
    
    public static void searchReservID(ArrayList<Reservation> reservations)
    {
        Scanner scan = new Scanner(System.in);
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
    
    public static void searchCustID(ArrayList<Customer> customers)
    {
        Scanner scan = new Scanner(System.in);
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

    public static ArrayList<Pilot> primePilots(ArrayList<Pilot> pilots)
    {
        pilots.add(new Pilot("Chesley Sullenberger", "2801 Franklin Rd SW, Roanoke, VA 24014", "5403454434"));
        pilots.add(new Pilot("Amelia Earhart", "15240 N 32nd St, Phoenix, AZ 85032", "6024937404"));
        pilots.add(new Pilot("Han Solo", "3226 Brandon Ave SW, Roanoke, VA 24018", "5403448200"));
        pilots.add(new Pilot("Orville Wright", "1919 W Deer Valley Rd, Phoenix, AZ 85027", "6237802330"));
 
        return pilots;
    }
    public static ArrayList<Flight> primeFlights(ArrayList<Pilot> pilots, ArrayList<Flight> flights) {

        flights.add(new Flight("12RPAM", LocalDate.of(2017, Month.NOVEMBER, 12), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(0)));
        flights.add(new Flight("12PRAM", LocalDate.of(2017, Month.NOVEMBER, 12), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(1)));
        flights.add(new Flight("12PRPM", LocalDate.of(2017, Month.NOVEMBER, 12), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(0)));
        flights.add(new Flight("12RPPM", LocalDate.of(2017, Month.NOVEMBER, 12), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(1)));

        flights.add(new Flight("13RPAM", LocalDate.of(2017, Month.NOVEMBER, 13), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(2)));
        flights.add(new Flight("13PRAM", LocalDate.of(2017, Month.NOVEMBER, 13), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(3)));
        flights.add(new Flight("13PRPM", LocalDate.of(2017, Month.NOVEMBER, 13), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(2)));
        flights.add(new Flight("13RPPM", LocalDate.of(2017, Month.NOVEMBER, 13), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(3)));

        flights.add(new Flight("14RPAM", LocalDate.of(2017, Month.NOVEMBER, 14), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(0)));
        flights.add(new Flight("14PRAM", LocalDate.of(2017, Month.NOVEMBER, 14), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(1)));
        flights.add(new Flight("14PRPM", LocalDate.of(2017, Month.NOVEMBER, 14), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(0)));
        flights.add(new Flight("14RPPM", LocalDate.of(2017, Month.NOVEMBER, 14), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(1)));

        flights.add(new Flight("15RPAM", LocalDate.of(2017, Month.NOVEMBER, 15), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(2)));
        flights.add(new Flight("15PRAM", LocalDate.of(2017, Month.NOVEMBER, 15), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(3)));
        flights.add(new Flight("15PRPM", LocalDate.of(2017, Month.NOVEMBER, 15), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(2)));
        flights.add(new Flight("15RPPM", LocalDate.of(2017, Month.NOVEMBER, 15), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(3)));

        flights.add(new Flight("16RPAM", LocalDate.of(2017, Month.NOVEMBER, 16), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(0)));
        flights.add(new Flight("16PRAM", LocalDate.of(2017, Month.NOVEMBER, 16), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(1)));
        flights.add(new Flight("16PRPM", LocalDate.of(2017, Month.NOVEMBER, 16), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(0)));
        flights.add(new Flight("16RPPM", LocalDate.of(2017, Month.NOVEMBER, 16), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(1)));

        flights.add(new Flight("17RPAM", LocalDate.of(2017, Month.NOVEMBER, 17), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(2)));
        flights.add(new Flight("17PRAM", LocalDate.of(2017, Month.NOVEMBER, 17), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(3)));
        flights.add(new Flight("17PRPM", LocalDate.of(2017, Month.NOVEMBER, 17), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(2)));
        flights.add(new Flight("17RPPM", LocalDate.of(2017, Month.NOVEMBER, 17), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(3)));

        flights.add(new Flight("18RPAM", LocalDate.of(2017, Month.NOVEMBER, 18), "8:00 a.m.", "Roanoke to Phoenix", pilots.get(0)));
        flights.add(new Flight("18PRAM", LocalDate.of(2017, Month.NOVEMBER, 18), "8:00 a.m.", "Phoenix to Roanoke", pilots.get(1)));
        flights.add(new Flight("18PRPM", LocalDate.of(2017, Month.NOVEMBER, 18), "6:00 p.m.", "Phoenix to Roanoke", pilots.get(0)));
        flights.add(new Flight("18RPPM", LocalDate.of(2017, Month.NOVEMBER, 18), "6:00 p.m.", "Roanoke to Phoenix", pilots.get(1)));
        
        return flights;

    }
    
    public static ArrayList<Customer> primeCustomers(ArrayList<Customer> customers)
    {
        customers.add(new Customer("Rick Sanchez","2072 Apperson Dr, Salem, VA 24153", "5407746295"));
        customers.add(new Customer("Daryl Dixon", "21611 N 26th Ave, Phoenix, AZ 85027", "6235826020"));
        customers.add(new Customer("Merle Dixon", "3202 E Greenway Rd, Phoenix, AZ 85032", "6024851000"));
        return customers;
    }
    
    public static ArrayList<Reservation> primeReservations(ArrayList<Reservation> reservations, ArrayList<Flight> flights, ArrayList<Customer> customers)
    {
        reservations.add(new Reservation(flights.get(0), customers.get(0), "FC1", true));
        reservations.add(new Reservation(flights.get(0), customers.get(0), "FC2", true));
        reservations.add(new Reservation(flights.get(0), customers.get(1), "EC1", false));
        reservations.add(new Reservation(flights.get(0), customers.get(2), "EC2", false));
        
        flights.get(0).getFirstClass()[0][0] = reservations.get(0);
        flights.get(0).getFirstClass()[0][1] = reservations.get(1);
        flights.get(0).getPeasantClass()[0][0] = reservations.get(2);
        flights.get(0).getPeasantClass()[0][1] = reservations.get(3);
        
        
        return reservations;
    }
    
    public static Customer createNewCustomer(ArrayList<Customer> customers)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is your name? ex: First Last");
        String name = scan.nextLine();
        System.out.println("What is your address? ex: StreetNumber Street Name, City, ST ZipCode");
        String address = scan.nextLine();
        System.out.println("What is your phone number? ex: 5409770923");
        String phone = scan.nextLine();
        
        Customer customer = new Customer(name, address, phone);
        
        customers.add(customer);
        System.out.println("Your ID is " + customer.getCustomerId());
        return customer;
    }
    
    // Finds the customer for the reservation
    public static Customer findCustomer(ArrayList<Customer> c){
        Scanner scan = new Scanner(System.in);

            System.out.println("Enter your ID number:");
            int searchID = scan.nextInt();
            for (int i=0;i<c.size();i++){
                if (c.get(i).getCustomerId()==searchID)
                    return c.get(i);
            }
                System.out.println("Customer not found. Create new customer please.");
                return createNewCustomer(c);    
    }

    // Gets paramaters for a flight from the user and passes them to a method
    public static void selectFlight(ArrayList<Flight> f, ArrayList<Customer> cList) {
        Scanner scan = new Scanner(System.in);
        int group = 0;
        Customer c = new Customer();
        // Determines if the customer is new or returning
        int custAnswer = 0;
        boolean valid = true;
        while (valid) {
            System.out.println("Are you a returning customer?: "
                    + "\n[1] Yes"
                    + "\n[2] No");
            custAnswer = scan.nextInt();
            if (custAnswer == 1) {
                c = findCustomer(cList);
                valid = false;
            }
        else if (custAnswer == 2) {
                c = createNewCustomer(cList);
                valid = false;
        }
        else
            System.out.println("Please enter 1 or 2.");
        }
        

        // Determines the route
        int routeAnswer = 0;
        valid = true;
        while (valid) {
            System.out.println("Please select route: "
                    + "\n[1] Roanoke to Pheonix"
                    + "\n[2] Pheonix to Roanoke");
            routeAnswer = scan.nextInt();

            if (routeAnswer == 1) {
                System.out.println("You have selected Roanoke to Pheonix.");
                valid = false;

            } else if (routeAnswer == 2) {
                System.out.println("You have selected Pheonix to Roanoke.");
                valid = false;

            } else {
                System.out.println("Please enter 1 or 2.");
            }
        }

        // Determines time of flight
        int timeAnswer = 0;
        valid = true;
        while (valid) {
            System.out.println("Please select time: "
                    + "\n[1] Morning"
                    + "\n[2] Evening");
            timeAnswer = scan.nextInt();

            if (timeAnswer == 1) {
                System.out.println("You have selected Morning.");
                valid = false;

            } else if (timeAnswer == 2) {
                System.out.println("You have selected Evening.");
                valid = false;

            } else {
                System.out.println("Please enter 1 or 2.");
            }
        }

        // Determines party size for the reservation
        int party = 0;
        valid = true;
        while (valid) {
            System.out.println("How many in your party?: ");
            party = scan.nextInt();

            if (party > 12) {
                System.out.println("That's too many for one flight.");
            } else if (party > 1) {
                boolean sValid = true;
                while (sValid) {
                    // Let's the customer request that the party sit together
                    System.out.println("Is it mandatory your party sit together? (answering yes will effect flight availability): ");
                    System.out.println("[1] yes"
                            + "\n[2] no");
                    group = scan.nextInt();
                    if (group == 1 || group == 2) {
                        sValid = false;
                        valid = false;
                    } else {
                        System.out.println("Please enter 1 or 2. ");
                    }
                }
            } else {
                valid = false;
            }
        }

        int day = 0;
        valid = true;
        while (valid) {
            System.out.println("Please select your date of flight for the week of November 12-18 (enter 12-18)");
            day = scan.nextInt();

            if (day < 12 || day > 18) {
                System.out.println("Please enter 12-18");
            } else {
                valid = false;
            }
        }

        searchFlight(f, routeAnswer, timeAnswer, party, LocalDate.of(2017, Month.NOVEMBER, day), cList, c, group);
    }

    // Searches for a flight based on the customers parameters
    public static void searchFlight(ArrayList<Flight> f, int route, int time, int party, LocalDate day, ArrayList<Customer> cList, Customer c, int group) {
        Scanner scan = new Scanner(System.in);
        // Converts customer answers to strings so the flight list can be searched
        String flightRoute = "";
        if (route == 1) {
            flightRoute = "Roanoke to Phoenix";
        } else if (route == 2) {
            flightRoute = "Phoenix to Roanoke";
        }

        String flightTime = "";
        if (time == 1) {
            flightTime = "8:00 a.m.";
        } else if (time == 2) {
            flightTime = "6:00 p.m.";
        }
        // searches for a flight matching the customers parameters
        int fClass = 0;
        int economy = 0;
        Flight foundFlight = new Flight();
        for (int i = 0; i < f.size(); i++) {
            Flight searchFlight = f.get(i);

            // checks if there are enough first class seats
            if (searchFlight.getRoute().equals(flightRoute)
                    && searchFlight.getTime().equals(flightTime)
                    && searchFlight.getDate().equals(day)) {

                foundFlight = searchFlight;
            }
        }

        for (int fCol = 0; fCol < foundFlight.getFirstClass().length; fCol++) {
            for (int fRow = 0; fRow < foundFlight.getFirstClass()[fCol].length; fRow++) {
                if (foundFlight.getFirstClass()[fCol][fRow] == null) {
                    fClass++;
                }
            }
        }

        // checks if there enough economy seats
        for (int eCol = 0; eCol < foundFlight.getPeasantClass().length; eCol++) {
            for (int eRow = 0; eRow < foundFlight.getPeasantClass()[eCol].length; eRow++) {
                if (foundFlight.getPeasantClass()[eCol][eRow] == null) {
                    economy++;
                }
            }
        }

        if (party < fClass + economy) {
            int bookClass = 0;
            if (party <= fClass) {
                boolean valid = true;
                while (valid) {
                    System.out.println("There are first class seats available. Would you like first class? "
                            + "\n[1] yes"
                            + "\n[2] no");
                    bookClass = scan.nextInt();
                    if (bookClass > 0 && bookClass < 3) {
                        valid = false;
                    } else {
                        System.out.println("Please enter 1 or 2. ");
                    }
                }

            }

            if (group == 1) {
                bookTogether(f, cList, foundFlight, bookClass, c, party);
            } else {
                bookReservation(f, foundFlight, bookClass, c, party);
            }
        } else {
            System.out.println("The flight for this day is full. Would you like to book a different flight? "
                    + "\n[1] Yes"
                    + "\n[2] No");
            int again = scan.nextInt();

            if (again == 1) {
                selectFlight(f, cList);
            } else if (again != 2) {
                System.out.println("Please enter 1 or 2. ");
            }
        }

    }

    // Books a reservation for parties that want to sit togeather
    public static void bookTogether(ArrayList<Flight> fList, ArrayList<Customer> cList, Flight f, int fc, Customer c, int party) {
        Scanner scan = new Scanner(System.in);
        
        boolean booked = false;
        int emptySeats = 0;
        if (fc == 1) {
            // loops to get the desired flight
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    // finds an empty seat
                    for (int col = 0; col < fList.get(i).getFirstClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getFirstClass()[col].length; row++) {
                            if (fList.get(i).getFirstClass()[col][row] == null) {
                                // checks to see if there are enough seats
                                // after the first empty seat for the rest of the party
                                for (int x = 1; x <= party; x++) {
                                    for (int nCol = col; nCol < fList.get(i).getFirstClass().length; nCol++) {
                                        for (int nRow = row; nRow < fList.get(i).getFirstClass()[nCol].length; nRow++) {
                                            if (fList.get(i).getFirstClass()[nCol][nRow] == null) {
                                                emptySeats++;
                                                // if there are enough seats for the party
                                                // book the reservation
                                                if (emptySeats >= party) {
                                                    booked = true;
                                                    while (party > 0) {
                                                        fList.get(i).getFirstClass()[nCol][nRow] = new Reservation(f, c, String.valueOf(nCol + nRow), false);
                                                        party--;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                emptySeats = 0;
                            
                            }
                        }
                    }
                }
            }
        }
        else if (fc == 2) {
            // loops to get the desired flight
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    // finds an empty seat
                    for (int col = 0; col < fList.get(i).getPeasantClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getPeasantClass()[col].length; row++) {
                            if (fList.get(i).getPeasantClass()[col][row] == null) {
                                // checks to see if there are enough seats
                                // after the first empty seat for the rest of the party
                                for (int x = 1; x <= party; x++) {
                                    for (int nCol = col; nCol < fList.get(i).getPeasantClass().length; nCol++) {
                                        for (int nRow = row; nRow < fList.get(i).getPeasantClass()[nCol].length; nRow++) {
                                            if (fList.get(i).getPeasantClass()[nCol][nRow] == null) {
                                                emptySeats++;
                                                // if there are enough seats for the party
                                                // book the reservation
                                                if (emptySeats >= party) {
                                                    booked = true;
                                                    while (party > 0) {
                                                        fList.get(i).getPeasantClass()[nCol][nRow] = new Reservation(f, c, String.valueOf(nCol + nRow), false);
                                                        party--;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                emptySeats = 0;
                            
                            }
                        }
                    }
                }
            }
        }
        if (!booked){
            boolean valid = true;
            while (valid) {
                System.out.println("Sorry. Your party will not be able to sit together. Book this flight anyway? "
                    + "\n[1] Yes"
                    + "\n[2] No");
                int answer = scan.nextInt();
                if (answer == 1) {
                    bookReservation(fList, f, fc, c, party);
                    } 
                else {
                    while (valid) {
                        System.out.println("Would you like to try to book a different flight? "
                            + "\n[1] Yes"
                            + "\n[2] No");
                            answer = scan.nextInt();
                            if (answer == 1) {
                                selectFlight(fList, cList);
                            }
                            else if (answer == 2){
                                valid = false;
                            }
                            else{
                                System.out.println("Please enter 1 or 2.");
                        }
                    }
                }
            }
        }
}

    //Books a reservation
    public static void bookReservation(ArrayList<Flight> fList, Flight f, int fc, Customer c, int party) {
        // adds a first class reservation
        if (fc == 1) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    for (int col = 0; col < fList.get(i).getFirstClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getFirstClass()[col].length; row++) {
                            if (fList.get(i).getFirstClass()[col][row] == null) {
                                fList.get(i).getFirstClass()[col][row] = new Reservation(f, c, String.valueOf(col + row), true);
                            }
                        }
                    }
                }
            }
        } // adds an economy reservation
        else {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    for (int col = 0; col < fList.get(i).getPeasantClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getPeasantClass()[col].length; row++) {
                            if (fList.get(i).getPeasantClass()[col][row] == null) {
                                fList.get(i).getPeasantClass()[col][row] = new Reservation(f, c, String.valueOf(col + row), false);
                            }
                        }
                    }
                }
            }
        }
    }


    

    // Cancels a reservation by reservation ID
    public static void cancelRes(ArrayList<Reservation> resList, ArrayList<Reservation> cancelList) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter the Reservation Number: ");
        int resID = scan.nextInt();
        boolean found = false;
        for (int i = 0; i < resList.size(); i++) {
            if (resList.get(i).getReservationNum() == resID) {
                found = true;
                cancelList.add(resList.get(i));
                resList.remove(resList.get(i));
                ArrayList<Reservation> custRes = resList.get(i).getCustomer().getReservationList();
                for(int j = 0; i < custRes.size(); i ++){
                    if(custRes.get(j).getReservationNum() == resID){
                        custRes.remove(j);
                    }
                }
                Flight f = resList.get(i).getFlight();
                if(resList.get(i).getFirstClass()){
                    Reservation[][] fc = f.getFirstClass();
                    for(int row = 0; row < fc.length; i++){
                        for(int col = 0; col < fc[row].length; col++){
                           if(fc[row][col].getReservationNum() == resID){
                               fc[row][col] = null;
                           }
                        }
                    }
                } else if (!resList.get(i).getFirstClass()){
                    Reservation[][] peasantClass = f.getPeasantClass();
                    for(int row = 0; row < peasantClass.length; i++){
                        for(int col = 0; col < peasantClass[row].length; col++){
                           if(peasantClass[row][col].getReservationNum() == resID){
                               peasantClass[row][col] = null;
                           }
                        }
                    }
                }
        }
        
        if(!found){
            System.out.println("There is no reservation under that number.");
        }
        }
    }

    // prints a customers reservation according to the customer ID
    public static void printRes(ArrayList<Customer> custList) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the customer ID?");
        int custNum = scan.nextInt();
        boolean found = false;
        
        for(int i = 0; i < custList.size(); i++)
        {
            if(custNum == custList.get(i).getCustomerId())
            {
                found = true;
                System.out.println("We found that customer:");
                custList.get(i).printRes();
            }
        } 
        if(!found)
        {
            System.out.println("That customer does not exist");
        }
    }


    // prints out a pilots schedule for the week
    public static void printSchedule(ArrayList<Flight> flight) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the pilot's ID?");
        int pilotID = scan.nextInt();
        boolean found = false;
        
        for (int i = 0; i < flight.size(); i++) {
            if (flight.get(i).getPilot().getPilotId() == pilotID) {
                System.out.println("Pilot " + pilotID + " Schedule: ");
                System.out.println(flight.get(i).getDate() + " " + flight.get(i).getFlightCode()
                        + " " + flight.get(i).getRoute() + " " + flight.get(i).getTime());
                found = true;
            }
        }
        if(!found)
        {
            System.out.println("That pilot does not exist");
        }
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
        System.out.println("6.  Print a Pilot's Weekly Schedule.");
        System.out.println("7.  Find All Reservations Made Under a Specific Customer ID Number.");
        System.out.println("8.  Search for Reservation by Reservation Number.");
        System.out.println("9.  Search Canceled Reservations By Reservation Number or Customer Name.");
        System.out.println("10. Print Seat Layout for Specified Flight.");
        System.out.println("11. Exit."); 
        System.out.print("Choice: ");
        
        try{
        	int choice = scan.nextInt();
        	
        	if(choice < 0 || choice > 11){
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
                int grossIncome = 0;
                System.out.println("Flight: " + flights.get(i).getFlightCode());
                //For Loop to read seat maps including getters and setters
                Reservation[][] firstClass = flights.get(i).getFirstClass();
                Reservation[][] peasantClass = flights.get(i).getPeasantClass();
                for(int row = 0; row < firstClass.length; row++){
                    for(int col = 0; col < firstClass.length; col++){
                        if(firstClass[row][col] != null){
                            grossIncome = grossIncome + firstClass[row][col].getCost();
                        }
                    }
                }
                for(int row = 0; row < peasantClass.length; row++){
                    for(int col = 0; col < peasantClass.length; col++){
                        if(peasantClass[row][col] != null){
                            grossIncome = grossIncome + firstClass[row][col].getCost();
                        }
                    }
                }
                System.out.println("Gross Income: " + grossIncome);
            }
            
            
    }
    
    public static void grossIncomeSpec(ArrayList<Flight> flights){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Please Enter the flight code: ");
        String flightCode = scan.nextLine().trim();
        boolean found = false;
        for(int i = 0; i < flights.size(); i++)
            {
                if(flightCode.equals(flights.get(i).getFlightCode())) {
                    found = true;
                    System.out.println("Matched Flight: ");
                    Flight f = flights.get(i);
                    //For Loop to read seat map
                    int grossIncome = 0;
                    System.out.println("Flight: " + flights.get(i).getFlightCode());
                    Reservation[][] firstClass = f.getFirstClass();
                    Reservation[][] peasantClass = f.getPeasantClass();
                    for(int row = 0; row < firstClass.length; row++){
                        for(int col = 0; col < firstClass.length; col++){
                            if(firstClass[row][col] != null){
                                grossIncome = grossIncome + firstClass[row][col].getCost();
                            }
                        }
                    }
                    for(int row = 0; row < peasantClass.length; row++){
                        for(int col = 0; col < peasantClass.length; col++){
                            if(peasantClass[row][col] != null){
                            grossIncome = grossIncome + firstClass[row][col].getCost();
                            }
                        }
                    }
                    System.out.println("Gross Income: " + grossIncome);
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
        } else {
            System.out.println("Incorrect Choice.  Returning to menu.");
        }
    }
    
    public static void printFlightSeats(ArrayList<Flight> flights){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter the flight code: ");
        String flightCode = scan.nextLine().trim();
        boolean found = false;
        for(int i = 0; i < flights.size(); i++)
            {
                if(flightCode.equals(flights.get(i).getFlightCode())) {
                    found = true;
                    System.out.println("Matched Flight: " + flightCode + "\n");
                    Flight f = flights.get(i);
                    //For Loop to read seat map
                    Reservation[][] firstClass = f.getFirstClass();
                    Reservation[][] peasantClass = f.getPeasantClass();
                    System.out.println("First Class:");
                    for(int row = 0; row < firstClass.length; row++){
                        System.out.print("\n");
                        for(int col = 0; col < firstClass[row].length; col++){
                            
                           if(firstClass[row][col] == null){
                               System.out.print("\tOpen");
                           } else {
                               System.out.print("\t" + firstClass[row][col].getCustomer().getName());
                           }
                        }
                    }
                    System.out.println("\nEconomy Class:");
                    for(int row = 0; row < peasantClass.length; row++){
                        System.out.print("\n");
                        for(int col = 0; col < peasantClass[row].length; col++){
                           if(peasantClass[row][col] == null){
                               System.out.print("\tOpen");
                           } else {
                               System.out.print("\t" + peasantClass[row][col].getCustomer().getName());
                           }
                        }
                    }
                    
                }
            }
        if(!found) {
            System.out.println("The flight code provided was invalid.");
        }
            
    }
}
