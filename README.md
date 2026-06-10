# Insurance Policy and Claim Management System

Status: Done

# Use Case Diagram

The Use Case Diagram illustrates the interactions between different users and the Insurance Policy and Claim Management System. It identifies the primary actors and the functionalities available to them.

- Actors
    
    ### Customer
    
    - Register into the system
    - Login
    - Manage profile
    - View products and plans
    - Purchase insurance policy
    - Make premium payments
    - Raise insurance claims
    - Track claim status
    
    ### Agent
    
    - Login to system
    - Review submitted claims
    - Recommend approval or rejection
    
    ### Admin
    
    - Manage users
    - Manage insurance products
    - Manage policy plans
    - Approve or reject claims
    - Monitor overall system operations

<img width="529" height="1103" alt="image" src="https://github.com/user-attachments/assets/fdb05fc6-fd84-4319-8244-21ac5683acf0" />


# ER Diagram

The ER Diagram represents the database structure of the Insurance Policy and Claim Management System. It shows the entities, their attributes, and the relationships among them.

<img width="641" height="949" alt="er-diagram" src="https://github.com/user-attachments/assets/54a44c20-0eda-4d34-8740-5b5866664ae0" />


### Main Entities

- User
    
    Stores authentication and role information for Admin, Agent, and Customer.
    
- Customer
    
    Stores personal and nominee details for customers.
    
- Insurance Product
    
    Stores information about the insurance products offered by the company.
    
- Policy Plan
    
    Stores plan details, such as premium amount, coverage amount, and policy duration.
    
- Policy
    
    Stores policy purchase information for customers.
    
- Payment
    
    Stores premium payment records associated with policies.
    
- Claim
    
    Stores claim requests submitted by customers.
    

| Relationship | Type |
| --- | --- |
| User → Customer | One-to-One |
| InsuranceProduct → PolicyPlan | One-to-Many |
| Customer → Policy | One-to-Many |
| PolicyPlan → Policy | One-to-Many |
| Policy → Payment | One-to-Many |
| Policy → Claim | One-to-Many |

# Class Diagram

The Class Diagram represents the static structure of the Insurance Policy and Claim Management System. It illustrates the major classes, their attributes, and the relationships among them.

# Relationships

- User – Customer
    
    Each User is associated with one Customer profile.
    
- Insurance Product – Policy Plan
    
    Each Product can include multiple Policy Plans.
    
- Customer – Policy
    
    Each Customer can purchase multiple Policies.
    
- Policy Plan – Policy
    
    Each Policy Plan can be purchased by multiple customers.
    
- Policy – Payment
    
    Each Policy can have multiple premium payments.
    
- Policy – Claim
    
    Each Policy can have multiple claims throughout its lifecycle.
    

<img width="1029" height="1018" alt="image 1" src="https://github.com/user-attachments/assets/5f8fe55f-9069-4759-8120-413f8893268c" />


# Sequence Diagram

## Sequence Diagram (Policy Purchase)

The Sequence Diagram illustrates the interaction between the customer and system components during the policy purchase process. It shows the chronological flow of messages exchanged among controllers, services, repositories, and the database.

<img width="1052" height="734" alt="image 2" src="https://github.com/user-attachments/assets/aca7d1f3-bca7-46dc-b535-7c271509d0ee" />


| Component | Responsibility |
| --- | --- |
| Customer | Initiates policy purchase |
| Policy Controller | Receives purchase request |
| Policy Service | Handles business logic |
| Plan Repository | Retrieves policy plan details |
| Policy Repository | Stores policy information |
| Payment Service | Processes premium payment |
| Database | Stores and retrieves data |

### Flow Description

1. The customer selects a policy plan and submits a purchase request.
2. The Policy Controller forwards the request to the Policy Service.
3. The Policy Service validates the selected plan through the Plan Repository.
4. A new policy is created with a **Pending Payment** status.
5. The policy details are saved in the database.
6. The customer completes the premium payment.
7. The payment information is recorded in the database.
8. After a successful payment, the policy status is updated to **Active**.
9. The customer receives confirmation that the policy has been activated.

## Sequence Diagram (Claim Approval)

The Sequence Diagram illustrates the interaction between Customer, Agent, Admin, and system components during the claim approval process. It shows the order of operations involved in claim submission, review, recommendation, and final approval.

<img width="1034" height="1025" alt="image 3" src="https://github.com/user-attachments/assets/0afd24b4-9bd1-43c2-8adc-236d4a4f9d70" />


### Actors and Components

| Component | Responsibility |
| --- | --- |
| Customer | Submits insurance claim |
| Agent | Reviews claim and recommends decision |
| Admin | Approves or rejects claim |
| Claim Controller | Handles claim-related requests |
| Claim Service | Executes business logic |
| Claim Repository | Stores claim information |
| Policy Repository | Validates policy details |
| Database | Persists claim and policy data |

### Flow Description

1. The customer submits an insurance claim request.
2. The system verifies that the policy is active.
3. The claim is saved with a **Submitted** status.
4. The agent reviews the claim.
5. The claim status changes to **Under Review**.
6. The agent recommends approval or rejection.
7. The administrator conducts the final review.
8. The claim is marked **Approved** or **Rejected**.
9. The final status is saved in the database.

# Activity Diagram

## Activity Diagram (Policy Purchase)

The Activity Diagram represents the workflow involved in purchasing an insurance policy. It illustrates the sequence of activities, decision points, and possible outcomes during the policy purchase process.

<img width="566" height="857" alt="image 4" src="https://github.com/user-attachments/assets/d8b336d1-3656-4308-9963-bce5aa64a3f2" />


### Activities Included

- Customer Login
- Browse Insurance Products
- Select Policy Plan
- Submit Purchase Request
- Validate Plan Details
- Create Policy
- Process Premium Payment
- Activate Policy
- Send Confirmation

### Decision Points

- Plan Availability Check
    
    The system verifies whether the selected policy plan is currently available.
    
- Payment Verification
    
    The system checks whether the premium payment has been completed successfully.
    

### Outcomes

- If payment is successful, the policy status becomes **Active**.
- If payment fails, the policy remains **Pending Payment**.
- If the selected plan is unavailable, the purchase process is terminated.

## Activity Diagram (Claim Process)

The Activity Diagram illustrates the workflow involved in processing an insurance claim. It shows the sequence of activities performed by the customer, agent, and administrator, along with the decision points that determine the claim outcome.

<img width="843" height="1113" alt="image 5" src="https://github.com/user-attachments/assets/861a7a2b-bd5a-4111-bc71-a89c675712a4" />


### Activities Included

- Customer login
- Select an active policy
- Submit a claim request
- Policy validation
- Claim creation
- Agent review
- Recommendation decision
- Admin review
- Claim approval or rejection
- Customer notification

### Decision Points

- Policy Validation
    
    The system verifies that the policy associated with the claim is active and eligible for claim submission.
    
- Claim Eligibility Review
    
    The agent reviews the supporting information and determines whether the claim is eligible for approval.
    
- Final Approval Decision
    
    The administrator conducts the final review and either approves or rejects the claim.
    

### Outcomes

- If the claim is valid and approved, the claim status becomes **Approved**.
- If the claim is invalid or rejected during review, the status becomes **Rejected**.
- If the policy is inactive, the claim submission process is terminated.

# Policy Lifecycle

The Policy Lifecycle Diagram illustrates the various states through which an insurance policy passes during its lifetime. It shows how a policy is created, activated, renewed, expired, or cancelled based on customer actions and system events.

<img width="733" height="606" alt="image 6" src="https://github.com/user-attachments/assets/f88d2c08-aaec-4240-b87e-c6ba6ef558c3" />


### States Included

| State | Description |
| --- | --- |
| Policy Created | Policy record is generated after purchase request |
| Pending Payment | Waiting for premium payment |
| Active | Policy becomes active after successful payment |
| Renewed | Policy is renewed before expiration |
| Expired | Policy validity period has ended |
| Cancelled | Policy is cancelled due to customer request or payment failure |

### State Transitions

1. A customer purchases a policy, and a policy record is created.
2. The policy enters the **Pending Payment** state.
3. After a successful premium payment, the policy becomes **Active**.
4. An active policy can:
    - Be renewed,
    - Expire after its duration ends,
    - Or be cancelled.
5. Expired and cancelled policies mark the end of the policy lifecycle.

# Claim Lifecycle

The Claim Lifecycle Diagram illustrates the different states through which an insurance claim progresses from submission to final closure. It helps visualize the workflow followed during claim evaluation, recommendation, approval, and settlement.

<img width="464" height="749" alt="image 7" src="https://github.com/user-attachments/assets/9b47503a-b94a-458c-9db2-b7dc67c57a5f" />


| State | Description |
| --- | --- |
| Submitted | Claim is submitted by the customer |
| Under Review | Claim is assigned to an agent for evaluation |
| Recommended Approval | Agent recommends claim approval |
| Recommended Rejection | Agent recommends claim rejection |
| Approved | Administrator approves the claim |
| Rejected | Administrator rejects the claim |
| Closed | Claim process is completed |

### State Transitions

1. The customer submits a claim request.
2. The claim enters the **Submitted** state.
3. The claim is assigned to an agent and moves to **Under Review**.
4. The agent evaluates the claim and recommends approval or rejection.
5. The administrator conducts the final review.
6. Based on the decision, the claim moves to **Approved** or **Rejected**.
7. After settlement or closure, the claim moves to the **Closed** state.

# API Design

The API Design defines the REST endpoints used by the Insurance Policy and Claim Management System. These APIs enable communication between clients and the backend application for authentication, policy management, payments, and claim processing.

<img width="691" height="472" alt="image 8" src="https://github.com/user-attachments/assets/64da4bb9-911f-4f79-ae49-7cb7853058cb" />


## API Categories

| Module | Purpose |
| --- | --- |
| Authentication API | User registration and login |
| Product API | Manage insurance products |
| Policy Plan API | Manage policy plans |
| Policy API | Purchase and manage policies |
| Payment API | Record premium payments |
| Claim API | Submit and process claims |

## Authentication APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Authenticate user and generate JWT |

## Product APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| POST | /api/products | Create product |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Deactivate product |

## Policy Plan APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/plans | View plans |
| POST | /api/plans | Create plan |
| PUT | /api/plans/{id} | Update plan |

## Policy APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/policies/purchase | Purchase policy |
| GET | /api/policies | View policies |
| GET | /api/policies/{id} | View policy details |

## Payment APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/payments | Record payment |
| GET | /api/payments | View payment history |

## Claim APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/claims | Submit claim |
| GET | /api/claims | View claims |
| PUT | /api/claims/review | Review claim |
| PUT | /api/claims/approve | Approve claim |
| PUT | /api/claims/reject | Reject claim |

# Security Design

The Security Design defines the mechanisms used to protect the Insurance Policy and Claim Management System from unauthorized access and data breaches. The system uses JWT-based authentication, role-based authorization, password encryption, and secure API access to ensure data confidentiality and integrity.

- Authentication Mechanism
    
    The system uses **JWT (JSON Web Token)** for user authentication.
    

### Authentication Process

1. The user submits an email and password.
2. The system validates the credentials.
3. A JWT token is generated.
4. The token is returned to the user.
5. The user sends the token with every API request.
6. The system validates the token before processing requests.

---

- Authorization Mechanism
    
    The system implements **Role-Based Access Control (RBAC)** for authorization.
    

### Roles

| Role | Permissions |
| --- | --- |
| Customer | Purchase policies, make payments, submit claims |
| Agent | Review claims and recommend decisions |
| Admin | Manage users, products, plans, and approve/reject claims |

<img width="704" height="530" alt="image 9" src="https://github.com/user-attachments/assets/bf4bbc00-e70e-437a-becb-c6b1bad59923" />


The JWT Authentication Flow Diagram illustrates how users are authenticated within the system. After successful login, the system generates a JWT token and returns it to the client. The client includes this token in subsequent API requests. The server validates the token before granting access to protected resources.

<img width="438" height="425" alt="image 10" src="https://github.com/user-attachments/assets/45d0d20b-8c0e-4fc0-b71a-6baf619e8063" />


Security Architecture Diagram

<img width="192" height="747" alt="image 11" src="https://github.com/user-attachments/assets/29efe395-e5f4-4438-9ee9-3d6f3a561852" />


# Technology Stack

The Technology Stack defines the set of technologies, frameworks, tools, and databases used to develop the Insurance Policy and Claim Management System. These technologies work together to provide a secure, scalable, and maintainable application.

## Technology Stack Overview

| Layer | Technology | Purpose |
| --- | --- | --- |
| Programming Language | Java 17 | Core application development |
| Backend Framework | Spring Boot | REST API development |
| Security | Spring Security + JWT | Authentication and Authorization |
| ORM Framework | Spring Data JPA / Hibernate | Database interaction |
| Database | MySQL | Data storage |
| API Documentation | Swagger/OpenAPI | API testing and documentation |
| Build Tool | Maven | Dependency management |
| Version Control | Git & GitHub | Source code management |
| Testing | JUnit | Unit testing |
| IDE | IntelliJ IDEA / Eclipse | Development environment |

<img width="578" height="532" alt="image 12" src="https://github.com/user-attachments/assets/1f4700c5-f658-4145-a1a8-3e28aaaf2bf0" />


## Layer-wise Technology Mapping

| Application Layer | Technology Used |
| --- | --- |
| Presentation Layer | Swagger UI / API Client |
| Controller Layer | Spring Boot REST Controllers |
| Business Layer | Spring Services |
| Security Layer | Spring Security + JWT |
| Persistence Layer | Spring Data JPA + Hibernate |
| Database Layer | MySQL |

# Project Structure

The Project Structure represents the organization of source code and application modules within the Insurance Policy and Claim Management System. A well-defined structure improves maintainability, scalability, readability, and separation of concerns.

<img width="2073" height="851" alt="image 13" src="https://github.com/user-attachments/assets/b14e2d47-29b3-48f3-8114-897d6bbad082" />

