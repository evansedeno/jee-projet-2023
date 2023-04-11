<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Annuaire de personnes</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        .bg-dark2 {
            background-color: #1d2023 !important;
        }
    </style>
</head>
<body class="bg-dark">

<c:if test="${empty utilisateur.personne}">
    <jsp:include page="header-ano.jsp"/>
</c:if>
<c:if test="${not empty utilisateur.personne}">
    <jsp:include page="header.jsp"/>
</c:if>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h1 class="mt-5 text-light text-center">Bienvenue ${utilisateur.personne.prenom} !</h1>
        </div>
    </div>

    <c:if test="${empty utilisateur.personne}">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card my-3 bg-dark2 border-white">
                    <div class="card-body text-center text-white">
                        <h5 class="card-title">Connexion ou Inscription</h5>
                        <a href="/connexion" class="btn btn-primary btn-block">Connexion</a>
                        <a href="/inscription" class="btn btn-secondary btn-block">Inscription</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

</div>

<jsp:include page="footer.jsp"/>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery-3.6.4.min.js"></script>
</body>
</html>