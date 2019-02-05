# AddressBook
Manages the 'Addresses and Contact Details' shared among multiple users

#### How to build and run the application
1. Clone the project repository from Github
 ~~~~
 $ git clone https://github.com/JinoGeorge/AddressBook.git
 ~~~~
2. From the project base directory, build the docker image
 ~~~~
 $ docker build -t addressbook:v1 .
 ~~~~
3. Run the project from the local image created
 ~~~~
 $ docker run -p9090:9090 addressbook:v1
 ~~~~

#### Use cases
1.Get access token for default admin user
default values: client_id = client_id, client_secret=client_secret, username=admin, password=admin
 ~~~~
 $ curl -X POST http://localhost:9090/addressbook/oauth/token \
       -u "client_id:client_secret" \
       -d "grant_type=password" \
       -d "username=admin" \
       -d "password=admin"
 ~~~~
2.Use the access_token from the response to make the next call to
create a new user using the path addressbook/users
 ~~~~
$ curl -X POST   'http://localhost:9090/addressbook/users?access_token=52ff3354-b701-417b-9faf-b17ec17631b1' \
  -H 'Content-Type: application/json'  \
  -d '{
 "username": "newuser",
 "password": "newpassword",
 "roles": ["USER"]
}'
 ~~~~
3.Get access token for the newly created user
 ~~~~
 $ curl -X POST http://localhost:9090/addressbook/oauth/token \
   -u "client_id:client_secret" \
   -d "grant_type=password" \
   -d "username=newuser" \
   -d "password=newpassword"
 ~~~~
 4.Create a new contact data using the access_token.
 ~~~~
$ curl -X POST \
  'http://localhost:9090/addressbook/contacts?access_token=a62b0050-de53-4612-9952-bec6004f9a00' \
  -H 'Content-Type: application/json' \
  -d '{
	"firstName":"Adam",
	"lastName":"Simon",
	"birthDate":"2000-01-01",
	"email":"adam.simon@test.com"
}'
 ~~~~
5.When you create a contact the reponse will have the url to get that data within the localtion header.
 ~~~~
$ curl -X GET \
  'http://localhost:9090/addressbook/contacts/aa873865-4a37-4bb6-a40e-195d544e9d2a?access_token=a62b0050-de53-4612-9952-bec6004f9a00'
 ~~~~
