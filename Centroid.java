import java.util.*;
class Centroid{
    Coordinates centroidCoordinates;
    ArrayList <Sample> centroidSamples = new ArrayList<>();
    void dispCentroid(){
        centroidCoordinates.dispCoordinates();
        for(Sample s:centroidSamples)
            System.out.println(s.name + " "+ centroidCoordinates.getEuclideanDistance(s.coordinate));
    }
}