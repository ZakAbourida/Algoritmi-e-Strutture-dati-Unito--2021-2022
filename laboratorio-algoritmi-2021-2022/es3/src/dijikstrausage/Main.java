package src.dijikstrausage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import src.dijikstra.Dijikstra;
import src.dijikstra.DijikstraException;
import src.dijikstra.Node;
import src.mygraph.MyGraphException;
import src.myheap.MyHeapException;

public class Main{
    public static void main(String args[]) throws MyHeapException, MyGraphException, DijikstraException{
        Dijikstra<String, Float> dijikstra = new Dijikstra<>();

        //-------------------------------------------------------------------------------//
        //load file
        if(args.length == 0){
            System.out.println("need to specify the path of the file containgin the paths informations");
            return;
        }
        File file = new File(args[0]);
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        //-------------------------------------------------------------------------------//
        //read file
        while(sc.hasNextLine()){
            //get values
            String line = sc.nextLine();
            String tmp[] = line.split(",");
            if(tmp.length != 3){
                System.out.println("found line with no unmatching number of fields");
                sc.close();
                return;
            }

            //-------------------------------------------------------------------------------//
            //load path
            try {
                dijikstra.addArch( new Node<>(tmp[0]), new Node<>(tmp[1]), Float.parseFloat(tmp[2]) );
            } catch (DijikstraException e) {
                System.out.println("DijikstraException: " + e + " type = " + e.type);
                sc.close();
                return;
            } catch (MyGraphException e) {
                System.out.println("MyGraphException: " + e + " type = " + e.type);
                sc.close();
                return;
            }
        }

        //-------------------------------------------------------------------------------//
        //user inout
        Scanner input = new Scanner(System.in);

        //ask for starting city
        String startingCity;
        do {
            System.out.println("insert starting city: ");
            startingCity = input.nextLine();    
        } while (!checkForCity(dijikstra, new Node<>(startingCity)));

        //-------------------------------------------------------------------------------//
        //calculate min path
        dijikstra.camminoMinmo( new Node<>(startingCity));

        //-------------------------------------------------------------------------------//
        while (true) {
            //ask for destination city or exit
            String destinationCity;
            do {
                //get input
                System.out.println("insert destination city (or type \"exit\" to quit): ");
                destinationCity = input.nextLine();
                //exit
                if(destinationCity.compareTo("exit") == 0)
                    break;
            } while (!checkForCity(dijikstra, new Node<>(destinationCity)));
            //exit
            if(destinationCity.compareTo("exit") == 0)
                break;
            //get distance
            Float distance = dijikstra.getDistance( new Node<>(destinationCity) );
            if(distance == null)
                System.out.println(startingCity + " and " + destinationCity + " are not connected by any path");
            else
                System.out.println("distance between " +startingCity + " and " + destinationCity + " is " + distance + "m");
        }

        //-------------------------------------------------------------------------------//
        //after
        input.close();
        sc.close();
    }

    //======================================================================================//

    private static boolean checkForCity(Dijikstra<String, Float> d, Node<String, Float> s) throws MyGraphException{
        if(d.containNode(s))
            return true;
        System.out.println("no such city");
        return false;
    }

}