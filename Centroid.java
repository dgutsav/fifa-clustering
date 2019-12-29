import java.util.*;
class Centroid{
    Coordinates centroidCoordinates, average;
    ArrayList <Sample> centroidSamples = new ArrayList<>();
    void dispCentroid(){
        centroidCoordinates.dispCoordinates();
        /*for(Sample s:centroidSamples)
            System.out.println(s.name + " "+ centroidCoordinates.getEuclideanDistance(s.coordinate));*/
    }
    void dispRefined(){
        this.refineCentroid();
        this.average.dispCoordinates();
    }
    void refineCentroid(){
        average = new Coordinates();
        average.resetCoordinate();
        for(Sample sample: centroidSamples){
            average.addCoordinates(sample.maxed_out_coordinate);
        }
        average.reduceCoordinate(centroidSamples.size());
    }
}