/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

/**
 *
 * @author ekozi
 */
/**
 * Created by scruf on 10/24/15.
 */
public class City {
    //@param cost The current cost of travelling to this city
    private int cost;
    //@param name The name of the city
    private java.lang.String name;
    //@param predecessor
    private java.lang.String predecessor;
    City(java.lang.String name){
        this.name=name;
    }
    /*
    * method geName()
    *
    * @return the name of this city
     * */
    public java.lang.String getName(){return this.name;}
    /*
    * method setName
    * Set the name of this city
    * */
    public void setName(java.lang.String name){this.name=name;}
    /*
    * method Get the predecessor associated with this city
    * */
    public java.lang.String getPredecessor(){return this.predecessor;}
    /*
    * method setPredecesoor Set the predecessor to the given city
    * */
    public void setPredecessor(java.lang.String predecessor){
        this.predecessor=predecessor;
    }
    /*
    * method getCost will get the cost associated with this city
    * */
    public int getCost(){return this.cost;}
    /*
    * method setCost will set the cost for a given city
    * */
    public void setCost(int cost){this.cost=cost;}
    /*
    * method compareTo will compare two cities to see how they are ordered
    * */
    public int compareTo(City other){
        if(this.cost==other.cost)
            return this.name.compareTo(other.name);
        if(this.cost>other.cost)
            return 1;
        if(this.cost<other.cost)
            return -1;
        return 0;
    }
    /*
    * method equals will compare this city to the other object.
    * They are equal of the both costs and name are equal
    * */
        @Override
    public boolean equals(java.lang.Object other){
        if(other==this)
            return true;
            //Typecast other object to a City so we can access it
            //member
            City city = (City)other;
            return city.cost==this.cost && city.getName().equals(getName());
    }
}
