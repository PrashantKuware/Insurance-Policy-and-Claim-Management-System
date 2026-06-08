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

![image.png](image.png)

# ER Diagram

The ER Diagram represents the database structure of the Insurance Policy and Claim Management System. It shows the entities, their attributes, and the relationships among them.

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
    

![image.png](image%201.png)

# Sequence Diagram

## Sequence Diagram (Policy Purchase)

The Sequence Diagram illustrates the interaction between the customer and system components during the policy purchase process. It shows the chronological flow of messages exchanged among controllers, services, repositories, and the database.

![image.png](image%202.png)

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

![image.png](image%203.png)

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

![image.png](image%204.png)

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

![image.png](image%205.png)

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

![image.png](image%206.png)

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

![image.png](image%207.png)

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

![image.png](image%208.png)

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

![image.png](image%209.png)

The JWT Authentication Flow Diagram illustrates how users are authenticated within the system. After successful login, the system generates a JWT token and returns it to the client. The client includes this token in subsequent API requests. The server validates the token before granting access to protected resources.

![image.png](image%2010.png)

Security Architecture Diagram

![image.png](image%2011.png)

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

![image.png](image%2012.png)

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

![image.png](image%2013.png)
