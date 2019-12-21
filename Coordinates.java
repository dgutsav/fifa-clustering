class Coordinates{
    int num_attributes = 33;
    double attributes[] = new double [num_attributes];
    String[] getAttributeNames(){
        String str[]={"acceleration","aggression","agility","balance","ballcontrol","crossing","curve","dribbling","finishing","freekickaccuracy","gkdiving","gkhandling","gkkicking","gkpositioning","gkreflexes","headingaccuracy","interceptions","jumping","longpassing","longshots","marking","penalties","positioning","reactions","shortpassing","shotpower","slidingtackle","sprintspeed","standingtackle","stamina","strength","vision","volleys"};
        return str;
    }
    void dispCoordinates(){
        String at[] = getAttributeNames();
        for(int i=0;i<num_attributes;i++){
            System.out.print(at[i]+"=");
            System.out.printf("%.2f ",attributes[i]);
        }    
        System.out.println();
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
        for(int i=0;i<num_attributes;i++){
            if(x.equalsIgnoreCase(attributes[i]))
                return attributes[i];
        }
    }
}