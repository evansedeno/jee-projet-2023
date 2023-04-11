<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Réinitialisation du mot de passe</title>
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
            <!-- Formulaire de réinitialisation du mot de passe -->
            <div class="form-container">
                <h1 class="text-center text-white">Réinitialisation du mot de passe</h1>
                <form:form action="reinitialiser" method="post" modelAttribute="personne" class="bg-dark">
                    <!-- Email -->
                    <div class="form-group">
                        <label for="email" class="text-white">Email :</label>
                        <form:input type="email" path="email" placeholder="Entrez votre email"
                                    class="form-control form-control-sm" required="true"/>
                    </div>
                    <div class="mt-3 mb-3 text-center">
                        <form:errors path="email" cssClass="alert alert-danger p-1"/>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Envoyer le lien de réinitialisation</button>
                    <c:if test="${emailEnvoie}">
                        <div class="alert alert-success p-1 mt-3 text-center" role="alert">
                            Un e-mail de réinitialisation du mot de passe a été envoyé à l'adresse e-mail indiquée.
                        </div>
                    </c:if>
                </form:form>

            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>