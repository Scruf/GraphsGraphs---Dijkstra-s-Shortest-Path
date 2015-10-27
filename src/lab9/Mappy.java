/**
  * File: {ID_TAG}
  * Revisions:
  *     {LOG_TAG}
  */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.TreeMap;

/**
 * The Mappy program reads in city and road information from a file.
 * It then allows the user to enter commands to do things like
 * display all cities / city neighbors / roads, as well
 * as compute and display the shortest path between two cities.
 *
 * @author sps (Sean Strout)
 * @author {YOUR_NAME_HERE}
 */
public class Mappy {

    /**
     * All relevant information is stored in a directed graph that
     * stores: city name, city data, road data.
     */
    private DiGraph<String, City, Road> graph =
        new ArrayDiGraph<String, City, Road>();

    /**
     * Constructs an object by reading in the filename and creating
     * the graph.  The data in the file must be organized with
     * city and road information as follows, one per line:<br>
     * <br>
     * city neighbor-city distance road<br>
     * ...<br>
     *
     * @param filename the file name that stores the data
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if there is an error reading from the file
     */
    public Mappy(String filename) throws FileNotFoundException, IOException {
        // read map data in: source destination distance road
        Scanner input = new Scanner(new File(filename));

        while (input.hasNext()) {
            String first = input.next();
            if (!graph.isVertex(first))
                graph.addVertex(first, new City(first));
            String second = input.next();
            if (!graph.isVertex(second))
                graph.addVertex(second, new City(second));
            int distance = input.nextInt();
            String road = input.next();
            Road edge = new Road(distance, road);
            try {
                graph.addEdge(first, second, edge);
                graph.addEdge(second, first, edge);
            } catch (NoSuchVertexException nve) {}
        }

        input.close();
    } // Mappy

    /**
     * Displays the list of valid commands and their descriptions.
     */
    private void help() {
        System.out.println("help - displays this help message.");
        System.out.println("cities - displays all city information.");
        System.out.println("neighbors city - " +
                           "displays neighbors for a given city.");
        System.out.println("quit - exits the program.");
        System.out.println("roads - displays all road information.");
        System.out.println("travel source dest - " +
                "display the shortest path between " +
                "source and destination cities");
    } // help

    /**
     * Displays the list of cities, sorted alphabetically using the
     * city name using the following format:<br>
     * <br>
     * Map has {#} cities:<br>
     * {First-city} has {#} road/s<br>
     * {Second-city} has {#} road/s<br>
     * ...<br>
     *
     * @throws NoSuchVertexException if a city does not exist in the map.
     */
    private void cities() throws NoSuchVertexException {
        // display cities alphabetical, with # roads
        TreeSet<String> cities = new TreeSet<String>(graph.vertexKeys());
        System.out.println("Map has " + graph.numVertices() + " cities:");
        for (String c : cities) {
            System.out.println(c + " has " + graph.inDegree(c) + " road/s");
        }
    } // cities

    /**
     * Displays the set of roads and each combined length, sorted from
     * largest to smallest length using the following format:<br>
     * <br>
     * Map has {#} roads:<br>
     * {First-road} has {#} mile/s<br>
     * {Second-road} has {#} mile/s<br>
     * ...<br>
     *
     * @throws NoSuchVertexException if a city does not exist in the map.
     */
    private void roads() throws NoSuchVertexException {
        // build a map keyed by road names with total lengths as values
        final Map<String, Integer> map = new HashMap<String, Integer>();
        for (Road edge : graph.edgeData()) {
            String road = edge.getRoad();
            map.put(road, map.get(road) == null ?
                    edge.getLength() : map.get(road)+edge.getLength());
        }

        // sort the roads from longest to shortest
        List<String> list = new ArrayList<String>(map.keySet());
        Collections.sort(list, new Comparator<String>() {
            public int compare(String p1, String p2) {
                int result = map.get(p2) - map.get(p1);
                if (result == 0) {
                    result = p1.compareTo(p2);
                }
                return result;
            }
        });

        System.out.println("Map has " + map.size() + " roads:");
        for (String road : list) {
            System.out.println(road + " has " + map.get(road) + " mile/s");
        }
    } // roads

    /**
     * Displays information about each of the neighbors of a given city
     * using the following format:<br>
     * <br>
     * {city} has {#} neighbors:<br>
     * {First-city} is {#} mile/s on {road}<br>
     * {Second-city} is {#} mile/s on {road}<br>
     * ...<br>
     *
     * @param city the city to determine the neighbors for.
     * @throws NoSuchVertexException if a city does not exist in the map.
     */


    private void neighbors(String city) throws NoSuchVertexException {
        // //@param data will hold the references of the neighbor objects
        ArrayList<Object> data = new ArrayList(graph.neighborData(city));
      // //@param treeMap will hold the name of the city and a reference to a road object which is associated
        //with a city it also will store them in alphabetical order
        Map<String,Road> treeMap =new TreeMap<String,Road>();
        for(Object o : data){

            //typecast the Object type to a City object
            City c = (City)o;
            //create empty object which will hold the parameters of the Road cbject
            Road road = null;
            road = graph.getEdgeData(city,c.getName());
            //put name of the city and the road object into the map
            treeMap.put(c.getName(),road);
        }
        System.out.println(city+" has "+treeMap.size()+" neighbors: ");
        for(Map.Entry<String,Road> entry : treeMap.entrySet()){
          String name=entry.getKey();
          Road r = entry.getValue();
          System.out.println(name+" is "+r.getLength()+" mile/s on "+r.getRoad());
        }
      
    } // neighbors

    /**
     * Displays the shortest path between the source and dest cities
     * using the following format:<br>
     * <br>
     * {first-city} to {second-city} via {road}, {#} miles<br>
     * {second-city} to {third-city} via {road}, {#} miles<br>
     * ...<br>
     * Total distance is {#} miles<br>
     * <br>
     * If the source and destination are the same it should print:<br>
     * <br>
     * You are already there!<br>
     * Total distance is 0 miles<br>
     * <br>
     * @param source the starting city.
     * @param dest the destination city.
     * @throws NoSuchVertexException if a city does not exist in the map.
     */
    private void travel(String source, String dest)
                       throws NoSuchVertexException {
	      if(source.equals(dest))
          System.out.println("You're already there\n"+"You're traveled 0 miles");

      System.out.println(graph.vertexKeys());
      DiGraph<String,Integer,String> verticies = new ArrayDiGraph<String,Integer,String>();


    } // travel

    /**
     * The main loop prompts and waits for user input.  If the command is
     * valid the appopriate helper function is called.  If an error occurs
     * because an invalid city name was entered, the following is
     * displayed:<br>
     * <br>
     * Error, a city was not found in the map!<br>
     * <br>
     * Program execution continues until the 'quit' command or CTRL-D
     * is entered.
     */
    private void mainLoop() {
        Scanner input = new Scanner(System.in);
        String command = "";
        // run the main loop until quit or CTRL-D is entered
        do {
            System.out.print("> ");
            command = input.next();

            // invoke the appropriate function based on the user command
            try {
                if (command.equals("cities")) {
                    cities();
                }
                else if (command.equals("help")) {
                    help();
                }
                else if (command.equals("neighbors")) {
                    neighbors(input.next());
                }
                else if (command.equals("roads"))  {
                    roads();
                }
                else if (command.equals("travel")) {
                    travel(input.next(), input.next());
                }
            } catch  (NoSuchVertexException nve) {
                System.out.println("Error, a city was not found in the map!");
            }

            input.nextLine();
        } while (!command.equals("quit"));

        input.close();
    } // mainLoop

    /**
     * The main routine verifies the command line args, constructts the
     * Mappy object, and executes its main loop.
     *
     * @param args the command line argument should be the name of the file
     *     the holds the city/road information.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Mappy filename");
            System.exit(0);
        }

        try {
            Mappy shortest = new Mappy(args[0]);
            shortest.mainLoop();
        } catch (FileNotFoundException fnfe) {
            System.err.println("Error opening file " + args[0]);
        } catch (IOException ioe) {
            System.err.println("Error opening file " + args[0]);
        }
    } // main
}
