# API Dev Project

## Overview
We have been tasked to explore the concepts of REST by implementing a service and extending functionality of our week 5 project.
This task involved implementing multiple mapping types, parameter handling approaches, authentication for APIs while integrating this with our JPA/Hibernate back end.

## Specific Requirements
We need to 
- Create an API to access the JPA entities created for the employees database in week 5
- Provide basic CRUD access for all relevant tables via the API
- Provide access to the additional analytical methods as well
- Implement appropriate error handling and reporting, including logging
- Implement the generation of API keys for users and allow multiple levels of user based on the API key (this will involve the creation of additional database resources to store the keys and access levels and a distribution mechanism)
    - Basic user (read-only access)
    - Update user (CRU access)
    - Admin user (full CRUD)
- Include testing using Jackson/JUnit, Postman and Spring Boot Test as appropriate

## User Stories

## Sprint 1

On the first day Craig created a new repository for this extension of week 5 and mirrored the week 5 repo into this new repo.
```
In up to date week 5 local repo -
git push --mirror <gitlink> 
```

We started creating a product backlog and sprint backlog with tasks derived from the given requirements.

### Plan

We worked through one controller, EmployeeController, together so we knew what we were doing.
Then once we had implemented standard CRUD methods there we split off to create the controllers for the other DAO classes we had.

| Patryk               | Hanibal                     | Abdullah          | Liam            | Cameron          | Omari         | Craig              |
|----------------------|-----------------------------|-------------------|-----------------|------------------|---------------|--------------------|
| DepartmentController | DepartmentManagerController | DeptEmpController | TitleController | SalaryController | ErrorHandling | EmployeeController |

If anyone had issues with code then they would share their screen, and we would troubleshoot together.

### Review
Most of the CRUD methods were completed, implementation of advanced methods are yet to be started.
It is harder to implement these methods than we expected.

### Retrospective

Workflow is good, progress should continue at a steady pace.