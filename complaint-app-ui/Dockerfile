FROM node:16.13.0 AS builder

WORKDIR /app

COPY . .

RUN npm install && \
    npm run build

FROM nginx:alpine

COPY nginx.conf  /etc/nginx/conf.d/default.conf
COPY nginx.conf  /etc/nginx/conf.d/nginx.conf

COPY --from=builder /app/dist/* /usr/share/nginx/html/
