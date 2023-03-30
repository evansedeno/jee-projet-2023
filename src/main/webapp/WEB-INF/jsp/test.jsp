<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
<head>
    <title>Page de test</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Les personnes suivantes ont bien été ajoutées:</h1>
<ul>
    <c:forEach items="${requestScope.personnes}" var="personne">
        <li>
            <c:out value="${personne.nom}"/>
            <c:out value="${personne.prenom}"/>
        </li>
    </c:forEach>
</ul>
<%@ include file="footer.jsp" %>
</body>
</html>