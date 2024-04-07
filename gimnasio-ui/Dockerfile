FROM node:16.17-slim AS build
WORKDIR /app
COPY package.json package-lock.json /app/
RUN npm install
COPY . /app/
RUN npm run build && rm -rf node_modules/

FROM nginx:1.23-alpine-slim as runtime
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/publish/entrypoint.sh /entrypoint.sh
COPY --from=build /app/publish/nginx.site.template /etc/nginx/conf.d/
COPY --from=build /app/build/resources/main/static /usr/share/nginx/html
RUN chmod 444 /usr/share/nginx/html/favicon.ico
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
CMD ["sh"]
