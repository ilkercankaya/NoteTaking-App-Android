# Notes App
An android app that uses Model-View-ViewModel architecture along with persistent local database storage to 
allow users to fetch, edit, sort, store notes. Please scroll to demo section for a demo link. 

![Notifellow Picture HERE](https://www.dropbox.com/s/r5kfnxcbrcsisph/FullMAinActivity.PNG?raw=1)
![Notifellow Picture HERE](https://www.dropbox.com/s/tqa3teirkdbi7ca/secondActivity.PNG?raw=1)

# How It Works
The users can fetch notes from a remote server with the following fields
* __Name__
* __Title__
* __Date__
* __Priority__
* __Enable__

which the user later can sort the notes or edit the fields while it is being stored in a persistent local database.

# App Features
* Fully supports both _portrait_ and _landscape_ mode.
* _Model-View-ViewModel MVVM_ architecture with _Android View Models_ are used
* No information loss when the screen is turned or app is terminated with system kills.
* Fetches _real-time notes from a remote server_ and stores them in a local database and displays them when the _**POPULATE**_ button is clicked.
* Removes all the notes from the _local database_ and the screen when _**DELETE**_ button is clicked.
* Allows the user to edit all the fields when a notes row is clicked, _local database_ is updated accordingly.
* Notes could be sorted with respect to either _Priority Level_ or _Date_.

# Project Details
Technologies involved:
* Android Studio
* Model-View-ViewModel (MVVM) architecture with Android View Models
* ROOM (Persistent Database Storage)
* Volley (Fetch API)
* GSON 


# Server For Notes - Hosted On Heroku (Node.js)
[Make A Request to Heroku Server Here!](https://reminder-app-server-node.herokuapp.com/getReminders)

[Server Github Link Here!](https://github.com/ilkercankaya/NoteTaking-Server-Nodejs)

# Demo
[Demo Here!](https://youtu.be/qqCsT_c-ok8)
