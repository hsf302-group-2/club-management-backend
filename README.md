# backend

### Start & setup MySQL on docker

<pre> docker run --name hiv-and-medical-system -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.42-debian </pre>
<pre> docker run --name hiv-and-medical-system -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=club_management_system -d mysql:8.0.42-debian <pre>
