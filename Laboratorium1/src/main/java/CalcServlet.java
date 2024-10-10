/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author kamil
 */
@WebServlet(urlPatterns = {"/CalcServlet"})
public class CalcServlet extends HttpServlet {
    String historyLog = "";
    HttpSession session;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        JakasKlasa wartosc = (JakasKlasa) session.getAttribute("jakis_id");
//        if (wartosc==null) {
//            wartosc = new JakasKlasa(...);
//            sessiona.setAttribute("jakis_id", wartosc);
//        }
//        zrobCosZ(wartosc);
        session = request.getSession(true);

        String cookieUserVisit = "";
        Cookie [ ] cookies = request.getCookies();
        if ( cookies != null ){ 
            cookieUserVisit = "Hello once again!";
        }
        else{
            cookieUserVisit = "Hello for the first time!";
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+cookieUserVisit+"</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+cookieUserVisit+"</h1>");
            out.println("<a href='calc.jsp'> Return back to form </a> <br> <a href='ClearCookie'>Clear calc history</a>");
            out.println("<h1>Result</h1>");
            out.println("Result is "+ calculate(request));
            
            out.println("<h1>History</h1>");
            out.println(session.getAttribute("historyLog"));
            out.println("</body>");
            out.println("</html>");
        }
    }
    public String calculate(HttpServletRequest request) {
    String firstValueParam = request.getParameter("first-value");
    String secondValueParam = request.getParameter("second-value");
    String sign = request.getParameter("sign");

    if (firstValueParam == null || firstValueParam.trim().isEmpty() ||
        secondValueParam == null || secondValueParam.trim().isEmpty()) {
        historyLog += String.valueOf(firstValueParam + sign + secondValueParam+":"+"Error: No values provided <br/>");
        session.setAttribute("historyLog", historyLog);
        return "Error: No values provided";
    }

    Double firstValue;
    Double secondValue;
    try {
        firstValue = Double.parseDouble(firstValueParam);
        secondValue = Double.parseDouble(secondValueParam);
    } catch (NumberFormatException e) {
        historyLog += String.valueOf(firstValueParam + sign + secondValueParam+":"+"Error: Invalid numeric values <br/>");
        session.setAttribute("historyLog", historyLog);
        return "Error: Invalid numeric values";
    }
    
    
    historyLog += String.valueOf(firstValueParam + sign + secondValueParam+":");
    switch (sign) {
        case "+":
            historyLog += String.valueOf(firstValue + secondValue +"<br/>");
            session.setAttribute("historyLog", historyLog);    
            return String.valueOf(firstValue + secondValue);
        case "-":
            historyLog += String.valueOf(firstValue - secondValue +"<br/>");
            session.setAttribute("historyLog", historyLog);    
            return String.valueOf(firstValue - secondValue);
        case "*":
            historyLog += String.valueOf(firstValue * secondValue +"<br/>");
            session.setAttribute("historyLog", historyLog);    
            return String.valueOf(firstValue * secondValue);
        case "/":
            if (secondValue == 0) {
                historyLog += "Error: Dividing by zero is illegal <br/>";
                session.setAttribute("historyLog", historyLog);    
                return "Error: Dividing by zero is illegal";
            }
            historyLog += String.valueOf(firstValue / secondValue +"<br/>");
            session.setAttribute("historyLog", historyLog);    
            return String.valueOf(firstValue / secondValue);
        default:
            historyLog += "Error: Invalid operator <br/>";
            session.setAttribute("historyLog", historyLog);    
            return "Error: Invalid operator";
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
        processRequest(request, response);
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
        processRequest(request, response);
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
