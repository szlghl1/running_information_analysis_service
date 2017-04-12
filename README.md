# Running Information Analysis Service

It is a web service to populate running information.

The structure of running information is followed.
```java
class RunningInformation {
    enum HealthWarningLevel {
        LOW, NORMAL, HIGH;
    }
    private String runningId;
    private double latitude;
    private double longitude;
    private double runningDistance;
    private double totalRunningTime;
    private int heartRate;
    private Date timestamp;
    private UserInfo userInfo;
    private HealthWarningLevel healthWarningLevel;
}
public class UserInfo {
    private int userId;
    private String name;
    private String address;
}
```
## Deployment

### What you need

Maven and Java

### Steps

1. Choose the database you want to use. For H3, everything is fine. For MySQL, install it by yourself or run 
```bash
docker-compose up -d
```
by bash in the project folder. Modify the dependencies in pom.xml, remove the H3 part and uncomment the MySQL part. Set the port, username and password in src.resources.application.properties. If you use to docker-compose.yml, the default one is user:root, password:password.

2. Run
```bash
mvn clean install
```
by bash in the project folder to compile. If you don't have a maven, install one.

3. Run
```bash
java -jar [jar-file-compiled by Maven]
```
The default place for the jar file is in the "./target" folder.

## API

All API are implemented in RESTful style. All data operation can be achieved by http request.

### Insert

Send POST request to http://localhost:8080/running_info
The request body example is followed.
```json
[
  {
    "runningId": "7c08973d-bed4-4cbd-9c28-9282a02a6032",
    "latitude": "38.9093216",
    "longitude": "-77.0036435",
    "runningDistance": "39492",
    "totalRunningTime": "2139.25",
    "heartRate": 0,
    "timestamp": "2017-04-01T18:50:35Z",
    "userInfo": {
      "username": "ross0",
      "address": "504 CS Street, Mountain View, CA 88888"
    }
  },
  {
    "runningId": "07e8db69-99f2-4fe2-b65a-52fbbdf8c32c",
    "latitude": "39.927434",
    "longitude": "-76.635816",
    "runningDistance": "1235",
    "totalRunningTime": "3011.23",
    "heartRate": 0,
    "timestamp": "2017-04-01T18:50:35Z",
    "userInfo": {
      "username": "ross1",
      "address": "504 CS Street, Mountain View, CA 88888"
    }
  }
]
```

### Delete

If you want to delete all data, send delete request to http://localhost:8080/running_info

If you want to delete data with specified ID, send delete request to http://localhost:8080/running_info/{ID_to_delete}

### Modify

It rare so I didn't implement it. You can achieve it by deleting and adding a new one.

### Query

Query can be done by sending GET request to http://localhost:8080/running_info/{searching_id}?page=0&size=10

The size parameter can be ignored and the default value is 2.

Then you will get the response in JSON like below.

```json
{
content: [
{
runningId: "7c08973d-bed4-4cbd-9c28-9282a02a6032",
latitude: 38.9093216,
longitude: -77.0036435,
runningDistance: 39492,
totalRunningTime: 2139.25,
heartRate: 194,
timestamp: 1491072635000,
userInfo: {
userId: 0,
address: "504 CS Street, Mountain View, CA 88888",
name: "ross0"
},
healthWarningLevel: "HIGH"
}
],
last: true,
totalElements: 1,
totalPages: 1,
sort: [
{
direction: "DESC",
property: "healthWarningLevel",
ignoreCase: false,
nullHandling: "NATIVE",
ascending: false,
descending: true
}
],
first: true,
numberOfElements: 1,
size: 2,
number: 0
}
```
Everything is the same for http://localhost/running_info, which return all data without filtering.

There is also an API returning only a part of the information. The request should be sent to http://localhost:8080/running_info_simple or http://localhost:8080/running_info/[running_id] every other things are the same as the complete APIs.

The simple response example is below.
```json
[
{
runningId: "7c08973d-bed4-4cbd-9c28-9282a02a6032",
totalRunningTime: 2139.25,
heartRate: 186,
userId: 0,
userName: "ross0",
userAddress: "504 CS Street, Mountain View, CA 88888",
healthWarningLevel: "HIGH"
}
]
```