# Nerds-CoAssignment

# SSO Auth

## Overview
This project contains two modules:
- **SSOProvider**: Spring Boot backend service acting as an SSO Provider
- **POCApplication**: The Client Application - not working


## Running the Application in IntelliJ

### Prerequisites
- JDK 17 (or your project's Java version) installed
- IntelliJ IDEA (Community or Ultimate)
- Gradle configured (depending on your build tool)

### Steps

1. **Import the project:**
    - Open IntelliJ IDEA.
    - Select `File > Open` and choose the root directory of the project.
    - IntelliJ will detect the modules and import the project.

2. **Build the project:**
    - Use the Maven/Gradle tool window to run `clean install` or `build`.
    - Make sure there are no compilation errors.

3. **Run modules:**
    - Currently, not having a front-end, but can run the back-end.
    - Open the main class for each Spring Boot module (usually annotated with `@SpringBootApplication`).
        - For example: `SSOProviderApplication.java`.
    - Right-click on the main class > `Run 'SSOProviderApplication'`.

4. **Access the applications:**
    - Backend services usually start on `http://localhost:8091` .
    - Check logs in IntelliJ console for exact ports.
   

---

If you face any issues, verify dependencies and your JDK setup.

