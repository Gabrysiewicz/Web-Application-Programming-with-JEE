<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edytuj dane pracownika</title>
    </head>
    <body>
        
            <h1>Edytuj dane pracownika</h1>
            <!-- Form action updated to match the controller's /saveedit method -->
            <form:form method="post" action="${pageContext.request.contextPath}/saveedit">
                <table>
                    <!-- Hidden input for the employee ID -->
                    <form:hidden path="id" />
                    
                    <tr>
                        <td>Nazwisko :</td>
                        <td> <form:input path="nazwisko" /> </td>
                    </tr>
                    <tr>
                        <td>Pensja :</td>
                        <td> <form:input path="pensja" /> </td>
                    </tr>
                    <tr>
                        <td>Firma :</td>
                        <td> <form:input path="firma" /> </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td> <input type="submit" value="Zapisz" /> </td>
                    </tr>
                </table>
            </form:form>
        
    </body>
</html>
