package Principal;

import static java.lang.Math.toRadians;
import static java.lang.Math.asin;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;


public class Haversine {
    double lat1, long1, lat2, long2;

    public Haversine(double lat1, double long1, double lat2, double long2) {
        this.lat1 = lat1;
        this.long1 = long1;
        this.lat2 = lat2;
        this.long2 = long2;
    }

    public double distancia(){
        double dlat = toRadians(lat2 - lat1); //diferenca entre as latitudes
        double dlong = toRadians(long2 - long1); //diferenca entre as longitudes
        double lati1 = toRadians(lat1); //latitude1, ja em radianos
        double lati2 = toRadians(lat2); //latitude2, ja em radianos
        double raio = 6371; //raio da Terra
        double trig = asin(sqrt(pow(sin(dlat/2),2) + cos(lati1)*cos(lati2)*pow(sin(dlong/2),2)));
        return 2*raio*trig;
    }
}


//Fonte: https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/