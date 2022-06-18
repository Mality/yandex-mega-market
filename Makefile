build:
	docker build -t eziganshin/mega-market .
run:
	docker run -p 80:8080 -d --name mega-market eziganshin/mega-market
stop:
	docker stop mega-market
