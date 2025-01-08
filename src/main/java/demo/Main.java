package demo;

import java.util.Vector;

public class Main {
    public static void main(String[] args) {


        Restaurant laTanaDelDuce = new Restaurant("la tana del duce", "piazza loreto", "3333333333", "latanadelduce");

        Vector<Restaurant> V = new Vector<>();

        V.add(laTanaDelDuce);

        RestaurantList mainList = new RestaurantList(V);
        RestaurantList.printAllRestaurants();

        //prova sonar cloud pazzo sgravato
    }
}