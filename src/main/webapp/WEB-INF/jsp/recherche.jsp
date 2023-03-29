<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
  <title>Recherche de personne</title>
  <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Recherche de personne</h1>
<form action="recherche" method="get">
  <label for="nom">Nom :</label>
  <input type="text" id="nom" name="nom">
  <label for="prenom">Prenom :</label>
  <input type="text" id="prenom" name="prenom">
  <label for="siteWeb">Site web :</label>
  <input type="url" id="siteWeb" name="siteWeb">
  <label for="identifiant">Identifiant :</label>
  <input type="number" id="identifiant" name="identifiant">
  <input type="submit" id="rechercher" value="Rechercher">
</form>
<c:if test="${not empty personnes}">
  <h2>Résultats de la recherche</h2>
  <ul>
    <c:forEach var="p" items="${personnes}">
      <li><a href="personne?id=${p.id}">${p.nom} ${p.prenom}</a></li>
    </c:forEach>
  </ul>
</c:if>
<%@ include file="footer.jsp" %>
</body>
</html>