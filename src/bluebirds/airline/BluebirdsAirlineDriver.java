/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluebirds.airline;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
        
        Connection connect = null;
        CallableStatement callSt = null;
        ResultSet resSet = null;
        
        connect = connect(connect);
        
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
        		searchCustID(connect, callSt, resSet);
        	}
        	else if(choice == 3){
        		cancelRes(reservationAL, canceledResAL, flightAL);
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
        		searchReservID(connect, callSt, resSet);
        	}
        	else if(choice == 9){
        		searchCanceledRes(canceledResAL);
        	}
        	else if(choice == 10){
        		printFlightSeats(connect, callSt, resSet);
        	}
        	else if(choice == 11){
        		System.out.println("\nGOOD BYE!!!");
        		System.exit(0);
        	}
        }  
    }
    
    public static Connection connect(Connection connect)
    {
	
	String uid = "itp220";
	String pass = "itp220";

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/BlueBirdsAirline";

	try 
	{ 
	  Class.forName(driver).newInstance();
	}
	catch( Exception e ) 
	{ 
	  e.printStackTrace();
	  
	}
	try 
	{
	  connect = DriverManager.getConnection(url, uid, pass);
	  System.out.println("Connection successful!");
	  
	}
	catch( SQLException e ) 
	{
	  e.printStackTrace();
	}
	return connect;
    }
    
    public static void searchReservID(Connection connect, CallableStatement callSt, ResultSet resSet)
    {
        Scanner scan = new Scanner(System.in);
        String procName = "searchReservID";
        System.out.println("What is the reservation number?");
        int resNum = scan.nextInt();

        String storedProc = "{call " + procName + " (" + resNum + ")}";

        try {
            callSt = connect.prepareCall(storedProc);
            resSet = callSt.executeQuery();
            
            try {
                System.out.println(" ");

                ResultSetMetaData meta = resSet.getMetaData();
                int columns = meta.getColumnCount();
                if (!resSet.isBeforeFirst() ) {    
                    System.out.println("There is no reservation with that id"); 
                }
                else
                {
                    System.out.println("We found that reservation:");
                }
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        String s = resSet.getString(i);
                        System.out.print(s + "  ");
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception");
            }

        } // end try
        catch (SQLException e) 
        {
            System.out.println("stored proc did not work");
        }
        System.out.println();

    }
    
    public static void searchCustID(Connection connect, CallableStatement callSt, ResultSet resSet)
    {
        Scanner scan = new Scanner(System.in);
        String procName = "searchCustID";
        System.out.println("What is the customer ID?");
        int custNum = scan.nextInt();

        String storedProc = "{call " + procName + " (" + custNum + ")}";

        try {
            callSt = connect.prepareCall(storedProc);
            resSet = callSt.executeQuery();
            
            try {
                System.out.println(" ");

                ResultSetMetaData meta = resSet.getMetaData();
                int columns = meta.getColumnCount();
                if (!resSet.isBeforeFirst() ) {    
                    System.out.println("That customer does not exist");
                }
                else
                {
                    System.out.println("We found that customer:");
                }
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        String s = resSet.getString(i);
                        System.out.print(s + "  ");
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception");
            }

        } // end try
        catch (SQLException e) 
        {
            System.out.println("stored proc did not work");
        }
        System.out.println();
        
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
        reservations.add(new Reservation(flights.get(0), customers.get(0), "FCA1", true));
        reservations.add(new Reservation(flights.get(0), customers.get(0), "FCA2", true));
        reservations.add(new Reservation(flights.get(0), customers.get(1), "ECA1", false));
        reservations.add(new Reservation(flights.get(0), customers.get(2), "ECA2", false));
        
        flights.get(0).getFirstClass()[0][0] = reservations.get(0);
        flights.get(0).getFirstClass()[0][1] = reservations.get(1);
        flights.get(0).getEconomyClass()[0][0] = reservations.get(2);
        flights.get(0).getEconomyClass()[0][1] = reservations.get(3);
        
        customers.get(0).addRes(reservations.get(0));
        customers.get(0).addRes(reservations.get(1));
        customers.get(1).addRes(reservations.get(2));
        customers.get(2).addRes(reservations.get(3));
        
  
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
        //12RPAM
        String flightCode = "" + day.getDayOfMonth();
        if (route == 1) {
            flightCode.concat("RP");
        } else if (route == 2) {
            flightCode.concat("PR");
        }
        if (time == 1) {
            flightCode.concat("AM");
        } else if (time == 2) {
            flightCode.concat("PM");
        }
        
//        String flightRoute = "";
//        if (route == 1) {
//            flightRoute = "Roanoke to Phoenix";
//        } else if (route == 2) {
//            flightRoute = "Phoenix to Roanoke";
//        }

//        String flightTime = "";
//        if (time == 1) {
//            flightTime = "8:00 a.m.";
//        } else if (time == 2) {
//            flightTime = "6:00 p.m.";
//        }
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
        for (int eCol = 0; eCol < foundFlight.getEconomyClass().length; eCol++) {
            for (int eRow = 0; eRow < foundFlight.getEconomyClass()[eCol].length; eRow++) {
                if (foundFlight.getEconomyClass()[eCol][eRow] == null) {
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
                bookReservation(f, foundFlight, bookClass, c, party, cList);
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
                                                    int seatCol = col;
                                                    int seatRow = row;
                                                    while(!booked){
                                                    while (party > 0) {
                                                       
                                                            String seatNum = "FC";
                                                            if(seatCol == 0) seatNum += "A" + (seatRow +1);
                                                            else if (seatCol == 1) seatNum += "B" + (seatRow+1);
                                                            fList.get(i).getFirstClass()[seatCol][seatRow] = new Reservation(f, c, seatNum, true);
                                                            System.out.println("Reservation Booked. Reservation ID is "+
                                                                    fList.get(i).getFirstClass()[seatCol][seatRow].getReservationNum()+
                                                                    " Seat Number: "+ fList.get(i).getFirstClass()[seatCol][seatRow].getSeatNumber());
                                                    party--;
                                                    seatRow++;
                                                    if (row>1){
                                                        seatCol++;
                                                        seatRow =0;
                                                    }
                                                    
                                                    
                                                    booked = true;
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
        }
        else if (fc == 2) {
            // loops to get the desired flight
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    // finds an empty seat
                    for (int col = 0; col < fList.get(i).getEconomyClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getEconomyClass()[col].length; row++) {
                            if (fList.get(i).getEconomyClass()[col][row] == null) {
                                // checks to see if there are enough seats
                                // after the first empty seat for the rest of the party
                                for (int x = 1; x <= party; x++) {
                                    for (int nCol = col; nCol < fList.get(i).getEconomyClass().length; nCol++) {
                                        for (int nRow = row; nRow < fList.get(i).getEconomyClass()[nCol].length; nRow++) {
                                            if (fList.get(i).getEconomyClass()[nCol][nRow] == null) {
                                                emptySeats++;
                                                // if there are enough seats for the party
                                                // book the reservation
                                                    int seatCol = col;
                                                    int seatRow = row;
                                                    while(!booked){
                                                        while (party > 0) {
                                                       
                                                            String seatNum = "EC";
                                                            if(seatCol == 0) seatNum += "A" + (seatRow +1);
                                                            else if (seatCol == 1) seatNum += "B" + (seatRow+1);
                                                            fList.get(i).getEconomyClass()[seatCol][seatRow] = new Reservation(f, c, seatNum, true);
                                                            System.out.println("Reservation Booked. Reservation ID is "+
                                                                    fList.get(i).getEconomyClass()[seatCol][seatRow].getReservationNum()+
                                                                    " Seat Number: "+ fList.get(i).getEconomyClass()[seatCol][seatRow].getSeatNumber());
                                                            party--;
                                                            seatRow++;
                                                            if (seatRow>1){
                                                                seatCol++;
                                                                seatRow =0;
                                                            }
                                                            booked = true;
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
                    bookReservation(fList, f, fc, c, party, cList);
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
    public static void bookReservation(ArrayList<Flight> fList, Flight f, int fc, Customer c, int party, ArrayList<Customer> custList) {
        // adds a first class reservation
        if (fc == 1) {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    for (int col = 0; col < fList.get(i).getFirstClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getFirstClass()[col].length; row++) {
                            
                            if (fList.get(i).getFirstClass()[col][row] == null) {
                                int seatCol = col;
                                int seatRow = row;
                                while(party>0){
                                    String seatNum = "FC";
                                    if(row == 0) seatNum += "A" + (seatRow +1);
                                    else if (row == 1) seatNum += "B" + (seatRow+1);
                                    
                                    fList.get(i).getFirstClass()[col][row] = new Reservation(f, c, seatNum, true);
                                    System.out.println("Reservation Booked. Reservation ID is "+
                                                                    fList.get(i).getFirstClass()[col][row].getReservationNum()+
                                                                    " Seat Number: "+ fList.get(i).getFirstClass()[col][row].getSeatNumber());
                                    for(int num = 0; num < custList.size(); num++){
                                        if(custList.get(num).getCustomerId() == c.getCustomerId()){
                                            custList.get(num).addRes(new Reservation(f, c, seatNum, true));
                                        }
                                        
                                    }
                                    
                                    party--;
                                seatCol++;
                                seatRow++;
                            }
                            }
                        }
                    }
                }
            }
        } // adds an economy reservation
        else {
            for (int i = 0; i < fList.size(); i++) {
                if (fList.get(i) == f) {
                    for (int col = 0; col < fList.get(i).getEconomyClass().length; col++) {
                        for (int row = 0; row < fList.get(i).getEconomyClass()[col].length; row++) {
                            
                            if (fList.get(i).getEconomyClass()[col][row] == null) {
                                while(party>0){
                                    int seatCol = col;
                                    int seatRow = row;
                                    String seatNum = "EC";
                                    if(row == 0) seatNum += "A" + (seatRow +1);
                                    else if (row == 1) seatNum += "B" + (seatRow+1);
                                    fList.get(i).getEconomyClass()[seatCol][seatRow] = new Reservation(f, c, seatNum, false);
                                    System.out.println("Reservation Booked. Reservation ID is "+
                                                                    fList.get(i).getEconomyClass()[seatCol][seatRow].getReservationNum()+
                                                                    " Seat Number: "+ fList.get(i).getEconomyClass()[seatCol][seatRow].getSeatNumber());
                                    for(int num = 0; num < custList.size(); num++)
                                    {
                                        if(custList.get(num).getCustomerId() == c.getCustomerId())
                                        {
                                            custList.get(num).addRes(new Reservation(f, c, seatNum, false));
                                        }
                                        
                                    }
                                    party--;
                                    seatRow++;
                                    if(seatRow > 3){
                                        seatCol++;
                                        seatRow = 0;
                                    }
                                    
                                
                            }
                            }
                        }
                    }
                }
            }
        }
    }


    

    // Cancels a reservation by reservation ID
    public static void cancelRes(ArrayList<Reservation> resList, ArrayList<Reservation> cancelList, ArrayList<Flight> flights) {
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
                
                for(int j = 0; j < custRes.size(); j ++){
                    if(custRes.get(j).getReservationNum() == resID){
                    custRes.remove(j);
                    }
                }
                Flight f = resList.get(i).getFlight();
                if(resList.get(i).getFirstClass()){
                    Reservation[][] fc = f.getFirstClass();
                    for(int row = 0; row < fc.length; row++){
                        for(int col = 0; col < fc[row].length; col++){
                            if(fc[row][col] != null){
                                if(fc[row][col].getReservationNum() == resID){
                                    fc[row][col] = null;
                                }
                            }
                        }
                    }
                    f.setFirstClass(fc);
                } else if (!resList.get(i).getFirstClass()){
                    Reservation[][] economyClass = f.getEconomyClass();
                    for(int row = 0; row < economyClass.length; row++){
                        for(int col = 0; col < economyClass[row].length; col++){
                            if(economyClass[row][col] != null){
                                if(economyClass[row][col].getReservationNum() == resID){
                                    economyClass[row][col] = null;
                                }
                            }
                        }
                    }
                    f.setEconomyClass(economyClass);
                }
                resList.get(i).setFlight(f);
                for(int j = 0; j < flights.size(); j++){
                    if(f.getFlightCode().equals(flights.get(j).getFlightCode())){
                        flights.set(j, f);
                    }
                }
            }
        }
        if(!found){
            System.out.println("There is no reservation under that number.");
        } else {
            System.out.println("Reservation was canceled.");
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
                if(!found)
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
    
    public static void grossIncomeEach(Connection con){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        CallableStatement stmt;
        ResultSet resSet;
        String procName = "GrossIncomeEach";
        String storedProc = "{call " + procName +"}";
        System.out.println("\n");
        try {
           stmt = con.prepareCall(storedProc);
           resSet = stmt.executeQuery();

           try {
               System.out.println();

               ResultSetMetaData meta = resSet.getMetaData();
               int columns = meta.getColumnCount();
               while (resSet.next()) {
                   String flightCode = resSet.getString(1);
                   int grossIncome = resSet.getInt(2);
                   System.out.println("Flight Code: "+ flightCode + "  Gross Income: " + nf.format(grossIncome));
               }
           } catch (SQLException e) {
               System.out.println("SQL Exception");
           }

       } // end try
       catch (SQLException e) 
       {
           System.out.println("Stored proc did not work");
       }
            
            
    }
    
    public static void grossIncomeSpec(Connection con){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Please Enter the flight code: ");
        String flightCode = scan.nextLine().trim();
        CallableStatement stmt;
        ResultSet resSet;
        String procName = "GrossIncomeSpec";
        String storedProc = "{call " + procName + " (" + flightCode + ")}";
        System.out.println("\n");
        try {
           stmt = con.prepareCall(storedProc);
           resSet = stmt.executeQuery();

           try {
               System.out.println();

               ResultSetMetaData meta = resSet.getMetaData();
               if(resSet.next()) {
                   flightCode = resSet.getString(1);
                   int grossIncome = resSet.getInt(2);
                   System.out.println("Flight Code: "+ flightCode + "  Gross Income: " + nf.format(grossIncome));
               } else {
                   System.out.println("No flight found.");
               }
           } catch (SQLException e) {
               System.out.println("SQL Exception");
           }

       } // end try
       catch (SQLException e) 
       {
           System.out.println("Stored proc did not work");
       }
            
            
    }
    
    public static void searchCanceledRes(ArrayList<Reservation> res){
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to search for canceled reservations by reservation number (1) or customer name (2)?");
        System.out.println("Choice (1|2): ");
        int choice = scan.nextInt();
        boolean found = false;
        if(choice == 1){
            System.out.println("What is the reservation number?");
            int resNum = scan.nextInt();
         
            for(int i = 0; i < res.size(); i++)
            {
                if(resNum == res.get(i).getReservationNum()) {
                    found = true;
                    System.out.println("We found that canceled reservation:");
                    System.out.println(res.get(i).toString());
                }
                if (!found){
                    System.out.println("There is no canceled reservation under that number.");
                }
            }
        } else if(choice == 2){
            System.out.println("What is the customer's name?");
            scan.nextLine();
            String name = scan.nextLine();
         
            for(int i = 0; i < res.size(); i++)
            {
                if(res.get(i).getCustomer().getName().contains(name)) {
                    found = true;
                    System.out.println("\nMatching Canceled Reservation:\n");
                    System.out.println(res.get(i).toString());
                }
            }
            if(!found) {
                System.out.println("There are no canceled reservations matching the provided name.");
            }
        } else {
            System.out.println("Incorrect Choice.  Returning to menu.");
        }
    }
    
    public static void printFlightSeats(Connection connect, CallableStatement callSt, ResultSet resSet)
    {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Please Enter the flight code: ");
        String flightCode = scan.nextLine().trim();
        
        String procName = "printFirstClass";
        String procName2 = "printEconomyClass";


        String storedProc = "{call " + procName + " ('" + flightCode + "')}";
        String storedProc2 = "{call " + procName2 + " ('" + flightCode + "')}";

        //Print FirstClass
        try {
            callSt = connect.prepareCall(storedProc);
            resSet = callSt.executeQuery();
            
            try {
                System.out.println(" ");

                ResultSetMetaData meta = resSet.getMetaData();
                int columns = meta.getColumnCount();
                
                System.out.println("First Class:");
                System.out.println();
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        String s = resSet.getString(i);
                        if(i == 3)
                        {
                            System.out.println();
                        }
                        System.out.printf("%-20s", s);
                    }
     
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception");
            }

        } // end try
        catch (SQLException e) 
        {
            System.out.println("stored proc did not work");
        }
        System.out.println();
        
        
        
        //Print Economy Class
        try {
            callSt = connect.prepareCall(storedProc2);
            resSet = callSt.executeQuery();
            
            try {
                System.out.println(" ");

                ResultSetMetaData meta = resSet.getMetaData();
                int columns = meta.getColumnCount();

                System.out.println("Economy Class:");
                System.out.println();
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        String s = resSet.getString(i);
                        if(i == 5)
                        {
                            System.out.println();
                        }
                        System.out.printf("%-20s", s);
                    }

                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception");
            }

        } // end try
        catch (SQLException e) 
        {
            System.out.println("stored proc did not work");
        }
        System.out.println();
        /*boolean found = false;
        for(int i = 0; i < flights.size(); i++)
            {
                if(flightCode.equals(flights.get(i).getFlightCode())) {
                    found = true;
                    System.out.println("Matched Flight: " + flightCode + "\n");
                    Flight f = flights.get(i);
                    //For Loop to read seat map
                    Reservation[][] firstClass = f.getFirstClass();
                    Reservation[][] economyClass = f.getEconomyClass();
                    System.out.println("\nFirst Class:");
                    for(int row = 0; row < firstClass.length; row++){
                        System.out.print("\n");
                        for(int col = 0; col < firstClass[row].length; col++){
                            
                           if(firstClass[row][col] == null){
                               System.out.printf("%-20s" , "Open");
                               //System.out.print("\tOpen");
                           } else {
                               System.out.printf("%-20s",firstClass[row][col].getCustomer().getName());
                           }
                        }
                    }
                    System.out.println("\n\nEconomy Class:");
                    for(int row = 0; row < economyClass.length; row++){
                        System.out.print("\n");
                        for(int col = 0; col < economyClass[row].length; col++){
                           if(economyClass[row][col] == null){
                               System.out.printf("%-20s","Open");
                           } else {
                               System.out.printf("%-20s",economyClass[row][col].getCustomer().getName());
                           }
                        }
                    }
                    
                }
            }
        if(!found) {
            System.out.println("The flight code provided was invalid.");
        }*/
            
    }
}
