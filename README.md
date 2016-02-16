# rs.images
REstFul Web Service for Images

default profile is dev:

	mvn -P dev spring-boot:run

production profile is prod:

	mvn -P prod spring-boot:run

test profile is test

	mvn -P test spring-boot:run
	
Generate jooq source

dev:

	mvn -P dev-jooq clean install

prod:
	
	mvn -P prod-jooq clean install
	
test:
	
	mvn -P prod-jooq clean install
