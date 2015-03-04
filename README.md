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
Checkout Yama from github
``` 
git clone https://github.com/meruvian/yama.git
cd yama/
git checkout 2.x
```
Change database configuration on `` webapi/src/main/resources/config/yama-dev.yml ``
Create database schema
Install node dependency
```
$ npm install
$ grunt init
```
Run Web API
```
$ grunt server
```
Run Web Application (run in separate console)
```
grunt client
```
Open your browser, go to http://localhost:8081/ (user/passwd: administrator/admin123)
