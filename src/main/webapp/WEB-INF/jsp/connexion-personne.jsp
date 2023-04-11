<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Connexion</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery-3.6.4.min.js"></script>
</head>
<body class="bg-dark">

<c:if test="${empty utilisateur.personne}">
    <jsp:include page="header-ano.jsp"/>
</c:if>
<c:if test="${not empty utilisateur.personne}">
    <jsp:include page="header.jsp"/>
</c:if>

<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="col-md-6 my-5">
        <h1 class="text-center text-white">CONNEXION</h1>
        <form:form action="connexion" method="post" modelAttribute="personne">
            <div class="form-group">
                <label for="email" class="text-white">Email :</label>
                <form:input type="text" path="email" placeholder="Entrez votre email"
                            class="form-control form-control-sm"/>
                <div class="mt-3 mb-3 text-center">
                    <form:errors path="email" cssClass="alert alert-danger p-1"/>
                </div>
            </div>
            <div class="form-group">
                <label for="motDePasse" class="text-white">Mot de passe :</label>
                <form:password path="motDePasse" placeholder="Entrez votre mot de passe"
                               class="form-control form-control-sm"/>
                <div class="mt-3 mb-3 text-center">
                    <form:errors path="motDePasse" cssClass="alert alert-danger p-1"/>
                </div>
            </div>
            <div class="text-center mt-3">
                <a href="/motdepasse/reinitialiser" class="text-white">Mot de passe oublié ?</a>
            </div>
            <button type="submit" class="btn btn-primary btn-block mt-3">Connexion</button>
        </form:form>
        <c:if test="${connexionReussie}">
            <div class="alert alert-success p-1 mt-3 text-center" role="alert">
                Connexion réussie ! Vous allez être redirigé vers la page d'accueil.
            </div>
            <script>
                setTimeout(function () {
                    window.location.href = "/accueil";
                }, 3000);
            </script>
        </c:if>
    </div>
</div>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">
        <p>${errorMessage}</p>
    </div>
</c:if>
</body>
</html>
