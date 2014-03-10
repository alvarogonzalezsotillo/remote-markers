remote-markers
==============

Experimental fork of Markers for Android (https://code.google.com/p/markers-for-android/). It adds features for share the current drawing.

The following share methods are planned:

 * Embedded web server: The application will serve a simple HTML with an image and a tiny javascript to reload that image every second. [NanoHttpd](https://github.com/NanoHttpd/nanohttpd) will be used.
 
 * VNC server: This method is potentialy less expensive in terms of CPU and bandwidth. [VNCj](http://emblemparade.net/projects/vncj/) will be used
 
 * Cloud web server: The above methods only work in a LAN environment with WiFi enabled. To overcome this limitation, the application would periodically send the bitmap to a web server (AppEngine), and that server would deliver the image to any client.
 

Embedded web server
===================

This method has a preliminary implementation. The web server opens at port 8081, and it only serves the current image (no auto-refresh yet)