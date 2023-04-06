FROM nginx:stable-alpine


COPY nginx /etc/nginx
WORKDIR /fe-wallet
COPY build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]