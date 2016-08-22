# README

This is a simple application to store bookmarks exposing REST API. 
This application uses [ActFramework](https://github.com/actframework/actframework)
and [MongoDB](mongodb.org) to store data.

To use this application, you must have Java, Maven and MongoDB installed.
You must have MongoDB running before using this application. 

## Load Init Data

Unlike the upstream project [SpringBootBookmarks](https://github.com/javaeeeee/SpringBootBookmarks), 
ActBookmarks stores hashed password into the database, thus the init 
data must be created through the application. Here is the steps to load
init data:

First run the app:

```
mvn clean compile exec:exec
```

Now move to another console and type

```
nc localhost 5461
```
(Note, if you don't have `nc` try `telnet`)

This will bring you into the ACT console and you should see something like:

```
    _           _    ____                 _                             _
   / \     ___ | |_ | __ )   ___    ___  | | __ _ __ ___    __ _  _ __ | | __
  / _ \   / __|| __||  _ \  / _ \  / _ \ | |/ /| '_ ` _ \  / _` || '__|| |/ /
 / ___ \ | (__ | |_ | |_) || (_) || (_) ||   < | | | | | || (_| || |   |   <
/_/   \_\ \___| \__||____/  \___/  \___/ |_|\_\|_| |_| |_| \__,_||_|   |_|\_\

 App: 0.0.1
 Act: v0.2.0-70b2
current dir: /Users/luog/p/greenlaw110/ActBookmarks
config profile: dev
running mode: DEV
ActBookmark[2kbNDCU1]>
```

Now type in `db.init` and you should see something like:

```
ActBookmark[2kbNDCU1]>db.init
db.init
DB initialized with sample data
ActBookmark[2kbNDCU1]>
```

Now you have two users created: 

* *Phil* and password *1*
* "Pete" and password *2*

You can type `user.list` in the ACT Console:

```
ActBookmark[2kbNDCU2]>user.list
user.list
+----------+-----------+
| USERNAME | BOOKMARKS |
+----------+-----------+
| Phil     |         2 |
| Pete     |         2 |
+----------+-----------+
Items found: 2
ActBookmark[2kbNDCU2]>
```

## Play with RESTful API

The API is secured with Basic Authentication, so the aforementioned credentials 
can be used to access the data.

To get all the bookmarks stored by user *Phil* one should key in the following.

~~~~
curl -w "\n" 2>/dev/null -H "accept: application/json" localhost:5460/Phil/bookmarks -u Phil:1
~~~~

To extract data for a single particular bookmark one should type in a command:

~~~~
curl -w "\n" 2>/dev/null -H "accept: application/json" localhost:5460/Phil/bookmarks/1 -u Phil:1
~~~~

To edit a bookmark the HTTP PUT method is used.

~~~~
curl -X PUT -w "\n" 2>/dev/null localhost:5460/my/bookmarks/1 -u Phil:1 \
 -H "Content-Type: application/json" -H "accept: application/json" -d '{"url":"github.com"}'
~~~~

To add a bookmark use the following.

~~~~
 curl -X POST -w "\n" 2>/dev/null localhost:5460/my/bookmarks \
 -u Phil:1 -H "Content-Type: application/json" -H "accept: application/json" \
 -d '{"url":"http://github.com", "description":"A lot of great projects"}'
~~~~

To remove a bookmark the HTTP DELETE method is used.

~~~~
curl -X DELETE -w "\n" 2>/dev/null -H "accept: application/json" \
localhost:5460/my/bookmarks/1 -u Phil:1
~~~~

The testing case will come up with the following updates to the project