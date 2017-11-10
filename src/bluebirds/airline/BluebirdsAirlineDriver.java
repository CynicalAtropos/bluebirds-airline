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
import java.util.HashMap;
import java.util.Map;
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
        Statement stmt = null;
        connect = connect(connect);
        
        while (true)
        {
        	int choice = menu();
        	if(choice == 0){
                        pilotAL = primePilots(connect, stmt, pilotAL);
                        flightAL = primeFlights(connect, stmt, pilotAL, flightAL);
                        customerAL = primeCustomers(connect, stmt, customerAL);
                        reservationAL = primeReservations(connect, stmt, reservationAL, flightAL, customerAL);
                        primeSeatMap(connect, stmt, flightAL);
        	}
        	else if(choice == 1){
        		selectFlight(connect);
        	}
        	else if(choice == 2){
        		searchCustID(connect, callSt, resSet);
        	}
        	else if(choice == 3){
        		cancelRes(connect, callSt);
        	}
        	else if(choice == 4){
        		grossIncomeEach(connect);
        	}
        	else if(choice == 5){
        		grossIncomeSpec(connect);
        	}
        	else if (choice == 6){
        		printSchedule(connect, callSt, resSet);
        	}
        	else if(choice == 7){
        		printRes(connect, callSt, resSet);
        	}
        	else if(choice == 8){
        		searchReservID(connect, callSt, resSet);
        	}
        	else if(choice == 9){
        		searchCanceledRes(connect, callSt, resSet);
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
                System.out.println();
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                        System.out.printf("%-20s", resSet.getString(i));
                        System.out.println();
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
                System.out.println();
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                        System.out.printf("%-20s", resSet.getString(i));
                        System.out.println();
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
        
    }

    public static ArrayList<Pilot> primePilots(Connection con, Statement stmt, ArrayList<Pilot> pilots)
    {
        pilots.add(new Pilot("Chesley Sullenberger", "2801 Franklin Rd SW, Roanoke, VA 24014", "5403454434"));
        pilots.add(new Pilot("Amelia Earhart", "15240 N 32nd St, Phoenix, AZ 85032", "6024937404"));
        pilots.add(new Pilot("Han Solo", "3226 Brandon Ave SW, Roanoke, VA 24018", "5403448200"));
        pilots.add(new Pilot("Orville Wright", "1919 W Deer Valley Rd, Phoenix, AZ 85027", "6237802330"));
        
        try{
            stmt = con.createStatement();
            for (Pilot p : pilots){
                stmt.executeUpdate("INSERT INTO pilots"
                        + " VALUES (" + p.getPilotId() + ", '"
                                      + p.getName() + "', '"
                                      + p.getAddress() + "', '"
                                      + p.getPhoneNumber() + "')" );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return pilots;
    }
    public static ArrayList<Flight> primeFlights(Connection con, Statement stmt, ArrayList<Pilot> pilots, ArrayList<Flight> flights) {

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
        
        try{
            stmt = con.createStatement();
            for (Flight f : flights){
                stmt.executeUpdate("INSERT INTO flights"
                        + " VALUES ('" + f.getFlightCode() + "', '"
                                      + f.getDate().toString()+ "', '"
                                      + f.getTime()+ "', '"
                                      + f.getRoute() + "', '"
                                      + f.getPilot().getPilotId() + "')" );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return flights;

    }
    
    public static ArrayList<Customer> primeCustomers(Connection con, Statement stmt, ArrayList<Customer> customers)
    {
        customers.add(new Customer("Rick Sanchez","2072 Apperson Dr, Salem, VA 24153", "5407746295"));
        customers.add(new Customer("Daryl Dixon", "21611 N 26th Ave, Phoenix, AZ 85027", "6235826020"));
        customers.add(new Customer("Merle Dixon", "3202 E Greenway Rd, Phoenix, AZ 85032", "6024851000"));
        
        try{
            stmt = con.createStatement();
            for (Customer c : customers){
                stmt.executeUpdate("INSERT INTO customers"
                        + " VALUES (" + c.getCustomerId() + ", '"
                                      + c.getName()+ "', '"
                                      + c.getAddress()+ "', '"
                                      + c.getPhoneNumber() + "')" );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return customers;
    }
    
    public static ArrayList<Reservation> primeReservations(Connection con, Statement stmt, ArrayList<Reservation> reservations, ArrayList<Flight> flights, ArrayList<Customer> customers)
    {
        int fc = 0;
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
        try{
            stmt = con.createStatement();
            for (Reservation r : reservations){
                if(r.getFirstClass()){
                    fc = 1;
                }
                else{
                    fc = 0;
                }
                stmt.executeUpdate("INSERT INTO reservations"
                        + " VALUES (" + r.getReservationNum() + ", '"
                                      + r.getCustomer().getCustomerId() + "', '"
                                      + r.getSeatNumber() + "', '"
                                      + fc + "', '"
                                      + r.getFlight().getFlightCode() + "', '"
                                      + r.getCost() + "')" );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
  
        return reservations;
    }
    
    public static void primeSeatMap(Connection connect, Statement stmt, ArrayList<Flight> flights)
    {
        try{
            stmt = connect.createStatement();
            for (Flight f : flights){
                stmt.executeUpdate("INSERT INTO seatmap(flightCode)"
                        + " VALUES ('" + f.getFlightCode() + "')" );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            stmt = connect.createStatement();
            
            stmt.executeUpdate("UPDATE seatmap\n" +
                               "SET \n" +
                               "FCA1 = " + flights.get(0).getFirstClass()[0][0].getReservationNum() + ",\n" +
                               "FCA2 = " + flights.get(0).getFirstClass()[0][1].getReservationNum() + ",\n" +
                               "ECA1 = " + flights.get(0).getEconomyClass()[0][0].getReservationNum() + ",\n" +
                               "ECA2 = " + flights.get(0).getEconomyClass()[0][1].getReservationNum() + "\n" +
                               "WHERE flightCode = '12RPAM'");
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static int createNewCustomer(Connection con)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is your name? ex: First Last");
        String name = scan.nextLine();
        System.out.println("What is your address? ex: StreetNumber Street Name, City, ST ZipCode");
        String address = scan.nextLine();
        System.out.println("What is your phone number? ex: 5409770923");
        String phone = scan.nextLine();
        String insert = "INSERT INTO customers" + " VALUES ('" + name + "', '" + address + "', '" + phone + "')" ;
        int custID = 0;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(insert);
            ResultSet resSet = stmt.getGeneratedKeys();
            System.out.println(resSet.getMetaData().getColumnName(1));
            custID = resSet.getInt(1);
           } // end try
        catch (SQLException e) 
        {
            System.out.println("Stored proc did not work");
        }
        
        
        System.out.println("Your customer ID is " + custID);
        return custID;
    }
    
    // Finds the customer for the reservation
    public static int findCustomer(Connection con){
        Scanner scan = new Scanner(System.in);
        CallableStatement stmt;
        ResultSet resSet;
        
        System.out.println("Enter your ID number:");
        int custID = scan.nextInt();
        
        String procName = "searchCustID";
        String storedProc = "{call " + procName + " (" + custID + ")}";
        System.out.println("\n");
        try {
            stmt = con.prepareCall(storedProc);
            resSet = stmt.executeQuery();
            if(resSet.next()){
                String name = resSet.getString(2);
                System.out.println("Customer found with the name: " + name);
                return custID;
            }
        } // end try
        catch (SQLException e) 
        {
            System.out.println("Stored proc did not work");
        }
                System.out.println("Customer not found. Create new customer please.");
                return createNewCustomer(con);    
    }

    // Gets paramaters for a flight from the user and passes them to a method
    public static void selectFlight(Connection con) {
        Scanner scan = new Scanner(System.in);
        int group = 2;
        int custID = 0;
        // Determines if the customer is new or returning
        int custAnswer = 0;
        boolean valid = true;
        while (valid) {
            System.out.println("Are you a returning customer?: "
                    + "\n[1] Yes"
                    + "\n[2] No");
            custAnswer = scan.nextInt();
            if (custAnswer == 1) {
                custID = findCustomer(con);
                valid = false;
            }
        else if (custAnswer == 2) {
                custID = createNewCustomer(con);
                valid = false;
        }
        else
            System.out.println("Please enter 1 or 2.");
        }
        
        String flightCode = "";
        //Determines Day
        int day = 0;
        valid = true;
        while (valid) {
            System.out.println("Please select your date of flight for the week of November 12-18 (enter 12-18)");
            day = scan.nextInt();

            if (day < 12 || day > 18) {
                System.out.println("Please enter 12-18");
            } else {
                valid = false;
                flightCode = flightCode + day;
            }
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
                System.out.println("You have selected Roanoke to Phoenix.");
                valid = false;
                flightCode = flightCode + "RP";

            } else if (routeAnswer == 2) {
                System.out.println("You have selected Phoenix to Roanoke.");
                valid = false;
                flightCode = flightCode + "PR";

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
                flightCode = flightCode + "AM";

            } else if (timeAnswer == 2) {
                System.out.println("You have selected Evening.");
                valid = false;
                flightCode = flightCode + "PM";
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


        searchFlight(flightCode, party, custID, group, con);
    }

    // Searches for a flight based on the customers parameters
    public static void searchFlight(String flightCode, int party, int custID, int group, Connection con) {
        Scanner scan = new Scanner(System.in);

        // searches for a flight matching the customers parameters
        int fClass = 0;
        int economy = 0;
        CallableStatement stmt;
        ResultSet resSet;
        String procName = "findSeatAvailability";
        String storedProc = "{call " + procName + " ('" + flightCode + "')}";
        try 
        {
           stmt = con.prepareCall(storedProc);
           resSet = stmt.executeQuery();

           try 
           {
               System.out.println();
               fClass = resSet.getInt(1);
               economy = resSet.getInt(2);
               
           } catch (SQLException e) {
               System.out.println("SQL Exception");
           }

       } // end try
       catch (SQLException e) 
       {
           System.out.println("Stored proc did not work");
       }
        if (party < fClass + economy) {
            int bookClass = 0;
            if (party <= fClass) {
                boolean valid = true;
                if((party < 3 || group == 2) && party <= fClass){
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

            }
           
            if (group == 1) {
                bookTogether(flightCode, bookClass, custID, party, con);
            } else {
                bookReservation(flightCode, bookClass, custID, party, con);
            }
        } else {
            System.out.println("The flight for this day is full. Would you like to book a different flight? "
                    + "\n[1] Yes"
                    + "\n[2] No");
            int again = scan.nextInt();

            if (again == 1) {
                selectFlight(con);
            } else if (again != 2) {
                System.out.println("Please enter 1 or 2. ");
            }
        }

    }

    // Books a reservation for parties that want to sit togeather
    public static void bookTogether(String flightCode, int fc, int custID, int party, Connection con) {
        Scanner scan = new Scanner(System.in);
        
        boolean booked = false;
        int emptySeats = 0;
        if (fc == 1) {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getFirstClassSeats";
            String storedProc = "{call " + procName + " (" + flightCode + ")}";
            System.out.println("\n");
            try {
                stmt = con.prepareCall(storedProc);
                resSet = stmt.executeQuery();

                try {
                    System.out.println();

                    ResultSetMetaData meta = resSet.getMetaData();
                    int columns = meta.getColumnCount();
                    //Arraylist of seatNames (Ex: FCA1)
                    ArrayList<String> seatNames = new ArrayList<>();
                    seatNames.add(meta.getColumnName(1));
                    seatNames.add(meta.getColumnName(2));
                    seatNames.add(meta.getColumnName(3));
                    seatNames.add(meta.getColumnName(4));
                    //ArrayList of seat values (Ex: 0=empty or 200 = resID
                    ArrayList<Integer> seat = new ArrayList<>();
                    seat.add(resSet.getInt(1));
                    seat.add(resSet.getInt(2));
                    seat.add(resSet.getInt(3));
                    seat.add(resSet.getInt(4));
                    if((seat.get(0) == null && seat.get(1) == null) || (seat.get(2) == null && seat.get(3) == null)){
                        while(party > 0){
                            int count = 0;
                            boolean found = false;
                            while(!found){
                                if(seat.get(count) == 0){
                                     String insert = "INSERT INTO reservations" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)" ;
                                     try {
                                         Statement stmt2 = con.createStatement();
                                         stmt.executeUpdate(insert);
                                         ResultSet resSet2 = stmt2.getGeneratedKeys();
                                         int resID = resSet.getInt(1);
                                         seat.set(count, resID);
                                         found = true;
                                         party--;
                                         System.out.println("Reservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
                                     } // end try
                                     catch (SQLException e) 
                                     {
                                         System.out.println("Stored proc did not work");
                                     }
                                }
                            }
                        }
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
        else if (fc == 2) {
            // loops to get the desired flight
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getEconomySeats";
            String storedProc = "{call " + procName + " (" + flightCode + ")}";
            System.out.println("\n");
            try {
               stmt = con.prepareCall(storedProc);
               resSet = stmt.executeQuery();

               try {
                   System.out.println();

                   ResultSetMetaData meta = resSet.getMetaData();
                   int columns = meta.getColumnCount();
                   //Arraylist of seatNames (Ex: FCA1)
                   ArrayList<String> seatNames = new ArrayList<>();
                   seatNames.add(meta.getColumnName(1));
                   seatNames.add(meta.getColumnName(2));
                   seatNames.add(meta.getColumnName(3));
                   seatNames.add(meta.getColumnName(4));
                   seatNames.add(meta.getColumnName(5));
                   seatNames.add(meta.getColumnName(6));
                   seatNames.add(meta.getColumnName(7));
                   seatNames.add(meta.getColumnName(8));
                   //ArrayList of seat values (Ex: 0=empty or 200 = resID
                   ArrayList<Integer> seat = new ArrayList<>();
                   seat.add(resSet.getInt(1));
                   seat.add(resSet.getInt(2));
                   seat.add(resSet.getInt(3));
                   seat.add(resSet.getInt(4));
                   seat.add(resSet.getInt(5));
                   seat.add(resSet.getInt(6));
                   seat.add(resSet.getInt(7));
                   seat.add(resSet.getInt(8));
                   
                   while(party > 0){
                       int count = 0;
                       boolean found = false;
                       while(!found){
                           if(seat.get(count) == 0){
                                String insert = "INSERT INTO reservations" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 0, '" + flightCode + "', 450)" ;
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt.executeUpdate(insert);
                                    ResultSet resSet2 = stmt2.getGeneratedKeys();
                                    int resID = resSet.getInt(1);
                                    seat.set(count, resID);
                                    found = true;
                                    party--;
                                    System.out.println("Reservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
                                } // end try
                                catch (SQLException e) 
                                {
                                    System.out.println("Stored proc did not work");
                                }
                           }
                       }
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
        if (!booked){
            boolean valid = true;
            while (valid) {
                System.out.println("Sorry. Your party will not be able to sit together. Book this flight anyway? "
                    + "\n[1] Yes"
                    + "\n[2] No");
                int answer = scan.nextInt();
                if (answer == 1) {
                    bookReservation(flightCode, fc, custID, party, con);
                    } 
                else {
                    while (valid) {
                        System.out.println("Would you like to try to book a different flight? "
                            + "\n[1] Yes"
                            + "\n[2] No");
                            answer = scan.nextInt();
                            if (answer == 1) {
                                selectFlight(con);
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
    public static void bookReservation(String flightCode, int fc, int custID, int party, Connection con) {
        // adds a first class reservation
        if (fc == 1) {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getFirstClassSeats";
            String storedProc = "{call " + procName + " (" + flightCode + ")}";
            System.out.println("\n");
            try {
               stmt = con.prepareCall(storedProc);
               resSet = stmt.executeQuery();

               try {
                   System.out.println();

                   ResultSetMetaData meta = resSet.getMetaData();
                   int columns = meta.getColumnCount();
                   //Arraylist of seatNames (Ex: FCA1)
                   ArrayList<String> seatNames = new ArrayList<>();
                   seatNames.add(meta.getColumnName(1));
                   seatNames.add(meta.getColumnName(2));
                   seatNames.add(meta.getColumnName(3));
                   seatNames.add(meta.getColumnName(4));
                   //ArrayList of seat values (Ex: 0=empty or 200 = resID
                   ArrayList<Integer> seat = new ArrayList<>();
                   seat.add(resSet.getInt(1));
                   seat.add(resSet.getInt(2));
                   seat.add(resSet.getInt(3));
                   seat.add(resSet.getInt(4));
                   
                   while(party > 0){
                       int count = 0;
                       boolean found = false;
                       while(!found){
                           if(seat.get(count) == null){
                                String insert = "INSERT INTO reservations" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)" ;
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt.executeUpdate(insert);
                                    ResultSet resSet2 = stmt2.getGeneratedKeys();
                                    int resID = resSet.getInt(1);
                                    seat.set(count, resID);
                                    found = true;
                                    party--;
                                    System.out.println("Reservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
                                } // end try
                                catch (SQLException e) 
                                {
                                    System.out.println("Stored proc did not work");
                                }
                           }
                           count++;
                       }
                   }

               } catch (SQLException e) {
                   System.out.println("SQL Exception");
               }

           } // end try
           catch (SQLException e) 
           {
               System.out.println("Stored proc did not work");
           }

        } // adds an economy reservation
        else {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getEconomySeats";
            String storedProc = "{call " + procName + " (" + flightCode + ")}";
            System.out.println("\n");
            try {
               stmt = con.prepareCall(storedProc);
               resSet = stmt.executeQuery();

               try {
                   System.out.println();

                   ResultSetMetaData meta = resSet.getMetaData();
                   int columns = meta.getColumnCount();
                   //Arraylist of seatNames (Ex: FCA1)
                   ArrayList<String> seatNames = new ArrayList<>();
                   seatNames.add(meta.getColumnName(1));
                   seatNames.add(meta.getColumnName(2));
                   seatNames.add(meta.getColumnName(3));
                   seatNames.add(meta.getColumnName(4));
                   seatNames.add(meta.getColumnName(5));
                   seatNames.add(meta.getColumnName(6));
                   seatNames.add(meta.getColumnName(7));
                   seatNames.add(meta.getColumnName(8));
                   //ArrayList of seat values (Ex: 0=empty or 200 = resID
                   ArrayList<Integer> seat = new ArrayList<>();
                   seat.add(resSet.getInt(1));
                   seat.add(resSet.getInt(2));
                   seat.add(resSet.getInt(3));
                   seat.add(resSet.getInt(4));
                   seat.add(resSet.getInt(5));
                   seat.add(resSet.getInt(6));
                   seat.add(resSet.getInt(7));
                   seat.add(resSet.getInt(8));
                   
                   while(party > 0){
                       int count = 0;
                       boolean found = false;
                       while(!found){
                           if(seat.get(count) == null){
                                String insert = "INSERT INTO reservations" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 0, '" + flightCode + "', 450)" ;
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt.executeUpdate(insert);
                                    ResultSet resSet2 = stmt2.getGeneratedKeys();
                                    int resID = resSet.getInt(1);
                                    seat.set(count, resID);
                                    found = true;
                                    party--;
                                    System.out.println("Reservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
                                } // end try
                                catch (SQLException e) 
                                {
                                    System.out.println("Stored proc did not work");
                                }
                           }
                           count++;
                       }
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
    }


    

    // Cancels a reservation by reservation ID
    public static void cancelRes(Connection con, CallableStatement cState) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter the Reservation Number: ");
        int resID = scan.nextInt();
        String storedProc1 = "{call cancelRes('" + resID + "')}";
        String storedProc2 = "{call deleteRes('" + resID + "')}";
        
        try{
           cState = con.prepareCall(storedProc1);
           
           cState.executeQuery();
           
           cState = con.prepareCall(storedProc2);
           
           cState.executeQuery();
           
           System.out.println("Reservation canceled");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

    // prints a customers reservation according to the customer ID
    public static void printRes(Connection con, CallableStatement cState, ResultSet rSet) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the customer ID?");
        int custNum = scan.nextInt();
        
        String storedProc = "{call getCustomerRes(" + custNum + ")}";
        try{
           cState = con.prepareCall(storedProc);
           
           rSet = cState.executeQuery();
           
           try{
               ResultSetMetaData meta = rSet.getMetaData();
               int columns = meta.getColumnCount();
               System.out.println("Reservations found for this customer: ");
             
               while(rSet.next()){
                  
                   for(int i=1;i<columns+1;i++){
                       System.out.print(rSet.getString(i) + " ");
                   }
                   System.out.println("\n");
               }
           }
           catch(SQLException e){
               System.out.println("Something went wrong with the SQL");
           }
        }
        catch(Exception e){
            System.out.println("Something went wrong with the SQL");
        }
    }


    // prints out a pilots schedule for the week
    public static void printSchedule(Connection con, CallableStatement cState, ResultSet rSet) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the pilot's ID?");
        int pilotID = scan.nextInt();
        
        String storedProc = "{call getSchedule('" + pilotID + "')}";
        try{
           cState = con.prepareCall(storedProc);
           
           rSet = cState.executeQuery();
           
           try{
               ResultSetMetaData meta = rSet.getMetaData();
               int columns = meta.getColumnCount();
               System.out.println("Flights for this pilot: ");
             
               while(rSet.next()){
                  
                   for(int i=1;i<columns+1;i++){
                       System.out.print(rSet.getString(i) + " ");
                   }
                   System.out.println("\n");
               }
           }
           catch(SQLException e){
               System.out.println("Something went wrong with the SQL");
           }
        }
        catch(SQLException e){
            System.out.println("Something went wrong with the SQL");
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
        String storedProc = "{call " + procName + " ('" + flightCode + "')}";
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
    
    public static void searchCanceledRes(Connection con, CallableStatement cState, ResultSet rSet){
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to search for canceled reservations by reservation number (1) or customer name (2)?");
        System.out.println("Choice (1|2): ");
        int choice = scan.nextInt();
        boolean found = false;
        if(choice == 1){
            System.out.println("What is the reservation number?");
            int resNum = scan.nextInt();
            
            String storedProc = "{call getCanceledByID(" + resNum + ")}";
        try{
           cState = con.prepareCall(storedProc);
           
           rSet = cState.executeQuery();
           
           try{
               ResultSetMetaData meta = rSet.getMetaData();
               int columns = meta.getColumnCount();
               System.out.println("Canceled reservation for this ID: ");
             
               while(rSet.next()){
                  
                   for(int i=1;i<columns+1;i++){
                       System.out.print(rSet.getString(i) + " ");
                   }
                   System.out.println("\n");
               }
           }
           catch(SQLException e){
               System.out.println("Something went wrong with the SQL");
           }
        }
        catch(Exception e){
            System.out.println("Something went wrong with the SQL");
        }
         
            
        } else if(choice == 2){
            System.out.println("What is the customer's name?");
            scan.nextLine();
            String name = scan.nextLine();
         
            String storedProc = "{call getCanceledByName('" + name + "')}";
        try{
           cState = con.prepareCall(storedProc);
           
           rSet = cState.executeQuery();
           
           try{
               ResultSetMetaData meta = rSet.getMetaData();
               int columns = meta.getColumnCount();
               System.out.println("Canceled reservations for this customer: ");
             
               while(rSet.next()){
                  
                   for(int i=1;i<columns+1;i++){
                       System.out.print(rSet.getString(i) + " ");
                   }
                   System.out.println("\n");
               }
           }
           catch(SQLException e){
               System.out.println("Something went wrong with the SQL");
           }
        }
        catch(Exception e){
            System.out.println("Something went wrong with the SQL");
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
        String procName3 = "getSeatName";


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
                if (!resSet.isBeforeFirst() ) {    
                    System.out.println("There is no flight with that code"); 
                }
                else
                {
                    System.out.println("First Class:");
                }
                
                System.out.println();
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        String seat = "";
                        if(resSet.getString(i) == null)
                        {
                            seat = "Open";
                        }
                        else
                        {
                            String storedProc3 = "{call " + procName3 + " (" + Integer.parseInt(resSet.getString(i)) + ")}";
                            try{
                                CallableStatement callSt2 = connect.prepareCall(storedProc3);
                                ResultSet resSet2 = callSt2.executeQuery();
                                while(resSet2.next()){
                                    seat = resSet2.getString("customerName");
                                }
                            }
                            catch (SQLException e) 
                            {
                                  System.out.println("get SeatName did not work");
                            }
                        }
                        if(i == 3)
                        {
                            System.out.println();
                        }
                        System.out.printf("%-20s", seat);
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
                if (resSet.isBeforeFirst() ) {    
                    System.out.println("Economy Class:");
                }
                
                System.out.println();
                while (resSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        String seat = "";
                        if(resSet.getString(i) == null)
                        {
                            seat = "Open";
                        }
                        else
                        {
                            String storedProc3 = "{call " + procName3 + " (" + Integer.parseInt(resSet.getString(i)) + ")}";
                            try{
                                CallableStatement callSt2 = connect.prepareCall(storedProc3);
                                ResultSet resSet2 = callSt2.executeQuery();
                                while(resSet2.next()){
                                    seat = resSet2.getString("customerName");
                                }
                            }
                            catch (SQLException e) 
                            {
                                  System.out.println("get SeatName did not work");
                            }
                        }
                        if(i == 5)
                        {
                            System.out.println();
                        }
                        System.out.printf("%-20s", seat);
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
}
