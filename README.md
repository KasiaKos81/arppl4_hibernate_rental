## Hibernate Car Rental - Demo Project

### Project description
The following project has been created to implement the connection with 
database & use Hibernate (ORM - Object Relational Mapper).
Project includes model 'Car', which represents a car at car rental service.
Application is handled by CarCommandLine Parser.

### Mandatory Dependencies
To launch the project it is mandatory to have:
- (local or remote) database MySQL,
- Java JRE (Java Runtime Environment)(min. Java 11)

### How to launch
To launch the project it is essential to configure the connection 
with database.
You can find the connection file template in 'scr/main/resources'.
It is necessary to copy the file and update the following fields:
```xml
        <property name="connection.url"></property> <!--TODO: zmienić adres połączenia-->
        <property name="connection.username"/> <!--TODO: wypełnić nazwę użytkownika-->
        <property name="connection.password"/> <!--TODO: wypełnić hasło-->
```
After update, change the file name from:

`template_hibernate.cfg.xml` to `hibernate.cfg.xml`.

### Author
Me (2022) (R)