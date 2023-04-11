<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Changement de mot de passe</title>
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
    </style>
</head>
<body class="bg-dark">
<jsp:include page="header-ano.jsp"/>
<div class="container">
    <div class="form-row">
        <div class="col-md-6 offset-md-3">
            <!-- Formulaire de changement de mot de passe -->
            <div class="form-container">
                <h1 class="text-center text-white">Changement de mot de passe</h1>
                <form:form action="" method="post" modelAttribute="jetonMotDePasse" class="bg-dark">
                    <!-- Nouveau mot de passe -->
                    <div class="form-group">
                        <label for="nouveauMotDePasse" class="text-white">Nouveau mot de passe :</label>
                        <input type="password" name="nouveauMotDePasse" placeholder="Entrez votre nouveau mot de passe"
                               class="form-control form-control-sm" required/>
                    </div>
                    <div class="mt-3 mb-3 text-center">
                        <form:errors path="nouveauMotDePasse" cssClass="alert alert-danger p-1"/>
                    </div>
                    <!-- Confirmation du nouveau mot de passe -->
                    <div class="form-group">
                        <label for="confirmationMotDePasse" class="text-white">Confirmation du nouveau mot de passe
                            :</label>
                        <input type="password" name="confirmationMotDePasse"
                               placeholder="Confirmez votre nouveau mot de passe" class="form-control form-control-sm"
                               required/>
                    </div>
                    <div class="mt-3 mb-3 text-center">
                        <form:errors path="confirmationMotDePasse" cssClass="alert alert-danger p-1"/>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Enregistrer le nouveau mot de passe</button>

                    <c:if test="${motDePasseModifie}">
                        <div class="alert alert-success p-1 mt-3 text-center" role="alert">
                            Votre mot de passe a bien été modifié. Vous allez être redirigé vers la page de connexion.
                        </div>
                        <script>
                            setTimeout(function () {
                                window.location.href = "/connexion";
                            }, 3000);
                        </script>
                    </c:if>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
