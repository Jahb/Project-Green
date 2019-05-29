# Project green presents: GoGreen
[Slide Presentation Of Project](https://docs.google.com/presentation/d/1GYgnPlYLZMJTrdB-rxhw0m1XmgOnL_pG9XIzCu-oea0/edit?usp=sharing)
<img src="/client/src/main/resources/images/TemporaryLogo.png"  width="120">


## Overview
* A simple and understandable UI
* Rings to show progress for you and friends
* Specify the amount of calories eaten or kilometres biked to get accurate readings

## Dependencies
* Java 8 (or 1.8, it's the same thing), yes thats all thanks to gradle!

## How to build/run
With Java (1.)8 installed, run `./gradlew bootJar` to generate the server jarfile in `/server/build/libs`!  
To generate the client files, run `./gradlew client:shadowJar` to generate the client files in `/client/build/libs`  
For tests, running `./gradlew test` will do

## The Api
Is honestly a bit of a mess. Since it was never specified it isn't actually RESTful, since it uses cookies for authentication. This is a design choice from the beginning when we were less wise and honestly had no idea what we were doing. But except for login (`/login`) and logout (`/logout`, using `get`), the API is pretty sane. 

As an example:
The user wants to log in, tell that they biked 10 km instead of taking a car, and get the total points that they have for this day:
1. The user logs in using `/login`, using `username` and `password` as POST parameters, this will return a JSON object which has a `data` field with value true. It's important to store the cookie, most REST clients actually do this, but it is only tested with UniREST, Postman and Insomnia.
1. The user sends a POST request to `/feature/new`, with POST parameters `feature` being `Bike` and `userInput` being `10`, This returns a JSON object containing the total points in the `data` attribute.
1. And were done, since the last request already sent the total points we don't need to send another request, but say we want the per category devided points for a user, then we would send a POST request to `/feature/points` with POST parameter `username` being our own username. This returns an array in the `data` attribute, this is the points per category: food, energy, transport and bonus. 

## Alongside the functionality required for the project, we have also added the following features:
* User rings that serve as visual representation of CO2 saved and refresh every day
* The ability to view the rings of followed users (2 users- one ahead and one behind, based on current daily score) and see their CO2 savings distribution by category
* A streak system that rewards users with bonus points based on the number of consecutive days in which they fill the ring in order to encourage them to reduce CO2 production every day
* A quiz that allows users to provide personal information, if they wish to do so, which is used to provide more accurate data regarding their CO2 emissions
* Notification system that lets users know when someone follows them, when they complete an achievement or when their ranking changes relative to the users they are following
* A notification log that shows recent notifications and the time when they were received
* The ability users see their progress over a period of time through a personal graph
* Leaderboard with functionality for switching the time period (week, month, year) for both the user rankings and the personal graph
* The option for users to recover their password through email in case they forget it
* An event screen that enables users to create custom events, specifying a name, time, date and description, and to browse and join events of users that they are following
* Predictive search functionality that lets users see potential matches when searching for a profile to follow
* An user profile complete with statistics, the most recent achievements and recent activities that helped reduce CO2 emissions

## Summary of members
Julius de Jeu
<img src="https://voidcorp.nl/images/julius.jpg"  width="120">

Paul Stepanov
<img src="/doc/images/cvpoza.png"  width="120">

Pablo Rodrigo 
<img src="/doc/images/PabloRodrigo.jpg"  width="120">

Vlad Florea
<img src="/doc/images/22d61c22-5ff1-4183-8bf7-37117d17b57a.jpg"  width="120">

Jahson O'Dwyer Wha Binda
<img src="/doc/images/Jahsonpic.jpg"  width="120">

Kamron Geijsen
<img src="/doc/images/KamronGeijsen.png"  width="120">
