package demo;
import java.util.Vector;

public class RestaurantList {

    private static Vector<Restaurant> Ristoranti;


    public RestaurantList(Vector<Restaurant> V){

        this.Ristoranti = V;


    }

    public RestaurantList(){

        this.Ristoranti = null;
    }

    public static void printAllRestaurants() {
        for (Restaurant restaurant : Ristoranti) {
            restaurant.PrintData();
        }
    }
}