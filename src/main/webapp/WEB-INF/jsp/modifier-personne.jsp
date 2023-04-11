<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                <form:form action="" method="post" modelAttribute="personne" class="bg-dark">
                    <!-- Nom et Prénom -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="nom" class="text-white">Nom :</label>
                                <form:input type="text" path="nom" placeholder="Entrez votre nom"
                                            class="form-control form-control-sm"/>
                                <div class="mt-3 mb-3 text-center">
                                    <form:errors path="nom" cssClass="alert alert-danger p-1"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="prenom" class="text-white">Prénom :</label>
                                <form:input type="text" path="prenom" placeholder="Entrez votre prénom"
                                            class="form-control form-control-sm"/>
                                <div class="mt-3 mb-3 text-center">
                                    <form:errors path="prenom" cssClass="alert alert-danger p-1"/>
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
                                            class="form-control form-control-sm"/>
                                <div class="mt-3 mb-3 text-center">
                                    <form:errors path="email" cssClass="alert alert-danger p-1"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="text-white">Site Web :</label>
                                <form:input type="url" path="siteWeb" placeholder="Entrez votre site web"
                                            class="form-control form-control-sm"/>
                                <div class="mt-3 mb-3 text-center">
                                    <form:errors path="siteWeb" cssClass="alert alert-danger p-1"/>
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
                                />
                                <div class="mt-3 mb-3 text-center">
                                    <form:errors path="dateDeNaissance" cssClass="alert alert-danger p-1"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="text-white">Groupe :</label>
                                <form:select path="groupe" class="form-control form-control-sm">
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
                                <div class="mt-3 mb-3 text-center">
                                    <form:errors path="groupe" cssClass="alert alert-danger p-1"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary btn-block">Enregistrer les modifications</button>
                </form:form>
                <c:if test="${modificationReussie}">
                    <div class="alert alert-success p-1 mt-3 text-center" role="alert">
                        Vos modifications ont été enregistrées avec succès.
                    </div>
                </c:if>
            </div>
        </div>

        <div class="col-md-6">
            <!-- Formulaire pour changer le mot de passe -->
            <div class="form-container">
                <h1 class="text-center text-white">Changer le mot de passe</h1>
                <form:form action="modifier/motdepasse" method="post" modelAttribute="changementMotDePasse"
                           class="bg-dark">
                    <!-- Mot de passe actuel -->
                    <div class="form-group">
                        <label for="ancienMotDePasse" class="text-white">Mot de passe actuel :</label>
                        <input type="password" id="ancienMotDePasse" name="ancienMotDePasse"
                               placeholder="Entrez votre mot de passe actuel"
                               class="form-control form-control-sm"/>
                    </div>
                    <div class="mt-3 mb-3 text-center">
                        <form:errors path="ancienMotDePasse" cssClass="alert alert-danger p-1"/>
                    </div>

                    <!-- Nouveau mot de passe -->
                    <div class="form-group">
                        <label for="nouveauMotDePasse" class="text-white">Nouveau mot de passe :</label>
                        <input type="password" id="nouveauMotDePasse" name="nouveauMotDePasse"
                               placeholder="Entrez votre nouveau mot de passe"
                               class="form-control form-control-sm"/>
                    </div>
                    <div class="mt-3 mb-3 text-center">
                        <form:errors path="nouveauMotDePasse" cssClass="alert alert-danger p-1"/>
                    </div>

                    <!-- Confirmation du nouveau mot de passe -->
                    <div class="form-group">
                        <label for="confirmationMotDePasse" class="text-white">Confirmation du nouveau mot de passe
                            :</label>
                        <input type="password" id="confirmationMotDePasse" name="confirmationMotDePasse"
                               placeholder="Confirmez votre nouveau mot de passe" class="form-control form-control-sm"
                        />
                    </div>
                    <div class="mt-3 mb-3 text-center">
                        <form:errors path="confirmationMotDePasse" cssClass="alert alert-danger p-1"/>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Changer le mot de
                        passe
                    </button>
                </form:form>
                <c:if test="${motDePasseReussie}">
                    <div class="alert alert-success p-1 mt-3 text-center" role="alert">
                        Votre mot de passe a été changé avec succès.
                    </div>
                </c:if>
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