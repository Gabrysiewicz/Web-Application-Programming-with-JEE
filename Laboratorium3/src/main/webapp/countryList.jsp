<%@page import="com.example.CountryBean"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <title>Country List</title>
</head>
<body>
    <h1>Countries in Europe</h1>
    
    <%
        // Retrieve the list from the session
        List<CountryBean> countries = (List<CountryBean>) request.getSession().getAttribute("list");

        if (countries == null || countries.isEmpty()) {
            out.println("<p>No countries found for the specified continent.</p>");
            out.println(countries);
        } else {
            out.println("<table border='1'>");
            out.println("<tr><th>Country Code</th><th>Country Name</th><th>Population</th></tr>");
            for (CountryBean country : countries) {
                out.println("<tr>");
                out.println("<td>" + country.getCode() + "</td>");
                out.println("<td>" + country.getName() + "</td>");
                out.println("<td>" + country.getPopulation() + "</td>");
                out.println("<td><a href='details.jsp?index=" + countries.indexOf(country) + "'>View Details</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    %>
</body>
</html>
