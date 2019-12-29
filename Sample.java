import java.util.ArrayList;
class Sample{
    String name;
    Coordinates coordinate;
    Coordinates maxed_out_coordinate;
    int centroid;
    Sample(){
        name = null;
        coordinate = new Coordinates();
        maxed_out_coordinate = new Coordinates();
    }
    public void set(String s[]){
        this.name = s[0] + " " + s[1];
        if(name.charAt(0) >= 97 && name.charAt(0) <= 122)
            name = ((char)(name.charAt(0)-32))+name.substring(1);
        for(int i=2;i<35;i++)
            this.coordinate.attributes[i-2] = Integer.parseInt(s[i]);
    }
    void dispSample(){
        System.out.print(this.name+" ");
        this.coordinate.dispCoordinates();
    }
    double attrib(String attribute){
        return this.coordinate.getAttribute(attribute);
    }
    double getRatingForPosition(Position position){
        double d = 0.0;
        for(Attribute attribute: position.attributes){
            d += attribute.rating * attrib(attribute.name);
        }
        return d;
    }
    public ArrayList<Double> getRatings(ArrayList<Position> positions){
        ArrayList<Double> ratings = new ArrayList<>();
        for(Position position: positions){
            ratings.add(getRatingForPosition(position));
        }
        return ratings;
    }
    public ArrayList<String> listAttributes(ArrayList<Double> ratings, double max,  ArrayList<Position> positions){
        max -= 2.5;
        ArrayList<String> attributes = new ArrayList<>();
        for(int i=0;i<ratings.size();i++){
            if(ratings.get(i)>max){
                for(Attribute attribute: positions.get(i).attributes){
                    if(!attributes.contains(attribute.name)){
                        attributes.add(attribute.name);
                    }
                }
            }
        }
        return attributes;
    }
    public void maxout(double max, ArrayList<String> attributes){
        double mul_factor = 100.00 / max;
        coordinate.copy(maxed_out_coordinate);
        for(String str: attributes){
            maxed_out_coordinate.setAttribute(str, mul_factor * maxed_out_coordinate.getAttribute(str));
        }
    }
}