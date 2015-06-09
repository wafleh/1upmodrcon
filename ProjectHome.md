# 1up ModRcon #

## About ##
1up ModRcon is a remote server administration tool for the popular first person shooter Urban Terror. The latest version is 1.5.1.0 which was released on July 26th, 2010 for Windows.

On August 15, 2010 this project was created to port 1up ModRcon to Java so that that it can be used cross-platform (i.e. Windows, Mac, Linux, etc.).

1up ModRcon is created and intended for use by members of the Urban Terror clan 1up (1upclan.info). Some of the built-in features are intended to control custom server functionality that 1up has implemented in their game servers. Your usage results may vary, although we will strive to keep things generic.

## Screenshot ##

<a href='http://www.1upclan.info/1uprcon/screenshots/MainWindow.png'><img src='http://www.1upclan.info/1uprcon/screenshots/MainWindow_thumb.png' /></a>

## Features ##

  * Complete Rcon System
  * Complete Moderator System
  * Server setup wizard
  * Realtime player tracking
  * Quick commands
  * Auto complete typing
  * Auto update wizard
  * Join server directly from application
  * Detailed help file
  * And more...

## Code ##

The code to query a Q3-based server is quite simple. If you have a Server object, you can pass it to the constructor of BowserQuery, and then issues commands and get responses. For example:
```
Server server = new Server("My Server", "208.43.15.81", "27960", "rcon", "somepassword");
BowserQuery q = new BowserQuery(server);
q.sendCmd("any rcon command goes here");
String response = q.getResponse();
```