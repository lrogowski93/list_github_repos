# list_github_repos

## Description
REST Api for listing all non-fork GitHub user repositories.

## Features
- listing all user repos which is not forks
- displaying SHA for last commit in each branch

## Tech Stack
* Java 17
* Spring Boot 3.1.2
* Lombok


## Installation
Clone repository

```bash
git clone https://github.com/lrogowski93/list_github_repos
```

Run app by using following command:
```bash
gradlew bootRun
```

## Usage

### List user repositories

### Request

```http
  GET /repos/{username}
```

#### Header

| Key      | Value              |
|----------|--------------------|
| `Accept` | `application/json` |

### Response

Array of repository objects:

| Parameter  | Type             | Description                  |
|------------|------------------|------------------------------|
| `name`     | string           | Repository name              |
| `owner`    | object           | Repository owner             |
| `fork`     | boolean          | Is repository a fork         |
| `branches` | array of objects | Array of repository branches |

Owner object:

| Parameter | Type   | Description |
|-----------|--------|-------------|
| `login`   | string | Owner login |

Branch object:

| Parameter | Type   | Description   |
|-----------|--------|---------------|
| `name`    | string | Branch name   |
| `commit`  | object | Latest commit |

Commit object:

| Parameter | Type   | Description |
|-----------|--------|-------------|
| `sha`     | string | Commit SHA  |

### Error response

| Parameter | Type   | Description      |
|-----------|--------|------------------|
| `status`  | int    | HTTP status code |
| `message` | string | Error message    |


### Example

#### Request

```shell
curl --location 'localhost:8080/repos/torvalds' \
--header 'Accept: application/json'
```

#### Response
```json
[
    {
        "name": "linux",
        "owner": {
            "login": "torvalds"
        },
        "fork": false,
        "branches": [
            {
                "name": "master",
                "commit": {
                    "sha": "7d2f353b2682dcfe5f9bc71e5b61d5b61770d98e"
                }
            }
        ]
    }
]
```

#### Error response (user not found)
```json
{
    "status": 404,
    "message": "Not Found. User not found."
}
```