IMG = 1.6
DOCKER_USER = user
DOCKER_PSWD = psswd
AZURE_USER = user
AZURE_PSWD = psswd

docker:
	$(call buildfn)

publish:
	$(call publishfn)

define buildfn
    docker build -t projeto:$(IMG) . --no-cache
    docker tag projeto:$(IMG) raquelvaladaojs/projeto:$(IMG)
    docker push raquelvaladaojs/projeto:$(IMG)
endef

define publishfn
	az login --user $(AZURE_USER) --password $(AZURE_PSWD)
	az webapp config container set --name vigilancia-comunitaria --resource-group scc --docker-custom-image-name raquelvaladaojs/projeto:$(IMG) --docker-registry-server-url  http://registry.hub.docker.com --docker-registry-server-user $(DOCKER_USER) --docker-registry-server-password $(DOCKER_PSWD)
endef