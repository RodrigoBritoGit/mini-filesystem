<template>
    <nav class="navbar">
      <a href="#" class="navbar-brand">FileSystem</a>
      <ul class="navbar-nav">
        <li class="nav-item"><a href="#" class="nav-link">Início</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Configurações</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Sair</a></li>
      </ul>
    </nav>
  </template>
  
  <style>
  .navbar {
    margin: 0;
    background-color: #007bff;
    padding: 30px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .navbar-brand {
    color: #fff;
    font-weight: bold;
    text-decoration: none;
  }
  
  .navbar-nav {
    display: flex;
    list-style-type: none;
    padding: 0;
    margin: 0;
  }
  
  .nav-item {
    margin-left: 20px;
  }
  
  .nav-link {
    color: #fff;
    text-decoration: none;
    transition: color 0.3s;
  }
  
  .nav-link:hover {
    color: #ffeb3b;
  }
  </style>
  