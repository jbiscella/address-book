# Technical test for GUMTREE.COM LTD
## Information
- __applicant name:__ _Jacopo Biscella_
- __applicant phone number:__ _0044 747 787 0122_
- __applicant e-mail:__ _work@jacopobiscella.me_

## How to build the application
Please clone the current repository and ensure to have __maven__ installed, then execute:
`mvn clean install`.

At the end of the build a jacoco repost will be created under `target/site/jacoco/index.html`.

__the tests covering the points requested in the specifications are under `src/test/java/com/gumtree/addressbook/CSVPersonUtilsTest.java`__:

In particular:
- _How many males are in the address book?_ -> `CSVPersonUtilsTest.testCountByGender`
- _Who is the oldest person in the address book?_ -> `SVPersonUtilsTest.testGetTheOldest`
- _How many days older is Bill than Paul?_ -> `CSVPersonUtilsTest.testCountDaysBetweenBirths`



