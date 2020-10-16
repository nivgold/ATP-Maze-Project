# ATP-Maze-Project


## Summary
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

## Examples
---

### Main Menu
![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602884314/samples/menu_empty_aajrap.png "Main Menu")

###### The User can play a new game (New option), save an on-going game (Save option) and load a saved game (Load option)

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885261/samples/menu_file_yrmwnp.png "Main Menu File")

###### The User can select which solver he prefers (Best-First-Serach, Breadth-First-Search or Depth-First-Search)

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885180/samples/menu_opt_solver_rq2ba7.png "Main Menu Option Solver")

###### The User can select which maze generator he prefers (Empty, Randomized or by Kruskal's algorithm)

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885181/samples/menu_opt_gen_trgx6p.png "Main Menu Option Generator")

###### The User can select the size of the server's thread pool

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885179/samples/menu_opt_pool_hqpl1n.png "Main Menu Option Pool")

###### The User can choose one character to play with among 3 characters 

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885180/samples/menu_choose_yqfwlm.png "Main Menu Choose")

###### The User can select the size (heigth and width) of the generated maze he likes to play

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885181/samples/choose_size_och50h.png "Menu Maze Size")

###### The Help message at the navbar explain what's the game targer and other defenitions 

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885163/samples/help_section_agsck0.png "Help Section")

###### Gameplay

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885185/samples/gameplay_1_hsonc9.png "Gameplay 1")

###### The User can click on the 'Solution' button at the left side of the screen in order to see the maze's solution

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885168/samples/solution_1_gj0imw.png "Solution 1")

###### Winning screen

![alt text](https://res.cloudinary.com/dxeniml9z/image/upload/v1602885176/samples/winner_fvpplc.png "Winner 1")
