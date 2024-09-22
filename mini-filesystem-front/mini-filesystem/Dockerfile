# Usar a imagem oficial do Node.js
FROM node:latest

# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto
COPY package*.json ./
RUN npm install

COPY . .

# Expor a porta 8081
EXPOSE 8081

# Comando para iniciar o aplicativo
CMD ["npm", "run", "serve"]
