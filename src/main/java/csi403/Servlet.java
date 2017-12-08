package csi403;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*; //shouldn't be nessessary ..?
import javax.json.*;

/**
 * Standard utilization of servlets, kept separate to make things cleaner.
 */

public class Servlet extends HttpServlet
{
    public void init() throws ServletException
    {
        ///////////////////////////////////////////////////
        // Standard initialization of the Servlet function
        //
        // Actual code comming soon to a program near you!
    }
    public void doPost(HttpServletRequest  request,
                       HttpServletResponse response)
                throws ServletException,   IOException
    {
        //////////////////////////////////////////////
        // Standard servlett method, handles a POST operation
        // Moved the actual processing to the "ReverseList" class
        doService(request, response);
    }
    public void doGet (HttpServletRequest request, HttpServletResponse response)
                throws ServletException,  IOException
    {
        //////////////////////////////////////////////
        // Another standard servlet method. BUT we do not accept GET requests
        //
        // So we will set response content type and return an error message

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{'message' : 'Use POST!'}");
    }

    private void doService(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        Process myProcess = new Process();
        DirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph = myProcess.makeGraph(request);
        String type = myProcess.getType(graph);

        response.setContentType("application/json");
        out.println("\t{ \"type\": \"" + type + "\" }");
    }
    public void destroy()
    {
        ////////////////////////////////////////////////////
        // Do any of the tear-down stuff here
    }
}
