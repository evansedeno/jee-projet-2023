<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
<style>
    .dropdown-item {
        color: #fff;
    }

    .dropdown-item:hover {
        color: #000;
        background-color: #fff;
    }

    .navbar-dark {
        background-color: #343a40;
    }
</style>
<script src="/js/jquery-3.6.4.min.js"></script>
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
                    <a href="#" class="nav-link dropdown-toggle mr-2" id="groupesDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-users"></i> Groupes
                    </a>
                    <div class="dropdown-menu bg-dark" aria-labelledby="groupesDropdown">
                        <a href="/groupe/liste" class="dropdown-item">Liste</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle mr-2" id="personnesDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
                    <a href="#" class="nav-link dropdown-toggle" id="compteDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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