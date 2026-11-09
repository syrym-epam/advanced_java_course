# Java gRPC Demo Project

A simple gRPC client-server application built with Java, Protocol Buffers (`proto3`), and Maven.

## Prerequisites

Ensure you have the following installed on your machine:
* **Java Development Kit (JDK) 25+** (or your configured Java version)
* **Apache Maven** (3.8+)

---

## Project Structure

```text
your-project-folder/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── grpc/
│   │   │           └── demo/
│   │   │               └── grpc/
│   │   │                   ├── ProductGrpcServer.java
│   │   │                   └── ProductGrpcClient.java
│   │   └── proto/
│   │       └── product.proto
├── pom.xml
└── README.md
```


## Step 1: Build the Project
Before running the server or client, you need to compile the .proto file so Maven can generate the required Java stubs.
Open your terminal in the project root directory and run: 

```Bash
mvn clean compile
```
Verification: The build should complete successfully with a BUILD SUCCESS message, and generated source files will appear in your target/generated-sources directory.
## Step 2: Run the gRPC Server
The server listens for incoming requests on port 8080.
Open a new terminal window in your project root directory.
Start the server using the Maven execution plugin:

```Bash
mvn exec:java -Dexec.mainClass="com.grpc.demo.grpc.ProductGrpcServer"
```
Verification: You should see terminal logs indicating the server has started:

```Plaintext
INFO: Server started, listening on 8080
```
## Step 3: Run the gRPC Client
With the server running, you can now trigger a gRPC request from your client.
Open a second terminal window in your project root directory (leave the server running in the first one).
Execute the client command:

```Bash
mvn clean compile exec:java -Dexec.mainClass="com.grpc.demo.grpc.ProductGrpcClient"
```
Verification: The client will connect to localhost:8080, query Product ID 42, and output the response:
```Plaintext
--- Received Response from gRPC Server ---
Product ID   : 42
Product Name : Sample Product 42
Product Price: 99.99
```