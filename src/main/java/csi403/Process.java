package csi403;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Traverse;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;
import sun.security.provider.certpath.Vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Processing of the Algorithm is done here
 */
public class Process {
    public DirectedGraph<String, DefaultEdge> makeGraph(String jsonstr)
    {
        InList myList = new InList();
        ObjectMapper map = new ObjectMapper();

        ///////////////////////////////////////////////////
        // Validatign the jsonstring
        try {
            myList = map.readValue(jsonstr, InList.class);
        } catch (Exception e) {
            System.out.println(" \"message\" : \"Error\" : \"Malformed JSON\" } ");
            return null;
        }
        ///////////////////////////////////////////////
        // Creating the graph
        DirectedGraph<String, DefaultEdge> myGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        try {
            Connected[] nodes = myList.getInList();

            if ((nodes == null) || (nodes.length == 0)) {
                throw new Exception("Null Relations");
            }
            for (int i = 0; i < nodes.length; i++) {
                String[] verts = nodes[i].getConnected();

                if (verts == null) {
                    throw new Exception("Null args");
                }
                if (verts.length > 2) {
                    throw new Exception("Too many args");
                }
                try {
                    if (!myGraph.containsVertex(verts[0])) {
                        myGraph.addVertex(verts[0]);
                        System.out.println("case [0] : " + verts[0]);
                    }
                    if (!myGraph.containsVertex(verts[1])) {
                        myGraph.addVertex(verts[1]);
                        System.out.println("case[1] : " + verts[1]);
                    }
                } catch (NullPointerException E) {
                }
                myGraph.addEdge(verts[0], verts[1]);
                System.out.println(myGraph.toString());
            }
        } catch (Exception e) {
            System.out.println(" \"message\" : \"Error\" : \"Malformed JSON\" } ");
            return null;
        }
        return (myGraph);
    }

     public String getType(DirectedGraph<String, DefaultEdge> myGraph)
     {
         String result = "Irregular";
         String  head  = null;
         String  tail  = null;
         ConnectivityInspector myInsp = new ConnectivityInspector(myGraph);

         if (myInsp.isGraphConnected()) {
             try {
                 TopologicalOrderIterator iter = new TopologicalOrderIterator<>(myGraph);
                 String v = null;
                 head = (String) iter.next();

                 while (iter.hasNext())
                 {
                     v = (String) iter.next();
                     System.out.print(v);

                 }
                 ////////////////////////
                 // Detecting ring topography
                 tail = v;

                 if(myInsp.pathExists(head,tail))
                 {
                     return("Ring");
                 }
             } catch (Exception E) {
             }
             result = "apples";
         }
         return result;
     }

    public static void main (String args[])
    {
        String test1 = "{ \"inList\" : [ { \"connected\" : [ \"C\", \"D\" ] } , { \"connected\" : [ \"D\", \"A\" ] } , { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"B\", \"C\" ] } ] }";

        ObjectMapper map = new ObjectMapper();
        InList myInList    = new InList();
        String[] verts = null;

        try
        {
            /////////////////////////////////////////////////////
            // Try validating & parsing the JSON array
            myInList          = map.readValue(test1, InList.class);
            Connected[] nodes = myInList.getInList();
            verts      = nodes[0].getConnected();

            System.out.println("The first value is "+verts[0]+" The second is "+verts[1]);
        }
        catch (Exception e) {
            System.out.println("\"Error\" : \"Malformed JSON\"");
        }

        DirectedGraph<String, DefaultEdge> myGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        Process myProcess = new Process();
        myGraph = myProcess.makeGraph(test1);
        String myString = myProcess.getType(myGraph);
        System.out.println("\nFINALLY:"+myString);
    }
}