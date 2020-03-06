# Steps/Thoughts
* Created initial project structure:
    * For proper separation two different Git repositories could be used. But for simplicity I decided to have one
    multi-project build, which holds two services.
    * Added git ignore
    * Added readme file (current)
    * Created git repo on GitHub
    * Configured remote
    * Pushing initial commit
* Investigated the gRPC
    * Added common gRPC dependencies to the parent pom
* Added Spring boot and Jersey dependencies to the proxy-service module
* Polished initial structure
* Proceed with core project structure configuration
* Jersey and REST endpoint(s) configuration
* Completed the basic flow without unit tests, exception handling and fibonacci function
* Unit tests for fibonacci-server (producer) added (basic happy path scenario).

# Library picks and decisions
SpringBoot for out of the box application server (Tomcat), dependency injection, fast enough configuration, good documentation, good integration
with other libraries.
For restful endpoints I picked up Jersey (full implementation of JAX-RS) because it's lightweight, convenient, and provides a large variety
of options for restful endpoints.
I picked up maven as a build tool and decided to use multi module build. Generated artifacts should be deployed separately, but,
if necessary - for example maven assembly plugin could generate a single fat jar with all dependencies.
gRPC/protobuf were taken for separation, low coupling and declaration of protocol. Also for good and fast generation of all necessary
abstractions (service, messages, contracts).
JUnit was taken as a main and good supported unit testing framework. It also fits for gRPC testing library purposes.
Also I used some Scala code with scala-maven-plugin for good functional implementation of lazy fibonacci numbers generation. Scala is good 
because of full compatibility with Java, JVM language with mixed OOP and FP paradigms.  
Of course as a version control system I picked up Git but also mercurial is good enough.

#Setup guide

#Preconditions
* Java8, Maven, Git
* Intellij Idea plugins:
    * Lombok
    * Multirun
    
#Run book
* Read the `*.pdf` from the assignment folder that located in the repo root.
This will help you to understand the expected behaviour of the solution.
* Build the root project:`mvn clean install`
* Configure Idea multirun configuration (if you're running this example locally on your machine).
Entry point classes to execute are: ProxyServiceApplication, ProducerApplication.
You can also run this applications separately but both of them should be up and running at the same time to
perform the result.
* Proceed to the `localhost:8083/fibonacci/{pathParam}` and get your stream result sequence.
For traversing your response you should query endpoint again and again.
The value that indicates that sequence is finished is `-1`.
