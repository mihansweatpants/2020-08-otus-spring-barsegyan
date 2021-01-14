FROM node:14.15.3-alpine AS build-stage

WORKDIR /app
COPY package*.json /app/
COPY ./ /app/

RUN npx yarn install
RUN npx yarn build


FROM nginx:1.19.6-alpine

COPY --from=build-stage /app/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /app/build/ /usr/share/nginx/html

ARG API_URL
RUN sed -i "s#__API_URL__#${API_URL}#g" /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
