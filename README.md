# Health Record Index
## Intro

Health Record Index is a Web Service implemented with the Spring Boot framework. This service is offered in the context of  **InteropEHRate** project. It exploits a mongoDB database to store citizens’ information regarding the S-EHR Cloud in use. It is an intermediate service that communicates with the S-EHR app and the HCP app. In more detail, it stores:

- the citizen’s cloud username (citizenUsername)
- the cloud location (cloudUri)
- the citizen’s identification (citizenId)

## Endpoints

HRI provides a set of endpoints that are exploited by **Healthcare Practitioners (HCP)** and **citizens** in order to perform requests to the desired data. In particular, it provides four endpoints that are responsible for posting, getting or updating entries. To make a request, the user has to use the base URL followed by the port 8080 [http://[URL]:8080]. The next segment refers to the actor that is making the request. The available options are hcp and citizen. (i.e. http://[URL]:8080/citizen ). This declares whether the request is coming from the S-EHR app or the HCP app. The endpoints provided by the Health Record Index are the following:

### Citizen side
1.	**citizen/createCitizen**: This is the endpoint called by the S-EHR app in order to create a new entry in the database. It is used when the citizen agrees to store his/ her information to the S-EHR cloud and to provide access to healthcare practitioners when needed. When called, the endpoints take as parameters the citizenUsername and the cloudUri. After a successful creation of the citizen’s entry, an **authorization** and an **emergency**  token are generated on the HR Index service that need to be used for any upcoming requests made by the citizen or the HCP.
    - Endpoint: http://[URL]:8080/citizen/createCitizen
    - Parameters as headers: **citizenUsername, cloudUri**
    - Response:
```json
{
    "msg": "Citizen created",
    "data": [
        {
            "cloudUri": "$cloudUri",
            "citizenId": "$citizenId",
            "citizenUsername": "$citizenUsername"
        }
    ],
    "hriAccessToken" : "$hriAccessToken",
    "hriEmergencyToken": "$hriEmergencyToken",
    "status": 200
}
```
2.	**citizen/removeData**: This is the endpoint called by the S-EHR app in order to delete a citizen’s information from the database. It is used when the citizen withdraws his/ her consent to share the information with healthcare professionals. When called, the endpoint takes as parameter the citizenId. In addition, the authorization token needs to be included in the Authorization header. Following the successful request, the entry is not completely removed, but the citizenUsername and the cloudUri values are deleted.
    - Endpoint: http://[URL]:8080/citizen/removeData
    - Parameters as headers: **citizenId, hriAccessToken**
    - Response:
```json
{
    "msg": "Citizen deleted",
    "data": [
        {
            "cloudUri": "",
            "citizenId": "$citizenId",
            "citizenUsername": ""
        }
    ],
    "hriAuthToken": "$hriAccessToken",
    "status": 200
}
```
3.	**citizen/updateCitizen**: This is the endpoint called by the S-EHR app in order to update a citizen’s information to the database. It is used when the citizen starts using a different cloud to store his/ her data and the HRI information must be up to date with the new cloudUri and citizenUsername. When called, the endpoint takes as parameters the citizenId, the new citizenUsername and the new cloudUri. In addition, the authorization token needs to be included in the Authorization header. Following the successful request, the entry is updated with the new information. 
    - Endpoint: http://[URL]:8080/citizen/updateCitizen
    - Parameters as headers: **citizenId, citizenUsername, cloudUri, hriAccessToken**
    - Response:
```json
{
    "msg": "Citizen updated",
    "data": [
        {
            "cloudUri": "$cloudUri",
            "citizenId": "$citizenId",
            "citizenUsername": "$citizenUsername"
        }
    ],
    "hriAuthToken": "$hriAccessToken",
    "status": 200
}
```

### HCP side
1.	/getCloud: This is the endpoint called by the HCP app in order to retrieve information about a specific citizen from the database. When called, the endpoint takes as parameter the citizenId. In addition, the emergency authorization token needs to be included in the Authorization header.
    - Endpoint: http://[URL]:8080/hcp/getCloud
    - Parameters as headers: **citizenId, hriEmergencyToken**
    - Response:
```json
{
    "msg": "Citizen found",
    "data": [
        {
            "cloudUri": "$cloudUri",
            "citizenId": "$citizenId",
            "citizenUsername": "$citizenUsername"
        }
    ],
    "hriAuthToken": "$hriEmergencyToken",
    "status": 200
}
```



## Setup Guide

1. Health Record Index requires [Docker](https://www.docker.com) and **docker-compose** to run.
2.	Clone Github repository to the local machine.
3.	Open terminal and navigate to ``health-record-index\src\main\resources``
4.	Run ``  docker-compose up -d ``
5.	Health record index is up and running and can be accessed by making requests to `` http://[URL]:8080/{request_name} ``
