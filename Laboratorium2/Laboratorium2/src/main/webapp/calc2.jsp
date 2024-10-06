<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<jsp:useBean id="loan" class="com.example.LoanBean" scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kalkulator Rat z JavaBean</title>
    </head>
    <body>
        <h1>Kalkulator Rat Kredytowych</h1>

        <form action="calc2.jsp" method="POST">
            <label for="kwota-pozyczki">Kwota pożyczki:</label>
            <input type="number" name="kwota" step="0.01" required />
            <br />
            <label for="procent-roczny">Procent roczny:</label>
            <input type="number" name="procentRoczny" step="0.01" required />
            <br />
            <label for="liczba-rat">Liczba rat:</label>
            <input type="number" name="liczbaRat" required />
            <br />
            <input type="submit" value="Oblicz" />
        </form>

        <%
            if (request.getParameter("kwota") != null) {
                double kwota = Double.parseDouble(request.getParameter("kwota"));
                double procentRoczny = Double.parseDouble(request.getParameter("procentRoczny"));
                int liczbaRat = Integer.parseInt(request.getParameter("liczbaRat"));

                // Ustawianie wartości w obiekcie loan
                loan.setKwota(kwota);
                loan.setProcentRoczny(procentRoczny);
                loan.setLiczbaRat(liczbaRat);

                // Obliczanie raty
                double rata = loan.getRata();

                // Formatowanie wyniku
                DecimalFormat df = new DecimalFormat("#.00");
                String rataFormatted = df.format(rata);

                // Wyświetlenie wyniku
                out.println("<h2>Twoja miesięczna rata wynosi: " + rataFormatted + " PLN</h2>");
            }
        %>
    </body>
</html>
