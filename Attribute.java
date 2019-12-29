class Attribute{
    String name;
    double rating;
    public Attribute(String x, double d){
        this.name = x;
        this.rating = d;
    }
    public void display(){
        System.out.println(name+" : "+rating);
    }
}