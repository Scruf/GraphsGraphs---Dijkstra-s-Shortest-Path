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
public class Road {
    //@param length The length of this road segment
    private int length;
    //@param road THe name of the road segment
    private java.lang.String road;
    /*
    * method Road(int length, java.lang.String road)
    * @param length will set the length of the road
    * @param road will set the name of the road
    * */
    Road(int length,java.lang.String road){
        this.length=length;
        this.road=road;
    }
    /*
    * method getLength()
    * Get the road length
    * @return the road length
    * */
    public int getLength(){return this.length;}
    /*
    * method getRoad()
    * Get the road name
    * @return the road name
    * */
    public java.lang.String getRoad(){return this.road;}
    /*
    * method setLength(int length)
    * @param length will be used on setting the length
    * Set the road length
    * */
    public void setLength(int length){this.length=length;};
    /*
    * method setRoad(java.lang.String road)
    * @param road will be used on setting the road name
    * will set the road name
    * */
    public void setRoad(java.lang.String road){this.road=road;}
}
