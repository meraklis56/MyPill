# MyPill

MyPill is a test application which is designed to remind my friend to take the pills every morning. It is my first Android application after 4 years so not all the best practices were followed. The purpose was to re-learn the basic concepts, catch up with last changes (like moving to AndroidX), play around with different APIs(Firebase, SQLite) and combine knowledge from the previous years.

More analytically, every morning around 5 o clock, the main notification is created so when my friend wakes up, to be reminded about the pill. (An approach which MyPill listens when the phone is unlocked and fire a notification the first time after 5 AM, was rejected as it is considered as an overkill and will cause high energy consumption) 

There are 3 actions:

1. Pill ingested
2. Pill was forgotten
3. Snooze for 30 minutes

When 1 and 3, a new notification will be fired up in 30 minutes. If 1, then a secondary notification is fired up that reminds that the pill is ingested and anything can be eaten. If 3, then the main notification appears again.

Every action, is logged not only on the local DB (SQLite) but on Firebase as well.

In the first time of execution, the LoginActivity is executed in which my friend must log in. (The authentication is done using Firebase Authentication API). Furthermore, for visual reasons, an animation of the main logo created by Adobe After Effects and used in Android using Lottie.

After the login, the user is presented with the MainActivity which includes 3 fragments in a TabbedView:

1. ListFragment: includes a custom CardView for every action the user has fired
2. CalendarFragment: includes every action placed into the according day in a calendar
3. GraphFragment: visualizes the actions into a pie chart

In addition, a menu is also implemented enabling the user to Logout()

I could say, the main functionality is implemented. 

**What I would have done if I started from the beginning/Future steps?**

* Implement tests
* Create my own CustomView
* Replace Firebase with something else so I can play with REST queries
* More complex layout so I can learn ConstantLayout
