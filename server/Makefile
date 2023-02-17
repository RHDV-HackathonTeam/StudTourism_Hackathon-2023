#---------------- Requirements ----------------
.PHONY freeze:
freeze:
	pip freeze > requirements.txt

.PHONY deps:
deps:
	pip install -r requirements.txt

#---------------- Docker-Compose --------------------------------
.PHONY docker_build:
docker_build:
	sudo docker-compose -f docker-compose.slave.dev.yml build

.PHONY docker_up:
docker_up:
	sudo docker-compose -f docker-compose.slave.dev.yml up

.PHONY docker_upd:
docker_upd:
	sudo docker-compose -f docker-compose.slave.dev.yml up -d

.PHONY grep_ipaddr: # for connect to dev db in docker container
grep_ipaddr:
	docker inspect pgdb | grep IPAddress

#---------------- Clean cache ----------------
.PHONY clean:
clean:
	cd app/ && find . -name __pycache__ -type d -print0|xargs -0 rm -r --

#---------------- Git ----------------
.PHONY gadd:
gadd:
	git add .

.PHONY gcom:
gcom: # git commit -am "{com}" | com=<your comment>
	git commit -am "$(com)"

.PHONY gpush_dev:
gpush_dev:
	git push origin dev

.PHONY gpull:
gpull:
	git pull
