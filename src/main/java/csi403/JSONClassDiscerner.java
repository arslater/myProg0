package csi403;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by hineeduh on 10/29/17.
 */
public class JSONClassDiscerner
{
    public String discern(String jsonStr)
    {
        ObjectMapper map = new ObjectMapper();

        try
        {
            InList MyInList = map.readValue(jsonStr,InList.class);
            return "inList";

        } catch (Exception e) {}
        return "<unkown>";

    }
}
