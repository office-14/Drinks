### How to deploy dev environment locally?

First, you need to install [Docker](https://www.docker.com/) and run it.

To start web api service locally use the following command (from inside the folder containing `docker-compose.yml` file):

```bash
$ docker-compose -f docker-compose.yml -f docker-compose-dev.yml up --build
```

If everything's fine, you should be able to visit `http://localhost:5000/swagger` and to see OpenAPI/Swagger documentation.

### How to enable live reloading during development?

Use the following command to restart the service when source code is changed:

```bash
$ dotnet watch --project src/Project.API/ run
```
