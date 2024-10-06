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
                double kwota = Double.parseDouble(request.getParameter("kwota-pozyczki"));
                double procentRoczny = Double.parseDouble(request.getParameter("procent-roczny"));
                int liczbaRat = Integer.parseInt(request.getParameter("liczba-rat"));

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
