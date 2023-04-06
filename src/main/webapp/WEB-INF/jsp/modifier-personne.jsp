<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Modifier le compte</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script>
        function confirmDelete() {
            return confirm("Êtes-vous sûr de vouloir supprimer votre compte?");
        }
    </script>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>MODIFIER LE COMPTE</h1>
<form:form action="modifier" method="post" modelAttribute="personne">
    <div class="container">
        <!-- Nom -->
        <div class="form-group">
            <label for="nom"><b>Nom</b></label>
            <form:input type="text" path="nom" value="${personne.nom}" placeholder="Entrez votre nom" class="form-control" required="true"/>
            <div>
                <form:errors path="nom" cssClass="error"/>
            </div>
        </div>

        <!-- Prénom -->
        <div class="form-group">
            <label for="prenom"><b>Prénom</b></label>
            <form:input type="text" path="prenom" value="${personne.prenom}" placeholder="Entrez votre prénom" class="form-control"
                        required="true"/>
            <div>
                <form:errors path="prenom" cssClass="error"/>
            </div>
        </div>

        <!-- Site Web -->
        <div class="form-group">
            <label for="siteWeb"><b>Site Web</b></label>
            <form:input type="url" path="siteWeb" value="${personne.siteWeb}" placeholder="Entrez votre site web" class="form-control"
                        required="true"/>
            <div>
                <form:errors path="siteWeb" cssClass="error"/>
            </div>
        </div>

        <!-- Email -->
        <div class="form-group">
            <label for="email"><b>Email</b></label>
            <form:input type="text" path="email" value="${personne.email}" placeholder="Entrer votre email" class="form-control" required="true"/>
            <div>
                <form:errors path="email" cssClass="error"/>
            </div>
        </div>

        <!-- Date de Naissance -->
        <div class="form-group">
            <label for="dateDeNaissance"><b>Date de Naissance</b></label>
            <fmt:formatDate value="${personne.dateDeNaissance}" pattern="yyyy-MM-dd" var="dateSansHeure"/>
            <form:input type="date" path="dateDeNaissance" value="${dateSansHeure}" class="form-control" required="true"/>
            <div>
                <form:errors path="dateDeNaissance" cssClass="error"/>
            </div>
        </div>

        <!-- Groupe -->
        <div class="form-group">
            <label for="groupe"><b>Groupe</b></label>
            <form:select path="groupe" class="form-control">
                <c:forEach items="${groupes}" var="groupe">
                    <form:option value="${groupe.id}" selected="${groupe.id == personne.groupe.id}">${groupe.nom}</form:option>
                </c:forEach>
            </form:select>
            <div>
                <form:errors path="groupe" cssClass="error"/>
            </div>
        </div>

        <button type="submit" class="signupbtn">Enregistrer les modifications</button>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            <p>${errorMessage}</p>
        </div>
    </c:if>
</form:form>

<!-- Formulaire pour changer le mot de passe -->
<form:form action="motdepasse" method="post" modelAttribute="passwordForm">
    <div class="container">
        <h2>Changer le mot de passe</h2>

        <!-- Mot de passe actuel -->
        <div class="form-group">
            <label for="motDePasse"><b>Mot de passe actuel</b></label>
            <input type="password" name="motDePasseActuel" placeholder="Entrez votre mot de passe actuel" class="form-control"
                   required="true"/>
        </div>

        <!-- Nouveau mot de passe -->
        <div class="form-group">
            <label for="nouveauMotDePasse"><b>Nouveau mot de passe</b></label>
            <input type="password" name="nouveauMotDePasse" placeholder="Entrez votre nouveau mot de passe" class="form-control"
                   required="true"/>
        </div>

        <!-- Confirmation du nouveau mot de passe -->
        <div class="form-group">
            <label for="confirmationMotDePasse"><b>Confirmation du nouveau mot de passe</b></label>
            <input type="password" name="confirmationNouveauMotDePasse" placeholder="Confirmez votre nouveau mot de passe" class="form-control"
                   required="true"/>
        </div>

        <button type="submit" class="signupbtn">Changer le mot de passe</button>
    </div>
</form:form>

<!-- Supprimer le compte -->
<form:form action="supprimer" method="post" onsubmit="return confirmDelete();">
    <div class="container">
        <button type="submit" class="deleteAccountBtn">Supprimer le compte</button>
    </div>
</form:form>

<%@ include file="footer.jsp" %>

</body>
</html>