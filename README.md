Yama
====

Meruvian Yama is an integration framework that help programmer to create a web app or a web api easily. With current version we share the same services that use by both web app and web api.

To run Yama
Web version /Struts
mvn test -Pyama-struts-run

Web API version / JAXRS
mvn test -Pyama-jaxrs-re-run

Please change the yama.properties under /src/main/resource
# database setting
db.url=jdbc:mysql://localhost:3306/yama

db.driver=com.mysql.jdbc.Driver

db.datasource=com.mysql.jdbc.jdbc2.optional.MysqlDataSource

db.username=root

db.password=meruvian

# initialization
init.username=admin

init.password=admin123

init.email=yama@meruvian.org

init.role.admin=administrator

init.role.user=user

Socmed integration 
# facebook
social.facebook.active=true

social.facebook.appId=645592102187632

social.facebook.appSecret=88ee3cdd671536f452879042a85ae780

social.facebook.redirectUri=http://localhost:8080/login/social/facebook/callback

social.facebook.scope=email,public_profile,user_friends

social.facebook.state=6234

# Google+
====
social.google.active=true

social.google.appId=677526068106-q8676gk7b3tehcqb9jpgamjc5d8rhq5p.apps.googleusercontent.com

social.google.appSecret=gCD9-CcaPVJrw_CClm7lXJcI

social.google.redirectUri=http://localhost:8080/login/social/google/callback

social.google.scope=https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email

social.google.state=6234

Login Security
# recaptcha
recaptcha.active=true

recaptcha.public.key=6LdoBdESAAAAAHXfBTxpdn0gdlo3Ov3oLaOf4Jeh

recaptcha.private.key=6LdoBdESAAAAAKF_QC5MSRVwLbXvozXLjljUFEsG

recaptcha.include.noscript=false



