<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>CONNEXION</h1>
<form>
    <div class="container">
        <label for="email"><b>Email</b>
            <input type="email" placeholder="Entrez votre email" name="email" required></label>

        <label for="motDePasse"><b>Mot de passe</b>
            <input type="password" placeholder="Entrez votre mot de passe" name="motDePasse" required></label>

        <button type="submit">Connexion</button>
    </div>

    <div class="container">
        <p class="motdepasseoublie">Mot de passe <a href="#">oublié?</a></p>
    </div>

</form>
<%@ include file="footer.jsp" %>
</body>
</html>
