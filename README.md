Для запуска требуется: Java 8 (или выше), PostgreSQL и пользователь с соответствующими правами
Сборка: Gradle

Сборка и запуск на Windows:
gradle clean build
gradle bootRun

Сборка и запуск на Linux:
./gradlew clean build
./gradlew bootRun

Дефолтные настройки (можно изменить в файле src/main/resources/application.yml):
порт сервера: 5000
хост БД:      localhost (или 127.0.0.1)
название БД:  sight-book
пользователь: postgres
пароль:       postgres 

Таблицы в БД будут сформированы во время сборки
В файле sight-book.postman_collection.json в корне проекта есть коллекция запросов для Postman
(можно импортировать и использовать для тестирования)