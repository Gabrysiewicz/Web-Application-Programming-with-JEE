<%-- 
    Document   : calc
    Created on : Oct 9, 2024, 3:50:31 PM
    Author     : kamil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Calculator!</h1>
        <form method="POST" action="CalcServlet" > 
            <label for="first-value">First value:</label>
            <input type="number" name="first-value" />
            <br/>
            <select id="sign" name="sign">
                <option name="+" id="+">+</option>
                <option name="-" id="-">-</option>
                <option name="*" id="*">*</option>
                <option name="/" id="/">/</option>
            </select>
            <br/>
            <label for="second-value">Second value:</label>
            <input type="number" name="second-value" />
            <br/>
            <input type="submit" name="calculate" value="calculate" />
        </form>

    </body>
</html>
