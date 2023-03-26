# click-speed-backend

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/diegorigo90/click-speed-backend)


## How to run

Launch the docker compose in this example the image pull is: https://hub.docker.com/r/nicholasmantovani23/speed-click

```bash
docker compose up
```

Open [http://localhost:8085](http://localhost:8085) with your browser to see the application.

## How to build

Build the image
```bash
docker build -t speed-click:latest .
```

Tag the image correctly
```bash
docker tag speed-click:latest <username>/speed-click:latest
```

## Push the image

Login with your docker credentials
```bash
docker login -u <username>
```

Create a repository https://hub.docker.com/repositories/

Push the image to the repository

```bash
docker push <username>/<image>:<tag>
```

