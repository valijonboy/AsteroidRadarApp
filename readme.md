# [Project Title](https://github.com/valijonboy/AsteroidRadarApp)
Asteroid Radar

## Getting Started
Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and if they are potentially hazardous.

The app is consists of two screens: A Main screen with a list of all the detected asteroids and a Details screen that is going to display the data of that asteroid once it´s selected in the Main screen list. The main screen will also show the NASA image of the day to make the app more striking.

This kind of app is one of the most usual in the real world, what you will learn by doing this are some of the most fundamental skills you need to know to work as a professional Android developer, as fetching data from the internet, saving data to a database, and display the data in a clear, clear, compelling UI.

###  [Screenshots](https://github.com/valijonboy/AsteroidRadarApp/tree/master/screenshots)
![screen_1.png](https://github.com/valijonboy/AsteroidRadarApp/raw/master/screenshots/screen_1.png)
![screen_2.png](https://github.com/valijonboy/AsteroidRadarApp/raw/master/screenshots/screen_2.png)
![screen_3.png](https://github.com/valijonboy/AsteroidRadarApp/raw/master/screenshots/screen_3.png)
![screen_4.png](https://github.com/valijonboy/AsteroidRadarApp/raw/master/screenshots/screen_4.png)

### Project Instructions
 In this project I used these the most important dependencies:

* Retrofit to download the data from the Internet.
* Moshi to convert the JSON data we are downloading to usable data in form of custom classes.
* Glide to download and cache images.
* RecyclerView to display the asteroids in a list.

 And I used the components from the Jetpack library:

* ViewModel
* Room
* LiveData
* Data Binding
* Navigation
* Work

The application I was build:
* Include Main screen with a list of clickable asteroids as seen in the provided design.
* Include a Details screen that displays the selected asteroid data once it’s clicked in the Main screen as seen in the provided design. The images in the details screen are going to be provided here, an image for a potentially hazardous asteroids and another one for the non potentially hazardous ones.
* Download and parse data from the NASA NeoWS (Near Earth Object Web Service) API.
* Save the selected asteroid data in the database using a button in details screen.
* The program save an asteroid in the database and to displays the list of asteroids from web or the database in the main screen top menu.
* The program to cache the asteroids data by using a worker, so it downloads and saves week asteroids in background when device is charging and wifi is enabled.
* App works in multiple screen sizes and orientations, also it provides talk back and push button navigation.

### Built with
To build this project you are going to use the NASA NeoWS (Near Earth Object Web Service) API, which you can find here: [https://api.nasa.gov/](https://api.nasa.gov/)

You will need an API Key which is provided for you in that same link, just fill the fields in the form and click Signup.