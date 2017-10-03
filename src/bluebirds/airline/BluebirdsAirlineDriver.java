/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluebirds.airline;

import java.util.ArrayList;

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
        
        ArrayList<Customer> customerAL = new ArrayList<Customer>();
        
        ArrayList<Flight> flightAL = new ArrayList<Flight>();
        
        ArrayList<Pilot> pilotAL = new ArrayList<Pilot>();
        
        ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
        
        primeData(pilotAL, flightAL);
    }
    
    public static void searchReservID()
    {
        
    }
    
    public static void searchCustID()
    {
        
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
}
