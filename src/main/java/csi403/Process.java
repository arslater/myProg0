package csi403;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hineeduh on 10/29/17.
 */
public class Process
{
    public static void main(String[] args)
    {
        System.out.println("Enter in the Array for processing");
        ObjectMapper map = new ObjectMapper();
        String input = new String();
        InList inList = new InList();
        Smarter[] relations;
        Smarter name;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
        } catch (IOException e) {}

        JSONClassDiscerner myTest = new JSONClassDiscerner();
        System.out.println ( " \n I am a... "+myTest.discern(input) );

        try{
            System.out.println("At the first try statement, before definition");
            //System.out.println(input);
            inList = map.readValue(input, InList.class);
            System.out.println("Hey, I'm still working after the declaration");

        }
        catch (Exception e) {}

        relations = inList.getInList();
        //name = relations[1];

        for( int i = 0; i< relations.length; i++) {
            System.out.println("**" + (relations[i].getSmarter())[0]);
        }

    }
}
/*
{ "inList" : [ { "smarter" : [ "Einstein", "Feynmann" ] }, { "smarter" : [ "Feynmann", "GellMann" ] } ] }
{ "inList" : [ { "smarter" : [ "Apple" , "Buuts" ] }, { "smarter" : [ "Apple" , "Buuts" ] }, { "smarter" : [ "Apple" , "Buuts" ] }, { "smarter" : [ "dsf" , "fff" ] } ] }

 { “inList” : [ { “smarter” : [ “Einstein”, “Feynmann” ] }, { “smarter” : [ “Feynmann”, “GellMann” ] } ] }
 */

