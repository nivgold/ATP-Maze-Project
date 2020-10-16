# ATP-Maze-Project


### Summary
---

A project that was made as part of the course ['Advanced Topics in Programming'](https://moodle2.bgu.ac.il/moodle/pluginfile.php/1750980/mod_resource/content/5/syllabus2019.pdf).

ATP-Maze-Project is a maze game that was built with JAVA and contains some popular, well known design patterns such as Observer, Strategy etc.
With a Server that responsible of generating maze maps by couple of different methods and a client with a GUI that was build with JavaFX as a MVVM (Model-view-viewmodel) architecture, the two sides communicate through a well-defined protocol in a Client-Server manner.
For every maze generation the server create, he has also a solution for that particular maze.
Inside the game, the user can change settings that relate to the maze generation difficulty, solution method and the number of the server's thread pool

for the maze generation difficulty levels there are:


* **EmptyMazeGenerator** - that generate an empty maze without any walls
* **SimpleMazeGenerator** -  that generate a maze with random walls
* **MyMazeGeneration** - that generate a maze by the [Kruskal's Algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm)

for the solution methods there are:


* **BestFirstSearch** - that solve the maze map by the [Best First Search](https://en.wikipedia.org/wiki/Best-first_search) algorithm
* **BreadthFirstSearch** - that solve the maze map by the [Breadth First Search](https://en.wikipedia.org/wiki/Breadth-first_search) algorithm
* **DepthFirstSearch** - that solve the maze map by the [Depth First Search](https://en.wikipedia.org/wiki/Depth-first_search) algorithm

The whole project accompanied with [Log4j](https://logging.apache.org/log4j/2.x/) logger that logs every connection, request, operation or move that the server's do. 
