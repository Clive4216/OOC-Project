package com.uniquedeveloper.registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/foodlog")
public class ProcessFoodLog extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the parameters from the form
        String foodName = request.getParameter("foodName");
        String quantity = request.getParameter("quantity");

        // Retrieve the username from the session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("name");

        if (foodName == null || quantity == null || username == null) {
            response.sendRedirect("Enter Values");
            return;
        }

        // Store the food log information in a file specific to the user
        String filePath = "C:\\Users\\CassandraE$PERANCE\\eclipse-workspace-2\\signup\\" + username + "_food_log.txt";
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            writer.println("Username: " + username);
            writer.println("Food Name: " + foodName);
            writer.println("Quantity: " + quantity);
            writer.println("---------------------------------------");
            
         // Retrieve food details from CSV
            String foodDetails = getFoodDetails(foodName);
            
            // Set the food details as a request attribute
            request.setAttribute("foodDetails", foodDetails);
            
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Redirect the user back to the index.jsp page with a success parameter in the URL
        //response.sendRedirect("index.jsp?success=true");
        
     // Redirect the user back to the index.jsp page with a success parameter in the URL
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    private String getFoodDetails(String foodName) {
        String csvFile = "C:\\Users\\CassandraE$PERANCE\\eclipse-workspace-2\\signup\\food_data.csv"; // Update with the correct path
        String line;
        String foodDetails = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the CSV file line by line
            while ((line = br.readLine()) != null) {
                // Split the line into values using a comma as the delimiter
                String[] values = line.split(",");

                // Check if the food name matches the user input
                if (values.length > 0 && values[0].equalsIgnoreCase(foodName.trim())) {
                    // Construct a string with food details
                    foodDetails = "Carbon footprint: " + values[1] + " kgCO2e/kg<br>"
                            + "Water footprint: " + values[2] + " L/kg<br>"
                            + "Protein: " + values[3] + " g/100g<br>"
                            + "Fat: " + values[4] + " g/100g<br>"
                            + "Carbohydrates: " + values[5] + " g/100g<br>"
                            + "Fiber: " + values[6] + " g/100g<br>";
                    break; // Stop searching once a match is found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodDetails;
    }
    
}
