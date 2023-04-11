<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des groupes</title>
    <meta charset="UTF-8">
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
        <h1 class="text-center text-white" style="margin-bottom: 1rem;">Liste des groupes</h1>
        <table class="table table-dark table-bordered table-striped">
            <thead>
            <tr>
                <th>Nom du groupe</th>
                <th style="width: 1%;">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="groupe" items="${groupes}">
                <tr>
                    <td>${groupe.nom}</td>
                    <td class="text-center">
                        <a href="${groupe.id}" class="btn btn-primary btn-sm">Voir</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>