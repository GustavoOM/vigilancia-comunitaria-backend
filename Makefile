IMG = latest

# Rodar make docker IMG=1.x+1
docker:
    docker build -t projeto:$(IMG) . --no-cache && docker tag projeto:$(IMG) raquelvaladaojs/projeto:$(IMG) && docker push raquelvaladaojs/projeto:$(IMG)

#TODO deploy GCP
publish:
    echo "Hello World"

run:
    docker run -d -p 80:8080 --env-file ./.env raquelvaladaojs/projeto:$(IMG)
