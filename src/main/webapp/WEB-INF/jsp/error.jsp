<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>ERREUR</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        .bg-dark2 {
            background-color: #1d2023 !important;
        }
    </style>
    <script src="/js/jquery-3.6.4.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>
<body class="bg-dark">
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h1 class="mt-5 text-light text-center">UNE ERREUR EST SURVENUE</h1>
            <p class="text-light text-center mt-4">Vous allez être redirigé vers la page d'accueil dans <span
                    id="countdown">5</span> secondes.</p>
            <script>
                let temps = 5;
                let timer = setInterval(function () {
                    if (temps <= 0) {
                        clearInterval(timer);
                        window.location.href = "/";
                    }
                    document.getElementById("countdown").textContent = temps;
                    temps -= 1;
                }, 1000);
            </script>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>