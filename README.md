Graphs - Dijkstra's Shortest Path

Overview
--------

In this lab you will implement Dijkstra's shortest path algorithm. You
will use data for New York's cities and roads to construct a graph that
can be used to determine the neighbors of any city and find the shortest
path between two cities.

![](http://www.smart-traveler.info/sitebuildercontent/sitebuilderpictures/map_of_new_york.gif)\
**New York State Road Map**

Pre - Lab Work
--------------

1.  Review your lecture notes on graphs and shortest paths, and read the
    relevant text sections.
2.  Fetch the jar file by clicking [here
    labDijkstra.jar](https://www.cs.rit.edu/~csci242/Labs/09/Binaries/labDijkstra.jar).
3.  [Eclipse] You can insert the java and text files into an eclipse
    workspace and also configure the build path so it recognizes the
    `ArrayDiGraph.class` from the jar file. To do that, right-click on
    workspace, then select Build Path-\>Configure Build
    Path-\>Libraries-\> Add External Jar.

In-Lab Activities
-----------------

Activity \#1 : Writing the City and Road classes
------------------------------------------------

In this activity you will write the data storage classes
[**`City`**](https://www.cs.rit.edu/~csci242/Labs/09/docs/City.html) and
[**`Road`**](https://www.cs.rit.edu/~csci242/Labs/09/docs/Road.html).
These classes are used to represent the cities and roads that form a
graph.

These classes are used by the
[**`Mappy`**](https://www.cs.rit.edu/~csci242/Labs/09/docs/Mappy.html)
class. `Mappy` constructs a directed graph,
[**`DiGraph`**](https://www.cs.rit.edu/~csci242/Labs/09/docs/DiGraph.html),
which is implemented in the class file `ArrayDiGraph`. This graph is
used to represent a geographical map of cities and roads. The graph is
composed of the city name for the vertex key, a `City` for the vertex
data, and a `Road` for the edge data:

+--------------------------------------------------------------------------+
|               private DiGraph<String, City, Road> graph =                |
|               new ArrayDiGraph<String, City, Road>();                    |
|                                                                          |
+--------------------------------------------------------------------------+

The `Mappy` class contains all the code necessary to read the input data
and construct the graph. You can use the supplied data file of New York
cities/roads,
[ny.txt](https://www.cs.rit.edu/~csci242/Labs/09/Auxiliary/ny.txt) when
running the program:

+--------------------------------------------------------------------------+
|      java Mappy ny.txt                                                   |
|                                                                          |
+--------------------------------------------------------------------------+

The data in this file contains 4 pieces of information per line: the
source city name, the destination city name, the distance between the
cities (in miles), and the major road that connects them.

When the program runs, it provides a command line interface to display
information. The list of available commands can be found by entering the
`help` command:

+--------------------------------------------------------------------------+
|     > help                                                               |
|     help - displays this help message.                                   |
|     cities - displays all city information.                              |
|     neighbors city - displays neighbors for a given city.                |
|     quit - exits the program.                                            |
|     roads - displays all road information.                               |
|     travel source dest - display the shortest path between source and de |
| st cities                                                                |
|                                                                          |
+--------------------------------------------------------------------------+

Once you have written the `City` and `Road` classes, you should verify
they work by writing `main` methods for each of the classes and fully
testing all the methods. It is important that you read the javadocs for
these classes so that you know exactly how to define them.

When finished, the `cities` and `roads` commands should work correctly:

+--------------------------------------------------------------------------+
|     > cities                                                             |
|     Map has 16 cities:                                                   |
|     Albany has 4 road/s                                                  |
|     Binghamton has 4 road/s                                              |
|     Buffalo has 3 road/s                                                 |
|     Cortland has 2 road/s                                                |
|     Elmira has 3 road/s                                                  |
|     Jamestown has 2 road/s                                               |
|     Middletown has 3 road/s                                              |
|     NYC has 1 road/s                                                     |
|     Olean has 3 road/s                                                   |
|     Oneonta has 3 road/s                                                 |
|     Plattsburgh has 2 road/s                                             |
|     Poughkeepsie has 2 road/s                                            |
|     Rochester has 3 road/s                                               |
|     Syracuse has 4 road/s                                                |
|     Utica has 3 road/s                                                   |
|     Watertown has 2 road/s                                               |
|                                                                          |
|     > roads                                                              |
|     Map has 11 roads:                                                    |
|     I90 has 774 mile/s                                                   |
|     I87 has 646 mile/s                                                   |
|     I86 has 446 mile/s                                                   |
|     I88 has 324 mile/s                                                   |
|     US11 has 316 mile/s                                                  |
|     I81 has 290 mile/s                                                   |
|     I390 has 250 mile/s                                                  |
|     17 has 242 mile/s                                                    |
|     I84 has 164 mile/s                                                   |
|     US219 has 150 mile/s                                                 |
|     RT8 has 118 mile/s                                                   |
|                                                                          |
+--------------------------------------------------------------------------+

#### How To Submit

When you feel your code is working correctly and is properly documented,
submit your work using the following command:

+--------------------------------------------------------------------------+
|     try grd-242 lab9-1 City.java Road.java                               |
|                                                                          |
+--------------------------------------------------------------------------+

Activity \#2 : Finding Neighbors
--------------------------------

Now that the graph is constructed correctly, your next task is to
implement the `neighbors` command. It should display information about
the neighbors of a city that is supplied by the user, i.e.:

+--------------------------------------------------------------------------+
|     > neighbors Rochester                                                |
|     Rochester has 3 neighbors:                                           |
|     Buffalo is 75 mile/s on I90                                          |
|     Elmira is 125 mile/s on I390                                         |
|     Syracuse is 85 mile/s on I90                                         |
|                                                                          |
+--------------------------------------------------------------------------+

You will implement your solution in the `neighbors` routine of the
[**`Mappy`**](https://www.cs.rit.edu/~csci242/Labs/09/docs/Mappy.html)
class. Notice that the neighbors are printed out in alphabetical order.
Since the graph does not guarantee the order of the neighbors, you
should use a sorted collection to store the neighbor city names before
displaying the neighbor and road information.

#### How To Submit

When you feel your code is working correctly and is properly documented,
submit your work using the following command:

+--------------------------------------------------------------------------+
|     try grd-242 lab9-2 Mappy.java                                        |
|                                                                          |
+--------------------------------------------------------------------------+

Activity \#3 : Shortest Path Calculation
----------------------------------------

In the final activity you will implement the `travel` command. It should
display information about the shortest path between two cities, as in
this example:

+--------------------------------------------------------------------------+
|     > travel Buffalo Binghamton                                          |
|     Buffalo to Rochester via I90, 75 miles                               |
|     Rochester to Syracuse via I90, 85 miles                              |
|     Syracuse to Cortland via I81, 33 miles                               |
|     Cortland to Binghamton via I81, 42 miles                             |
|     Total distance is 235 miles                                          |
|                                                                          |
+--------------------------------------------------------------------------+

You will implement your solution in the `travel` routine of the
[**`Mappy`**](https://www.cs.rit.edu/~csci242/Labs/09/docs/Mappy.html)
class.

Your program will be able to compute the shortest path between any two
supplied cities. Use the following algorithm for calculating the
shortest path between a source and destination city:

+--------------------------------------------------------------------------+
|     Let 'vertices' contain all the vertices in the graph.                |
|                                                                          |
|     Initialize the source vertex data                                    |
|     with a cost of 0, and a null predecessor.                            |
|                                                                          |
|     Initialize the remaining vertex data                                 |
|     with a cost of Integer.MAX_VALUE, and a null predecessor.            |
|                                                                          |
|     Do:                                                                  |
|         Remove the 'vertex' that has the smallest cost from 'vertices'.  |
|                                                                          |
|         For each 'neighbor' of that 'vertex' that remains in 'vertices': |
|                                                                          |
|             Get the 'edge' between 'neighbor' and 'vertex'.              |
|                                                                          |
|             If 'vertex' cost + 'edge' distance is less than 'neighbor' c |
| ost:                                                                     |
|                                                                          |
|                 'neighbor' cost = 'vertex' cost + 'edge' distance.       |
|                                                                          |
|                 'neighbor' predecessor = 'vertex' name.                  |
|                                                                          |
|     While 'vertices' is not empty.                                       |
|                                                                          |
|     The graph's vertices now hold their predecessor (if any) and         |
|     the shortest distance/cost from the start.                           |
|                                                                          |
+--------------------------------------------------------------------------+

The graphs below show a trace of the algorithm as it selects each
least-cost vertex from the initial collection of 'vertices'. The
vertices begin as a list of all the vertices. As the algorithm removes
each vertex (when it becomes the working vertex), the graph shows it
double-circled. After removal from the 'vertices', a vertex contains its
finalized cost and predecessor information. The algorithm will change
only those vertices that remain in the 'vertices' collection.

![](https://www.cs.rit.edu/~csci242/Labs/09/Auxiliary/dijkstra.gif)\
**Dijkstra's Shortest Path Algorithm**

When the algorithm finishes, the graph will contain all the necesary
information to determine the shortest path. Each destination city stores
the total distance from the start city. To get the path, you should
iterate from the destination city to the source city, using the
predecessor of each city along the way. You can use a list to store each
city as you traverse the graph, inserting each predecessor at the front
of the list. When finished, you can traverse through the list from start
to end and print the city pairs along with the road information.

If the user wants to travel from the same source and destination city,
the algorithm will still work, but you should display a different
message for the route:

+--------------------------------------------------------------------------+
|     > travel Rochester Rochester                                         |
|     You are already there!                                               |
|     Total distance is 0 miles                                            |
|                                                                          |
+--------------------------------------------------------------------------+

This algorithm also supports the case where the graph is not fully
connected and there is no path from the source to destination. If a
vertex in the graph is unconnected, then the vertex will have the
initial, maximum cost value when pulled out of the 'vertices'. You can
tell that a vertex was unreachable if it has a maximum cost and a null
predecessor. (By contrast, the start vertex has 0 cost and a null
predecessor.)

#### How To Submit

When you feel your code is working correctly and is properly documented,
submit your work using the following command:

+--------------------------------------------------------------------------+
|     try grd-242 lab9-3 Mappy.java                                        |
|                                                                          |
+--------------------------------------------------------------------------+


