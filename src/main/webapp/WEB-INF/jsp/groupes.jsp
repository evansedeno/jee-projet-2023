<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des groupes</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Liste des groupes</h1>
<ul>
    <c:forEach var="groupe" items="${groupes}" >
        <li>
            <a href="${groupe.id}">${groupe.nom}</a>
        </li>
    </c:forEach>
</ul>
<%@ include file="footer.jsp" %>
</body>
</html>