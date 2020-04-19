FROM node:13.8 AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

# Add dev environment variables explicitly
# in order to override .env.production build variables
ENV REACT_APP_WEB_API=http://localhost:5000

COPY . .
RUN npm run build

FROM nginx:1.17-alpine as runtime
COPY docker/nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/build /var/www

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]