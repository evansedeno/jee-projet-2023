<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
    <title>Annuaire de personnes</title>
    <link rel="stylesheet" href="/style.css">
</head>

<c:if test="${empty utilisateur.personne}">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<body style="justify-content: center; align-items: center; height: 100vh;">
<div class="boutons-div">
    <a href="/connexion" class="boutons-login">Connexion</a>
    <a href="/inscription" class="boutons-login">Inscription</a>
</div>
</c:if>
<c:if test="${not empty utilisateur.personne}">
<body>
<jsp:include page="header.jsp"/>
<h1>Bienvenue ${utilisateur.personne.prenom} !</h1>

<%@ include file="footer.jsp" %>
</c:if>

</body>
</html>