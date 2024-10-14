<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Wystąpił błąd</title>
    </head>
    <body>
        <h1>Coś poszło nie tak!</h1>
        <p>Przepraszamy, ale wystąpił błąd w Twojej aplikacji.</p>

        <h2>Szczegóły błędu:</h2>
        <p><strong>Wiadomość błędu:</strong> <%= exception.getMessage() %></p>

        <p>Spróbuj ponownie lub skontaktuj się z administratorem.</p>
        
        <a href="calc2.jsp"> Powrót </a>
    </body>
</html>
