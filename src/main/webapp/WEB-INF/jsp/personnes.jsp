<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des personnes</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        .bg-dark2 {
            background-color: #1d2023 !important;
        }
    </style>
</head>
<body class="bg-dark">
<c:if test="${empty utilisateur.personne}">
    <jsp:include page="header-ano.jsp"/>
</c:if>
<c:if test="${not empty utilisateur.personne}">
    <jsp:include page="header.jsp"/>
</c:if>

<div style="margin-bottom: 6rem;" class="container w-50">
    <h1 class="mt-5 text-white text-center">Liste des personnes</h1>

    <c:forEach var="groupe" items="${groupes}">
        <div class="card mt-3 bg-dark2 border-white">
            <div class="card-header border-white text-white">
                <h2>${groupe.nom}</h2>
            </div>
            <div class="card-body">
                <table class="table table-bordered table-hover text-white">
                    <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="personne" items="${personnes}">
                        <c:if test="${personne.groupe.id == groupe.id}">
                            <tr>
                                <td class="align-middle">${personne.nom}</td>
                                <td class="align-middle">${personne.prenom}</td>
                                <td style="width: 1%;"><a href="${personne.id}" class="btn btn-primary">Voir</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="footer.jsp"/>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery-3.6.4.min.js"></script>
</body>
</html>