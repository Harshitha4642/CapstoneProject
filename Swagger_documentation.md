# OpenAPI Definition

## Info
- Title: OpenAPI definition
- Version: v0

## Servers
- Generated server URL: [http://localhost:8080](http://localhost:8080)

## Security
- Bearer Authentication

## Paths

### /api/search/getMessageRecords

#### POST getMessageRecords
- Tags: search-controller
- Operation ID: getMessageRecords
- Request Body:
  - Content Type: application/json
  - Schema: [SearchContent](#searchcontent)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: Array of [MessageSearchResult](#messagesearchresult)

### /api/search/getCallRecords

#### POST getCallRecords
- Tags: search-controller
- Operation ID: getCallRecords
- Request Body:
  - Content Type: application/json
  - Schema: [SearchContent](#searchcontent)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: Array of [CallSearchResult](#callsearchresult)

### /api/message/saveMessage

#### POST saveMessageCDR
- Tags: message-controller
- Operation ID: saveMessageCDR
- Request Body:
  - Content Type: application/json
  - Schema: [MessageForm](#messageform)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: [ExcecutionStatus](#excecutionstatus)

### /api/home/saveNormalCall

#### POST saveNormalCall
- Tags: home-controller
- Operation ID: saveNormalCall
- Request Body:
  - Content Type: application/json
  - Schema: [NormalCallForm](#normalcallform)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: [ExcecutionStatus](#excecutionstatus)

### /api/home/saveFailedCall

#### POST saveFailedCall
- Tags: home-controller
- Operation ID: saveFailedCall
- Request Body:
  - Content Type: application/json
  - Schema: [NormalCallForm](#normalcallform)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: [ExcecutionStatus](#excecutionstatus)

### /api/auth/token

#### POST token
- Tags: api-auth-controller
- Operation ID: token
- Request Body:
  - Content Type: application/json
  - Schema: [LoginBody](#loginbody)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: [TokenDTO](#tokendto)

### /api/auth/register

#### POST registerUser
- Tags: api-auth-controller
- Operation ID: registerUser
- Request Body:
  - Content Type: application/json
  - Schema: [User](#user)
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: string

### /api/search/getNumbers

#### GET getAllNumbers
- Tags: search-controller
- Operation ID: getAllNumbers
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: Array of string

### /api/search/getNames

#### GET getAllNames
- Tags: search-controller
- Operation ID: getAllNames
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: Array of string

### /

#### GET getClients_1
- Tags: Clients
- Summary: This method is used to get the clients.
- Operation ID: getClients_1
- Responses:
  - 200 OK
    - Content Type: */*
    - Schema: Array of string

## Components

### Schemas

- SearchContent
  - Type: object
  - Properties:
    - content: string
    - type: string
    - category: string

- MessageSearchResult
  - Type: object
  - Properties:
    - subscriberName: string
    - subscriberPhn: string
    - recieverName: string
    - recieverPhn: string
    - date: string
    - time: string
    - subscriberLocation: string
    - recieverLocation: string
    - status: string
    - type: string

- CallSearchResult
  - Type: object
  - Properties:
    - subscriberName: string
    - subscriberPhn: string
    - recieverName: string
    - recieverPhn: string
    - date: string
    - time: string
    - duration: integer (int32)
    - subscriberLocation: string
    - recieverLocation: string
    - callType: string
    - hasVoiceMail: string
    - voiceMailDuration: integer (int32)
    - reason: string

- MessageForm
  - Type: object
  - Properties:
    - subscriber: integer (int32)
    - reciever: integer (int32)
    - date: string
    - time: string
    - subscriberLocation: string
    - recieverLocation: string
    - status: string
    - type: string

- ExcecutionStatus
  - Type: object
  - Properties:
    - status: string

- NormalCallForm
  - Type: object
  - Properties:
    - subscriber: integer (int32)
    - reciever: integer (int32)
    - date: string
    - time: string
    - duration: integer (int32)
    - subscriberLocation: string
    - recieverLocation: string
    - callType: string
    - reason: string
    - hasVoiceMail: boolean
    - voiceMailDuration: integer (int32)

- LoginBody
  - Type: object
  - Properties:
    - username: string
    - password: string

- TokenDTO
  - Type: object
  - Properties:
    - token: string
    - username: string

- User
  - Type: object
  - Properties:
    - id: integer (int64)
    - name: string
    - password: string

### Security Schemes

- Bearer Authentication
  - Type: http
  - Scheme: bearer
  - Bearer Format: JWT
