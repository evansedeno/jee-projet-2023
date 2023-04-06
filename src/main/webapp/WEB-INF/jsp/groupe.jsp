<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des personnes du groupe: ${groupe.nom}</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Liste des personnes du groupe: ${groupe.nom}</h1>
<ul>
    <c:forEach var="personne" items="${personnes}">
        <li>
            <a href="${pageContext.request.contextPath}/personne/${String.valueOf(personne.id)}">${personne.nom} ${personne.prenom}</a>
        </li>
    </c:forEach>
</ul>
<%@ include file="footer.jsp" %>
</body>
</html>