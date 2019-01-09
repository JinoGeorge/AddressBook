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
###### Add dependencies for Liqibase
```
    <dependency>
            <groupId>org.liquibase</groupId>
            <artifactId>liquibase-core</artifactId>
    </dependency>
```
###### Specify the Liquibase changelog file location in application.properties
```
spring.liquibase.change-log=classpath:/db/changelog/changelog-master.yaml
```
###### Specify the Liquibase migration files location in changelog-master.yaml
```
    databaseChangeLog:
        - includeAll:
            path: classpath*:db/changelog/migrations/
```
###### Add the H2 in memory database dependency
```
    <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
    </dependency>
```
###### Enable the H2 database console using the following property
```
    spring.h2.console.enabled=true
```
###### By default the h2 console can be opened at http://localhost:8080/h2-console
###### Once the tables are set up, you can validate the tables and entities using the hibernate validate property at startup
```
    spring.jpa.hibernate.ddl-auto=validate
```
##### 5. Create database repositories (DAO), services and resources (rest end points)
##### 6. Create batch jobs

```
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-batch</artifactId>
    <dependency>
```
###### Invoking the Job
By default, Spring runs all the job as soon as it has started its context. If you want to disable that you can define below the property
```
    spring.batch.job.enabled=false
```
##### 5. Add OAuth 2.0 security for the rest end endpoints
###### Add security dependencies
```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security.oauth</groupId>
			<artifactId>spring-security-oauth2</artifactId>
			<version>2.3.3.RELEASE</version>
		</dependency>
```
###### To read about password encoding refer blog: https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
###### Implement ResourceServerConfig, AuthorizationServerConfig, SecurityConfig and UserDetailsService
###### Create oAuth 2.0 tables for access token and refresh tokens
