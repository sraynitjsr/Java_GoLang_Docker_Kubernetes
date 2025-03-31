DOCKER_REPO=<Docker_Repo>
JAVA_IMAGE=java-app:latest
GO_IMAGE=go-app:latest

build-java:
	docker build -t $(DOCKER_REPO)/$(JAVA_IMAGE) ./java-app

build-go:
	docker build -t $(DOCKER_REPO)/$(GO_IMAGE) ./go-app

push-java:
	docker push $(DOCKER_REPO)/$(JAVA_IMAGE)

push-go:
	docker push $(DOCKER_REPO)/$(GO_IMAGE)

apply-java:
	kubectl apply -f java-app/kubernetes/configmap.yaml
	kubectl apply -f java-app/kubernetes/deployment.yaml
	kubectl apply -f java-app/kubernetes/service.yaml

apply-go:
	kubectl apply -f go-app/kubernetes/deployment.yaml
	kubectl apply -f go-app/kubernetes/service.yaml

deploy: build-java build-go push-java push-go apply-java apply-go
