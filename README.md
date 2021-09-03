## TRMS System
# Project Description
The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.

# Technologies Used
* Java
* REST
* Javalin
* Log4j
* JUnit
* Postman
* NoSQL
* AWS Keyspaces
* AWS S3

# Features
* Register and login functionalities for users
* Employees can submit reimbursement requests
* Managers can approve or deny pending requests
* Approvals can escalate through departments
* File upload for requests




# Getting Started
Clone the repository in your terminal with the git clone <repository url> command
You need to establish a Keyspace connection on the AWS website
Once you get your AWS Username and AWS Password from the IAM, you need to set those as environmental variables
Please do not push these to the repo! You will have to pay AWS alot of money if you do!
Usage
To start the application, go to the driver.java file and run the application
Once the connection is established, use Postman to test any routes from the User Controller!
Contributors
