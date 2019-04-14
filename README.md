Flights Dashboard

Simple app to parse CSV file with flights schedule and display it in live.

**HOW TO RUN**

1. To run application simply run `bootRun` gradle task.
2. Go to `http://localhost:8080/dashboard`

**WHATS USED**
1. Java 8
2. Gradle
3. Spring WebFlux for Server-Sent events
4. Vanilla JS

**UPDATE SCHEDULE**

You can replace existing file in repo with new one.

For change in runtime you can post raw cvs text like:

`17:07,Geneva,GVA,CM101,x,,,,,,`

to `POST: /flight`
