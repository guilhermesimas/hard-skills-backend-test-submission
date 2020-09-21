# Modec Hard Skills Backend Test Submission
This project contains my submission.
Please refer to the instructions section on how to execute the project.

## Technologies
* SpringBoot Web
* Java 11
* Gradle
* SpringBoot Data JPA
* H2

## Instructions
Please download Java 11's JDK in order to properly run the application.
After having downloaded the SDK, use GradleWrapper to run the application:
```
./gradlew bootRun
```
The application should start on port `8080`.

Please access `http://localhost:8080/swagger-ui.html` on your browser to access the API's specification.

Instruction's on how to execute commands should be clear from the hard skills test requirements and from Swagger's descriptions.

## On technology choices

On this section I will briefly touch upon some technology choices and API design decisions which went into the final product.

### SpringBoot, Java 11 and Gradle

After reading the test's requirements, SpringBoot Web's framework seemed like a safe and effective choice.
Having worked with SpringBoot on similar projects in the past, I was confident I would have the tools needed at my
disposal in order to offer persistence of resources over a RESTful API.

As for Java 11 (over other distributions), it was chosen mainly due to my familiarity with past projects. If 
performance or non-functional requirements were put into play, more time would have been invested in choosing a
more appropriate version or distribution. My choice of Gradle followed a similar line of thought than that of Java.

### H2 Database

Having read the product's description, it seemed to involve mainly well-defined entities with clear relationships among
them. As such, I opted for a relational database as to benefit from the ACID constraints of mainstream distributions.

This choice also allowed me to make use of Java's Hibernate ORM and its well documented integration with Spring Data JPA.
Opting for Hibernate also allowed me to make use of its auto-generation of ddl, saving me the effort of manually modelling
the database and managing its bootstrap process. 

Due to the nature of the project, having an in-memory database such as H2 seemed like the most effective and
effortless choice.

### API modelling

Since the project features were mainly focused on persisting and exposing resources, a RESTful API semmed like the best
match.

The relational modelling described made clear that all equipments are associated with a vessel, as as such it made sense
 to me to expose the `equipment`resource under a `vessel` resource.
 
 Features 1, 2 and 4 of the requirements fit straight away in the RESTful model:
 
*  Creating a vessel easily translated to a `POST` request to the `vessel` collection. 
* Registering an equipment could be interpreted as creating an equipment, and as such translated to a `POST` request
 to the `equipment` collection (under a `vessel` resource). 
 * Querying active equipment of a given vessel
  was exposed via a `GET` request to the `equipment` collection under the `vessel` resource.
  
   Deactivating multiple equipment by passing one or a list of `equipment` proved a little more of a challenge. 
   
  I was familiar with an approach on RESTful APIs which offered batch-like operations via `POST`ing a sort of
   `action`-like collection. The issued operation is then queryable, in order to consult
  the status of the process, being therefore a resource of types, and thus matching the RESTful model. This might, however,
  be an overengineering of the feature. 
  
  Another valid approach would be to issue a `DELETE` on the `equipment` collection, passing the target equipment list.
  This would be a very simple and effective implementation, but 
 In the end I opted for exposing the feature by `POST`ing a `deactivate` resource under the `equipment` collection. It made sense as
  the user would be creating a sort of action-like resource. 
 