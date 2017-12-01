/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluebirds.airline;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JOptionPane;

/**
 *
 * @author tdambp
 */
public class BluebirdsAirlineDriver {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
       // ArrayList<Customer> customerAL = new ArrayList<Customer>();

       // ArrayList<Flight> flightAL = new ArrayList<Flight>();

       // ArrayList<Pilot> pilotAL = new ArrayList<Pilot>();

       // ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
        
       // ArrayList<Reservation> canceledResAL = new ArrayList<Reservation>();
        
        Connection connect = null;
        CallableStatement callSt;
        ResultSet resSet = null;
        Statement stmt = null;
        connect = connect(connect);
        final Connection conn = connect;
        BlueBirdsJFrame newFrame = new BlueBirdsJFrame();
        newFrame.setScreenSize(newFrame);
        newFrame.setVisible(true);
        newFrame.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                
                ArrayList<Customer> customerAL = new ArrayList<Customer>();

                ArrayList<Flight> flightAL = new ArrayList<Flight>();

                ArrayList<Pilot> pilotAL = new ArrayList<Pilot>();

                ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
        
                ArrayList<Reservation> canceledResAL = new ArrayList<Reservation>();
                
                
                CallableStatement callSt = null;
                ResultSet resSet = null;
                Statement stmt = null;
                        
                int getOption = newFrame.getJComboBox1().getSelectedIndex();
                //if getOption = 0
                    // prime data
                //else if getOption = 1
                    //do this
                OptionExample nj = new OptionExample();
                nj.setScreenSize(nj);
                //nj.setVisible(true);

                if (getOption == 0)
                {
                    pilotAL = primePilots(conn, stmt, pilotAL);
                    flightAL = primeFlights(conn, stmt, pilotAL, flightAL);
                    customerAL = primeCustomers(conn, stmt, customerAL);
                    reservationAL = primeReservations(conn, stmt, reservationAL, flightAL, customerAL);
                    primeSeatMap(conn, stmt, flightAL);
                    
                    JOptionPane.showMessageDialog(null, "The data has been primed","Primed data",1);
                }
                else if (getOption == 1)
                {
                    BookReservation brFrame = new BookReservation();
                    brFrame.setVisible(true);
                    brFrame.getjButton1().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                    String flightDate = brFrame.getjTextField1().getText();
                    int route = brFrame.getjComboBox1().getSelectedIndex();
                    String flightRoute = "";
                    if(route == 0){
                        flightRoute = "RP";
                    } else {
                        flightRoute = "PR";
                    }
                    String flightTime = brFrame.getjTextField3().getText();
                    String flightCode = flightDate + flightRoute + flightTime;
                    int partySize = Integer.parseInt(brFrame.getjTextField4().getText()); 
                    int group = 2;
                    if(brFrame.getjCheckBox1().isSelected()){
                        group = 1;
                    }
                    String custIDInput = brFrame.getjTextField2().getText();
                    int custID = 0;
                    boolean validCust = false;
                    try{
                        custID = Integer.parseInt(custIDInput);
                        validCust = findCustomer(conn,custID);
                    } catch (NumberFormatException ne) {
                    }
                    if(!validCust){
                        createNewCustomer(conn, nj, flightCode, partySize, group);
                    }
                    if(validCust){
                    brFrame.dispose();
                        String flightResults = searchFlight(flightCode, partySize, custID, group, conn);
                        if(flightResults != ""){
                            nj.getJLabel1().setVisible(false);
                            nj.getJButton2().setVisible(false);
                            nj.getJTextField1().setVisible(false);
                            nj.setVisible(true);
                            nj.setJTextArea1(flightResults);
                        } 
                    }
                    
                }});
                }
                else if (getOption == 2)
                {
                    searchCustID(conn,newFrame);
                }
                else if (getOption == 3)
                {
                    nj.setVisible(true);
                    nj.setJLabel1("Enter the reservation ID to cancel.");
                    nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        String resID = nj.getJTextField1().getText();
                        cancelRes(conn, callSt, stmt, resSet, resID);
                        
                    }});

                }
                else if (getOption == 4)
                {
                    nj.setVisible(true);
                    nj.setJLabel1("Please Confirm: ");
                    nj.getJTextField1().setVisible(false);
                    nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        String flightCode = nj.getJTextField1().getText();                      
                        String results = grossIncomeEach(conn);
                        System.out.println(results);
                        nj.setJTextArea1(results);
                    }});
                }
                else if (getOption == 5)
                {
                    nj.setVisible(true);
                    nj.setJLabel1("Please Enter the flight code: ");
                    nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        String flightCode = nj.getJTextField1().getText();                      
                        String results = grossIncomeSpec(conn,flightCode);
                        nj.setJTextArea1(results);
                    }});
                }
                else if (getOption == 6)
                {
                    nj.setVisible(true);
                    nj.setJLabel1("Please Enter the pilot ID: ");
                    nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        String pilotID = nj.getJTextField1().getText();                      
                        String results = printSchedule(conn, pilotID);
                        nj.setJTextArea1(results);
                    }});
                    
                }
                else if (getOption == 7)
                {

                }
                else if (getOption == 8)
                {
                    searchReservID(conn, newFrame);
                }
                else if (getOption == 9)
                {

                }
                else if (getOption == 10)
                {
                    printFlightSeats(conn, newFrame);
                }
            }
        });
        /*while (true)
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
        		cancelRes(connect, callSt, stmt, resSet);
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
        }  */
    }
    
    /**
     *
     * @param connect
     * @return
     */
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
    
    /**
     *
     * @param connect
     * @param newFrame
     */
    public static void searchReservID(Connection connect, BlueBirdsJFrame newFrame )
    {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener( new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
                        
            }} );
        nj.setJLabel1("What is the reservation number?");
      
        //on click of search button
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent event) {
            int resNum = Integer.parseInt(nj.getJTextField1().getText());     
            //set font
            nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
            //String results = searchReservID(conn, resNum);
            // insert method here method here
            String results = "";

            String procName = "searchReservID";

            CallableStatement callSt;
            ResultSet resSet;

            String storedProc = "{call " + procName + " (" + resNum + ")}";

            try {
                callSt = connect.prepareCall(storedProc);
                resSet = callSt.executeQuery();

                try {
                   // System.out.println(" ");

                    ResultSetMetaData meta = resSet.getMetaData();

                    int columns = meta.getColumnCount();
                    if (!resSet.isBeforeFirst() ) {    
                        //System.out.println("There is no reservation with that id"); 
                        results = results + "There is no reservation with that id";
                    }
                    else
                    {
                        //System.out.println("We found that reservation:");
                        results = results + "We found that reservation:\n\n";
                    }
                    //System.out.println();
                    while (resSet.next()) {

                        for (int i = 1; i < columns + 1; i++) {
                            //System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                            results = results + String.format("%-20s", meta.getColumnLabel(i) + ":") ;
                            String col = "";
                            if(meta.getColumnLabel(i).equals("Seat Type"))
                            {
                                if(resSet.getString(i).equals("1")){
                                    col = "First Class";
                                }
                                else
                                {
                                    col = "Economy Class";
                                }

                            }
                            else
                            {
                                col = resSet.getString(i);
                            }

                            //System.out.printf("%-20s", col);
                            results = results +  String.format("%-20s", col) + "\n";
                           // System.out.println();
                        }

                    }
                } catch (SQLException e) {
                    //System.out.println("SQL Exception");
                    results = results + "SQL Exception";
                }

            } // end try
            catch (SQLException e) 
            {
                //System.out.println("stored proc did not work");
                results = results + "Stored procedure did not work";
            }
            //System.out.println();
            nj.setJTextArea1(results);
            
        }});

    }
    
    /**
     *
     * @param connect
     * @param newFrame
     */
    public static void searchCustID(Connection connect, BlueBirdsJFrame newFrame)
    {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener( new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();   
            }} );
        nj.setJLabel1("What is the customer ID?");
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                int custNum = Integer.parseInt(nj.getJTextField1().getText());
                        
                //set font
                nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
                //String results = searchCustID(conn, custID);
                //insert method here
                // Scanner scan = new Scanner(System.in);
                String procName = "searchCustID";
                //System.out.println("What is the customer ID?");
                //int custNum = scan.nextInt();
                String results = "";

                CallableStatement callSt;
                ResultSet resSet;
                String storedProc = "{call " + procName + " (" + custNum + ")}";

                try {
                    callSt = connect.prepareCall(storedProc);
                    resSet = callSt.executeQuery();

                    try {
                        //System.out.println(" ");

                        ResultSetMetaData meta = resSet.getMetaData();
                        int columns = meta.getColumnCount();
                        if (!resSet.isBeforeFirst() ) {    
                            //System.out.println("That customer does not exist");
                            results = results + "That customer does not exist";
                        }
                        else
                        {
                            //System.out.println("We found that customer:");
                            results = results + "We found that customer:\n\n";
                        }
                        //System.out.println();
                        while (resSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                //System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                                results = results + String.format("%-20s", meta.getColumnLabel(i) + ":");
                                //System.out.printf("%-20s", resSet.getString(i));
                                results = results + String.format("%-20s", resSet.getString(i)) + "\n";
                                //System.out.println();
                            }

                        }
                    } catch (SQLException e) {
                        //System.out.println("SQL Exception");
                        results = results + "SQL Exception";
                    }

                } // end try
                catch (SQLException e) 
                {
                    //System.out.println("stored proc did not work");
                    results = results + "Stored procedure did not work";
                }
                //System.out.println();
                nj.setJTextArea1(results);
                
            }});
    }

    /**
     *
     * @param con
     * @param stmt
     * @param pilots
     * @return
     */
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

    /**
     *
     * @param con
     * @param stmt
     * @param pilots
     * @param flights
     * @return
     */
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
    
    /**
     *
     * @param con
     * @param stmt
     * @param customers
     * @return
     */
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
    
    /**
     *
     * @param con
     * @param stmt
     * @param reservations
     * @param flights
     * @param customers
     * @return
     */
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
    
    /**
     *
     * @param connect
     * @param stmt
     * @param flights
     */
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
    
    /**
     *
     * @param con
     * @return
     */
    private static int custIDGlobal = 0;
    public static void createNewCustomer(Connection con, OptionExample nj, String flightCode, int partySize, int group)
    {
        BookReservation createCust = new BookReservation();
        createCust.getjCheckBox1().setVisible(false);
        createCust.getjComboBox1().setVisible(false);
        createCust.getjTextField2().setVisible(false);
        createCust.getjLabel6().setVisible(false);
        createCust.getjLabel5().setText("Create a New Customer");
        createCust.getjLabel1().setText("Name: ");
        createCust.getjLabel3().setText("Address: ");
        createCust.getjLabel4().setText("Phone Number: ");
        createCust.pack();
        createCust.getjButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                String name = createCust.getjTextField1().getText();
                String address = createCust.getjTextField3().getText();
                String phone = createCust.getjTextField4().getText();

                String insert = "INSERT INTO customers (customerName, address, phone)" + " VALUES ('" + name + "', '" + address + "', '" + phone + "')";
                int custID = 0;
                try {
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
                    ResultSet resSet = stmt.getGeneratedKeys();
                    resSet.next();
                    custIDGlobal = resSet.getInt(1);
                   } // end try
                catch (SQLException e) 
                {
                    System.out.println("Stored proc did not work");
                }

                createCust.setVisible(false);
                custID = custIDGlobal;
                custIDGlobal = 0;
                JOptionPane.showMessageDialog(null, "Customer has been created with custID of " + custID,"Created Customer",1);
                String flightResults = searchFlight(flightCode, partySize, custID, group, con);        
                if(flightResults != ""){
                    nj.getJLabel1().setVisible(false);
                    nj.getJButton2().setVisible(false);
                    nj.getJTextField1().setVisible(false);
                    nj.setVisible(true);
                    nj.setJTextArea1(flightResults);
                } 
            }});
        createCust.setVisible(true);
    }
    
    // Finds the customer for the reservation

    /**
     *
     * @param con
     * @return
     */
    public static boolean findCustomer(Connection con, int custID){
        Scanner scan = new Scanner(System.in);
        CallableStatement stmt;
        ResultSet resSet;
        
        String procName = "searchCustID";
        String storedProc = "{call " + procName + " (" + custID + ")}";
        try {
            stmt = con.prepareCall(storedProc);
            resSet = stmt.executeQuery();
            if(resSet.next()){
                String name = resSet.getString(2);
                System.out.println("Customer found with the name: " + name + "\n");
                return true;
            }
        } // end try
        catch (SQLException e) 
        {
            System.out.println("Stored proc did not work");
        }
                System.out.println("Customer not found. Create new customer please.");
                return false;    
    }



    

    /**
     *
     * @param flightCode
     * @param party
     * @param custID
     * @param group
     * @param con
     * @return
     */
    // Searches for a flight based on the customers parameters
    public static String searchFlight(String flightCode, int party, int custID, int group, Connection con) {
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
               resSet.next();
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
        if (party <= fClass || party <= economy) {
            int bookClass = 2;
            if (party <= fClass) {
                boolean valid = true;
                if((party < 3 || group == 2) && party <= fClass){

                    int reply = JOptionPane.showConfirmDialog(null, "There are first class seats available. Would you like first class?", "First Class Select", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        bookClass = 1;
                    }
                    else {
                        bookClass = 2;
                    }
                
                }
            } else {
                JOptionPane.showMessageDialog(null, "There are no first class seats available","First Class",1);
            }
           
            if (group == 1) {
                return bookTogether(flightCode, bookClass, custID, party, con);
            } else {
                return bookReservation(flightCode, bookClass, custID, party, con);
            }
        } else {
            int reply = JOptionPane.showConfirmDialog(null, "There are not enough available seats on this flight.", "Not Enough Seats", JOptionPane.OK_OPTION);
            return null;
        }
    }

    // Books a reservation for parties that want to sit togeather

    /**
     *
     * @param flightCode
     * @param fc
     * @param custID
     * @param party
     * @param con
     * @return
     */
    public static String bookTogether(String flightCode, int fc, int custID, int party, Connection con) {
        Scanner scan = new Scanner(System.in);
        String results = "\n";
        boolean booked = false;
        int emptySeats = 0;
        if (fc == 1) {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getFirstClassSeats";
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            System.out.println("\n");
            try {
                stmt = con.prepareCall(storedProc);
                resSet = stmt.executeQuery();

                try {
                    System.out.println();

                    ResultSetMetaData meta = resSet.getMetaData();
                    //Arraylist of seatNames (Ex: FCA1)
                    ArrayList<String> seatNames = new ArrayList<>();
                    seatNames.add(meta.getColumnName(1));
                    seatNames.add(meta.getColumnName(2));
                    seatNames.add(meta.getColumnName(3));
                    seatNames.add(meta.getColumnName(4));
                    //ArrayList of seat values (Ex: 0=empty or 200 = resID
                    ArrayList<Integer> seat = new ArrayList<>();
                    resSet.next();
                    seat.add(resSet.getInt(1));
                    seat.add(resSet.getInt(2));
                    seat.add(resSet.getInt(3));
                    seat.add(resSet.getInt(4));
                    int startCount;
                    if(seat.get(0) == 0 && seat.get(1) == 0){
                        startCount = 0;
                    } else {
                        startCount = 2;
                    }
                    if((seat.get(0) == 0 && seat.get(1) == 0) || (seat.get(2) == 0 && seat.get(3) == 0)){
                        booked = true;
                        while(party > 0){
                            int count = startCount;
                            boolean found = false;
                            while(!found){
                                if(seat.get(count) == 0){
                                     String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)" ;
                                     try {
                                        Statement stmt2 = con.createStatement();
                                        stmt2.executeUpdate(insert,Statement.RETURN_GENERATED_KEYS);
                                        ResultSet resSet2 = stmt2.getGeneratedKeys();
                                        resSet2.next();
                                        int resID = resSet2.getInt(1);
                                        seat.set(count, resID);
                                        String updateSeatMap = "UPDATE seatmap SET " + seatNames.get(count) + " = " + resID + " WHERE flightCode = '" + flightCode + "'";
                                        Statement stmt3 = con.createStatement();
                                        stmt3.executeUpdate(updateSeatMap);
                                        found = true;
                                        party--;
                                        results = results + ("\nReservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
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
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            System.out.println("\n");
            try {
               stmt = con.prepareCall(storedProc);
               resSet = stmt.executeQuery();

                try {
                    System.out.println();

                    ResultSetMetaData meta = resSet.getMetaData();
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
                    resSet.next();
                    seat.add(resSet.getInt(1));
                    seat.add(resSet.getInt(2));
                    seat.add(resSet.getInt(3));
                    seat.add(resSet.getInt(4));
                    seat.add(resSet.getInt(5));
                    seat.add(resSet.getInt(6));
                    seat.add(resSet.getInt(7));
                    seat.add(resSet.getInt(8));
                    int econRowA = 0;
                    for(int i = 0; i <4;i++){
                        if(seat.get(i) == 0) econRowA++;
                    }
                    int econRowB = 0;
                    for(int i = 4; i <8;i++){
                        if(seat.get(i) == 0) econRowB++;
                    }
                    int startCount;
                    if(party <= econRowA){
                        startCount = 0;
                    } else if (party <= econRowB){
                        startCount = 4;
                    } else {
                        startCount = -1;
                    }
                    if(startCount >=0){
                        booked = true;
                        while(party > 0){
                            int count = startCount;
                            boolean found = false;
                            while(!found){
                                if(seat.get(count) == 0){
                                     String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)" ;
                                     try {
                                        Statement stmt2 = con.createStatement();
                                        stmt2.executeUpdate(insert,Statement.RETURN_GENERATED_KEYS);
                                        ResultSet resSet2 = stmt2.getGeneratedKeys();
                                        resSet2.next();
                                        int resID = resSet2.getInt(1);
                                        seat.set(count, resID);
                                        String updateSeatMap = "UPDATE seatmap SET " + seatNames.get(count) + " = " + resID + " WHERE flightCode = '" + flightCode + "'";
                                        Statement stmt3 = con.createStatement();
                                        stmt3.executeUpdate(updateSeatMap);
                                        found = true;
                                        party--;
                                        results = results + ("\nReservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
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
            int reply = JOptionPane.showConfirmDialog(null, "Sorry, your party will not be able to sit together on this flight  Would you like to book anyways?", "Book Separate", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        return bookReservation(flightCode, fc, custID, party, con);
                    }
                    else {
                        JOptionPane.showConfirmDialog(null, "There are not enough available seats on this flight.", "Not Enough Seats", JOptionPane.OK_OPTION);
                    return null;
                    }

            } else return results;
        
        }


    //Books a reservation

    /**
     *
     * @param flightCode
     * @param fc
     * @param custID
     * @param party
     * @param con
     * @return
     */
    public static String bookReservation(String flightCode, int fc, int custID, int party, Connection con) {
        String results = "\n";
        // adds a first class reservation
        if (fc == 1) {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getFirstClassSeats";
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            System.out.println("\n");
            try {
               stmt = con.prepareCall(storedProc);
               resSet = stmt.executeQuery();

               try {
                   ResultSetMetaData meta = resSet.getMetaData();
                   //Arraylist of seatNames (Ex: FCA1)
                   ArrayList<String> seatNames = new ArrayList<>();
                   seatNames.add(meta.getColumnName(1));
                   seatNames.add(meta.getColumnName(2));
                   seatNames.add(meta.getColumnName(3));
                   seatNames.add(meta.getColumnName(4));
                   //ArrayList of seat values (Ex: 0=empty or 200 = resID
                   ArrayList<Integer> seat = new ArrayList<>();
                   resSet.next();
                   seat.add(resSet.getInt(1));
                   seat.add(resSet.getInt(2));
                   seat.add(resSet.getInt(3));
                   seat.add(resSet.getInt(4));
                   while(party > 0){
                       int count = 0;
                       boolean found = false;
                       while(!found){
                           if(seat.get(count) == 0){
                                String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)" ;
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt2.executeUpdate(insert,Statement.RETURN_GENERATED_KEYS);
                                    ResultSet resSet2 = stmt2.getGeneratedKeys();
                                    resSet2.next();
                                    int resID = resSet2.getInt(1);
                                    seat.set(count, resID);
                                    String updateSeatMap = "UPDATE seatmap SET " + seatNames.get(count) + " = " + resID + " WHERE flightCode = '" + flightCode + "'";
                                    Statement stmt3 = con.createStatement();
                                    stmt3.executeUpdate(updateSeatMap);
                                    found = true;
                                    party--;
                                    results = results + ("\nReservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
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
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            System.out.println("\n");
            try {
               stmt = con.prepareCall(storedProc);
               resSet = stmt.executeQuery();

               try {
                   System.out.println();

                   ResultSetMetaData meta = resSet.getMetaData();
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
                   resSet.next();
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
                       while(!found  && count < 8){
                           if(seat.get(count) == 0){
                                String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 0, '" + flightCode + "', 450)" ;
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt2.executeUpdate(insert,Statement.RETURN_GENERATED_KEYS);
                                    ResultSet resSet2 = stmt2.getGeneratedKeys();
                                    resSet2.next();
                                    int resID = resSet2.getInt(1);
                                    seat.set(count, resID);
                                    String updateSeatMap = "UPDATE seatmap SET " + seatNames.get(count) + " = " + resID + " WHERE flightCode = '" + flightCode + "'";
                                    Statement stmt3 = con.createStatement();
                                    stmt3.executeUpdate(updateSeatMap);
                                    found = true;
                                    party--;
                                    results = results + ("\nReservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
                                    System.out.println("Reservation " + resID + " created with seat number " + seatNames.get(count) + " on flight " + flightCode + ".");
                                } // end try
                                catch (SQLException e) 
                                {
                                    System.out.println("Stored proc did not work");
                                }
                           }
                           count++;
                           if(count > 7 && !found){
                               System.out.println("Sorry there is not room on this flight for your party.");
                               JOptionPane.showMessageDialog(null, "Sorry there is not room on this flight for your party..","Seat Availablility",1);
                               party = 0;
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
        return results;
    }


    

    // Cancels a reservation by reservation ID

    /**
     *
     * @param con
     * @param cState
     * @param stmt
     * @param rSet
     * @param resID
     */
    public static void cancelRes(Connection con, CallableStatement cState, Statement stmt, ResultSet rSet, String resID) {
        //Scanner scan = new Scanner(System.in);
       // System.out.println("Please Enter the Reservation Number: ");
        //int resID = scan.nextInt();
        
        String selectStmt = "SELECT seatNumber, flightCode "
                          + "FROM reservations "
                          + "WHERE resID = " + resID + ";";
        
        String storedProc1 = "{call cancelRes('" + resID + "')}";
        String storedProc2 = "{call deleteRes('" + resID + "')}";
        String seatNum = "";
        String flightCode = "";
        
        try{
                cState = con.prepareCall(selectStmt);
           
                rSet = cState.executeQuery();
               ResultSetMetaData meta = rSet.getMetaData();
               int columns = meta.getColumnCount();
                if (!rSet.isBeforeFirst() ) {    
                    JOptionPane.showMessageDialog(null, "There are no reservations for that number","Invalid Reservation ID",1); 
                }
                else{
                    while (rSet.next()) {

                         for (int i = 1; i < columns + 1; i++) {
                             if(meta.getColumnLabel(i).equals("seatNumber"))
                             {
                                seatNum = rSet.getString(i);
                             }
                             else if (meta.getColumnLabel(i).equals("flightCode"))
                             {
                                 flightCode = rSet.getString(i);
                             }
                         }
                     }
                

             try{
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE seatmap SET " + seatNum + " = NULL  "
                + "WHERE flightCode = '" + flightCode + "';");
                cState = con.prepareCall(storedProc1);

                cState.executeQuery();


                
                     cState = con.prepareCall(storedProc2);
                     cState.executeQuery();
                    // System.out.println("Reservation canceled");
                    JOptionPane.showMessageDialog(null, "The reservation has been canceled","Reservation Canceled",1);
                

            }
            catch(Exception e){
                e.printStackTrace();
            }
                }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
    }

    // prints a customers reservation according to the customer ID

    /**
     *
     * @param con
     * @param cState
     * @param rSet
     */
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
                if (!rSet.isBeforeFirst() ) {    
                    System.out.println("There are no reservations for that customer"); 
                }
                else
                {
                    System.out.println("Reservations found for this customer: ");
                }
               
               while (rSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                        String col = "";
                        if(meta.getColumnLabel(i).equals("Seat Type"))
                        {
                            if(rSet.getString(i).equals("1")){
                                col = "First Class";
                            }
                            else
                            {
                                col = "Economy Class";
                            }
                               
                        }
                        else
                        {
                            col = rSet.getString(i);
                        }
                        
                        System.out.printf("%-20s", col);
                        System.out.println();
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

    /**
     *
     * @param con
     * @param pilotID
     * @return
     */
    public static String printSchedule(Connection con, String pilotID) {
       // Scanner scan = new Scanner(System.in);
        //System.out.println("What is the pilot's ID?");
        //int pilotID = scan.nextInt();
        CallableStatement cState;
        ResultSet rSet;
        String storedProc = "{call getSchedule('" + pilotID + "')}";
        try{
           cState = con.prepareCall(storedProc);
           
           rSet = cState.executeQuery();
           
           try{
               ResultSetMetaData meta = rSet.getMetaData();
               int columns = meta.getColumnCount();
                if (!rSet.isBeforeFirst() ) {    
                    System.out.println("There are no flights with that pilot"); 
                }
                else
                {
                    System.out.println("Flights for this pilot: ");
                }
             String result = "Upcoming flights for this pilot: \n\n";
               while(rSet.next()){
                  
                   for(int i=1;i<columns+1;i++){
                      result += rSet.getString(i) + " ";
                   }
                   result += "\n";
               }
               return result;
           }
           catch(SQLException e){
               return "Something went wrong with the SQL";
           }
        }
        catch(SQLException e){
            return "Something went wrong with the SQL";
        }
        
    }
    
    /**
     *
     * @return
     */
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
    
    /**
     *
     * @param con
     * @return
     */
    public static String grossIncomeEach(Connection con){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        CallableStatement stmt;
        ResultSet resSet;
        String procName = "GrossIncomeEach";
        String storedProc = "{call " + procName +"}";
        //System.out.println("\n");
        String results = "\n";
        try {
           stmt = con.prepareCall(storedProc);
           resSet = stmt.executeQuery();

           try {
               //System.out.println();
               while (resSet.next()) {
                   String flightCode = resSet.getString(1);
                   int grossIncome = resSet.getInt(2);
                   results = results + ("\nFlight Code: "+ flightCode + "  Gross Income: " + nf.format(grossIncome));
                   //System.out.println("Flight Code: "+ flightCode + "  Gross Income: " + nf.format(grossIncome));
               }
           } catch (SQLException e) {
               //System.out.println("SQL Exception");
           }

       } // end try
       catch (SQLException e) 
       {
           //System.out.println("Stored proc did not work");
       }
        return results;
            
            
    }
    
    /**
     *
     * @param con
     * @param flightCode
     * @return
     */
    public static String grossIncomeSpec(Connection con, String flightCode){
        String results = "\n";
        NumberFormat nf = NumberFormat.getCurrencyInstance();
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
                   results = results + ("Flight Code: "+ flightCode + "  Gross Income: " + nf.format(grossIncome));
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
        return results;
            
            
    }
    
    /**
     *
     * @param con
     * @param cState
     * @param rSet
     */
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
                if (!rSet.isBeforeFirst() ) {    
                    System.out.println("There are no canceled reservations that have that number"); 
                }
                else
                {
                    System.out.println("Canceled reservation for this ID: ");
                }
               
               while (rSet.next()) {

                    for (int i = 1; i < columns + 1; i++) {
                        System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                        String col = "";
                        if(meta.getColumnLabel(i).equals("Seat Type"))
                        {
                            if(rSet.getString(i).equals("1")){
                                col = "First Class";
                            }
                            else
                            {
                                col = "Economy Class";
                            }
                               
                        }
                        else
                        {
                            col = rSet.getString(i);
                        }
                        
                        System.out.printf("%-20s", col);
                        System.out.println();
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
                if (!rSet.isBeforeFirst() ) {    
                    System.out.println("There are no canceled reservations with that name."); 
                }
                else
                {
                    System.out.println("Canceled reservations for this customer: ");
                }
               
               while(rSet.next()){
                  
                   for(int i=1;i<columns+1;i++){
                        System.out.printf("%-20s", meta.getColumnLabel(i) + ": ");
                        String col = "";
                        if(meta.getColumnLabel(i).equals("Seat Type"))
                        {
                            if(rSet.getString(i).equals("1")){
                                col = "First Class";
                            }
                            else
                            {
                                col = "Economy Class";
                            }
                               
                        }
                        else
                        {
                            col = rSet.getString(i);
                        }
                        
                        System.out.printf("%-20s", col);
                        System.out.println();
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
    
    /**
     *
     * @param connect
     * @param newFrame
     */
    public static void printFlightSeats(Connection connect, BlueBirdsJFrame newFrame)
    {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener( new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }} );
        nj.setJLabel1("Please Enter the flight code: ");
        
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent event) {
            String flightCode = nj.getJTextField1().getText();
            //String results = printFlightSeats(conn,flightCode);
            //insert method here
            String results = "";
            //Scanner scan = new Scanner(System.in);
            CallableStatement callSt; 
            ResultSet resSet;

            // System.out.println("Please Enter the flight code: ");
            //String flightCode = scan.nextLine().trim();

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
                    //System.out.println(" ");

                    ResultSetMetaData meta = resSet.getMetaData();
                    int columns = meta.getColumnCount();
                    if (!resSet.isBeforeFirst() ) {    
                       // System.out.println("There is no flight with that code"); 
                       results = results + "There is no flight with that code";
                    }
                    else
                    {
                        //System.out.println("First Class:");
                        results = results + "First Class:\n";
                    }

                    //System.out.println();
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
                                      //System.out.println("get SeatName did not work");
                                    results = results + "Get seatName did not work";
                                }
                            }
                            if(i == 3)
                            {
                                //System.out.println();
                                results = results + "\n";
                            }
                            //System.out.printf("%-20s", seat);

                            results = results + String.format("%-20s", seat);
                           // System.out.println(results);

                        }

                    }
                } catch (SQLException e) {
                    //System.out.println("SQL Exception");
                    results = results + "SQL Exception";
                }

            } // end try
            catch (SQLException e) 
            {
                //System.out.println("stored proc did not work");
                results = results + "Stored procedure did not work";
            }
            //System.out.println();
            //System.out.println(results);
            results = results + "\n\n";
            //Print Economy Class
            try {
                callSt = connect.prepareCall(storedProc2);
                resSet = callSt.executeQuery();

                try {
                    //System.out.println(" ");

                    ResultSetMetaData meta = resSet.getMetaData();
                    int columns = meta.getColumnCount();
                    if (resSet.isBeforeFirst() ) {    
                        //System.out.println("Economy Class:");
                        results = results + "Economy Class:\n";
                    }

                   // System.out.println();
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
                                      //System.out.println("get SeatName did not work");
                                    results = results + "Get seatName did not work";
                                }
                            }
                            if(i == 5)
                            {
                                //System.out.println();
                                results = results + "\n";
                            }
                            //System.out.printf("%-20s", seat);
                            //results = results + seat + "  ";
                            results = results + String.format("%-20s", seat);
                        }

                        //System.out.println();
                    }
                } catch (SQLException e) {
                   // System.out.println("SQL Exception");
                   results = results + "SQL Exception";
                }

            } // end try
            catch (SQLException e) 
            {
                //System.out.println("stored proc did not work");
                results = results + "Stored procedure did not work";
            }
            //System.out.println();       
            //set font
            nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
            nj.setJTextArea1(results);
            //System.out.println(nj.getJTextArea1().getFont());
                        
        }});

    }
}
