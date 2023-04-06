<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav>
    <div class="dropdown">
        <a href="/" class="dropbtn">Accueil</a>
    </div>
    <div class="dropdown">
        <div class="dropbtn">Groupes</div>
        <div class="dropdown-content">
            <a href="/groupe/liste">Liste</a>
            <a href="/groupe/ajouter">Ajouter</a>
            <a href="/groupe/recherche">Rechercher</a>
        </div>
    </div>
    <div class="dropdown">
        <div class="dropbtn">Personnes</div>
        <div class="dropdown-content">
            <a href="/personne/liste">Liste</a>
            <a href="/personne/rechercher">Recherche</a>
        </div>
    </div>
    <div class="dropdown">
        <div class="dropbtn">Mon Compte</div>
        <div class="dropdown-content">
            <a href="/personne/${utilisateur.personne.id}/modifier">Modifier</a>
            <a href="/deconnexion" class="dropbtn">Déconnexion</a>
        </div>
    </div>
</nav>