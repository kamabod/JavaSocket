# JavaSocket


**Overview of Functionality**


This is a Java chat application. It uses Java Socket API.

This application uses threads and can handle multiple clients. The clients connect to the server and are able to chat to each other.

Server starts up and waits for socket connections on a specific port.A client starts up and tries to
create a socket connection to the server.If a connection is established, multiple users can have a text-based chat session.
After connecting to a server, a user is asked to type their name to enter a chat. Server sends a list of currently connected
users to a new user. Every user receives a notification when a new user is connected and when a user exits.
Every message has a user name specified in a prefix. This facilitates keeping a record of who sent a message.
To exit the chat, a user must type \q.



**Project Design & Implementation**


This application comprises server side and client side.
The project employs 6 classes.

## Server Side
The server side is implemented by two classes ChatClient and UserHandlingThread.
The class ChatServer starts the server and it listens on a specified port.
A client connects and an instance of UserHandlingThread is created. This thread is specifically created for that client.
Every client connection is handled by a different thread and thus the server can concurrently handle many clients.
ChatServer class uses sets to store user names as well as UserHandling threads. Set has been selected due to the fact that it doesn't
allow duplicate elements.
UserHandlingThread reads client messages and delivers messages to other users.It forwards a list of currently connected users
to a new user. Moreover, it notifies users that a new user has been connected.If a user needs to disconnect, they need to type \q. 
After a user has left, other users receive a notification about it.
 
## Client Side
The server side is implemented by three classes:ChatClient, ReadingThread, WritingThread.
ChatClient starts the client program.It connects to server using hostname and port number.
After the connection is established, it starts two threads: ReadThread and WriteThread.
ReadingThread reads server's input and displays it on the screen till the user types \q to terminate the program.
WritingThread reads user's input and forwards it to the server until the user decided to exit the chat.
Two threads are utilized to make the client responsive.

Auxiliary class
A class called SharedConfig is used by both Server side and Client side. It contains shared parameters.
This class was created to make the code more manageable.


**Usage of the Application**


1. Type in the port number when running server program from the command line.
   If no port number is provided, default port number 3333 will be used.

2. Once server is started, the following output will be displayed:

     Server port number is: 3333 (or a port number provided)
     ChatServer is listening on port 3333 (or on a port number provided)

3. To run client, specify hostname and port number in the command line.
   If no hostname or port number is provided, default hostname "localhost" and default port number 3333 will be used.

   Output on server's console is:
     New user has connected.
   Output on user's console is: 
     Connected to chatServer.  No other users connected.  Welcome to chat. Please enter your name: 

   When another client connects to the server the following output is visible on the user's console:
     Connected to chatServer.   Users currently connected:[[kama] :] Welcome to chat. Please enter your name: 

   After a user enters their name they can start  a chat. The output is as follows:
     New user connected:[mary] :
     [kama]: hi
     [mary]: hi  

4. If a user wishes to exit the chat and terminate the program, they need to type \q. Sample outputs are as follows:
   Server's console output is: 
     The user[kama] :has left
   Mary's console: 
     [kama] :has left.
   Kama's console: 
     Goodbye  kama
