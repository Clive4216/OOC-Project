package com.uniquedeveloper.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProcessFoodLog
 */
@WebServlet("/foodlog")
public class ProcessFoodLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String foodType = request.getParameter("foodType");
		String quantity = request.getParameter("quantity");
		String mealTime = request.getParameter("mealTime");
		
		if (foodType == null || quantity == null || mealTime == null) {
			response.sendRedirect("Enter Values");
			return;
		}
		
		System.out.println("Food Type: " + foodType);
		System.out.println("Quantity: " + quantity);
		System.out.println("Meal Time: " + mealTime);
		
		response.sendRedirect("index.jsp?success=true");
	}

}
