FROM node:13.8 AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install --silent

COPY . .
RUN npm run build

FROM node:13.8 AS runtime
WORKDIR /app
RUN npm install http-server -g
COPY --from=build /app/dist/angular-app ./
ENTRYPOINT ["http-server", "/app", "-c-1", "-p", "8080"]
