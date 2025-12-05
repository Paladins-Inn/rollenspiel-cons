# ROLLENSPIEL-CONS - Eating the Elephant one bite at a time

> What man is a man who does not make the world better.
>
> -- Balian, Kingdom of Heaven

## Abstract
The objective of this project is to create a full-blown RPG helper application to organize and run role playing events.
The focus is on conventions or gatherings with multiple game masters and multiple groups of players. 
The software should help to organize the event, manage the players and game masters.


## Planned Features

* Event Management
  * [X] Provide a calendar of events to the public.
  * [ ] Let people register their own events.
  * [ ] Let people subscribe to new events.
  * Gamers
    * [ ] Register for events as gamer.
    * [ ] Register for gaming tables at events.
    * [ ] Manage their own registrations.
    * [ ] List their registered games.
    * [ ] Provide calendar feeds for their registered games and events.
  * Game Masters
    * [ ] Register for events as game master.
    * [ ] Register gaming tables at events.
    * [ ] Manage gamers at their tables.
    * [ ] Manage their gaming tables at events.
    * [ ] List their events and gaming tables.
  * Convention Organizers
    * [ ] Manage events.
    * [ ] Manage game masters.
    * [ ] Manage gamers.
    * [ ] Manage gaming tables.
    * [ ] Provide statistics about events, game masters, gamers, gaming tables.
* User Management
  * [ ] User registration and login.
  * [ ] User profiles.
  * User enablement
    * [ ] Provide an activity list for users.
* Integrations
  * [ ] Website https://www.rollenspiel-cons.info as primary site.
  * [X] Integration Google Calendar
  * [ ] Integration 3words
  * [ ] Integration OpenStreetMap
  * [ ] Discord for registering tables and games (like the late RPG-Schedule bot).
  * [ ] Calendar feeds (iCal) for gamers and game masters.
  * [ ] Email notifications for event changes.
  * [ ] SMS notifications for event changes.

Enough of the theory. Let's look at the current state:


## License
The license for the software is Gnu Affero General Public License 3 or newer. 

If you are interested in using parts of the code under a different license, please contact the author.


## Architecture

tl;dr (ok, only the bullshit bingo words):
- Immutable Objects
- Relying heavily on generated code
- 100 % test coverage of human generated code
- Every line of code not written is bug free!

Code test coverage for human generated code should be 100%, machinge generated code is considered bugfree until proven 
wrong. But every line that needs not be written is a bug free line without need to test it. So aim for not writing code.


## A note from the author
This should be a solution for organizing and running role playing convetions or gatherings.

If someone is interested in getting it faster, we may team up. I'm open for that. But be warned: I want to do it 
_right_. So no short cuts to get faster. And be prepared for some basic discussions about the architecture or software 
design :-).

---
Bensheim, 2025-09-07
