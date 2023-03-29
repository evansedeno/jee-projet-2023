<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Ajouter une personne</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Ajouter une personne</h1>
<form:form method="post" action="ajouter-personne" commandName="personne">
    <table>
        <tr>
            <td><form:label path="nom">Nom:</form:label></td>
            <td><form:input path="nom" type="text"/></td>
        </tr>
        <tr>
            <td><form:label path="prenom">Prénom:</form:label></td>
            <td><form:input path="prenom" type="text"/></td>
        </tr>
        <tr>
            <td><form:label path="email">Email:</form:label></td>
            <td><form:input path="email" type="email"/></td>
        </tr>
        <tr>
            <td><form:label path="siteWeb">Site Web:</form:label></td>
            <td><form:input path="siteWeb" type="url"/></td>
        </tr>
        <tr>
            <td><form:label path="dateDeNaissance">Date de naissance:</form:label></td>
            <td><form:input path="dateDeNaissance" type="date"/></td>
        </tr>
        <tr>
            <td><form:label path="groupe">Groupe:</form:label></td>
            <td>
                <form:select path="groupe">
                    <c:forEach items="${groupes}" var="groupe">
                        <form:option value="${groupe.id}">${groupe.nom}</form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td><form:label path="motDePasse">Mot de Passe:</form:label></td>
            <td><form:input path="motDePasse" type="password"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Ajouter"/></td>
        </tr>
    </table>
</form:form>
<%@ include file="footer.jsp" %>
</body>
</html>