.PHONY: build run test package clean help

help:
	@echo "make build    - compile"
	@echo "make run      - jalankan game"
	@echo "make test     - jalankan unit test"
	@echo "make package  - buat target/nangor-blast.jar"
	@echo "make clean    - hapus hasil build"

build:
	mvn -q compile

run: package
	java -jar target/nangor-blast.jar

test:
	mvn -q test

package:
	mvn -q -DskipTests package

clean:
	mvn -q clean
