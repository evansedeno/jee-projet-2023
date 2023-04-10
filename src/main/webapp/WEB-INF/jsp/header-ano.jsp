<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/header.css">
<script src="/js/jquery-3.6.4.min.js"></script>
<script>
    $(function () {
        $(".nav-item.dropdown").hover(
            function () {
                $(this).find(".dropdown-menu").stop(true, true).fadeIn("fast");
            },
            function () {
                $(this).find(".dropdown-menu").stop(true, true).fadeOut("fast");
            }
        );
    });
</script>
<script src="/js/bootstrap.min.js"></script>

<div class="container mx-auto">
    <nav class="navbar navbar-expand-md navbar-dark">
        <a href="/" class="navbar-brand">Accueil</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto text-center">
                <li class="nav-item dropdown">
                    <a href="" class="nav-link dropdown-toggle mr-2" id="groupesDropdown" role="button">
                        <i class="fas fa-users"></i> Groupes
                    </a>
                    <div class="dropdown-menu bg-dark" aria-labelledby="groupesDropdown">
                        <a href="/groupe/liste" class="dropdown-item">Liste</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a href="" class="nav-link dropdown-toggle mr-2" id="personnesDropdown" role="button">
                        <i class="fas fa-user"></i> Personnes
                    </a>
                    <div class="dropdown-menu bg-dark" aria-labelledby="personnesDropdown">
                        <a href="/personne/liste" class="dropdown-item">Liste</a>
                        <a href="/personne/rechercher" class="dropdown-item">Rechercher</a>
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <a href="" class="nav-link dropdown-toggle" id="compteDropdown" role="button">
                        <i class="fas fa-user-circle"></i> Mon Compte
                    </a>
                    <div class="dropdown-menu bg-dark" aria-labelledby="compteDropdown">
                        <a href="/connexion" class="dropdown-item">Connexion</a>
                        <a href="/inscription" class="dropdown-item">Inscription</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>