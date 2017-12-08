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
                      //  System.out.println("case [0] : " + verts[0]);
                    }
                    if (!myGraph.containsVertex(verts[1])) {
                        myGraph.addVertex(verts[1]);
                      //  System.out.println("case[1] : " + verts[1]);
                    }
                } catch (NullPointerException E) {
                }
                myGraph.addEdge(verts[0], verts[1]);
                //System.out.println(myGraph.toString());
            }
        } catch (Exception e) {
            System.out.println(" \"message\" : \"Error\" : \"Malformed JSON\" } ");
            return null;
        }
        return (myGraph);
    }

     public String getType(DirectedGraph<String, DefaultEdge> myGraph)
     {
         String result    = "default -- invalid";
         String  head     = null;
         String  tail     = null;
         int edges        = 0;
         int i            = 0;
         int rt           = 1;
         try{
             DepthFirstIterator dfs = new DepthFirstIterator(myGraph);
             String v = new String();
             ConnectivityInspector myInsp = new ConnectivityInspector(myGraph);

             if( myInsp.isGraphConnected() ) {
                 head = (String) dfs.next();
                 System.out.println(head);

                 v = head;
                 while (dfs.hasNext()) {
                     int currEdges = ((myGraph.edgesOf(v).toString().length())/9);
                     if( currEdges > 2 )
                     {
                         edges = currEdges;
                         System.out.println("MULTIPLE EDGES DETECTED!");
                         i++;
                         rt = -5000;
                     }
                     else if (currEdges == 2)
                     {
                         rt++;
                     }
                     System.out.println(v + "edges touching: " + edges+" total edges:"+(myGraph.vertexSet().size())+"i="+i);
                     System.out.println("If statement "+(currEdges));
                     v = (String) dfs.next();
                     tail = v;
                 }
             }
             if(( edges == (myGraph.vertexSet().size()) -1) && i == 1 )
             {
                 return "star";
             }
             System.out.println("rt is "+rt);
             System.out.println(myGraph.containsEdge(tail,head)+"     tail:"+tail+" head: "+head);
             if ( (rt == myGraph.vertexSet().size() ) && myGraph.containsEdge(tail,head))
                return "ring";
             else if((rt == myGraph.vertexSet().size()-1) && !myGraph.containsEdge(tail,head))
                 return "bus";
         } catch (Exception E) {
         }

         return result;
     }

    public static void main (String args[])
    {
        String test1 = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"B\", \"C\" ] } , { \"connected\" : [ \"C\", \"D\" ] } , { \"connected\" : [ \"D\", \"E\" ] } ] }";
        String bad1  = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"B\", \"C\" ] } , { \"connected\" : [ \"B\", \"D\" ] } , { \"connected\" : [ \"D\", \"E\" ] } , { \"connected\" : [ \"E\", \"A\" ] } ] }";
        String bad2  = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"B\", \"C\" ] } , { \"connected\" : [ \"B\", \"D\" ] } , { \"connected\" : [ \"D\", \"E\" ] } ] }";
        String star  = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"A\", \"C\" ] } , { \"connected\" : [ \"A\", \"D\" ] } , { \"connected\" : [ \"A\", \"E\" ] } ] }";
        String ring  = "{ \"inList\" : [ { \"connected\" : [ \"C\", \"D\" ] } , { \"connected\" : [ \"B\", \"C\" ] } , { \"connected\" : [ \"D\", \"A\" ] } , { \"connected\" : [ \"A\", \"B\" ] } ] }";

        String tIr   = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"B\", \"C\" ] } , { \"connected\" : [ \"C\", \"D\" ] } , { \"connected\" : [ \"C\", \"P\" ] } , { \"connected\" : [ \"P\", \"A\" ] } ] }";
        String tRi   = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"C\", \"B\" ] } , { \"connected\" : [ \"D\", \"C\" ] } , { \"connected\" : [ \"D\", \"E\" ] } , { \"connected\" : [ \"E\", \"A\" ] } ] }";
        String hard  = "{ \"inList\" : [ { \"connected\" : [ \"A\", \"B\" ] } , { \"connected\" : [ \"B\", \"C\" ] } , { \"connected\" : [ \"B\", \"D\" ] } , { \"connected\" : [ \"D\", \"E\" ] } , { \"connected\" : [ \"D\", \"F\" ] } ] }";

        ObjectMapper map = new ObjectMapper();
        InList myInList    = new InList();
        InList InList1    = new InList();
        InList InList2    = new InList();
        InList InList3    = new InList();
        InList InList4    = new InList();
        InList InList5    = new InList();
        InList InList6    = new InList();
        InList InList7    = new InList();
        String[] verts = null;

        try
        {
            /////////////////////////////////////////////////////
            // Try validating & parsing the JSON array
            myInList   = map.readValue(test1, InList.class);
            InList1    =  map.readValue(bad1, InList.class);
            InList2    =  map.readValue(bad2, InList.class);
            InList3    =  map.readValue(star, InList.class);
            InList4    =  map.readValue(ring, InList.class);
            InList5    =  map.readValue(tIr, InList.class);
            InList6    =  map.readValue(tRi, InList.class);
            InList7    =  map.readValue(hard, InList.class);
        }
        catch (Exception e) {
            System.out.println("\"Error\" : \"Malformed JSON\"");
        }

        DirectedGraph<String, DefaultEdge> myGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        Process myProcess = new Process();
        myGraph = myProcess.makeGraph(hard);
        String myString = myProcess.getType(myGraph);
        System.out.println("\nFINALLY:"+myString);
    }
}