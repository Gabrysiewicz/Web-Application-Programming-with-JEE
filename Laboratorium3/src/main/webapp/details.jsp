<%-- 
    Document   : details
    Created on : Oct 7, 2024, 2:29:54 PM
    Author     : kamil
--%>

<%@page import="java.util.List"%>
<%@page import="com.example.CountryBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Country Details</h1>

        <%
            // Get the index from the request parameter
            String indexParam = request.getParameter("index");
            int index = Integer.parseInt(indexParam);

            // Retrieve the list from the session
            List<CountryBean> countries = (List<CountryBean>) request.getSession().getAttribute("list");

            if (countries == null || index < 0 || index >= countries.size()) {
                out.println("<p>Invalid country selection.</p>");
            } else {
                // Get the selected country
                CountryBean country = countries.get(index);
                out.println("<h2>" + country.getName() + "</h2>");
                out.println("<p><strong>Country Code:</strong> " + country.getCode() + "</p>");
                out.println("<p><strong>Population:</strong> " + country.getPopulation() + "</p>");
                out.println("<p><strong>Area Surface:</strong> " + country.getSurfaceArea() + "</p>");
                out.println("<p><strong>GNP:</strong> " + country.getGNP() + "</p>");
                out.println("<p><strong>Life Expectancy:</strong> " + country.getLifeExpectancy() + "</p>");
            }
        %>

        <br>
        <a href="countryList.jsp">Back to Country List</a>
    </body>
</html>
