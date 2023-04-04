<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>INSCRIPTION</h1>
<form:form action="inscription" method="post" modelAttribute="personne">
    <div class="container">
        <!-- Nom -->
        <div class="form-group">
            <label for="nom"><b>Nom</b></label>
            <form:input type="text" path="nom" placeholder="Entrez votre nom" class="form-control" required="true"/>
            <div>
                <form:errors path="nom" cssClass="error"/>
            </div>
        </div>

        <!-- Prénom -->
        <div class="form-group">
            <label for="prenom"><b>Prénom</b></label>
            <form:input type="text" path="prenom" placeholder="Entrez votre prénom" class="form-control"
                        required="true"/>
            <div>
                <form:errors path="prenom" cssClass="error"/>
            </div>
        </div>

        <!-- Site Web -->
        <div class="form-group">
            <label for="siteWeb"><b>Adresse</b></label>
            <form:input type="url" path="siteWeb" placeholder="Entrez votre site web" class="form-control"
                        required="true"/>
            <div>
                <form:errors path="siteWeb" cssClass="error"/>
            </div>
        </div>

        <!-- Email -->
        <div class="form-group">
            <label for="email"><b>Email</b></label>
            <form:input type="text" path="email" placeholder="Entrer votre email" class="form-control" required="true"/>
            <div>
                <form:errors path="email" cssClass="error"/>
            </div>
        </div>

        <!-- Date de Naissance -->
        <div class="form-group">
            <label for="dateDeNaissance"><b>Date de Naissance</b></label>
            <form:input type="date" path="dateDeNaissance" class="form-control" required="true"/>
            <div>
                <form:errors path="dateDeNaissance" cssClass="error"/>
            </div>
        </div>

        <!-- Groupe -->
        <div class="form-group">
            <label for="groupe"><b>Groupe</b></label>
            <form:select path="groupe" class="form-control">
                <c:forEach items="${groupes}" var="groupe">
                    <form:option value="${groupe.id}">${groupe.nom}</form:option>
                </c:forEach>
            </form:select>
            <div>
                <form:errors path="groupe" cssClass="error"/>
            </div>
        </div>

        <!-- Mot de passe -->
        <div class="form-group">
            <label for="motDePasse"><b>Mot de passe</b></label>
            <form:password path="motDePasse" placeholder="Entrer votre mot de passe" class="form-control"
                           required="true"/>
            <div>
                <form:errors path="motDePasse" cssClass="error"/>
            </div>
        </div>

        <button type="submit" class="signupbtn">S'inscrire</button>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            <p>${errorMessage}</p>
        </div>
    </c:if>
</form:form>
<%@ include file="footer.jsp" %>
</body>
</html>