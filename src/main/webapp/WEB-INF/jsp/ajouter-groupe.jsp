<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un groupe</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery-3.6.4.min.js"></script>
</head>
<body class="bg-dark">
<jsp:include page="header.jsp"/>
<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="col-md-6 my-5">
        <h1 class="text-center text-white" style="margin-bottom: 1rem;">Ajouter un groupe</h1>
        <form:form method="post" action="ajouter" commandName="groupe" class="bg-dark">
            <div class="form-group">
                <form:label path="nom" class="text-white">Nom:</form:label>
                <form:input path="nom" class="form-control"/>
            </div>
            <div class="form-group">
                <input type="submit" value="Ajouter" class="btn btn-primary"/>
            </div>
        </form:form>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>