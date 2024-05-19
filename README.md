[![Build status](https://github.com/DireElf/Sight-book/actions/workflows/Tests.yml/badge.svg)](https://github.com/DireElf/Sight-book/actions/workflows/Tests.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/dcfd062924c7d771d847/maintainability)](https://codeclimate.com/github/DireElf/Sight-book/maintainability)

Для запуска потребуются: Java 8 (или выше), PostgreSQL и пользователь с соответствующими правами.     

Сборка проекта: Gradle  

Сборка и запуск на Windows:  
```
.\gradlew clean build  
.\gradlew bootRun
```  

Сборка и запуск на Linux: 
```
./gradlew clean build  
./gradlew bootRun 
```

Дефолтные настройки (можно изменить в файле src/main/resources/application.yml):  
```
порт сервера: 5000  
хост БД:      localhost (или 127.0.0.1)  
порт БД:      5432  
название БД:  sight-book  
пользователь: postgres  
пароль:       postgres   
```


Таблицы в БД будут сформированы во время сборки. 

Базовый функционал покрыт тестами (позитивные кейсы), для тестирования используется in-memory БД H2.  

По ссылке https://drive.google.com/file/d/1SbrPyYYDeu1PdeD24XaX782CpS0zitxN/view?usp=sharing   
лежит коллекция запросов для Postman (можно импортировать и использовать для тестирования).
