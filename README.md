# TRMS System
## Project Description
The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.

## Technologies Used
* Java
* REST
* Javalin
* Log4j
* JUnit
* Postman
* NoSQL
* AWS Keyspaces
* AWS S3

## Features
* Register and login functionalities for users
* Employees can submit reimbursement requests
* Managers can approve or deny pending requests
* Approvals can escalate through departments
* File upload for requests

## Getting Started
To run the project locally, you need to have:
* Java 8
* AWS Keyspace
* AWS S3 Bucket
* IAM user with access to the associated keyspace and S3 bucket

1. Once that's set up clone the repository using:
* git clone https://github.com/210712-Richard/brian.tran.p1

2. Then set up environmental variables AWS_USER and AWS_PASS corresponding the the IAM user previously set up

3. Then build the project and run using the driver.java file

4. You can then use Postman to send requests
5. * The application should be running on port 8080 
