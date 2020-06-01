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


### Movies

> GET : **api/movie**

> GET : **api/movie/{imdbID}**

> GET : **api/movie/search**

> GET : **api/movie/{imdbID}/score**

> POST | PUT : **api/movie/score**


### Watchlist

> GET | POST | PUT : **api/watchlist**

> DELETE : **api/watchlist/{imdbID}**


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
        "username": "useruwu",
        "password": "1234"
    }

> Response:

    {
        "token": "05165665bee34cedac21feceb36a7dc5",
        "user": {
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


### Get movie by imdbID


> GET: api/movie/{imdbID}

> Response: Movie object. Status: 200

    {
        "imdbID": "tt0386676",
        "title": "The Office",
        "year": "2005–2013",
        "released": "24 Mar 2005",
        "genre": "Comedy",
        "director": "N/A",
        "writer": "Greg Daniels, Ricky Gervais, Stephen Merchant",
        "actors": "Rainn Wilson, John Krasinski, Jenna Fischer, Leslie David Baker",
        "plot": "A mockumentary on a group of typical office workers, where the workday consists of ego clashes, inappropriate behavior, and tedium.",
        "language": "English",
        "country": "USA",
        "awards": "Won 1 Golden Globe. Another 47 wins & 186 nominations.",
        "poster": "https://m.media-amazon.com/images/M/MV5BMDNkOTE4NDQtMTNmYi00MWE0LWE4ZTktYTc0NzhhNWIzNzJiXkEyXkFqcGdeQXVyMzQ2MDI5NjU@._V1_SX300.jpg",
        "score": 0.0
    }


___

### Get movies in local db


> GET: api/movie

> Response: Movie objects. Status: 200

    [
        {
            ...
        },
        {
            ...
        }
    ]
    


___

### Get movies by search


> GET: api/movie/search

> Query Params: 
- title: required; separated by +, _ or by space
- page: optional

> Response: List of objects. Status: 200

> Unsuccessful search returns an empty list

> Example query: **api/movie/search?title=the+office**


    [
        {
            "Title": "The Office",
            "Year": "2005–2013",
            "imdbID": "tt0386676",
            "Type": "series",
            "Poster": "https://m.media-amazon.com/images/M/MV5BMDNkOTE4NDQtMTNmYi00MWE0LWE4ZTktYTc0NzhhNWIzNzJiXkEyXkFqcGdeQXVyMzQ2MDI5NjU@._V1_SX300.jpg"
        },
        {
            "Title": "The Office",
            "Year": "2001–2003",
            "imdbID": "tt0290978",
            "Type": "series",
            "Poster": "https://m.media-amazon.com/images/M/MV5BNGI4YWZhZDAtMjYwNC00YWJjLTgzODQtNmQ0OTI2NGQzMWUwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"
        },
        {
            "Title": "The Office",
            "Year": "2019–",
            "imdbID": "tt8305218",
            "Type": "series",
            "Poster": "https://m.media-amazon.com/images/M/MV5BZTFmNzMxYzktODVjZC00ZDNkLTgzYTYtMTBkNDg1OGMzYmE1XkEyXkFqcGdeQXVyMjUxMTY3ODM@._V1_SX300.jpg"
        }
    ]
    
___

### Get user score by movie


> GET: api/movie/{imdbID}/score

> Response: float score . Status: 200

> Score not found return 0.0 as score

> Example query: **api/movie/tt0275847/score**

    3.5
    
___

### Set user score to movie


> POST: api/movie/score

> Provide the following structure in body:

    {
    	"imdbID" : "tt0275847",
    	"score": "2.75"
    }

> Successful post request Status: 200
___

### Update user score to movie


> PUT: api/movie/score

> Provide the following structure in body:

    {
    	"imdbID" : "tt0275847",
    	"score": "2.75"
    }

> Successful post request Status: 200
___


### Get Watchlist

> GET: **api/watchlist**

> Response: Objects containing basic info + movie state

> NOTE: the return list is grouped by MovieState and sorted in alphabetical order (by section).

> The possible states are: WATCHING, COMPLETED, PLAN_TO_WATCH, ON_HOLD

    [
        {
            "title": "The Office",
            "year": "2005–2013",
            "imdbID": "tt0386676",
            "poster": "https://m.media-amazon.com/images/M/MV5BMDNkOTE4NDQtMTNmYi00MWE0LWE4ZTktYTc0NzhhNWIzNzJiXkEyXkFqcGdeQXVyMzQ2MDI5NjU@._V1_SX300.jpg",
            "state": "COMPLETED"
        },
        {
            "title": "Your Name.",
            "year": "2016",
            "imdbID": "tt5311514",
            "poster": "https://m.media-amazon.com/images/M/MV5BODRmZDVmNzUtZDA4ZC00NjhkLWI2M2UtN2M0ZDIzNDcxYThjL2ltYWdlXkEyXkFqcGdeQXVyNTk0MzMzODA@._V1_SX300.jpg",
            "state": "COMPLETED"
        },
        {
            "title": "Harry Potter and the Chamber of Secrets",
            "year": "2002",
            "imdbID": "tt0295297",
            "poster": "https://m.media-amazon.com/images/M/MV5BMTcxODgwMDkxNV5BMl5BanBnXkFtZTYwMDk2MDg3._V1_SX300.jpg",
            "state": "ON_HOLD"
        }
    ]

___


### POST | PUT Watchlist

> POST | PUT: **api/watchlist**

> Response: string. HTTP status = 200

> NOTES: POST sets state to WATCHING by default.

> Request: 

    {
        "imdbID": "tt0275847",
        "state": "WATCHING"
    }

___

### DELETE Watchlist

> DELETE: **api/watchlist/{imdbID}**

> Response: string. HTTP status = 200

> NOTES: Deletes record from list


___