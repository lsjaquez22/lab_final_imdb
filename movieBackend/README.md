# Backend documentation

## Headers

Every request must contain the header key, <i>Token</i> and a valid token string provided at login.

> Token : <valid_token_id>

> Content-Type: application/json

## Endpoints

### Users

> GET all users: **api/users**

> POST new user: **api/users**

> PUT update user: **api/users**

> GET user by id: **api/user**


### Sessions

> POST : **api/login**

> GET : **api/logout**


## Errors codes:

HTTP 400: incomplete or invalid parameters provided.

HTTP 404: object not found.

HTTP 401: unauthorized. Missing token.

## Request structure

### Login

> POST: api/login

> Provide the following structure in body:

    {
        "email": "user@gmail.com",
        "password": "1234"
    }

> Response:

    {
        "token": "05165665bee34cedac21feceb36a7dc5",
        "user": {
            "id": <number>,
            "name": "Full name",
            "email": "user@gmail.com",
            "username": "username",
            "password": "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"
        }
    }


___


### New user 

> POST: api/users

> Response: User object. Status: 200

> Provide the following structure in body:

    {
        "name": "Full name",
        "email": "user@gmail.com",
        "username": "username",
        "password": "1234"
    }


___


### Return current user


> GET: api/user

> Response: User object. Status: 200

    {
        "name": "Full name",
        "email": "user@gmail.com",
        "password": <hashed_password>,
        "username": "username"
    }


___