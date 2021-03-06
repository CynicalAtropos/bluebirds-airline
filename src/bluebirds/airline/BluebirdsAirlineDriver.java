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
 * BlueBirds Airline Simulator
 *
 * @author Matthew Sluder
 * @author Josh Whitt
 * @author Timothy Alligood
 */
public class BluebirdsAirlineDriver {

    /**
     * Runs the program
     * @param args 
     */
    public static void main(String[] args) {
        Connection connect = null;
        final Connection conn = connect(connect);
        BlueBirdsJFrame newFrame = new BlueBirdsJFrame();
        newFrame.setScreenSize(newFrame);
        newFrame.setVisible(true);
        newFrame.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {

                int getOption = newFrame.getJComboBox1().getSelectedIndex();

                if (getOption == 0) {
                    
                    primeData(conn);
                    
                } else if (getOption == 1) {
                    
                    selectFlightOldCust(conn, newFrame);
                    
                } else if (getOption == 2) {
                    
                    searchCustID(conn, newFrame);
                    
                } else if (getOption == 3) {
                    
                    cancelRes(conn, newFrame);
                    
                } else if (getOption == 4) {
                    
                    grossIncomeEach(conn, newFrame);
                    
                } else if (getOption == 5) {
                    
                    grossIncomeSpec(conn, newFrame);
                    
                } else if (getOption == 6) {
                    
                    printSchedule(conn, newFrame);
                    
                } else if (getOption == 7) {
                    
                    printRes(conn, newFrame);
                    
                } else if (getOption == 8) {
                    
                    searchReservID(conn, newFrame);
                    
                } else if (getOption == 9) {
                    
                    searchCanceledRes(conn, newFrame);
                    
                } else if (getOption == 10) {
                    
                    printFlightSeats(conn, newFrame);
                    
                }
            }
        });
    }

    /**
     * Creates the connection to the bluebirds database
     *
     * @param connect  the connection to database
     * @return  the connection to database
     */
    public static Connection connect(Connection connect) {

        String uid = "itp220";
        String pass = "itp220";

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/BlueBirdsAirline";

        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            connect = DriverManager.getConnection(url, uid, pass);
            System.out.println("Connection successful!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    /**
     * Searches for a reservation by resId
     *
     * @param connect  the connection to database
     * @param newFrame the initial frame
     */
    public static void searchReservID(Connection connect, BlueBirdsJFrame newFrame) {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();

            }
        });
        nj.setJLabel1("What is the reservation number?");

        //on click of search button
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                int resNum = Integer.parseInt(nj.getJTextField1().getText());
                //set font
                nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));

                String results = "";

                String procName = "searchReservID";

                CallableStatement callSt;
                ResultSet resSet;

                String storedProc = "{call " + procName + " (" + resNum + ")}";

                try {
                    callSt = connect.prepareCall(storedProc);
                    resSet = callSt.executeQuery();

                    try {
                        ResultSetMetaData meta = resSet.getMetaData();

                        int columns = meta.getColumnCount();
                        if (!resSet.isBeforeFirst()) {
                            results = results + "There is no reservation with that id";
                        } else {
                            results = results + "We found that reservation:\n\n";
                        }

                        while (resSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                results = results + String.format("%-20s", meta.getColumnLabel(i) + ":");
                                String col = "";
                                if (meta.getColumnLabel(i).equals("Seat Type")) {
                                    if (resSet.getString(i).equals("1")) {
                                        col = "First Class";
                                    } else {
                                        col = "Economy Class";
                                    }

                                } else {
                                    col = resSet.getString(i);
                                }
                                results = results + String.format("%-20s", col) + "\n";

                            }

                        }
                    } catch (SQLException e) {
                        results = results + "SQL Exception";
                    }

                } // end try
                catch (SQLException e) {
                    results = results + "Stored procedure did not work";
                }

                nj.setJTextArea1(results);

            }
        });

    }

    /**
     * Searches for a customer by customerId
     *
     * @param connect  the connection to database
     * @param newFrame the initial frame
     */
    public static void searchCustID(Connection connect, BlueBirdsJFrame newFrame) {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
        nj.setJLabel1("What is the customer ID?");
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                int custNum = Integer.parseInt(nj.getJTextField1().getText());

                //set font
                nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));

                String procName = "searchCustID";

                String results = "";

                CallableStatement callSt;
                ResultSet resSet;
                String storedProc = "{call " + procName + " (" + custNum + ")}";

                try {
                    callSt = connect.prepareCall(storedProc);
                    resSet = callSt.executeQuery();

                    try {
                        ResultSetMetaData meta = resSet.getMetaData();
                        int columns = meta.getColumnCount();
                        if (!resSet.isBeforeFirst()) {
                            results = results + "That customer does not exist";
                        } else {
                            results = results + "We found that customer:\n\n";
                        }

                        while (resSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                results = results + String.format("%-20s", meta.getColumnLabel(i) + ":");

                                results = results + String.format("%-20s", resSet.getString(i)) + "\n";

                            }

                        }
                    } catch (SQLException e) {
                        results = results + "SQL Exception";
                    }

                } // end try
                catch (SQLException e) {
                    results = results + "Stored procedure did not work";
                }

                nj.setJTextArea1(results);

            }
        });
    }

    /**
     * Inserts sample data into the database tables
     * @param con the connection to the database
     */
    public static void primeData(Connection con) {
        ArrayList<Customer> customers = new ArrayList<Customer>();

        ArrayList<Flight> flights = new ArrayList<Flight>();

        ArrayList<Pilot> pilots = new ArrayList<Pilot>();

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        Statement stmt;
        boolean sqlError = false;
        
        pilots.add(new Pilot("Chesley Sullenberger", "2801 Franklin Rd SW, Roanoke, VA 24014", "5403454434"));
        pilots.add(new Pilot("Amelia Earhart", "15240 N 32nd St, Phoenix, AZ 85032", "6024937404"));
        pilots.add(new Pilot("Han Solo", "3226 Brandon Ave SW, Roanoke, VA 24018", "5403448200"));
        pilots.add(new Pilot("Orville Wright", "1919 W Deer Valley Rd, Phoenix, AZ 85027", "6237802330"));

        try {
            stmt = con.createStatement();
            for (Pilot p : pilots) {
                stmt.executeUpdate("INSERT INTO pilots"
                        + " VALUES (" + p.getPilotId() + ", '"
                        + p.getName() + "', '"
                        + p.getAddress() + "', '"
                        + p.getPhoneNumber() + "')");
            }
        } catch (SQLException e) {
            sqlError = true;
        }

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

        try {
            stmt = con.createStatement();
            for (Flight f : flights) {
                stmt.executeUpdate("INSERT INTO flights"
                        + " VALUES ('" + f.getFlightCode() + "', '"
                        + f.getDate().toString() + "', '"
                        + f.getTime() + "', '"
                        + f.getRoute() + "', '"
                        + f.getPilot().getPilotId() + "')");
            }
        } catch (SQLException e) {
            sqlError = true;
        }

        customers.add(new Customer("Rick Sanchez", "2072 Apperson Dr, Salem, VA 24153", "5407746295"));
        customers.add(new Customer("Daryl Dixon", "21611 N 26th Ave, Phoenix, AZ 85027", "6235826020"));
        customers.add(new Customer("Merle Dixon", "3202 E Greenway Rd, Phoenix, AZ 85032", "6024851000"));

        try {
            stmt = con.createStatement();
            for (Customer c : customers) {
                stmt.executeUpdate("INSERT INTO customers"
                        + " VALUES (" + c.getCustomerId() + ", '"
                        + c.getName() + "', '"
                        + c.getAddress() + "', '"
                        + c.getPhoneNumber() + "')");
            }
        } catch (SQLException e) {
            sqlError = true;
        }

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
        try {
            stmt = con.createStatement();
            for (Reservation r : reservations) {
                if (r.getFirstClass()) {
                    fc = 1;
                } else {
                    fc = 0;
                }
                stmt.executeUpdate("INSERT INTO reservations"
                        + " VALUES (" + r.getReservationNum() + ", '"
                        + r.getCustomer().getCustomerId() + "', '"
                        + r.getSeatNumber() + "', '"
                        + fc + "', '"
                        + r.getFlight().getFlightCode() + "', '"
                        + r.getCost() + "')");
            }
        } catch (SQLException e) {
            sqlError = true;
        }

        try {
            stmt = con.createStatement();
            for (Flight f : flights) {
                stmt.executeUpdate("INSERT INTO seatmap(flightCode)"
                        + " VALUES ('" + f.getFlightCode() + "')");
            }
        } catch (SQLException e) {
            sqlError = true;
        }

        try {
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE seatmap\n"
                    + "SET \n"
                    + "FCA1 = " + flights.get(0).getFirstClass()[0][0].getReservationNum() + ",\n"
                    + "FCA2 = " + flights.get(0).getFirstClass()[0][1].getReservationNum() + ",\n"
                    + "ECA1 = " + flights.get(0).getEconomyClass()[0][0].getReservationNum() + ",\n"
                    + "ECA2 = " + flights.get(0).getEconomyClass()[0][1].getReservationNum() + "\n"
                    + "WHERE flightCode = '12RPAM'");

        } catch (SQLException e) {
            sqlError = true;
        }
        if (sqlError)
            JOptionPane.showMessageDialog(null, "Couldn't prime the data due to an error", "Error", 2);
        else
            JOptionPane.showMessageDialog(null, "The data has been primed", "Primed data", 1);
    }

    /**
     * Selects a flight from given data and creates a new customer
     *
     * @param con  the connection to database
     * @param nj the results frame
     * @param flightCode the flight code
     * @param partySize the size of the party
     * @param group an integer specifying whether or not the party must be grouped together (1 = true, 2 = false)
     * 
     */
    public static void selectFlightNewCust(Connection con, OptionExample nj, String flightCode, int partySize, int group) {
        BookReservation createCust = new BookReservation();
        createCust.getjCheckBox1().setVisible(false);
        createCust.getjComboBox1().setVisible(false);
        createCust.getjTextField2().setVisible(false);
        createCust.getjLabel2().setVisible(false);
        createCust.getjLabel6().setVisible(false);
        createCust.getjLabel5().setText("Create a New Customer");
        createCust.getjLabel1().setText("Name: ");
        createCust.getjLabel3().setText("Address: ");
        createCust.getjLabel4().setText("Phone Number: ");
        createCust.getjButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                String name = createCust.getjTextField1().getText();
                String address = createCust.getjTextField3().getText();
                String phone = createCust.getjTextField4().getText();
                String flightResults ="";
                String insert = "INSERT INTO customers (customerName, address, phone)" + " VALUES ('" + name + "', '" + address + "', '" + phone + "')";
                int custID = 0;
                try {
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
                    ResultSet resSet = stmt.getGeneratedKeys();
                    resSet.next();
                    custID = resSet.getInt(1);
                } // end try
                catch (SQLException e) {
                    
                    flightResults = flightResults + "\nStored proc did not work";
                }

                createCust.setVisible(false);
                
                flightResults = searchFlight(flightCode, partySize, custID, group, con);        
                String custAlert = "Reservations for CustomerID " + custID + ":\n";
                nj.getJLabel1().setVisible(false);
                nj.getJButton2().setVisible(false);
                nj.getJTextField1().setVisible(false);
                if(flightResults ==(null) || flightResults.isEmpty()) {
                    flightResults = "No reservations were made.";
                } 
                nj.setJTextArea1(custAlert + flightResults);

                nj.setVisible(true);
            }
        });
        createCust.setScreenSize(createCust);
        createCust.setVisible(true);
    }

    /**
     * Determines if a inputted custID is valid
     * @param con  the connection to database
     * @param custID the customer ID number
     * @return true if valid custID, false if invalid custID
     */
    public static boolean validateCustomer(Connection con, int custID) {
        
        CallableStatement stmt;
        ResultSet resSet;

        String procName = "searchCustID";
        String storedProc = "{call " + procName + " (" + custID + ")}";
        try {
            stmt = con.prepareCall(storedProc);
            resSet = stmt.executeQuery();
            if (resSet.next()) {
                String name = resSet.getString(2);
                
                return true;
            }
        } // end try
        catch (SQLException e) {
            System.out.println("Stored proc did not work");
        }
        
        return false;
    }

    /**
     * Searches for a flight based on the customers parameters
     * @param flightCode the flight code
     * @param party the size of the party
     * @param custID the customer id number
     * @param group an integer specifying whether or not the party must be grouped together (1 = true, 2 = false)
     * @param con  the connection to database
     * @return a string with all reservations successfully made
     */
    public static String searchFlight(String flightCode, int party, int custID, int group, Connection con) {
        

        // searches for a flight matching the customers parameters
        int fClass = 0;
        int economy = 0;
        String sqlError = "";
        CallableStatement stmt;
        ResultSet resSet;
        String procName = "findSeatAvailability";
        String storedProc = "{call " + procName + " ('" + flightCode + "')}";
        try {
            stmt = con.prepareCall(storedProc);
            resSet = stmt.executeQuery();

            try {
                resSet.next();
                
                fClass = resSet.getInt(1);
                economy = resSet.getInt(2);

            } catch (SQLException e) {
                
                sqlError = sqlError + "SQL Exception";
            }

        } // end try
        catch (SQLException e) {
            
            sqlError = sqlError + "Stored proc did not work";
        }
        if (party <= fClass || party <= economy) {
            int bookClass = 2;
            if (party <= fClass) {
                boolean valid = true;
                if ((party < 3 || group == 2) && party <= fClass) {

                    int reply = JOptionPane.showConfirmDialog(null, "There are first class seats available. Would you like first class?", "First Class Select", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        bookClass = 1;
                    } else {
                        bookClass = 2;
                    }

                } else {
                JOptionPane.showMessageDialog(null, "There are no first class seats available", "First Class", 1);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There are no first class seats available", "First Class", 1);
            }

            if (group == 1 && party > 1) {
                return sqlError + bookTogether(flightCode, bookClass, custID, party, con);
            } else {
                return sqlError + bookReservation(flightCode, bookClass, custID, party, con);
            }
        } else {
            JOptionPane.showMessageDialog(null, "There are not enough available seats on this flight.", "Not Enough Seats", 1);
            return sqlError;
        }
    }
    /**
     * Selects a flight as well as a customer if specified 
     *
     * @param conn  the connection to database
     * @param newFrame the initial frame
     */
    public static void selectFlightOldCust(Connection conn, BlueBirdsJFrame newFrame) {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
        BookReservation brFrame = new BookReservation();
        WindowAdapter windowClose = new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                brFrame.toFront();
            }
        };
        brFrame.addWindowListener(windowClose);
        brFrame.setScreenSize(brFrame);
        brFrame.setVisible(true);
        brFrame.getjButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
            brFrame.removeWindowListener(windowClose);
            String flightDate = brFrame.getjTextField1().getText();
            int route = brFrame.getjComboBox1().getSelectedIndex();
            String flightRoute = "";
            if(route == 0){
                flightRoute = "RP";
            } else {
                flightRoute = "PR";
            }
            String flightTime = brFrame.getjTextField3().getText();
            int partySize = 0;
            try{
                partySize = Integer.parseInt(brFrame.getjTextField4().getText());
            } catch (NumberFormatException ne){}
            int group = 2;
            if(brFrame.getjCheckBox1().isSelected()){
                group = 1;
            }
            String custIDInput = brFrame.getjTextField2().getText();
            boolean dataValid = true;
            if(dataValid){
                try{
                    int flightDateInt = Integer.parseInt(flightDate);
                    if(flightDateInt > 18 || flightDateInt < 12){
                        dataValid = false;
                        JOptionPane.showMessageDialog(null, "Flight Date Inputted Was Out of Range.  Please try again.","Invalid Flight Date",1);
                        brFrame.dispose();
                        
                        selectFlightOldCust(conn, newFrame);
                    }
                } catch(NumberFormatException ne){
                    JOptionPane.showMessageDialog(null, "Flight Date Inputted Was Invalid.  Please try again.","Invalid Flight Date",1);
                    dataValid = false;
                    brFrame.dispose();
                    
                    selectFlightOldCust(conn, newFrame);
                }
            }
            if(!dataValid){}
            else if(!(flightTime.equalsIgnoreCase("AM") || flightTime.equalsIgnoreCase("PM")) ){
                JOptionPane.showMessageDialog(null, "Flight Time Inputted Was Invalid.  Please try again.","Invalid Flight Time",1);
                dataValid = false;
                brFrame.dispose();
                
                selectFlightOldCust(conn, newFrame);
            } else if(partySize < 1){
                JOptionPane.showMessageDialog(null, "Party Size Inputted Must Be a Postive Integer.  Please try again.","Invalid Party Size",1);
                dataValid = false;
                brFrame.dispose();
                
                selectFlightOldCust(conn, newFrame);
            }
            if(dataValid){
                String flightCode = flightDate + flightRoute + flightTime.toUpperCase();
                int custID = 0;
                boolean validCust = false;
                try{
                    custID = Integer.parseInt(custIDInput);
                    validCust = validateCustomer(conn,custID);
                    if(!validCust) JOptionPane.showMessageDialog(null, "There is no existing customer with the inputted id. Please create a new customer.","Invalid CustomerID",1);
                } catch (NumberFormatException ne) {}
                if(!validCust){
                    brFrame.dispose();
                    selectFlightNewCust(conn, nj, flightCode, partySize, group);
                }else if(validCust){
                    brFrame.dispose();
                    String custAlert = "Reservations for CustomerID " + custID + ":\n";
                    String flightResults = searchFlight(flightCode, partySize, custID, group, conn);
                    nj.getJLabel1().setVisible(false);
                    nj.getJButton2().setVisible(false);
                    nj.getJTextField1().setVisible(false);
                    if(flightResults == (null) || flightResults.isEmpty()) {
                        flightResults = "No reservations were made.";
                    }
                    nj.setJTextArea1(custAlert + flightResults);
                
                    nj.setVisible(true);
                }
            }
            
        }});
    }

    /**
     * Books reservations for parties that want to sit together
     * @param flightCode the flight code
     * @param fc whether it is first class (0 = economy, 1 = first class)
     * @param custID the customer id number
     * @param party the size of the party
     * @param con the connection to database
     * @return a string with all reservations successfully made
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
                    int startCount;
                    if (seat.get(0) == 0 && seat.get(1) == 0) {
                        startCount = 0;
                    } else {
                        startCount = 2;
                    }
                    if ((seat.get(0) == 0 && seat.get(1) == 0) || (seat.get(2) == 0 && seat.get(3) == 0)) {
                        booked = true;
                        while (party > 0) {
                            int count = startCount;
                            boolean found = false;
                            while (!found) {
                                if (seat.get(count) == 0) {
                                    String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)";
                                    try {
                                        Statement stmt2 = con.createStatement();
                                        stmt2.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
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
                                        
                                    } // end try
                                    catch (SQLException e) {
                                        
                                        results = results + "Stored proc did not work";
                                    }
                                }
                                count++;
                            }
                        }
                    }

                } catch (SQLException e) {
                    
                    results = results + "SQL Exception";
                }

            } // end try
            catch (SQLException e) {
                
                results = results + "Stored proc did not work";
            }
        } else if (fc == 2) {
            // loops to get the desired flight
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getEconomySeats";
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            
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
                    for (int i = 0; i < 4; i++) {
                        if (seat.get(i) == 0) {
                            econRowA++;
                        }
                    }
                    int econRowB = 0;
                    for (int i = 4; i < 8; i++) {
                        if (seat.get(i) == 0) {
                            econRowB++;
                        }
                    }
                    int startCount;
                    if (party <= econRowA) {
                        startCount = 0;
                    } else if (party <= econRowB) {
                        startCount = 4;
                    } else {
                        startCount = -1;
                    }
                    if (startCount >= 0) {
                        booked = true;
                        while (party > 0) {
                            int count = startCount;
                            boolean found = false;
                            while (!found) {
                                if (seat.get(count) == 0) {
                                    String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)";
                                    try {
                                        Statement stmt2 = con.createStatement();
                                        stmt2.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
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
                                        
                                    } // end try
                                    catch (SQLException e) {
                                        
                                        results = results + "Stored proc did not work";
                                    }
                                }
                                count++;
                            }
                        }
                    }

                } catch (SQLException e) {
                    
                    results = results + "Stored proc did not work";
                }

            } // end try
            catch (SQLException e) {
                
                results = results + "Stored proc did not work";
            }
        }
        if (!booked) {
            int reply = JOptionPane.showConfirmDialog(null, "Sorry, your party will not be able to sit together on this flight  Would you like to book anyways?", "Book Separate", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                return bookReservation(flightCode, fc, custID, party, con);
            } else {
                JOptionPane.showMessageDialog(null, "There are not enough available seats on this flight.", "Not Enough Seats", 1);
                return "";
            }

        } else {
            return results;
        }

    }

    /**
     * Books reservations
     * @param flightCode the flight code
     * @param fc whether it is first class (0 = economy, 1 = first class)
     * @param custID the customer id number
     * @param party the size of the party
     * @param con the connection to database
     * @return a string with all reservations successfully made
     */
    public static String bookReservation(String flightCode, int fc, int custID, int party, Connection con) {
        String results = "\n";
        // adds a first class reservation
        if (fc == 1) {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getFirstClassSeats";
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            
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
                    while (party > 0) {
                        int count = 0;
                        boolean found = false;
                        while (!found) {
                            if (seat.get(count) == 0) {
                                String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 1, '" + flightCode + "', 850)";
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt2.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
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
                                    
                                } // end try
                                catch (SQLException e) {
                                    
                                    results = results + "Stored proc did not work";
                                }
                            }
                            count++;
                        }
                    }

                } catch (SQLException e) {
                    
                    results = results + "SQL Exception";
                }

            } // end try
            catch (SQLException e) {
                
                results = results + "Stored proc did not work";
            }

        } // adds an economy reservation
        else {
            CallableStatement stmt;
            ResultSet resSet;
            String procName = "getEconomySeats";
            String storedProc = "{call " + procName + " ('" + flightCode + "')}";
            
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

                    while (party > 0) {
                        int count = 0;
                        boolean found = false;
                        while (!found && count < 8) {
                            if (seat.get(count) == 0) {
                                String insert = "INSERT INTO reservations (custID,seatNumber,firstClass,flightCode,cost)" + " VALUES (" + custID + ", '" + seatNames.get(count) + "', 0, '" + flightCode + "', 450)";
                                try {
                                    Statement stmt2 = con.createStatement();
                                    stmt2.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
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
                                    
                                } // end try
                                catch (SQLException e) {
                                    
                                    results = results + "Stored proc did not work";
                                }
                            }
                            count++;
                            if (count > 7 && !found) {
                                
                                JOptionPane.showMessageDialog(null, "Sorry there is not room on this flight for your party..", "Seat Availablility", 1);
                                party = 0;
                            }
                        }
                    }

                } catch (SQLException e) {
                    
                    results = results + "SQL Exception";
                }

            } // end try
            catch (SQLException e) {
                
                results = results + "Stored proc did not work";
            }
        }
        return results;
    }
 
    /**
     * Cancels a reservation by reservation ID
     *
     * @param con the connection to the database
     * @param newFrame the initial frame
     */
    public static void cancelRes(Connection con, BlueBirdsJFrame newFrame) {
        
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
        nj.setVisible(true);
        nj.setJLabel1("Enter the reservation ID to cancel.");
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                CallableStatement cState;
                ResultSet rSet;
                Statement stmt;
                int resID = Integer.parseInt(nj.getJTextField1().getText());

                String selectStmt = "SELECT seatNumber, flightCode "
                        + "FROM reservations "
                        + "WHERE resID = " + resID + ";";

                String storedProc1 = "{call cancelRes('" + resID + "')}";
                String storedProc2 = "{call deleteRes('" + resID + "')}";
                String seatNum = "";
                String flightCode = "";

                try {
                    cState = con.prepareCall(selectStmt);

                    rSet = cState.executeQuery();
                    ResultSetMetaData meta = rSet.getMetaData();
                    int columns = meta.getColumnCount();
                    if (!rSet.isBeforeFirst()) {
                        nj.setJTextArea1("There are no reservations for that number");
                    } else {
                        while (rSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                if (meta.getColumnLabel(i).equals("seatNumber")) {
                                    seatNum = rSet.getString(i);
                                } else if (meta.getColumnLabel(i).equals("flightCode")) {
                                    flightCode = rSet.getString(i);
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE seatmap SET " + seatNum + " = NULL  "
                            + "WHERE flightCode = '" + flightCode + "';");
                    cState = con.prepareCall(storedProc1);

                    cState.executeQuery();

                    cState = con.prepareCall(storedProc2);
                    cState.executeQuery();
                    
                    nj.setJTextArea1("The reservation has been canceled");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * prints a customers reservation according to the customer ID
     * 
     * @param con the connection to the database
     * @param newFrame the initial frame
     * 
     * */
    public static void printRes(Connection con, BlueBirdsJFrame newFrame) {
        
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
       
        nj.setJLabel1("Please enter the custormer's ID number: ");
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                String custID = nj.getJTextField1().getText();
                //set font
                nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
                
                String results = "";

                String procName = "getCustomerRes";

                CallableStatement callSt;
                ResultSet resSet;

                String storedProc = "{call " + procName + " (" + custID + ")}";

                try {
                    callSt = con.prepareCall(storedProc);
                    resSet = callSt.executeQuery();

                    try {
                        

                        ResultSetMetaData meta = resSet.getMetaData();

                        int columns = meta.getColumnCount();
                        if (!resSet.isBeforeFirst()) {
                            
                            results = results + "There are no reservations for that customer. ";
                        } else {
                            
                            results = results + "Reservations found for that customer:\n\n";
                        }
                        
                        while (resSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                
                                results = results + String.format("%-20s", meta.getColumnLabel(i) + ":");
                                String col = "";
                                if (meta.getColumnLabel(i).equals("Seat Type")) {
                                    if (resSet.getString(i).equals("1")) {
                                        col = "First Class";
                                    } else {
                                        col = "Economy Class";
                                    }

                                } else {
                                    col = resSet.getString(i);
                                }

                                
                                results = results + String.format("%-20s", col) + "\n";
                            }
                            results += "\n";

                        }
                    } catch (SQLException e) {
                        
                        results = results + "SQL Exception";
                    }

                } // end try
                catch (SQLException e) {
                   
                    results = results + "Stored procedure did not work";
                }
                
                nj.setJTextArea1(results);

            }
        });                

    }

    /**
     * prints a pilots upcoming scheduled flights
     * 
     * @param con the connection to the database
     * @param newFrame the initial frame
     * 
     * */
    public static void printSchedule(Connection con, BlueBirdsJFrame newFrame) {
 
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
                
            }
        });
        
        nj.setJLabel1("Please enter the pilots's ID number: ");
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                
                CallableStatement cState;
                ResultSet rSet;
                String pilotID = nj.getJTextField1().getText();
                String storedProc = "{call getSchedule('" + pilotID + "')}";
                String result = "";
                try {
                    cState = con.prepareCall(storedProc);

                    rSet = cState.executeQuery();

                    try {
                        ResultSetMetaData meta = rSet.getMetaData();
                        int columns = meta.getColumnCount();
                        if (!rSet.isBeforeFirst()) {
                            result += ("There are no flights with that pilot");
                        } else {
                        
                        result += "Upcoming flights for this pilot: \n\n";
                        while (rSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                result += String.format("%-20s", meta.getColumnLabel(i) + ":");

                                result += String.format("%-20s", rSet.getString(i)) + "\n";
                            }
                            result += "\n";
                        }
                        }
                        
                    } catch (SQLException e) {
                        result += "There was an SQL error.";
                    }
                } catch (SQLException e) {
                    result += "There was an SQL error.";
                }
                nj.setJTextArea1(result);

            }
        });

    }

    /**
     * Calculates and displays the gross income for each flight.
     *
     * @param con  the connection to database
     * @param newFrame the initial frame
     */
    public static void grossIncomeEach(Connection con, BlueBirdsJFrame newFrame) {
        OptionExample nj = new OptionExample();

        nj.getJLabel1().setText("Gross Income For Each Flight");
        nj.getJButton2().setVisible(false);
        nj.getJTextField1().setVisible(false);
        nj.setScreenSize(nj);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        CallableStatement stmt;
        ResultSet resSet;
        String procName = "GrossIncomeEach";
        String storedProc = "{call " + procName + "}";
        
        String results = "";
        try {
            stmt = con.prepareCall(storedProc);
            resSet = stmt.executeQuery();

            try {
                
                while (resSet.next()) {
                    String flightCode = resSet.getString(1);
                    int grossIncome = resSet.getInt(2);
                    results = results + ("\nFlight Code: " + flightCode + "  Gross Income: " + nf.format(grossIncome));
                    
                }
                
            } catch (SQLException e) {
                results = results + "SQL Exception";
            }

        } // end try
        catch (SQLException e) {
            
            results = results + "Stored procedure did not work";
        }
        nj.setJTextArea1(results);
        nj.setVisible(true);
    }

    /**
     * Calculates and displays the gross income for a specific flight.
     *
     * @param con  the connection to database
     * @param newFrame the initial frame
     */
    public static void grossIncomeSpec(Connection con, BlueBirdsJFrame newFrame) {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
        nj.setVisible(true);
        nj.setJLabel1("Please Enter the flight code: ");
        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                String flightCode = nj.getJTextField1().getText();
                String results = "\n";
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                CallableStatement stmt;
                ResultSet resSet;
                String procName = "GrossIncomeSpec";
                String storedProc = "{call " + procName + " ('" + flightCode + "')}";
                
                try {
                    stmt = con.prepareCall(storedProc);
                    resSet = stmt.executeQuery();

                    try {
                        

                        ResultSetMetaData meta = resSet.getMetaData();
                        if (resSet.next()) {
                            flightCode = resSet.getString(1);
                            int grossIncome = resSet.getInt(2);
                            results = results + ("Flight Code: " + flightCode + "  Gross Income: " + nf.format(grossIncome));
                            
                        } else {
                            
                            results = results + "No flight found.";
                        }
                    } catch (SQLException e) {
                        
                        results = results + "SQL Exception";
                    }
                } // end try
                catch (SQLException e) {
                    
                    results = results + "Stored proc did not work";
                }
                nj.setJTextArea1(results);
            }
        });
    }
    
    /**
     * searches the database for a canceled reservation by name or id number
     * 
     * @param con the connection to the database
     * @param newFrame the initial frame
     * 
     * */
    public static void searchCanceledRes(Connection con, BlueBirdsJFrame newFrame) {


        String[] choices = {"Customer", "Reservation ID"};
        Object response = JOptionPane.showInputDialog(null,
                "Would you like to search by customer name or reservation ID? ", "Choose one",
                JOptionPane.INFORMATION_MESSAGE, null,
                choices, choices[0]);

        if (response.equals("Reservation ID")) {
            OptionExample nj = new OptionExample();
            nj.setScreenSize(nj);
            nj.setVisible(true);
            newFrame.setEnabled(false);
            nj.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent we) {
                    newFrame.setEnabled(true);
                    newFrame.toFront();
                }
            });
            
            nj.setJLabel1("Please enter the reservation number: ");
            nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent event) {
                    int resNum = Integer.parseInt(nj.getJTextField1().getText());
                    //set font
                    nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));

                    String results = "";

                    String procName = "getCanceledByID";

                    CallableStatement callSt;
                    ResultSet resSet;

                    String storedProc = "{call " + procName + " (" + resNum + ")}";

                    try {
                        callSt = con.prepareCall(storedProc);
                        resSet = callSt.executeQuery();

                        try {
                            

                            ResultSetMetaData meta = resSet.getMetaData();

                            int columns = meta.getColumnCount();
                            if (!resSet.isBeforeFirst()) {
                                 
                                results = results + "There is no reservation with that id";
                            } else {
                                
                                results = results + "Canceled reservations with that ID:\n\n";
                            }
                            
                            while (resSet.next()) {

                                for (int i = 1; i < columns + 1; i++) {
                                    
                                    results = results + String.format("%-20s", meta.getColumnLabel(i) + ":");
                                    String col = "";
                                    if (meta.getColumnLabel(i).equals("Seat Type")) {
                                        if (resSet.getString(i).equals("1")) {
                                            col = "First Class";
                                        } else {
                                            col = "Economy Class";
                                        }

                                    } else {
                                        col = resSet.getString(i);
                                    }

                                    
                                    results = results + String.format("%-20s", col) + "\n";
                                    
                                }
                                

                            }
                        } catch (SQLException e) {
                            
                            results = results + "SQL Exception";
                        }

                    } // end try
                    catch (SQLException e) {
                        
                        results = results + "Stored procedure did not work";
                    }
                    
                    nj.setJTextArea1(results);

                }
            });

        } else if (response.equals("Customer")) {
            OptionExample nj = new OptionExample();
            nj.setScreenSize(nj);
            nj.setVisible(true);
            newFrame.setEnabled(false);
            nj.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent we) {
                    newFrame.setEnabled(true);
                    newFrame.toFront();
                }
            });
            
            nj.setJLabel1("Please enter the customer's name (first and last): ");
            nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent event) {
                    String name = nj.getJTextField1().getText();
                    //set font
                    nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
                    
                    String results = "";

                    String procName = "getCanceledByName";

                    CallableStatement callSt;
                    ResultSet resSet;

                    String storedProc = "{call " + procName + " ('" + name + "')}";

                    try {
                        callSt = con.prepareCall(storedProc);
                        resSet = callSt.executeQuery();

                        try {
                            

                            ResultSetMetaData meta = resSet.getMetaData();

                            int columns = meta.getColumnCount();
                            if (!resSet.isBeforeFirst()) {
                                
                                results = results + "There are no reservations for that customer. ";
                            } else {
                                
                                results = results + "Canceled reservations for " + name + ":\n\n";
                            }
                            
                            while (resSet.next()) {

                                for (int i = 1; i < columns + 1; i++) {
                                    
                                    results = results + String.format("%-20s", meta.getColumnLabel(i) + ":");
                                    String col = "";
                                    if (meta.getColumnLabel(i).equals("Seat Type")) {
                                        if (resSet.getString(i).equals("1")) {
                                            col = "First Class";
                                        } else {
                                            col = "Economy Class";
                                        }

                                    } else {
                                        col = resSet.getString(i);
                                    }

                                    
                                    results = results + String.format("%-20s", col) + "\n";
                                }
                                results += "\n";

                            }
                        } catch (SQLException e) {
                            
                            results = results + "SQL Exception";
                        }

                    } // end try
                    catch (SQLException e) {
                        
                        results = results + "Stored procedure did not work";
                    }
                    
                    nj.setJTextArea1(results);

                }
            });

        }
    }

    /**
     * Prints out the seat map for a certain flight
     *
     * @param connect  the connection to database
     * @param newFrame the initial frame
     */
    public static void printFlightSeats(Connection connect, BlueBirdsJFrame newFrame) {
        OptionExample nj = new OptionExample();
        nj.setScreenSize(nj);
        nj.setVisible(true);
        newFrame.setEnabled(false);
        nj.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) {
                newFrame.setEnabled(true);
                newFrame.toFront();
            }
        });
        nj.setJLabel1("Please Enter the flight code: ");

        nj.getJButton2().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                String flightCode = nj.getJTextField1().getText();

                String results = "";

                CallableStatement callSt;
                ResultSet resSet;

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

                        ResultSetMetaData meta = resSet.getMetaData();
                        int columns = meta.getColumnCount();
                        if (!resSet.isBeforeFirst()) {

                            results = results + "There is no flight with that code";
                        } else {
                            results = results + "First Class:\n";
                        }

                        while (resSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                String seat = "";
                                if (resSet.getString(i) == null) {
                                    seat = "Open";
                                } else {
                                    String storedProc3 = "{call " + procName3 + " (" + Integer.parseInt(resSet.getString(i)) + ")}";
                                    try {
                                        CallableStatement callSt2 = connect.prepareCall(storedProc3);
                                        ResultSet resSet2 = callSt2.executeQuery();
                                        while (resSet2.next()) {
                                            seat = resSet2.getString("customerName");
                                        }
                                    } catch (SQLException e) {
                                        results = results + "Get seatName did not work";
                                    }
                                }
                                if (i == 3) {
                                    results = results + "\n";
                                }

                                results = results + String.format("%-20s", seat);

                            }

                        }
                    } catch (SQLException e) {
                        results = results + "SQL Exception";
                    }

                } // end try
                catch (SQLException e) {
                    results = results + "Stored procedure did not work";
                }

                results = results + "\n\n";
                //Print Economy Class
                try {
                    callSt = connect.prepareCall(storedProc2);
                    resSet = callSt.executeQuery();

                    try {
                        ResultSetMetaData meta = resSet.getMetaData();
                        int columns = meta.getColumnCount();
                        if (resSet.isBeforeFirst()) {
                            results = results + "Economy Class:\n";
                        }

                        while (resSet.next()) {

                            for (int i = 1; i < columns + 1; i++) {
                                String seat = "";
                                if (resSet.getString(i) == null) {
                                    seat = "Open";
                                } else {
                                    String storedProc3 = "{call " + procName3 + " (" + Integer.parseInt(resSet.getString(i)) + ")}";
                                    try {
                                        CallableStatement callSt2 = connect.prepareCall(storedProc3);
                                        ResultSet resSet2 = callSt2.executeQuery();
                                        while (resSet2.next()) {
                                            seat = resSet2.getString("customerName");
                                        }
                                    } catch (SQLException e) {
                                        results = results + "Get seatName did not work";
                                    }
                                }
                                if (i == 5) {
                                    results = results + "\n";
                                }

                                results = results + String.format("%-20s", seat);
                            }

                        }
                    } catch (SQLException e) {
                        results = results + "SQL Exception";
                    }

                } // end try
                catch (SQLException e) {
                    results = results + "Stored procedure did not work";
                }

                //set font
                nj.getJTextArea1().setFont(new Font("Courier New", Font.PLAIN, 12));
                nj.setJTextArea1(results);
            }
        });
    }
}
