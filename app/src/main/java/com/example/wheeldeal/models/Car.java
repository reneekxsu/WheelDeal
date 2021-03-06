package com.example.wheeldeal.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Car")
public class Car extends ParseObject {
    // keys: description, author, image, rate

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_OWNER = "author";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATE = "rate";
    public static final String KEY_MODEL = "model";
    public static final String KEY_MAKE = "make";
    public static final String KEY_YEAR = "year";
    public static final String KEY_PASSENGERS = "passengers";
    public static final String KEY_SIZE = "size";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ADDRESSGEOPOINT = "addressGeoPoint";
    public static final String KEY_EVENTCOUNT = "eventCount";
    public static final String KEY_SCORE = "score";

    // description getter and setter
    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    // car owner getter and setter
    public ParseUser getOwner(){
        return getParseUser(KEY_OWNER);
    }

    public void setOwner(ParseUser owner){
        put(KEY_OWNER,owner);
    }

    // image getter and setter
    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE,image);
    }

    // rate getter and setter
    public Number getRate(){
        return getNumber(KEY_RATE);
    }

    public void setRate(Number rate){
        put(KEY_RATE,rate);
    }

    // model getter and setter
    public String getModel() {
        return getString(KEY_MODEL);
    }

    public void setModel(String model) {
        put(KEY_MODEL, model);
    }

    // make getter and setter
    public String getMake() {
        return getString(KEY_MAKE);
    }

    public void setMake(String make) {
        put(KEY_MAKE, make);
    }

    // year getter and setter
    public String getYear() {
        return getString(KEY_YEAR);
    }

    public void setYear(String year) {
        put(KEY_YEAR, year);
    }

    // passengers getter and setter
    public String getPassengers() {
        return getString(KEY_PASSENGERS);
    }

    public void setPassengers(String passengers) {
        put(KEY_PASSENGERS, passengers);
    }

    // size getter and setter
    public String getSizeType() {
        return getString(KEY_SIZE);
    }

    public void setSizeType(String size) {
        put(KEY_SIZE, size);
    }

    // address getter and setter
    public String getAddress() {
        return getString(KEY_ADDRESS);
    }

    public void setAddress(String address) {
        put(KEY_ADDRESS, address);
    }

    // address getter and setter
    public ParseGeoPoint getAddressGeoPoint() {
        return getParseGeoPoint(KEY_ADDRESSGEOPOINT);
    }

    public void setAddressGeoPoint(ParseGeoPoint address) {
        put(KEY_ADDRESSGEOPOINT, address);
    }

    // eventCount getter and setter
    public Number getEventCount(){
        return getNumber(KEY_EVENTCOUNT);
    }

    public void setEventCount(Number events){
        put(KEY_EVENTCOUNT,events);
    }

    // score getter and setter
    public Number getScore(){
        return getNumber(KEY_SCORE);
    }

    public void setScore(Number score){
        put(KEY_SCORE, score);
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Car)){
            return false;
        }
        Car other = (Car) o;
        return this.getObjectId().equals(other.getObjectId());
    }

    @Override
    public int hashCode(){
        return getObjectId().hashCode();
    }
}
