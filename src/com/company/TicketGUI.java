package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Jessy on 11/19/2015.
 */


// public TicketGUI class extends to JFrame
public class TicketGUI extends JFrame {


    // Creating LinkedList resolvedTicket and ticketQueue
    LinkedList<Ticket> resolvedTicket = new LinkedList<>();
    LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();



    // property name of the GUI
    private JPanel rootPanel;
    private JTextField textFieldDescription;
    private JTextField textFieldPriority;
    private JTextField textFieldReport;
    private JButton addTicketButton;
    private JList<Ticket> listTicket;
    private JButton deleteButton;
    private JTextField textFieldResolved;
    private JLabel resolvedLabel;
    private JButton quitButton;


    // Declaring ticketListModel
    DefaultListModel<Ticket> ticketListModel;


    // Declaring Date variable reportedDate
    public Date reportedDate;



    // TicketGUI method starts here
    public TicketGUI() {


        // Displaying Title bar label "Support TicketGUI"
        super("Support TicketGUI");

        setContentPane(rootPanel);  //  setting the JPanel called "rootPanel" to JFrame
        pack();   // pack or make it smaller to display the form

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting the default close operation on JFrame
        setVisible(true);    // make the form visible
        setSize(new Dimension(400, 400));   // setting the form size


        // creating a ticketListModel for the DefaultListModel
        // Creating a basic set up and function for a JList
        ticketListModel = new DefaultListModel<Ticket>();
        listTicket.setModel(ticketListModel);

        // Change list selection mode to single selection
        listTicket.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // Creating a string variable for a open ticket file
        String fileNameOpen = "Open_Ticket.txt";


        // Creating a BufferedReader bReader to read the file "Open_Ticket.txt"
        try (BufferedReader bReader = new BufferedReader(new FileReader(fileNameOpen));) {

            // reading a line from a "Open_Ticket.txt" and Creating a variable name "line"
            String line = bReader.readLine();


            // Using while loop, while line is not equal to null... and splitting the line variable data with
            // space between them.
            while (line != null) {
                String[] lineData = line.split(" ");

                // Creating SimpleDateFormat formatter variable to format the date in certain ways
                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");

                // Creating a dateInString variable from the data information from the lineData Array.
                String dateInString = lineData[11] + " " + lineData[12] + " " + lineData[13] + " " + lineData[14] +
                        " " + lineData[15] + " " + lineData[16];


                // formatting the dateInString variable  date information by using a formatter
                Date date = formatter.parse(dateInString);


                // Creating a variable for "priority" and "id" from a lineData Array of index position of data stored
                int priority = Integer.parseInt(lineData[5]);
                int id = Integer.parseInt(lineData[1]);


                // Creating a openTicketData instance in a Ticket class with perimeter
                // and adding the data to LinkedList of ticketQueue
                Ticket openTicketData = new Ticket(lineData[3], priority, lineData[8], date, id);
                ticketQueue.add(openTicketData);
                TicketGUI.this.ticketListModel.addElement(openTicketData);

                line = bReader.readLine();


            }
        }

        // Catching any file and parse date exception
        catch (IOException ioe){
            System.out.println("Error creating or writing file " +ioe);
        }
        catch (ParseException pe){

            System.out.println("Couldn't parse the date");
        }



        // Listening to any change on addTicketButton
        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // getting the user input from each text box
                String descriptionData = textFieldDescription.getText();
                String priorityData = textFieldPriority.getText();
                String reportData = textFieldReport.getText();
                int priority = Integer.parseInt(priorityData);   // changing the string variable to Integer variable
                reportedDate = new Date();


                // creating the instance called "newTicket" from Ticket class
                Ticket newTicket = new Ticket(descriptionData, priority, reportData, reportedDate);

                // adding the newTicket data to ticketQueue
                ticketQueue.add(newTicket);


                // adding newTicket data to the JList
                TicketGUI.this.ticketListModel.addElement(newTicket);
                setSize(new Dimension(1000, 400));   // resizing the form


                // clearing the text box for new input data
                textFieldDescription.setText("");
                textFieldPriority.setText("");
                textFieldReport.setText("");
            }


        });



        // listening to any change on deleteButton
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // if data selection was made from the List and if the resolved text box is empty,
                // show a message "Please enter a resolution"
                if (!listTicket.isSelectionEmpty()) {

                    if (textFieldResolved.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(TicketGUI.this, "Please enter a resolution");


                        //  get user input from resolved text box
                    } else {
                        String resolvedMethod = textFieldResolved.getText();
                        Date resolvedNow = new Date();  // set resolvedNow date

                        // getting the value of the selected data from the list and store it in "toDelete"
                        Ticket toDelete = TicketGUI.this.listTicket.getSelectedValue();

                        // remove the toDelete value from the JList
                        TicketGUI.this.ticketListModel.removeElement(toDelete);
                        ticketQueue.remove(toDelete);  // remove toDelete from ticketQueue


                        // Calling a method to construct the resolution and resolutionDate
                        toDelete.setResolution(resolvedMethod);
                        toDelete.setResolutionDate(resolvedNow);

                        resolvedTicket.add(toDelete);  // adding the toDelete value date to resolvedTicket
                        textFieldResolved.setText("");  // clear the text box
                    }

                    // if ticket data is not selected from JList, show message " Please select a ticket to delete"
                } else {
                    JOptionPane.showMessageDialog(TicketGUI.this, "Please select a ticket to delete");

                }
            }


        });


        // listening to quit button
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // creating the variable for txt.file
                String fileNameOpen = "Open_ticket.txt";
                String fileName = "resolved_ticket.txt";


                //  Creating a writer to write on txt.file
                try {
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileName, true));

                    BufferedWriter jWriter = new BufferedWriter(new FileWriter(fileNameOpen));


                    // using for loop on ticketQueue to get each data
                    // and writting the data to Open_ticket.txt file
                    for (Ticket w : ticketQueue) {
                        jWriter.write((w.toString() + "\n"));
                    }


                    // using for loop on resolvedTicket to get each data
                    // and writing the data to resolved ticket txt.file
                    for (Ticket j : resolvedTicket) {
                        bWriter.write(j.toString() + "\n");

                    }

                    bWriter.close(); // save and close writer and file
                    jWriter.close();

                }

                // catch any file related exception
                catch (IOException ioe) {
                    JOptionPane.showMessageDialog(TicketGUI.this, "Error creating or writing file " + ioe);
                }
                System.exit(0);
            }
        });
    }
}
