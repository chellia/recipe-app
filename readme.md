# Project Overview

It is a spring boot and angular project to showcase the recipe management.

# To Run the Application

# First make sure the home-made-recipe-api up and running

Go to home-made-recipe-api folder and run the following command,

mvn spring-boot:run

the application starts and running on 8000 port by default

# Second run the angular UI 

Go to home-made-recipe-ui folder
 
npm install  ( make sure installs all the dependencies )

npm start

the app will start running on  http://localhost:4200/ 

# To navigate the app
login credentials are guest/welcome

# Api documentation is available at 
http://localhost:8000/api/swagger-ui/index.html?configUrl=/api/recipe-api-docs/swagger-config

# To run in docker
  docker-compose up

the app will be running on http://localhost:9000

