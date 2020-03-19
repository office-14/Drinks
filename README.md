[![Build Status](https://img.shields.io/travis/office-14/Drinks?style=for-the-badge)](https://travis-ci.com/office-14/Drinks)
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
- `http://localhost:8081` to see web admin;
- `http://localhost:5000/swagger` to see web API OpenAPI/Swagger documentation.

If you want to start only one of the components (Web API or anything) then use the following command:

```bash
$ docker-compose -f docker-compose.yml -f docker-compose-dev.yml up --build webapi
```

### How to enable Firebase Cloud Messaging in web-api?

To enable Firebase Cloud Messsaging in `web-api`, you need to provide [_Google Service Account_](https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk) credentials.

To authenticate a service account and authorize it to access Firebase services, you must generate a private key file in JSON format.

To generate a private key file for your service account:

- In the Firebase console, open **Settings** > [**Service Accounts**](https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk).
- Click **Generate New Private Key**, then confirm by clicking **Generate Key**.
- Name this file `firebase-sdk-sa.json` and put/replace it in `./web-api/firebase/` folder.

**BE CAREFUL!** Don't put this file under source control because it contains very sensitive credentials!
