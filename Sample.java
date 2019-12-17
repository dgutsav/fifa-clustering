class Sample{
    String name;
    Coordinates coordinate;
    int centroid;
    Sample(){
        name = null;
        coordinate = new Coordinates();
    }
    public void set(String s[]){
        this.name = s[0]+" "+s[1];
        if(name.charAt(0)>=97 && name.charAt(0)<=122)
            name = ((char)(name.charAt(0)-32))+name.substring(1);
        for(int i=2;i<35;i++)
            this.coordinate.attributes[i-2] = Integer.parseInt(s[i]);
    }
    void dispSample(){
        System.out.print(this.name+" ");
        this.coordinate.dispCoordinates();
    }
}