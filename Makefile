clean:
	./gradlew clean

build:
	./gradlew clean build

start:
	./gradlew bootRun

install:
	./gradlew installDist

start-dist:
	./build/install/app/bin/app

test:
	./gradlew test

generate:
	gradle diffChangeLog