package csi403;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static java.lang.StrictMath.abs;

/**
 * Created by hineeduh on 11/16/17.
 */
public class Process {
    public static void main(String args[]) {
        System.out.println("Processing the array of coordinates...");
        ObjectMapper map = new ObjectMapper();
        String input = new String();
        inList myInList = new inList();
        Coordinate[] co = null;
        int i = 0;

        int fX = 0;
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        input = "{ \"inList\" : [  { \"x\" : 2,   \"y\" : 1 } , { \"x\" : 2,   \"y\" : 4 } , { \"x\" : 8,  \"y\" : 4 }, { \"x\" : 11, \"y\" : 1 } ] }";
        try {
            //System.out.println(map.readValue(input,inList.class));
            myInList = map.readValue(input, inList.class);
            co = myInList.getInList();

            fX = co[1].getY();
        } catch (Exception e) {
            System.out.println("Malformed json");
        }

        System.out.println("The first  value is:" + fX);
        Process myp = new Process();
        System.out.println(myp.doArea(co));
    }

    public int doArea(Coordinate[] co) {
        int i   = 0;
        int sum = 0;
        int limit = co.length;
        int[] xVals = new int[limit+1];
        int[] yVals = new int[limit+1];

        for (i = 0; i < limit; i++) {
            xVals[i] = co[i].getX();
            yVals[i] = co[i].getY();
        }
        xVals[limit] = xVals[0];
        yVals[limit] = yVals[0];

        System.out.println(Arrays.toString(xVals));
        System.out.println(Arrays.toString(yVals));



        for (i=0;i<limit;i++)
        {
            sum += (xVals[i]*yVals[i+1]) - (yVals[i]*xVals[i+1]);
            System.out.println((xVals[i]+"*"+yVals[i+1])+"-"+ (yVals[i]+"*"+xVals[i+1]));
        }
        return ( (int)abs(sum*.5) );
    }
}
/* Test input

{ "inList" : [ { "x" : 2, "y" : 1 } , { "x" : 2, "y" : 4 } ] }

{ "inList" : [  { "x" : 2,   "y" : 1 } , { "x" : 2,   "y" : 4 } , { "x" : 8,  "y" : 4 }, { "x" : 11, "y" : 1 } ] }

 */