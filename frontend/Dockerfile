# Use an official Node runtime as a parent image
FROM node:16 as build-stage

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install project dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the project
RUN npm run build

# Use a lightweight Nginx image to serve the built files
FROM nginx:alpine as production-stage

# Copy the built files from the build-stage to the Nginx public directory
COPY --from=build-stage /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]