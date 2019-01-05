## Development Process - Step by Step

##### 1. Create the project template using spring-boot CLI
spring init --description="Address Book" -n=AddressBook -g=com.addressbook -a=AddressBook --package-name=com.addressbook --build=maven -j=1.8 -p=jar -d=web -x 

##### 2. Create domain objects
###### Define domain objects: Contact, ContactPreferences
##### 3. Make the domain objects persistable.
###### Add JPA starter dependency
```
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
```
###### Create BaseEntity with primary key field using UUID. Annotate the Id field with @Id and @GeneratedValue
###### Convert domain objects to entities using @Entity annotation and extend the class from BaseEntity
###### Add Lombok dependency
```
    <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.4</version>
            <scope>provided</scope>
    </dependency>
```
###### Add Lombok annotations for the common methods
```
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString(callSuper = true, onlyExplicitlyIncluded = true)
```
###### Add validation annotations for domain objects
##### 4. Create Tables with Liquibase and setup database connection

