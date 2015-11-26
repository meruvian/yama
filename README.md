Yama
====

Meruvian Yama is an integration framework that help programmer to create a web app or a web api easily. With current version we share the same services that use by both web app and web api.

### Prerequisites
* JDK >= 1.6
* Maven 3
* MySQL
* Nodejs
* Grunt CLI
* Bower

### Quickstart
##### Running Yama
Generate from archetype

```
mvn archetype:generate \
-DarchetypeGroupId=org.meruvian.yama \
-DarchetypeArtifactId=yama-starter-archetype \
-DarchetypeVersion=2.0.0.Beta2
```

Change database configuration on `` webapi/src/main/resources/config/yama-dev.yml ``
Create database schema

Install node and bower dependency in `` webpp `` directory
```
$ cd <yama-root-directory>/webapp
$ npm install
$ bower install
```
Run Yama
```
$ cd <yama-root-directory>
$ mvn test -Pwebapi
```
Open your browser, the application will be available at http://localhost:8080/ (user/passwd: administrator/admin123)

If you want to use grunt for frontend automation (livereload, jslint, etc) run following command in `` webapp `` directory, make sure you've installed grunt-cli on your computer
```
$ grunt serve
```
Your browser will automatically open http://localhost:8081/

### Production
##### Package Yama as Production WAR
To package application as WAR (without building frontend), type:
```
$ mvn package
```
If you want to package application with "production" frontend, activate `` prod `` profile by typing:
```
$ mvn package -Pprod
```

This will generate war file on ```webapi/target``` directory:
* ``` yama-webapi-1.0-SNAPSHOT.war ```; and
* ``` yama-webapi-1.0-SNAPSHOT.war.original ```
 
##### Running Yama without an Application Server
Yama comes with embedded Jetty server, instead of deploying to an application server you can always execute WAR file by typing:
```
$ java -jar yama-webapi-1.0-SNAPSHOT.war
```
this will run yama in embedded jetty server in production mode (the default profile is ``prod``), if you want to run Yama in development mode, type:
```
$ java -jar yama-webapi-1.0-SNAPSHOT.war --spring.profiles.active=dev
```
