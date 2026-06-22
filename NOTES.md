# Maven and Spring Boot - Quick Notes

This project was first built with plain Java. Then we added **Maven** and **Spring Boot**.
The old files are still in the project so you can compare them side by side.

---

## Part 1 - Maven

### Why do we use Maven?

Before Maven, we compiled the project by hand. We had to list every `.java` file in a long `javac` command.

Maven does three useful things for us:

1. **Compile** all Java files automatically
2. **Download libraries** we need (like Spring Boot) from the internet
3. **Run** the app with one command

Instead of many manual steps, we use:

```bash
mvn spring-boot:run
```

### What is `pom.xml`?

`pom.xml` means **Project Object Model**. It is one file that tells Maven everything about the project.

Here is our `pom.xml`, section by section:

```xml
<groupId>com.gestionale</groupId>
<artifactId>garage</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

This is the project identity, like a name tag:
- `groupId` - who owns the project (company or package name)
- `artifactId` - project name
- `version` - current version

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.11</version>
</parent>
```

We inherit ready-made settings from Spring Boot, so we do not need to configure everything ourselves.

```xml
<properties>
    <java.version>21</java.version>
</properties>
```

This sets the Java version used to compile the project.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

This is the list of external libraries we need. Maven downloads them automatically.
We only added one dependency: `spring-boot-starter-web` (web server + JSON support).

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.gestionale.garage.GarageApplication</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

This tells Maven how to run the app. The main class is `GarageApplication`.

---

## Part 2 - Spring Boot

Spring Boot is a framework that helps us build web applications with less code.
It replaces the manual HTTP server we wrote before.

### Before vs After

| What the code does | Before (legacy) | After (Spring Boot) |
|--------------------|-----------------|---------------------|
| Start the server | `VehicleHttpServer` creates `HttpServer`, binds port 8080, registers handlers | `GarageApplication` calls `SpringApplication.run()` - Spring starts Tomcat automatically |
| Handle HTTP routes | `VehicleHandler.handle()` checks method and path with `if/else` | `VehicleController` methods use `@GetMapping`, `@PostMapping`, etc. |
| Convert objects to JSON | `VehicleJson.toJson()` / `fromJson()` - written by hand | Return a `Vehicle` object; Spring converts it to JSON automatically |
| Serve `index.html` | `StaticFileHandler` reads the file and sends bytes | Put files in `src/main/resources/static/` and Spring serves them |
| Create the repository | `new VehicleRepository()` in `main` | Spring injects it automatically (`@Component`) |
| Seed sample data | `seedRepository()` in `VehicleHttpServer` | `seedRepository()` in `GarageApplication.run()` |

### Main entry point

**Before** (`VehicleHttpServer.java`):

```java
HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
server.createContext("/api/vehicles", new VehicleHandler(repository));
server.start();
```

**After** (`GarageApplication.java`):

```java
@SpringBootApplication
public class GarageApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(GarageApplication.class, args);
    }
}
```

One line starts the whole application.

### REST API

**Before** (`VehicleHandler.java`): one big `handle()` method with many `if/else` checks.

**After** (`VehicleController.java`): one method per endpoint.

```java
@GetMapping
public List<Vehicle> list() { ... }

@GetMapping("/{id}")
public ResponseEntity<Vehicle> getById(@PathVariable Long id) { ... }

@PostMapping
public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) { ... }
```

Each annotation tells Spring which URL and HTTP method the method handles.

### Annotations we use

| Annotation | What it does |
|------------|--------------|
| `@SpringBootApplication` | Marks the main class; Spring scans the project for components |
| `@RestController` | This class handles HTTP requests and returns data (JSON) |
| `@RequestMapping("/api/vehicles")` | Base URL for all methods in the class |
| `@GetMapping` | Handles HTTP GET requests |
| `@PostMapping` | Handles HTTP POST requests |
| `@PutMapping` | Handles HTTP PUT requests |
| `@DeleteMapping` | Handles HTTP DELETE requests |
| `@PathVariable` | Reads a value from the URL (example: `/api/vehicles/3` -> `id = 3`) |
| `@RequestBody` | Converts JSON from the request body into a Java object |
| `@Component` | Tells Spring to create and manage this class automatically |

### Dependency injection (simple explanation)

**Before:** you create objects yourself.

```java
VehicleRepository repository = new VehicleRepository();
```

**After:** Spring creates the object and passes it to you.

```java
public VehicleController(VehicleRepository repository) {
    this.repository = repository;
}
```

You only declare what you need in the constructor. Spring finds `VehicleRepository` (because of `@Component`) and injects it.

### How to run the app

**Spring Boot (new version):**

```bash
mvn spring-boot:run
```

Or run `GarageApplication` from VS Code.

**Legacy version (for comparison):**

Run `VehicleHttpServer` from VS Code.

Both use port 8080, so run only one at a time.

- UI: `http://localhost:8080/`
- API: `http://localhost:8080/api/vehicles`

---

## Files to compare

| Legacy (before) | Spring Boot (after) |
|-----------------|---------------------|
| `VehicleHttpServer.java` | `GarageApplication.java` |
| `VehicleHandler.java` | `VehicleController.java` |
| `VehicleJson.java` | not needed (Jackson handles JSON) |
| `StaticFileHandler.java` | `src/main/resources/static/index.html` |
| `public/index.html` | `src/main/resources/static/index.html` |

Open both versions side by side to see the difference.
