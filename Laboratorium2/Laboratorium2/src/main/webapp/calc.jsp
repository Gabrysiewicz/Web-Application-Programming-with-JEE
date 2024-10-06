<%-- 
    Document   : calc
    Created on : Oct 6, 2024, 9:45:21 PM
    Author     : kamil
--%>

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
        <h1>Hello World!</h1>
        <%= new Date() %>
        <%
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String date = dateFormat.format(now);
//            out.println(date);
        %>
        <br />
        <%= date %>
    </body>
</html>
