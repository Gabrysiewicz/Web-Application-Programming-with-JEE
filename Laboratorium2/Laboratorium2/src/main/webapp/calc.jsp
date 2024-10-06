<%-- 
    Document   : calc
    Created on : Oct 6, 2024, 9:45:21 PM
    Author     : kamil
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String date = dateFormat.format(now);
        %>
        
        <h1>Kalkulator Rat | <%= date %></h1>
        
        <form action="calc.jsp" method="POST">
            <label for="kwota-pozyczki">Kwota pożyczki</label>
            <input type="number" name="kwota-pozyczki" id="kwota-pozyczki"  />
            <br />
            <label for="procent-roczny">Procent roczny</label>
            <input type="number" name="procent-roczny" id="procent-roczny"  />
            <br />
            <label for="liczba-rat">Liczba rat</label>
            <input type="number" name="liczba-rat" id="liczba-rat" />
            <br />
            <input type="submit" name="Oblicz" value="Oblicz" id="Oblicz"/>
        </form>

        <% 
            if (request.getParameter("Oblicz") != null) {
                Double wynik = -1.0;
                try {
                    // Parse input values
                    Double kwota_pozyczki = Double.parseDouble(request.getParameter("kwota-pozyczki"));
                    Double procent_roczny = Double.parseDouble(request.getParameter("procent-roczny"));
                    Double liczba_rat = Double.parseDouble(request.getParameter("liczba-rat"));
                    Double p = (procent_roczny / 100) / 12;

                    // Debugging
//                    out.println("<p>Kwota pożyczki: " + kwota_pozyczki + "</p>");
//                    out.println("<p>Procent roczny: " + procent_roczny + "</p>");
//                    out.println("<p>Miesięczna stopa procentowa: " + p + "</p>");
//                    out.println("<p>Liczba rat: " + liczba_rat + "</p>");

                    wynik = (kwota_pozyczki * p) / (1 - (1 / Math.pow((1 + p), liczba_rat)));

                    DecimalFormat df = new DecimalFormat("#.00");
                    String rataf = df.format(wynik);

                    out.println("<h2>Twoja miesięczna rata wynosi: " + rataf + " PLN</h2>");
                } catch (Exception ex) {
                    out.println("<h2>Coś poszło nie tak: " + ex.getMessage() + "</h2>");
                }
            }
        %>
    </body>
</html>