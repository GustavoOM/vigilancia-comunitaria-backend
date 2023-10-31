IMG = 1.5

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
    echo "TODO"
endef