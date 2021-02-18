# Java, Client, Server, URL-fetch, Swing GUI demo app

This application demonstrats how you can setup a very basic client server communication using Java.

## Interesting classes/methods

Server-class 
This class is setting up a server that listnes for clients to connect. When a client connects the socket-object is passed to the ClientHandler-class that runs on a new Thread. The ClientHandler is handling the communication with the client. 

GUI-class
The GUI is built with Java Swing. The communication with the server is running on a separate worker thread to avoid the GUI to hang.   


getMd5(String s) in the CarFactory-class
This function takes a string and returns a MD5 hash. This class is not used in this version.

getCarFromWeb(String regnr) in the ClientHandler-class
Takes a registration number and returns a Car object. This method is fetching data from a simple web api.  

## Try it out
To try the app: In the subfolder jars/ are two files, server.jar and client.jar. Execute the server.jar first by typeing the following command in your terminal 'java -jar server.jar' and when it is up and running execute client.jar in the same way but from another terminal. The GUI window will show, and you can try searching for regnr. The search will return only dummy content. 
