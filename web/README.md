# Durák server
This is an implementation of a web server for the Durák card game.


The server can be connected to using the #join endpoint (optional parameter: playerName);


After enough players connected, the server will send a #playerstate message containing
the player's current cards, the trump color and enemy cards if any.


After receiving #playerstate, clients can send a json message to #taketurn endpoint.
The format for hitting cards shall be:
{
    "playerName": "Andor",
    "hitCards": [
        {"enemyCard": "Piros Hetes","hitWith": "Piros Nyolcas"},
        {"enemyCard": "Gourd King","hitWith": "Zöld Alsó"}
    ]
}


After successfully hitting all enemy cards (if there were any) the server will send another #playerstate
in the response which includes the newly drawn cards.


The format for sending cards shall be:
{
"playerName": "Béla",
"gameId": "123412345"
"sentCards": [
{"Piros Felső", "Zöld felső", "Tök hetes"}
]
}

The server allows any localized card names and ignores capitalization.


The server will notify if the planned turn is invalid, or if the game is over.


The server will use event sourcing to save all events. Clients can use this to display a history, animations or backtrack.


Have fun!