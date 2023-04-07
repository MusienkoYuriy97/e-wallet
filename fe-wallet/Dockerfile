FROM node:16-alpine as build
WORKDIR /
COPY . .
RUN npm install
RUN npm run build

FROM nginx:1.21.1-alpine
COPY --from=build /build /usr/share/nginx/html
COPY /nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]