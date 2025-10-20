# 🎵 Serratec Music API

Uma API REST desenvolvida em Spring Boot para gerenciamento de músicas, artistas, usuários e playlists.

## 🎯 Sobre o Projeto

A Serratec Music API é uma aplicação backend que permite o gerenciamento completo de um sistema de música, incluindo:

- **Usuários**: Cadastro e gerenciamento de usuários com perfis
- **Artistas**: Controle de artistas e suas informações
- **Músicas**: Gerenciamento de músicas com gêneros e artistas associados
- **Playlists**: Criação e gerenciamento de playlists personalizadas

## 🛠 Tecnologias Utilizadas:

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **Spring Web**
- **PostgreSQL**
- **SpringDoc OpenAPI 3** (Swagger)
- **Maven**
- **Spring Boot DevTools**

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [PostgreSQL 12+](https://www.postgresql.org/download/)

## 💡 Exemplos de Uso

### Criar um usuário
{
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "perfil": {
    "telefone": "21999990000",
    "dataNascimento": "1990-05-15"
  }
}

### Criar um artista
{
  "nome": "The Beatles",
  "nacionalidade": "Reino Unido"
}

### Criar uma música
{
  "titulo": "Hey Jude",
  "minutos": 7,
  "genero": "ROCK",
  "artistas": [
    {
      "id": 1
    }
  ]
}

## Criar uma playlist
{
  "nome": "Minhas Favoritas",
  "descricao": "Uma playlist com meus hits preferidos",
  "musicas": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ],
  "usuario": {
    "id": 1
  }
}

## Autor

- Bernardo Serra Santos
- Progeto da Disciplina: API
- Residência em TIC Software 2025.2
