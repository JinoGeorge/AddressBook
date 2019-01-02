## Development Process - Step by Step

##### 1. Create the project template using spring-boot CLI
spring init --description="Address Book" -n=AddressBook -g=com.addressbook -a=AddressBook --package-name=com.addressbook --build=maven -j=1.8 -p=jar -d=web -x 

##### 2. Create domain objects
###### Add lombok dependency
```
    <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.4</version>
            <scope>provided</scope>
    </dependency>
```

###### Define domain objects: Contact, ContactPreferences
##### 3. 