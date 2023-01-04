# api-users
Api para registro y logeo de usuarios.

Proporciona un token de duración de 30 días ó hasta nuevo logeo.

[GitHub](https://github.com/agusftorres/api-users)
###Stack
- Java 17
- Spring 3
- Spring security & JWT
- H2 Database

### Diagrama
![alt text](docs/diagrama.png)

### Endpoints
- para registro: 
```
/api/v1/user/signin
```
- para logeo
```
/api/v1/user/login
```
### Requisitos para los campos
- password: debe contener un numero, una letra minuscula, una mayuscula y debe tener al menos 8 caracteres
- email y name no deben ser nulos
### CURLs exitosos
- registro
```
curl --location --request POST 'localhost:8080/api/v1/user/signin' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=6EB5CA2BE92FAB1BBF5071A20C7C3A60' \
--data-raw '{
    "name": "John Doe",
    "email": "john@doe.com",
    "password": "Aapple$123",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}'
```
- login
```
curl --location --request PUT 'localhost:8080/api/v1/user/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=6EB5CA2BE92FAB1BBF5071A20C7C3A60' \
--data-raw '{
    "email": "john@doe.com",
    "password": "Aapple$123"
}'
```
### Respuesta esperada
```
    {
      "uuid": "43912715-e1cc-4b09-89f4-c8618b56b815",
      "name": "John Doe",
      "email": "john@doe.com",
      "phones": [
          {
              "id": 1,
              "number": "1234567",
              "cityCode": "1",
              "countryCode": "57"
          }
      ],
      "created": "2023-01-04T10:24:39.046796",
      "modified": "2023-01-04T10:24:39.046841",
      "lastLogin": "2023-01-04T10:31:29.403906",
      "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZ3VzQGFndXN0aW5hLmNvbSIsImV4cCI6MTY3NTQzMTA4OSwibmFtZSI6ImFndXMifQ.u8RXUeYc8MwZEpi0IRJVvUJCG8x-r7ZrlAKaR_48Ztkov-rQM_-wnWyKyFprhB-0Myx6aN4fao1jz3KKKL5INg",
      "active": true
    }
```
### Errores esperados
- invalid request
```
  {
    name": "Bad Request",
    "status": 400,
    "message": "Algunos campos no son correctos",
    "detail": {
      "password": "debe coincidir con \"(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}\""
    }
  }
 ```
- email existente
```
  {
    "name": "Conflict",
    "status": 409,
    "message": "El email ya esta en uso",
    "detail": {
      "com.module.apiusers.exception.BusinessException": "El email ya esta en uso"
    }
  }
```
- credenciales incorrectas
```
  {
    "name": "Conflict",
    "status": 409,
    "message": "La combinación de datos no es correcta",
    "detail": {
      "com.module.apiusers.exception.BusinessException": "La combinación de datos no es correcta"
    }
  }
```
### Scripts
CREATE TABLE USERS (
uuid UUID PRIMARY KEY,
name VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
created VARCHAR(255),
modified VARCHAR(255),
last_login VARCHAR(255),
token VARCHAR(255),
is_active BOOLEAN
);

CREATE TABLE PHONE (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
number VARCHAR(255),
city_code VARCHAR(255),
country_code VARCHAR(255)
);

CREATE TABLE users_phone (
users_id UUID,
phone_id BIGINT,
FOREIGN KEY (users_id) REFERENCES USERS(uuid),
FOREIGN KEY (phone_id) REFERENCES PHONE(id)
);
