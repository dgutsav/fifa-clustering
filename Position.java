import java.util.ArrayList;

class Position{
    String name;
    Position(String name){
        this.name = name;
    }
    ArrayList <Attribute> attributes = new ArrayList <Attribute>();
    public void addAttribute(String attrib, double factor){
        Attribute attribute = new Attribute(attrib,factor);
        if(!attributes.contains(attribute))
            attributes.add(attribute);
    }
    public ArrayList<String> getAttributeNames(){
        ArrayList <String> attrib = new ArrayList<String>();
        for(Attribute attribute : this.attributes){
            attrib.add(attribute.name);
        }
        if(attrib!=null)
            return attrib;
        else
            return null;
    }
    public ArrayList<Attribute> getAttributes(){
        return this.attributes;
    }
    public void display(){
        System.out.println(name);
        for(Attribute attribute : this.attributes){
            attribute.display();
        }
    }
}