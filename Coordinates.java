class Coordinates{
    static int num_attributes = 33;
    double attributes[] = new double [num_attributes];
    static String[] getAttributeNames(){
        String str[]={"acceleration","aggression","agility","balance","ball control","crossing","curve","dribbling","finishing","free kick accuracy","gk diving","gk handling","gk kicking","gk positioning","gk reflex","heading","interceptions","jumping","long pass","long shots","marking","penalties","positioning","reactions","short pass","shot power","sliding tackle","sprint speed","standing tackle","stamina","strength","vision","volleys"};
        return str;
    }
    public void resetCoordinate(){
        for(int i=0;i<num_attributes;i++){
            this.attributes[i] = 0;
        }
    }
    public void dispCoordinates(){
        String at[] = getAttributeNames();
        for(int i=0;i<num_attributes;i++){
            System.out.print(at[i]+"=");
            System.out.printf("%.2f ",attributes[i]);
        }    
        System.out.println();
    }
    public void addCoordinates(Coordinates c){
        for(int i=0;i<num_attributes;i++){
            this.attributes[i] += c.attributes[i];
        }
    }
    public void reduceCoordinate(int n){
        for(int i=0;i<num_attributes;i++){
            this.attributes[i] /= n;
        }
    }
    void getMax(Coordinates a){
        for(int i=0;i<num_attributes;i++)
            this.attributes[i] = (int)(Math.max(this.attributes[i],a.attributes[i]));
    }
    void getMin(Coordinates a){
        for(int i=0;i<num_attributes;i++)
            this.attributes[i] = (int)(Math.min(this.attributes[i],a.attributes[i]));
    }
    void randomCoordinates(Coordinates a, Coordinates b){
        for(int i=0;i<num_attributes;i++){
            this.attributes[i] = ((int)(Math.random()*(b.attributes[i]-a.attributes[i])))+a.attributes[i];
        }
    }
    double getEuclideanDistance(Coordinates a){
        double dist = 0.0;
        for(int i=0;i<num_attributes;i++){
            dist+= Math.pow((this.attributes[i]-a.attributes[i]),2);
        }
        dist = Math.sqrt(dist);
        return dist;
    }
    double getAttribute(String x){
        String attr_names[] = getAttributeNames();
        for(int i=0;i<num_attributes;i++){
            if(x.equalsIgnoreCase(attr_names[i]))
                return this.attributes[i];
        }
        return 0.0;
    }
    void setAttribute(String x, double d){
        if(d>99.99)
            d = 99.99;
        String attr_names[] = getAttributeNames();
        for(int i=0;i<num_attributes;i++){
            if(x.equalsIgnoreCase(attr_names[i]))
                this.attributes[i] = d;
        }
    }
    void copy(Coordinates c){
        for(int i=0;i<num_attributes;i++){
            c.attributes[i] = this.attributes[i];
        }
    }
}