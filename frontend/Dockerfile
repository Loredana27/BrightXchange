FROM node:18 AS front-end

WORKDIR /app

COPY package.json .

RUN npm install

COPY . .
VOLUME ./src ./src
VOLUME ./public ./public
EXPOSE 3000
CMD ["npm", "start"]

