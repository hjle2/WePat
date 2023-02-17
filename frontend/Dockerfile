FROM node:14.21.2 as build-stage
RUN npm install -g http-server
WORKDIR .
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 8081
CMD [ "http-server", "dist" ]
