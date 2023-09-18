# deepak251222-country-api-jwt-filter-sorting

Country REST API 

This is a REST API project that includes JWT Token Authentication, Pagination,
and Filtering features. It does not use any database and is built using Java
17 and the following technologies:

Using Into this Project
1.IntelliJ IDEA
2.Postman
3.Maven Porject
4.java 17
(i did not use any database)

following technologies:
- Spring Security for Authentication
- JWT Token for Access Control
- RestTemplate for making external API calls
- 
Running the Project
To run this project, follow these steps:
1. Clone the project to your local machine.
2. Open it in your preferred Integrated Development Environment (IDE), such as
IntelliJ IDEA or Spring Tool Suite (STS).
3. Add the project as an existing Maven project in your IDE.
4. The project should automatically download all required dependencies.
5. Try to run the project.


## endpoint url
1. Logging In (Getting JWT Token)
- **Endpoint:** POST Request - localhost:8081/api/credential/login
- **Request Body:json
{
"username": "deepak123",
"password": "deepak"
}
or
{
"username":"kumar123",
"password":"kumar"
}
(i add to user only)
It will return a JWT token.

2. Get Country by country name
Endpoint: GET Request -localhost:8081/api/country/{name}

      like:-'localhost:8081/api/country/eesti'
      {name}-pass country name
Authorization: Choose "Bearer Token" and pass the token obtained during login.
If the token is valid, it will return the result.

4. Getting All Country Details by Filter and Pagination
Endpoint: GET Request - localhost:8081/api/country/filter/{field name}/{sortOrder}/{page}/{pageSize}
like :- localhost:8081/api/country/filter/languages/asc/1/10

field-name-> languages,area,population
sortOrder->asc,desc
For ascending order, use "asc" for sortOrder.
For descending order, use "desc" for sortOrder.
Authorization: Choose "Bearer Token" and pass the token obtained during login.
If the token is valid, it will return the result.
                      Or
Alternatively, you can also use form-data in the request body with the
following parameters:
Endpoint: GET Request -> localhost:8081/api/country/filter
pass into boyd form data ->
                         sortBy: population
                         sortOrder: asc or desc
                         page: 1
                         pageSize: 4

6. Getting All Country Details
Endpoint: GET Request - localhost:8081/api/country
Authorization: Choose "Bearer Token" and pass the token obtained during login.
If the token is valid, it will return the result.
                                                                       Thanks You!
