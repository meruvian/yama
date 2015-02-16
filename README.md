Yama
====

Meruvian Yama is an integration framework that help programmer to create a web app or a web api easily. With current version we share the same services that use by both web app and web api.

Quickstart
===

Checkout Yama from github
``` 
git clone https://github.com/meruvian/yama.git
cd yama/
git checkout 2.x
```

Change database configuration on `` webapi/src/main/resources/config/yama-dev.yml ``

Install node, bower dependency, and then run grunt `` watch `` task (run in separate console)
```
cd webapi/
npm install
bower install
grunt serve
```

Running Yama
```
cd <yama_root_directory>
mvn test -Pwebapi
```

Open your browser, go to http://localhost:8080/
