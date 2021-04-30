# TaskTracker

## Project Description

A web application for managing tasks given to employees by managers.

## Technologies Used

* Gradle
* Gretty
* Jackson
* Java Servlet API
* PostgreSQL
* Bootstrap 4
* Log4j2 API
* Singleton Design Pattern
* RESTful API Design
* Docker

## Features

- An employee...
    - [X] can login
    - [X] can logout
    - [X] can view the employee homepage
    - [X] can view their assigned tasks
    - [X] can submit a request for a manager to verify work done
    - [X] can upload an image as proof of their work
    - [X] can view past completed work
    - [X] can view their account info
    - [X] can update their account info

- A manager...
    - [X] can login
    - [X] can logout
    - [X] can view the manager homepage
    - [X] can view all pending requests for sign off from employees assigned to them
    - [X] can sign off on work
    - [X] can deny work
    - [X] can create new tasks for their employees
    - [X] can view any submitted images of work
    - [X] can view all assigned employees
    - [X] can view history of an employee

## Getting Started / Usage
   
In order to see this project working correctly you will need to do the following:

1) Have Java 8 runtime environment installed
2) Have Docker installed

Once you have both of these installed you can clone this repo by using this command:

    git clone https://github.com/javatures/Project1-Max-TaskTracker.git

You will also need to create a Docker image and run it on a container, to do this run these commands:

    docker build -t project1
    docker run -d -p 5432:5432 project1

Once these are accomplished, the web application should be viewable at http://localhost:8080/TaskManager