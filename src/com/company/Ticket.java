package com.company;

/**
 * Created by Jessy on 11/20/2015.
 */
import java.util.Date;
public class Ticket {

    // declaring the variable, that will be used for this program
    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private String resolution;
    private Date resolutionDate;


    // get and set method for the resolution and resolutionDate
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }




    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;


    // Constructor for the Ticket (bluePrint of how Ticket is made)
    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    // Constructor for the Ticket with changing the ticket ID number
    public Ticket(String desc, int p, String rep, Date date, int id) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = id;
        staticTicketIDCounter = id + 1;
    }


    // get and set method for priority
    protected int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }



    // toString method to make the resolutionString and resolvedDateString variable to set a string data
    // for the display on the console
    public String toString() {


        // if the resolutionDate or resolution is equal to null... display "Unresolved" on the console, otherwise,
        // display the string data to the console
        String resolvedDateString = ( resolutionDate == null) ? "Unresolved" : this.resolutionDate.toString();
        String resolutionString = ( this.resolution == null) ? "Unresolved" : this.resolution;


        return ("ID= " + this.ticketID + " Issued: " + this.description + " Priority: " + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported + " Resolved Date = " + resolvedDateString +
                " Resolution = " + resolutionString);

    }


    // method to get tickedId data and return the data where the method was called
    public int getTicketID() {


        return ticketID;
    }


}
