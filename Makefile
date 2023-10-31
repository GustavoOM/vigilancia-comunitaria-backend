IMG = 1.5

docker:
	$(call build)

publish:
	$(call publish)

define build
#	TODO: docker login pipeline
    docker build -t projeto:$(IMG) . --no-cache && \
    docker tag projeto:$(IMG) raquelvaladaojs/projeto:$(IMG) && \
    docker push raquelvaladaojs/projeto:$(IMG)
endef

define publish
    echo "TODO"
endef