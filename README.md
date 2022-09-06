# Steam GameLogger
SSF-Mini-Project

Heroku: https://hyhy-mini-project-one.herokuapp.com/login

---

## Description

The aim of this application is to help user log down their total playing hours and score for each Steam game.

This application uses API from Steam and SteamSpy to gather the following data:

    1. Steam
        - App ID
        - Review score (0-10)
        - Review score description (Negative - Positive)
        - Game Image URL

    2. SteamSpy
        - Average time spent for all users (hours)
        - Median time spent for all users (hours)


---

## How to use

 1. Pick a username to login
 2. Add Game 
    1. Input the exact title found on Steam
    2. Rate the game you just played from 1-10
    3. Input the status of the game
       1. Playing
       2. Backlog
       3. Completed
       4. Retired
    4. Log in the hours played
 3. Submit and it will appear on the list
 4. You may also update or delete as required
 5. You may also sign out and log in as other user


---

## Limitations

As much as I want to include games from other platforms (i.e. Switch, PS), they do not provide the required API data.
Thus, we are only able log games that are found on Steam platform.

___

