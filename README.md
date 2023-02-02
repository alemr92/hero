
# Hero Demo API


## Features

- Create Hero
- Update Hero
- Delete Hero by Id / name
- Search Hero by Id / name
- Get all Heroes
- Basic security

## Access


```bash
http://localhost:8080/
```

Swagger
 
```bash
http://localhost:8080/swagger-ui/index.html#
```

## Endpoints
#### GET:
```bash
/hero 
/{id}
/name/{keywords}
/name2/{keywords}
```
#### POST
#### PUT
#### DELETE:
```bash
/{id}
/name/{keywords}
```
## Authentication


Go to Swagger. Use next values: 
```bash
user: admin
pwd: admin
```

## Tests

Code includes next unitaires tests:

```bash
  Integration Tests:
  HeroServiceIT
```

```bash
  Integration Tests:
  HeroServiceTest
```

## FAQ

#### Has it any DDL library?

Flyway has been implemented. Files are stored in db/migration.

#### Is API Dock?

For use it like docker API you should use next command:
(JAVA 11 required)
```bash
docker build -t app .

docker run -p 8080:8080 app
```

#### Has any cache?

Next endpoint has cache implemented

```bash
getAllHeroes
```

#### Has it two same endpoints?

Searching has two endpoints: 
By JPARepository and By Query (2) . 
Same results, differents methods.

