<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des personnes du groupe: ${groupe.nom}</title>
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
    <div class="col-md-8 my-5">
        <div class="card bg-dark border-white">
            <h1 class="card-header border-white text-white">Liste des personnes du groupe: ${groupe.nom}</h1>
            <div class="card-body">
                <table class="table table-dark table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th style="width: 1%;">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="personne" items="${personnes}">
                        <tr>
                            <td>${personne.nom}</td>
                            <td>${personne.prenom}</td>
                            <td style="width: 1%;"><a
                                    href="${pageContext.request.contextPath}/personne/${String.valueOf(personne.id)}"
                                    class="btn btn-primary">Voir</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
