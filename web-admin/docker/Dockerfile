FROM node:13.8 AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:1.17-alpine as runtime
COPY docker/nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/build /var/www

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]