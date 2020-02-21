# Spring Boot + JPA Starter

## Running Locally
1. Make sure Postgres is running locally with default settings (see instructions below)
1. Run `com.davidagood.springbootjpa.SpringBootJpaStarterApplication` or run `./gradlew bootRun`
1. Starts @ [http://localhost:8080](http://localhost:8080)
1. Supports basic CRUD operations:
    1. `GET /relation`
    1. `POST /relation?parent=<parentId>&child=<childId>`
    1. `DELETE /relation`
1. Supports seeding an arbitrary number of relations:
    `POST /seed/{count}`
    
## Build
Run `./gradlew build`

## Running Postgres with Docker

- `docker run -d --name sb-jpa-starter -v sb-jpa-starter-data:/var/lib/postgresql/data -p 5432:5432 postgres:latest`
- `docker logs -f sb-jpa-starter`
- `docker exec -it sb-jpa-starter psql -U postgres`
    - `\l` to list databases
    - `CREATE DATABASE test;`
    - `Ctrl + D` to quit
- You can now either start the app and table creation will be handled automatically 
(Spring runs `resources/schema.sql`) or you can manually create the tables and insert 
some data:  
    - `\c test` to change to `test` database
    - Run the statements in `resources/schema.sql`
    - `INSERT INTO relation(id, parentId, childId, createdAt) VALUES (1, 1, 2, timezone('UTC'::text, now()));`
    - `INSERT INTO relation(id, parentId, childId) VALUES (2, 2, 3);`
    - `SELECT * FROM relation;`
    - `Ctrl + D` to quit
