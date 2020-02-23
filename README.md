[![Built by engineers](https://img.shields.io/badge/built_by-engineers-success?style=for-the-badge)](https://github.com/office-14)

# Drinks

### How to deploy dev environment locally?

First, you need to install [Docker](https://www.docker.com/) and run it.

To start components locally use the following command (from inside the folder containing `docker-compose.yml` file):

```bash
$ docker-compose -f docker-compose.yml -f docker-compose-dev.yml up --build
```

If everything's fine, you should be able to visit:

- `http://localhost:8080` to see web client;
- `http://localhost:5000/swagger` to see OpenAPI/Swagge documentation.
