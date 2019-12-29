import java.util.*;
import java.io.*;
class Main{
    static List<Sample> samples = new ArrayList<>();
    static int num_centroids = 20;
    static Centroid[] centroids = new Centroid[num_centroids];
    static int num_attributes = 33;
    static double distances[][];
    static ArrayList<Position> positions = new ArrayList<>();
    public static void loadSamples(){
        ArrayList <Double> ratings;
        double max;
        String str[];
        try{
            String line;
            BufferedReader csvReader = new BufferedReader(new FileReader("fut17.csv"));
            while ((line = csvReader.readLine()) != null) {
                str = line.split(",");
                Sample sample=new Sample();
                sample.set(str);
                ratings = sample.getRatings(positions);
                max = findMax(ratings);
                sample.maxout(max, sample.listAttributes(ratings, max, positions));
                samples.add(sample);
            }
        }
        catch(IOException e){
            System.out.println("File could not be read");
        }
        System.out.println("Samples Loaded: "+samples.size());
        distances = new double [samples.size()][num_centroids];
    }
    public static Centroid[] generateRandomCentroids(int n){
        Centroid gen[] = new Centroid[n];
        Coordinates max = new Coordinates();
        Coordinates min = new Coordinates();
        Arrays.fill(max.attributes,0);
        Arrays.fill(min.attributes,99);
        for(Sample i:samples){
            max.getMax(i.maxed_out_coordinate);
            min.getMin(i.maxed_out_coordinate);
        }
        //min.dispCoordinates();
        //max.dispCoordinates();
        for(int i=0;i<n;i++){
            Coordinates c = new Coordinates();
            c.randomCoordinates(min,max);
            Centroid centroid = new Centroid();
            centroid.centroidCoordinates = c;
            //System.out.println("Inital Centroids");
            //centroid.dispCentroid();
            gen[i] = centroid;
        }
        return gen;
    }
    public static void findNearestCentroid(){
        double min = 10000000.0,d;
        int index;
        for(Sample smp:samples){
            min = 1000000.0;
            index = -1;
            for(int i=0;i<num_centroids;i++){
                d = smp.maxed_out_coordinate.getEuclideanDistance(centroids[i].centroidCoordinates);
                if(d<min){
                    min = d;
                    index = i;
                }
            }
            centroids[index].centroidSamples.add(smp);
            smp.centroid = index;
        }
    }
    public static void showCentroids(){
        //ArrayList <Double> ratings = new ArrayList<>();
        for(int i=0;i<num_centroids;i++){
            if(centroids[i].centroidSamples.size()==0)
                continue;
            System.out.println("=====================CENTROID "+i+"==================================");
            System.out.println("Samples Assigned: "+centroids[i].centroidSamples.size());
            System.out.println("Best Position: "+ getBestPosition(centroids[i].centroidCoordinates));
            centroids[i].dispRefined();
            centroids[i].dispCentroid();
        }
    }
    public static void setDistances(){
        for(int j=0;j<samples.size();j++){
            for(int i=0;i<num_centroids;i++){
                distances[j][i] = samples.get(j).maxed_out_coordinate.getEuclideanDistance(centroids[i].centroidCoordinates);
            }
        }
    }
    public static void adjustCentroids(){
        Centroid temp[] = new Centroid[1];
        for(int i=0;i<num_centroids;i++){
            if(centroids[i].centroidSamples.size()==0){
                temp = generateRandomCentroids(1);
                centroids[i] = temp[0];
                continue;
            }
            else if(centroids[i].centroidSamples.size()==1)
                centroids[i].centroidCoordinates = centroids[i].centroidSamples.get(0).maxed_out_coordinate;
            else{
                Coordinates c = new Coordinates();
                Arrays.fill(c.attributes,0);
                for(Sample smp:centroids[i].centroidSamples){
                    for(int k=0;k<num_attributes;k++){
                        c.attributes[k]+=smp.maxed_out_coordinate.attributes[k]/centroids[i].centroidSamples.size();
                    }
                }
                centroids[i].centroidCoordinates = c;
            }
        }
    }
    public static void main(String args[]){
        List <Integer> before = new ArrayList<>();
        List <Integer> after = new ArrayList<>();
        boolean hasChanged = true;
        int flag = 0;
        int iterations = 0;
        loadPositions();
        loadSamples();
        centroids = generateRandomCentroids(num_centroids);
        findNearestCentroid();
        while(hasChanged){
            flag = 0;
            // set before adjustment
            for(Sample smp:samples)
                before.add(smp.centroid);
            
            adjustCentroids();
            
            for(int i=0;i<num_centroids;i++){
                centroids[i].centroidSamples.clear();
            }
            findNearestCentroid();
            for(Sample smp:samples)
                after.add(smp.centroid);
            //System.out.println("Iteration "+iterations);
            //System.out.println("Before: "+before);
            //System.out.println("After:  "+after);
            iterations++;

            for(int i=0;i<after.size();i++){
                if(after.get(i)!=before.get(i))
                    flag++;
            }
            if(flag==0)
                hasChanged = false;
            else{
                before.clear();
                after.clear();
            }
        }
        //System.out.println("After "+iterations+" iterations\nFinal Result:\n");
        System.out.println("Centroids: "+ num_centroids);
        System.out.println("Iterations: "+iterations);
        showCentroids();
        setDistances();
        /*System.out.println("Distance of each data point from centroid");
        for(int i=0;i<samples.size();i++){
            System.out.print(samples.get(i).name+" ");
            for(int j=0;j<num_centroids;j++)
                System.out.printf("%.2f ",distances[i][j]);
            System.out.println();
        }*/
    }
    /*public static void main(String args[]){
        Main main = new Main();
        main.populatePositions();
        main.loadPositions();
        main.showPositions();
    }*/
    public static void populatePositions(){
        String str[] = getPositions();
        for(String s: str){
            Position p = new Position(s);
            positions.add(p);
        }
    }
    public static void addAttributetoPosition(String attrib, String fact, String pos){
        for(Position p: positions){
            if(p.name.equalsIgnoreCase(pos)){
                p.addAttribute(attrib, Double.parseDouble(fact));
            }
        }
    }
    public static void loadPositions(){
        populatePositions();
        File file = new File("positions.csv");
        String line;
        String parts[];
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((line=br.readLine()) != null){
                parts = line.split(",");
                addAttributetoPosition(parts[0],parts[1],parts[2]);
            }
            br.close();
        }
        catch(FileNotFoundException e){
            System.out.println("positions.csv not found");
        }
        catch(Exception e){
            System.out.println("Error occurred");
        }
    }
    void showPositions(){
        for(Position p: positions){
            p.display();
        }
    }
    static String[] getPositions(){
        String str[] = {"GK","SW","CB","FB","WB","DM","CM","WM","AM","WF","SS","ST"};
        return str;
    }
    static String getPositionName(int pos){
        return getPositions()[pos];
    }
    int getPosition(String pos){
        String positions[] = getPositions();
        for (int i=0;i<positions.length;i++){
            if(positions[i].equalsIgnoreCase(pos))
                return i;
        }
        return -1;
    }
    static double findMax(ArrayList<Double> ratings){
        double max = 0.0;
        for(double d: ratings){
            if(max <= d){
                max = d;
            }
        }
        return max;
    }
    static void dispPositionalRatings(ArrayList<Double> ratings){
        for(int i=0;i<ratings.size();i++){
            System.out.println(getPositionName(i)+" :: "+ ratings.get(i));
        }
    }
    static String getBestPosition(Coordinates coordinate){
        Sample sample = new Sample();
        sample.name = "New Sample";
        sample.coordinate = coordinate;
        ArrayList<Double> ratings = sample.getRatings(positions);
        dispPositionalRatings(ratings);
        double max = 0.0;
        int index = 0;
        for(int i=0;i<ratings.size();i++){
            if(ratings.get(i)>max){
                max = ratings.get(i);
                index = i;
            }
        }
        return getPositionName(index);
    }
}