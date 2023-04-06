<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<h1>CONNEXION</h1>
<form:form action="connexion" method="post" modelAttribute="personne">
    <div class="container">

        <!-- Email -->
        <div class="form-group">
            <label for="email"><b>Email</b></label>
            <form:input type="text" path="email" placeholder="Entrer votre email" class="form-control" required="true"/>
            <div>
                <form:errors path="email" cssClass="error"/>
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

        <button type="submit" class="signupbtn">Connexion</button>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            <p>${errorMessage}</p>
        </div>
    </c:if>
</form:form>
</body>
</html>
