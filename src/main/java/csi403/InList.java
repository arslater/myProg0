package csi403;

import java.util.List;

/**
 * Created by hineeduh on 10/29/17.
 */

public class InList extends Smarter
{
    ///////////////////////////////////////////////////////////
    // Creating the inList master List

    private Smarter[] inList;
    private Smarter mySmarter;

    public Smarter[] getInList()
    {
        return inList;
    }
}

// { "inList" : [ { "smarter" : [ "Apple" , "Buuts" ] }, { "smarter" : [ "Apple" , "Buuts" ] }, { "smarter" : [ "Apple" , "Buuts" ] }, { "smarter" : [ "dsf" , "fff" ] } ] }
