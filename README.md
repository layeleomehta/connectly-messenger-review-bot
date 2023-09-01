# Connectly Coding Challenge: Messenger Review Bot

## Overview: how to use
This is a backend system which uses the Messenger Platform API by Meta for Developers to implement a chatbot which can process and store customer feedback reviews for a business page. 

Users can register their business page within the backend once they've set up a Facebook Business page and registered it within an application on Meta for Developers. Once a user registers their page, this system will configure their Messenger chat, and prompt for customer feedback with a few triggers. 

Triggers that a customer can use to make the system request customer feedback: 
1) Clicking the 'Get Started' button when initially entering chat. 
2) Clicking 'Leave a review!' in the persistent menu.
3) Sending a greeting or a farewell text message, which will be parsed by the system using Facebook NLP and initiate a quick reply message. Clicking 'Yes' to the quick reply message will request customer feedback.
4) Sending any text message to the business, which will prompt the system to initiate a quick reply message. Clicking 'Yes' to the quick reply message will request customer feedback.


## Implementation details

The service is built with Spring Boot and Kotlin, which stores data in a PostgreSQL database. The application is deployed on Google App Engine, which interfaces with a PostgreSQL database hosted in Google Cloud SQL. 

The service is deployed on Google App Engine, and it has a base URL of: 
`https://connectly-397506.uc.r.appspot.com/`

## Design & Architecture Overview
There are two tables in the database: `BusinessPage` and `CustomerFeedbackReview`. The `BusinessPage` table stores data for a business page. The `CustomerFeedbackReview` has a foreign key to the `BusinessPage`, and is able to store a customer's review text and their star rating from 1 to 5. 

The code in this Spring Boot application follows the Controller-Service-Repository design pattern. The `BusinessPageController` and `MessengerPlatformWebhookController` are entry points into the application for a user and the Meta for Developers platform.   
