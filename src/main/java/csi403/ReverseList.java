package csi403;

/*
 * This METHOD gets CALLED WHEN THE POST OPERATION IS SPECIFIED.
 * NOT THE OTHER WAY AROUND
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*; //shouldn't be nessessary ..?
import javax.json.*;

public class ReverseList extends HttpServlet
{
    public void doReverse(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonStr = "";
        if(br != null)
        {
            jsonStr = br.readLine();
        }

        PrintWriter out = response.getWriter();
        //out.println("{ \"outList\": Doing things!}");

        /////////////////////////////////////////
        // Creating a JsonReader Object
        StringReader strReader = new StringReader(jsonStr);
        JsonReader reader = Json.createReader(strReader);

        /////////////////////////////////////////
        // Get the singular JSON object (name:value pair) in this message.
        JsonObject obj = reader.readObject();

        // From the object get the array named "inList"
        JsonArray inArray = obj.getJsonArray("inList");

        //////////////////////////////////////////////
        // Convert the JSON array into a number array

        // Create an int array to accomodate the numbers.
        int[] numbers = new int[inArray.size()];

        // Extract numbers from JSON array.
        for (int i = 0; i < inArray.size(); ++i) {
            numbers[i] = inArray.getInt(i);
        }

        if(inArray == null)
        {
            // Set response content type to be JSON
            response.setContentType("application/json");
            // Send back the response JSON message
            //PrintWriter out = response.getWriter();
            out = response.getWriter();
            out.println("{ \"ERROR\":INVALID LIST");
            System.exit(1);
        }

        ////////////////////////////////////////
        // Insertion List sorting algorithm.
        // Sorts lists from LEAST to GREATEST
        int i   = 0;
        int j   = 0;
        int key = 0;

        JsonArrayBuilder outArrayBuilder = Json.createArrayBuilder();

        for(i=1;i<inArray.size(); i++)
        {
           // out.println("SIZE IS :"+inArray.size())
            out.println("Comparing "+numbers[j]+" and "+key);
            for(j=i; j>0; j--)
            {
                if (numbers[j] < numbers[j - 1])
                {
                    key = numbers[j];
                    out.println("ALERT! " + numbers[j] + " > " + key);
                    numbers[j] = numbers[j - 1];
                    numbers[j - 1] = key;
                    // out.println("Swapping "+numbers[j+i]+" with "+numbers[j]);
                }

            }
        }
        for(i=0;i<inArray.size();i++) {
            outArrayBuilder.add(String.valueOf(numbers[i]));
        }
        ////////////////////////////////////////
        // Set response content type to be JSON
        response.setContentType("application/json");

        ////////////////////////////////////////
        // Send back the response JSON message
        //PrintWriter out = response.getWriter();
        out.println("{ \"outList\" : " + outArrayBuilder.build().toString() + "}");
    }
}