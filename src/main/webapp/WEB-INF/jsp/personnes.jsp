<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des personnes</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<h1>Liste des personnes</h1>

<c:forEach var="groupe" items="${groupes}">
    <h2>Groupe : ${groupe.nom}</h2>
    <ul>
        <c:forEach var="personne" items="${personnes}">
            <c:if test="${personne.groupe.id == groupe.id}">
                <li><a href="personne?id=${personne.id}">${personne.nom} ${personne.prenom}</a></li>
            </c:if>
        </c:forEach>
    </ul>
</c:forEach>

<%@ include file="footer.jsp" %>
</body>
</html>