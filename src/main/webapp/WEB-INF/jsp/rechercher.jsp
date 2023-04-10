<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Recherche de personne</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery-3.6.4.min.js"></script>
    <style>
        .error {
            color: red;
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
<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="col-md-12 my-5">
        <h1 class="text-center text-white">Recherche de personnes</h1>
        <div class="row
            <c:choose>
                <c:when test="${not empty personnes}">
                    justify-content-space-evenly flex-nowrap
                </c:when>
                <c:otherwise>
                    justify-content-center flex-wrap
                </c:otherwise>
            </c:choose> my-5">
            <div class="col-md-6 mx-2 <c:if test="${empty personnes}">mx-auto</c:if>">
                <h2 class="text-white text-center">Formulaire de recherche</h2>
                <form:form action="rechercher" method="post" class="search-form">
                    <div class="form-group">
                        <label for="nom" class="text-white">Nom :</label>
                        <form:input path="nom" id="nom" class="form-control form-control-sm" placeholder="Ex: Dupont"/>
                    </div>
                    <div class="form-group">
                        <label for="prenom" class="text-white">Prénom :</label>
                        <form:input path="prenom" id="prenom" class="form-control form-control-sm"
                                    placeholder="Ex: Jean"/>
                    </div>
                    <div class="form-group">
                        <label for="siteWeb" class="text-white">Site web :</label>
                        <form:input path="siteWeb" type="url" id="siteWeb" class="form-control form-control-sm"
                                    placeholder="Ex: https://www.example.com"/>
                    </div>
                    <div class="form-group">
                        <label for="id" class="text-white">Identifiant :</label>
                        <form:input path="id" type="number" id="id" class="form-control form-control-sm"
                                    placeholder="Ex: 123"/>
                    </div>
                    <div class="form-group">
                        <label for="groupe" class="text-white">Groupe :</label>
                        <form:select path="groupe" id="groupe" class="form-control form-control-sm">
                            <form:option value="" label="Sélectionnez un groupe"/>
                            <c:forEach var="g" items="${groupes}">
                                <form:option value="${g.id}" label="${g.nom}"/>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Rechercher</button>
                        <button type="reset" class="btn btn-secondary ml-3">Effacer</button>
                    </div>
                </form:form>
            </div>
            <c:if test="${not empty personnes}">
                <div class="col-md-6 mx-2">
                    <div class="search-results">
                        <h2 class="text-white text-center">Résultats de la recherche</h2>
                        <ul class="list-group">
                            <c:forEach var="p" items="${personnes}">
                                <li class="list-group-item">
                                    <a href="${p.id}" class="text-dark">${p.nom} ${p.prenom}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                </div>
            </c:if>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
