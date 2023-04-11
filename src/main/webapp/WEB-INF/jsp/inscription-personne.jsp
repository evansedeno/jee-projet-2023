<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Inscription</title>
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
        <h1 class="text-center text-white">INSCRIPTION</h1>
        <form:form action="inscription" method="post" modelAttribute="personne">
            <!-- Nom et Prénom -->
            <div class="form-group">
                <div class="row">
                    <div class="col">
                        <label for="nom" class="text-white">Nom :</label>
                        <form:input type="text" path="nom" placeholder="Entrez votre nom"
                                    class="form-control form-control-sm"/>
                        <div class="mt-3 mb-3 text-center">
                            <form:errors path="nom" cssClass="alert alert-danger p-1"/>
                        </div>
                    </div>
                    <div class="col">
                        <label for="prenom" class="text-white">Prénom :</label>
                        <form:input type="text" path="prenom" placeholder="Entrez votre prénom"
                                    class="form-control form-control-sm"/>
                        <div class="mt-3 mb-3 text-center">
                            <form:errors path="prenom" cssClass="alert alert-danger p-1"/>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Site Web et Email -->
            <div class="form-group">
                <div class="row">
                    <div class="col">
                        <label class="text-white">Site Web :</label>
                        <form:input type="url" path="siteWeb" placeholder="Entrez votre site web"
                                    class="form-control form-control-sm"/>
                        <div class="mt-3 mb-3 text-center">
                            <form:errors path="siteWeb" cssClass="alert alert-danger p-1"/>
                        </div>
                    </div>
                    <div class="col">
                        <label class="text-white">Email :</label>
                        <form:input type="text" path="email" placeholder="Entrez votre email"
                                    class="form-control form-control-sm"/>
                        <div class="mt-3 mb-3 text-center">
                            <form:errors path="email" cssClass="alert alert-danger p-1"/>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Date de Naissance et Groupe -->
            <div class="form-group">
                <div class="row">
                    <div class="col">
                        <label class="text-white">Date de Naissance:</label>
                        <form:input type="date" path="dateDeNaissance" class="form-control form-control-sm"/>
                        <div class="mt-3 mb-3 text-center">
                            <form:errors path="dateDeNaissance" cssClass="alert alert-danger p-1"/>
                        </div>
                    </div>
                    <div class="col">
                        <label class="text-white">Groupe :</label>
                        <form:select path="groupe" class="form-control form-control-sm">
                            <c:forEach items="${groupes}" var="groupe">
                                <form:option value="${groupe.id}">${groupe.nom}</form:option>
                            </c:forEach>
                        </form:select>
                        <div class="mt-3 mb-3 text-center">
                            <form:errors path="groupe" cssClass="alert alert-danger p-1"/>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Mot de passe -->
            <div class="form-group">
                <label for="motDePasse" class="text-white">Mot de passe :</label>
                <form:input type="password" path="motDePasse" placeholder="Entrez votre mot de passe"
                            class="form-control form-control-sm"/>
                <div class="mt-3 mb-3 text-center">
                    <form:errors path="motDePasse" cssClass="alert alert-danger p-1"/>
                </div>
            </div>
            <button type="submit" class="btn btn-primary btn-block mt-3">S'inscrire</button>
        </form:form>

        <c:if test="${inscriptionReussie}">
            <div class="alert alert-success p-1 mt-3 text-center" role="alert">
                L'inscription a été effectuée avec succès. Vous êtes maintenant connecté et allez être redirigé vers la
                page d'accueil.
            </div>
            <script>
                setTimeout(function () {
                    window.location.href = "/accueil";
                }, 3000);
            </script>
        </c:if>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>