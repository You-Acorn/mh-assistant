Monster Hunter Assistant was built as a tool to help new players plan their next quest. To start the application fill in the database properties in the application.properties file and run the MhAssistantApplication.java file and open your browser to localhost:8080.

A user will log into the application and search for a quest which contains monsters they want to fight. Then they will be given the shortest list of quests to complete hunt every monster. However, they application is designed to not always give the quickest quests to the user as a way of keeping the game interesting.

The user can also look up quests by their id for reference.

Another use of the MHA is to find monsters a user wants to fight based on what monsters they will have an advantage against given their current weapon. The application then provides a handy link to present quests which fight those monsters.

MHA is a Java application build with Spring Boot and Apache Maven. Its backend is MySQL while the front end is maintained with Bootstrap but also utilizes Thymeleaf to make it more dynamic and interface with the Java controller.

The most challenging part of this project was integrating all the different elements together. Getting the repository to communicate with the database the way I wanted or figuring out how to get styles to work with security. If I was to do this project again, I would have a much easier time now that I more completely understand how the individual parts work together.
Through the process of building this application I learned how much I love this profession. While I also developed a plethora of technical skills the biggest takeaway, was the joy of problem solving and the excitement of seeing something I’ve build from the ground up work exactly as intended. Tools change and systems evolve but problem-solving skills, resilience, curiosity, and drive are tools I will take with me far into the future. The Monster Hunter Assistant has reinvigorated me for coding.

Future projects include a more robust admin page, different quest finding algorithms, and visuals to enhance the accessibility. If I had more time I would also reimplement the microservice structure of the original design which was unfortunately cut for time.