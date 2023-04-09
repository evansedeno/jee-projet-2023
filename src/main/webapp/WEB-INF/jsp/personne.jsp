<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails de la personne</title>
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

<div class="container d-flex justify-content-center align-items-center vh-100 mb-5">
    <div class="col-md-6 my-5">
        <div class="card bg-dark border-white">
            <h1 class="card-header border-white text-white">Détails de la personne</h1>
            <div class="card-body">
                <table class="table table-dark table-bordered border-white table-striped">
                    <tbody>
                    <tr>
                        <th>Nom</th>
                        <td>${personne.nom}</td>
                    </tr>
                    <tr>
                        <th>Prénom</th>
                        <td>${personne.prenom}</td>
                    </tr>
                    <tr>
                        <th>Site web</th>
                        <td><a href="${personne.siteWeb}" class="site-hover text-decoration-none text-white">${personne.siteWeb}</a></td>
                    </tr>
                    <tr>
                        <th>Groupe</th>
                        <td><a href="/groupe/${personne.groupe.id}" class="text-decoration-none text-white">${personne.groupe.nom}</a></td>
                    </tr>
                    <c:if test="${not empty utilisateur.personne}">
                        <tr>
                            <th>Date de naissance</th>
                            <td><fmt:formatDate value="${personne.dateDeNaissance}" pattern="dd/MM/yyyy"/></td>
                        </tr>
                        <tr>
                            <th>E-mail</th>
                            <td><a href="mailto:${personne.email}" class="text-decoration-none text-white">${personne.email}</a></td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>