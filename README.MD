# README

This is a simple application to store bookmarks exposing REST API. 
This application uses [ActFramework](https://github.com/actframework/actframework)
and [MongoDB](mongodb.org) to store data.

To use this application, you must have [Java](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html), [Maven](http://maven.apache.org/download.cgi) and [MongoDB](https://www.mongodb.com/download-center) downloaded and installed.

You must have MongoDB running before using this application. 

## Start application

Start the application in dev mode

```
mvn clean compile exec:exec
```

Start the application in prod mode

```
mvn clean package
cd target/dist
unzip *
./start
```

Once application has been started, you should see something like:

```
   / \     ___ | |_ | __ )   ___    ___  | | __ _ __ ___    __ _  _ __ | | __
  / _ \   / __|| __||  _ \  / _ \  / _ \ | |/ /| '_ ` _ \  / _` || '__|| |/ /
 / ___ \ | (__ | |_ | |_) || (_) || (_) ||   < | | | | | || (_| || |   |   < 
/_/   \_\ \___| \__||____/  \___/  \___/ |_|\_\|_| |_| |_| \__,_||_|   |_|\_\
                                                                             
                                          powered by ActFramework v0.3.0-38e7

 version: 0.0.1
base dir: /home/luog/p/greenlaw110/ActBookmarks/target/dist
 profile: prod
    mode: PROD

Oct 28, 2016 6:49:27 AM org.mongodb.morphia.logging.MorphiaLoggerFactory chooseLoggerFactory
INFO: LoggerImplFactory set to org.mongodb.morphia.logging.jdk.JDKLoggerFactory
06:49:27.878 [main] INFO  a.Act - loading application(s) ...
06:49:27.882 [main] INFO  a.Act - App starting ....
06:49:27.940 [main] WARN  a.c.AppConfig - Application secret key not set! You are in the dangerous zone!!!
06:49:27.964 [main] WARN  a.a.DbServiceManager - DB configuration not found. Will try to init default service with the sole db plugin: act.db.morphia.MorphiaPlugin@6ca367aa
06:49:28.054 [main] INFO  o.m.d.cluster - Cluster created with settings {hosts=[localhost:27017], mode=SINGLE, requiredClusterType=UNKNOWN, serverSelectionTimeout='30000 ms', maxWaitQueueSize=500}
06:49:28.131 [cluster-ClusterId{value='58125a48276a8c4147016ace', description='null'}-localhost:27017] INFO  o.m.d.connection - Opened connection [connectionId{localValue:1, serverValue:223}] to localhost:27017
06:49:28.133 [cluster-ClusterId{value='58125a48276a8c4147016ace', description='null'}-localhost:27017] INFO  o.m.d.cluster - Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=STANDALONE, state=CONNECTED, ok=true, version=ServerVersion{versionList=[3, 2, 10]}, minWireVersion=0, maxWireVersion=4, maxDocumentSize=16777216, roundTripTimeNanos=993392}
06:49:28.284 [main] WARN  a.m.MailerConfig - smtp host configuration not found, will use mock smtp to send email
06:49:28.284 [main] WARN  a.c.AppConfig - host is not configured. Use localhost as hostname
06:49:28.480 [main] INFO  o.m.d.connection - Opened connection [connectionId{localValue:2, serverValue:224}] to localhost:27017
06:49:28.492 [main] WARN  a.Act - acl.yaml file not found...
06:49:28.494 [main] INFO  a.Act - App[ActBookmark] loaded in 612ms
06:49:28.507 [main] INFO  o.xnio - XNIO version 3.3.6.Final
06:49:28.520 [main] INFO  o.x.nio - XNIO NIO Implementation Version 3.3.6.Final
06:49:28.586 [main] INFO  a.Act - network client hooked on port: 5460
06:49:28.587 [main] INFO  a.Act - CLI server started on port: 5461
06:49:28.587 [main] INFO  a.b.a.RunApp - it takes 1693ms to start the app
```

Once application started you can proceed with the next step:

## Load Init Data

Unlike the upstream project [SpringBootBookmarks](https://github.com/javaeeeee/SpringBootBookmarks), 
ActBookmarks stores hashed password into the database, thus the init 
data must be created through the application. Here is the steps to load
init data:

Open another console and type:

```
nc localhost 5461
```
(Note, if you don't have `nc` try `telnet`)

**Tips** If you have installed [rlwrap](https://github.com/hanslub42/rlwrap), use it along wit `nc` or `telnet`:

```
rlwrap nc localhost 5461
```

This will bring you into the ACT console and you should see something like:

```
   / \     ___ | |_ | __ )   ___    ___  | | __ _ __ ___    __ _  _ __ | | __
  / _ \   / __|| __||  _ \  / _ \  / _ \ | |/ /| '_ ` _ \  / _` || '__|| |/ /
 / ___ \ | (__ | |_ | |_) || (_) || (_) ||   < | | | | | || (_| || |   |   < 
/_/   \_\ \___| \__||____/  \___/  \___/ |_|\_\|_| |_| |_| \__,_||_|   |_|\_\
                                                                             
                                          powered by ActFramework v0.3.0-38e7

 version: 0.0.1
base dir: /home/luog/p/greenlaw110/ActBookmarks/target/dist
 profile: prod
    mode: PROD
ActBookmark[2kbNDb01]>
```

Now type in `db.init` and you should see something like:

```
ActBookmark[2kbNDb01]>db.init
db.init
DB initialized with sample data
ActBookmark[2kbNDb01]>
```

Now you have two users created: 

* *Phil* and password *1*
* *Pete* and password *2*

You can type `user.list` in the ACT Console:

```
ActBookmark[2kbNDb01]>user.list
user.list
+----------+-----------+
| USERNAME | BOOKMARKS |
+----------+-----------+
| Phil     |         2 |
| Pete     |         2 |
+----------+-----------+
Items found: 2
ActBookmark[2kbNDb01]>
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

## Play with CLI

CLI is the friend to system administrator. ActFramework makes it super easy
to create CLI command, and you can even share the same code to serve both
HTTP request and CLI command:

In the `BookmarkService`, we have the following code:

```java
    @Command("bm.list")
    @GetAction
    public Collection<Bookmark> getAllBookmarks() {
        return user.getBookmarks();
    }
```

Which denote the method `getAllBookmarks()` as the HTTP GET request handler 
at `/{username}/bookmarks` which is defined in the `@Controller` class
annotation; And it mark the method as the command executor for `bm.list`
command at the same time. We have already seen how to use RESTful 
request to check all bookmarks of a user. Now we are going to use CLI command
to do the same job.

First use telnet or nc or any other similar to to hook into ACT's CLI console
session:

```
GreenLaptop:ActBookmarks luog$ nc localhost 5461
   / \     ___ | |_ | __ )   ___    ___  | | __ _ __ ___    __ _  _ __ | | __
  / _ \   / __|| __||  _ \  / _ \  / _ \ | |/ /| '_ ` _ \  / _` || '__|| |/ /
 / ___ \ | (__ | |_ | |_) || (_) || (_) ||   < | | | | | || (_| || |   |   < 
/_/   \_\ \___| \__||____/  \___/  \___/ |_|\_\|_| |_| |_| \__,_||_|   |_|\_\
                                                                             
                                          powered by ActFramework v0.3.0-38e7

 version: 0.0.1
base dir: /home/luog/p/greenlaw110/ActBookmarks/target/dist
 profile: prod
    mode: PROD
ActBookmark[2kbNDb01]>
```

Then type in `help -a` to check all application commands:

```
APPLICATION COMMANDS

bm.list       - show all bookmarks of the specified person
bm.show       - show specific bookmark of the specified person
db.init       - init database (Note, this will clean up all existing data!)
user.add      - add an new user
user.list     - list all users
user.show     - show user
```

To check detail of a certain command type `help <command-name>` or `<command-name> -h`:

```
bm.list -h
Usage: bm.list
show all bookmarks of the specified person

Options:
  -u,--user     specify user by username

Shortcuts: .bl, b.l, b.list, bm.l, bm.li
ActBookmark[2kbNDb01]>
```

One can type in `bm.list -u Pete` to list all bookmarks of user `Pete`

```
bm.list -u Pete
+----+--------------+-------------------+
| ID |  DESCRIPTION |        URL        |
+----+--------------+-------------------+
|  3 | A playground | http://github.com |
|  4 | Be cautious  | http://ibm.com    |
+----+--------------+-------------------+
Items found: 2
```

