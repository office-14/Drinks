FROM node:13.8 AS build
WORKDIR /app

COPY . .
RUN npm install && npm run postinstall

FROM node:13.8 AS runtime
WORKDIR /app
RUN npm install http-server -g
COPY --from=build /app/app ./
ENTRYPOINT ["http-server", "/app", "-c-1", "-p", "8080"]