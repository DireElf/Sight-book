Для запуска потребуются: Java 8 (или выше), PostgreSQL и пользователь с соответствующими правами.   

Сборка проекта: Gradle  

Сборка и запуск на Windows:  
```
gradle clean build  
gradle bootRun
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

По ссылке https://drive.google.com/file/d/1SbrPyYYDeu1PdeD24XaX782CpS0zitxN/view?usp=sharing   
лежит коллекция запросов для Postman (можно импортировать и использовать для тестирования).
