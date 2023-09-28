# Football_Standings
The project finds standings of a team playing league football match using country name, league name and team name.

# To test code
1. To hit the endpoint using terminal use Curl command. Example:
```http://localhost:8080/api/v1/countries?countryName=England&leagueName=Non%20League%20Premier&teamName=Chatham%20Town&isClientOffline=false```.
2. You can also use Postman to hit the endpoint and get required results.
3. The above code is also configured with springdoc-openapi java library which helps to automate the generation of API documentation using spring boot projects. Hence, you can also use Swagger UI, to test your changes.

# To integrate code with Jenkins
1. Install [Jenkins](https://www.jenkins.io/doc/book/installing/) and complete the setup.
2. Create a new item and mention Git repository in the configuration.
3. In configuration, under build steps, add `Goals: install` in Invoke top-level Maven targets.
4. Add another build step, Docker Build and Publish. Under repository name, add the name with which you want to create Docker repository and add Jenkins login credentials under Registry credentials.
5. Click on Apply and Save.
6. Build the project. 

# Reference Documentation
For further reference, please consider the following sections:

1. [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
2. [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/htmlsingle/)
3. [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/htmlsingle/#build-image)
4. [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#web)

# Guides
The following guides illustrate how to use some features concretely:

1. [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
2. [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
3. [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
4. [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)

