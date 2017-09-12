package csi403;

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
  /*  public void doPost(HttpServletRequest  request,
                       HttpServletResponse response)
                throws ServletException,   IOException
    {
        //////////////////////////////////////////////
        // Standard servlett method, handles a POST operation
        doService(request, response);
    }*/
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
    public void destroy()
    {
        ////////////////////////////////////////////////////
        // Do any of the tear-down stuff here
    }
}
