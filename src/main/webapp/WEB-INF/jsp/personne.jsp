<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
    <title>Détails de la personne</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Détails de la personne</h1>
<div class="personne">
    <p><strong>Nom:</strong> ${personne.nom}</p>
    <p><strong>Prénom:</strong> ${personne.prenom}</p>
    <p><strong>Site web:</strong> <a href="${personne.siteWeb}" class="site-hover">${personne.siteWeb}</a></p>
    <p><strong>Groupe:</strong> ${personne.groupe.nom}</p>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>