# Koi Wiki

## Development guide

### IntelliJ

The project have multiple runners to help developers :

- Maven [clean, compile]
- Start API
- Start WebApp
- Run Development Docker Containers
- Run All API Tests
- Run All WebApp Tests

### Keycloak configuration for development

1. First, run keycloak development docker container
2. Login into administration console : `http://localhost:8082`
3. Create `koi-wiki` realm
    - Realm name : koi-wiki
    - Enabled: true
4. Create `koi-wiki-client` client in `koi-wiki` realm
    - Clients > Create client
        - Client type : OpenID Connect
        - Client ID : koi-wiki-api
        - Next
        - Client authentication : Off
        - Authorization : Off
        - Standard flow : On
        - Direct access grants : On
        - Implicit flow : Off
        - Service accounts roles : Off
        - OAuth 2.0 Device Authorization Grant : Off
        - OIDC CIBA Grant : Off
    - Update client (koi-wiki-api)
      - Clients
        - Valid redirect URIs : *
        - Valid post logout redirect URIs : *
        - Web Origins : *
5. Create all roles in koi-wiki (See section : Keycloak Configuration to see which roles)
6. Create many groups/users that you need for your tests and developments
    1. (Optional) Create users (See section : Create a user)
        - root
        - admin
        - user
    2. (Optional) Map roles to users
        1. User: root
            - create-article-category
            - read-article-category
            - read-deleted-article-category
            - update-article-category
            - update-deleted-article-category
            - delete-article-category
            - delete-deleted-article-category
            - search-article-category
            - search-deleted-article-category
        2. User: admin
            - create-article-category
            - read-article-category
            - update-article-category
            - delete-article-category
            - search-article-category
        3. User: user
            - No roles

#### Create a user

- Users > Create a new user
    - Username : <username>
    - Email : <email>
    - Email verified : On
    - Create
    - Credentials > Set password
        - Password : <password>
        - Password confirmation : <password>
        - Temporary : Off

### Environment variables for WebApp

In dev and prod environments, the WebApp need environments variables in `app/webapp/src/environments/environment.ts`
file.

See `Build WebApp` to see how to set environments variables values.

This file is not tracked by git.

## Deployment guide

### Build and push API docker image

Build .jar file :

```bash
cd app/api # Go into API source root directory
mvn package
```

Build the API image :

```bash
cd app/api # Go into API source root directory
docker build . -t <repo>/<image>[:<tag>] # Build the image
```

Example :

```bash
docker build . -t knifeonlyi/koi-wiki-api:1.0.0
```

Push the image in registry :

```bash
docker push <repo>/<image>[:<tag>] # Push the specified image in registry
```

Example :

```bash
docker push knifeonlyi/koi-wiki-api:1.0.0
```

### Build WebApp

Update environments file (`app/webapp/src/environments/environment.ts`) following this example :

```js
const appTitle = 'Koi Wiki'
const apiHost = 'http://localhost';
const apiPort = 8080;
const baseApiUrl = `${apiHost}:${apiPort}/api/v1`;
const baseKeycloakUrl = 'http://localhost:8082';
const keycloakRealm = 'koi-wiki';
const keycloakClientId = 'koi-wiki-api';

export const environment = {
    appTitle,
    baseApiUrl,
    baseKeycloakUrl,
    keycloakRealm,
    keycloakClientId
};
```

Build dist files :

```bash
cd /app/webapp # Go into WebApp source root directory
npm run build
```

## Configuration

### Keycloak

Koi Wiki app need some realms role :

- create-article-category
- read-article-category
- read-deleted-article-category
- update-article-category
- update-deleted-article-category
- delete-article-category
- delete-deleted-article-category
- search-article-category
- search-deleted-article-category