/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kamil
 */
@WebServlet(urlPatterns = {"/ListServlet"}, name="ListServlet")
public class ListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException, ClassNotFoundException {
    response.setContentType("text/html;charset=UTF-8");
    
    try (PrintWriter out = response.getWriter()) {
        // Load MySQL driver
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        
        Connection conn = null;
        
        try {
            // Attempt to connect to MySQL
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC", "root", "qwe123");
            
            // If connection is successful, process the query or return success message
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Connected successfully to MySQL database</h1>");
            Statement st = conn.createStatement();
            String query="SELECT * FROM country WHERE Continent = 'Europe'";
            //wykonanie zapytania SQL:
            ResultSet rs = st.executeQuery(query);
            out.println("<table>");
            while (rs.next()) {
                //pobierz i wyświetl dane z odpowiedniej kolumny
                out.println("<tr><td>");
                    out.print(rs.getString("name"));
                out.println("</td></tr>");    
                //out.println …
            }
            out.println("</table>");
            

            out.println("</body>");
            out.println("</html>");
            
            // Perform your SQL operations here
            // For example: Statement stmt = conn.createStatement();
            // ResultSet rs = stmt.executeQuery("SELECT * FROM Country WHERE Continent = 'Europe'");
            // Loop through result set and print results

        } catch (SQLException e) {
            // Handle database connection failure
            out.println("<h2>Error: Unable to connect to the database</h2>");
            out.println("<p>Exception message: " + e.getMessage() + "</p>");
        } finally {
            // Close the connection if it was opened
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
