FROM node:latest
# To run from command line:
# sudo docker run -d -p 8080:3000 -v $(pwd):/var/www/ppmtool/ -w "/var/www/ppmtool/" node npm run start
# To actually build a custom image using the Dockerfile
# sudo docker build -f Dockerfile -t msanti/ppmtoolfrontend .
# sudo docker run -d --name ppmtool_latest -p 8080:3000 msanti/ppmtoolfrontend
LABEL author="Manuel Santiago Laboy"

ENV NODE_ENV=development
ENV PORT=3000

COPY      . /var/www/ppmtool
WORKDIR   /var/www/ppmtool

RUN       npm install

#VOLUME ["/var/www/ppmtool/"]

EXPOSE $PORT

ENTRYPOINT ["npm", "run", "start"]