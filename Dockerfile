# Étape 1 : Build du frontend avec Vite
FROM node:20 AS frontend-build

WORKDIR /app-builder

# Copie des fichiers nécessaires pour Vite
COPY vite.config.js package*.json ./
COPY src/js ./src/js
COPY public ./public
COPY index.html ./index.html

# Installation des dépendances et build
RUN npm install && npm run build

# Étape 2 : Build de l'application Spring Boot
FROM eclipse-temurin:17-jdk-alpine AS backend-build

WORKDIR /app

# Copie du code backend
COPY . .

# Copie les fichiers build du frontend dans les ressources statiques
COPY --from=frontend-build /app-builder/dist ./src/main/resources/static

# Build du JAR avec Maven
RUN ./mvnw clean package -DskipTests

# Étape 3 : Image finale
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copie du JAR généré
COPY --from=backend-build /app/target/*.jar app.jar

EXPOSE 8080

# Lancement de l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
