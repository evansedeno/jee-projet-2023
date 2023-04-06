<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un groupe</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Ajouter un groupe</h1>
<form:form method="post" action="ajouter" commandName="groupe">
    <table>
        <tr>
            <td><form:label path="nom">Nom:</form:label></td>
            <td><form:input path="nom"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Ajouter"/></td>
        </tr>
    </table>
</form:form>
<%@ include file="footer.jsp" %>
</body>
</html>



