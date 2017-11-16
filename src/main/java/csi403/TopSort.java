package csi403;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.Date;

/**
 * Created by hineeduh on 10/29/17.
 */
public class TopSort
{
    ///////////////////////////////////////////////
    // "Master" class that calls all of the other classes. Not much actual
    // processing is done here. Just calling the various other functions.
    //
    public String Process( String jsonStr)
    {
        ////////////////////////////////////////////
        // Validating JSOn, etc

        Date date = new Date();
        ObjectMapper map = new ObjectMapper();

        InList inList = new InList();
        try
        {
            inList = map.readValue(jsonStr, InList.class);
        }
        catch (Exception e)
        {
            return " \"message\" : \"Error - Malformed JSON\" }";
        }
        return "butts";

        //Graph myGraph = new Graph();
    }
}
