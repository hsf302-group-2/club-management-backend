# University Spring Tech Club Backend

## Project Description
The Spring Tech club is a web-based application designed to manage university club memberships efficiently. The system allows guests to create accounts, register as PreMembers, submit membership forms, and be approved by administrators to become official Club Members. The goal is to streamline the membership process while keeping accurate records of all applicants and members.

## Tech Stack
- **Language:** Java
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT
- **ORM/Persistence:** JPA/Hibernate
- **Database:** MySQL
- **Email:** Java Mail 
- **Build Tool:** Maven

### Start & setup MySQL on docker

<pre> docker run --name hiv-and-medical-system -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.42-debian </pre>
<pre> docker run --name hiv-and-medical-system -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=club_management_system -d mysql:8.0.42-debian <pre>
