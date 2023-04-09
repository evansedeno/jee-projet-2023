<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Formulaires</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery-3.6.4.min.js"></script>
    <style>
        .form-container {
            padding: 20px;
            border-radius: 5px;
        }

        .form-row {
            display: flex;
            justify-content: space-evenly;
            margin-bottom: 20px;
        }

        .error {
            color: red;
        }
    </style>
    <script>
        function confirmDelete() {
            return confirm("Êtes-vous sûr de vouloir supprimer votre compte?");
        }
    </script>
</head>
<body class="bg-dark">
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="form-row">
        <div class="col-md-6">
            <!-- Formulaire Modifier le compte -->
            <div class="form-container">
                <h1 class="text-center text-white">Modifier le compte</h1>
                <form:form action="modifier" method="post" modelAttribute="personne" class="bg-dark">
                    <!-- Nom et Prénom -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="nom" class="text-white">Nom :</label>
                                <form:input type="text" path="nom" placeholder="Entrez votre nom"
                                            class="form-control form-control-sm" required="true"/>
                                <div>
                                    <form:errors path="nom" cssClass="error"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="prenom" class="text-white">Prénom :</label>
                                <form:input type="text" path="prenom" placeholder="Entrez votre prénom"
                                            class="form-control form-control-sm" required="true"/>
                                <div>
                                    <form:errors path="prenom" cssClass="error"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Email et Site Web -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="text-white">Email :</label>
                                <form:input type="text" path="email" placeholder="Entrez votre email"
                                            class="form-control form-control-sm" required="true"/>
                                <div>
                                    <form:errors path="email" cssClass="error"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="text-white">Site Web :</label>
                                <form:input type="url" path="siteWeb" placeholder="Entrez votre site web"
                                            class="form-control form-control-sm" required="true"/>
                                <div>
                                    <form:errors path="siteWeb" cssClass="error"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Date de Naissance et Groupe -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="text-white">Date de Naissance:</label>
                                <form:input type="date" path="dateDeNaissance" class="form-control form-control-sm"
                                            required="true"/>
                                <div>
                                    <form:errors path="dateDeNaissance" cssClass="error"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="text-white">Groupe :</label>
                                <form:select path="groupe" class="form-control form-control-sm" required="true">
                                    <c:forEach items="${groupes}" var="groupe">
                                        <c:if test="${groupe.id == personne.groupe.id}">
                                            <form:option value="${groupe.id}"
                                                         selected="selected">${groupe.nom}</form:option>
                                        </c:if>
                                        <c:if test="${groupe.id != personne.groupe.id}">
                                            <form:option value="${groupe.id}">${groupe.nom}</form:option>
                                        </c:if>
                                    </c:forEach>
                                </form:select>
                                <div>
                                    <form:errors path="groupe" cssClass="error"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary btn-block">Enregistrer les modifications</button>
                </form:form>
            </div>
        </div>

        <div class="col-md-6">
            <!-- Formulaire pour changer le mot de passe -->
            <div class="form-container">
                <h1 class="text-center text-white">Changer le mot de passe</h1>
                <form:form action="motdepasse" method="post" modelAttribute="passwordForm" class="bg-dark">
                    <!-- Mot de passe actuel -->
                    <div class="form-group">
                        <label for="motDePasse" class="text-white">Mot de passe actuel :</label>
                        <input type="password" name="motDePasseActuel" placeholder="Entrez votre mot de passe actuel"
                               class="form-control form-control-sm" required="true"/>
                    </div>

                    <!-- Nouveau mot de passe -->
                    <div class="form-group">
                        <label for="nouveauMotDePasse" class="text-white">Nouveau mot de passe :</label>
                        <input type="password" name="nouveauMotDePasse" placeholder="Entrez votre nouveau mot de passe"
                               class="form-control form-control-sm" required="true"/>
                    </div>

                    <!-- Confirmation du nouveau mot de passe -->
                    <div class="form-group">
                        <label for="confirmationMotDePasse" class="text-white">Confirmation du nouveau mot de passe
                            :</label>
                        <input type="password" name="confirmationMotDePasse"
                               placeholder="Confirmez votre nouveau mot de passe" class="form-control form-control-sm"
                               required="true"/>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Changer le mot de
                        passe
                    </button>
                </form:form>
            </div>
        </div>
    </div>
    <div class="row" style="margin-bottom: 5rem;">
        <div class="col-md-8 offset-md-2">
            <!-- Formulaire pour supprimer le compte -->
            <div class="form-container p-0">
                <form:form action="supprimer" method="post" onsubmit="return confirmDelete();" class="bg-dark">
                    <button type="submit" class="btn btn-danger btn-block">Supprimer le compte</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>