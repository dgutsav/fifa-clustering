import java.util.*;
import java.io.*;
class Main{
    static List<Sample> samples = new ArrayList<>();
    static int num_centroids = 10;
    static Centroid[] centroids = new Centroid[num_centroids];
    static int num_attributes = 33;
    static double distances[][];
    public static void loadSamples(){
        File file = new File("fut17text1.txt");
        Scanner sc;
        String str[];
        try{
            //sc = new Scanner(file);
            //System.out.println("File Found");
            String line;
            //System.out.println(sc.hasNextLine());
            BufferedReader csvReader = new BufferedReader(new FileReader("fut17text1.txt"));
            while ((line = csvReader.readLine()) != null) {
                //String[] data = line.split(",");
                // do something with the data
                //System.out.println(sc.hasNextLine());
                //line = sc.nextLine();
                //System.out.println(line.length());
                str = line.split(",");
                Sample s=new Sample();
                s.set(str);
                samples.add(s);
                //s.dispSample();
            }
        }
        catch(IOException e){
            System.out.println("File could not be read");
        }
        /*catch(FileNotFoundException e){
            System.out.println("File NOT Found");
        }*/
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
            max.getMax(i.coordinate);
            min.getMin(i.coordinate);
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
                d = smp.coordinate.getEuclideanDistance(centroids[i].centroidCoordinates);
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
        for(int i=0;i<num_centroids;i++){
            System.out.println("=====================CENTROID "+i+"==================================");
            centroids[i].dispCentroid();
        }
    }
    public static void setDistances(){
        for(int j=0;j<samples.size();j++){
            for(int i=0;i<num_centroids;i++){
                distances[j][i] = samples.get(j).coordinate.getEuclideanDistance(centroids[i].centroidCoordinates);
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
                centroids[i].centroidCoordinates = centroids[i].centroidSamples.get(0).coordinate;
            else{
                Coordinates c = new Coordinates();
                Arrays.fill(c.attributes,0);
                for(Sample smp:centroids[i].centroidSamples){
                    for(int k=0;k<num_attributes;k++){
                        c.attributes[k]+=smp.coordinate.attributes[k]/centroids[i].centroidSamples.size();
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
            System.out.println("Iteration "+iterations);
            System.out.println("Before: "+before);
            System.out.println("After:  "+after);
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
        System.out.println("After "+iterations+" iterations\nFinal Result:\n");
        showCentroids();
        setDistances();
        System.out.println("Distance of each data point from centroid");
        for(int i=0;i<samples.size();i++){
            System.out.print(samples.get(i).name+" ");
            for(int j=0;j<num_centroids;j++)
                System.out.printf("%.2f ",distances[i][j]);
            System.out.println();
        }
    }
}