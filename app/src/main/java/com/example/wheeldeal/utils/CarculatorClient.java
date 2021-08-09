package com.example.wheeldeal.utils;

import android.util.Log;

import com.example.wheeldeal.models.Car;
import com.example.wheeldeal.models.CarScore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarculatorClient {
    public static final String TAG = "CarculatorClient";
    ArrayList<Car> allCars;
    ArrayList<String> UltLuxury;
    ArrayList<String> Luxury;
    ArrayList<Double> x;
    ArrayList<Double> y;
    ArrayList<Double> scoreX;
    ArrayList<Double> daysY;
    String make, year, passengers;
    CarScore thisCar;
    public CarculatorClient(String make, String year, String passengers){
        allCars = new ArrayList<>();
        UltLuxury = new ArrayList<>();
        Luxury = new ArrayList<>();
        x = new ArrayList<>();
        y = new ArrayList<>();
        scoreX = new ArrayList<>();
        daysY = new ArrayList<>();
        this.make = make;
        this.year = year;
        this.passengers = passengers;
        initLuxury();
    }
    public int calculatePricing() {
        int price;
        double score, prediction;
        for (Car car : allCars){
            Log.i(TAG, "score from car: " + car.getScore());
            Log.i(TAG, "rate from car: " + car.getRate());
            x.add((double)car.getScore());
            y.add(Double.parseDouble(car.getRate().toString()));
        }
        LinearRegression lr = new LinearRegression(x,y);
        Log.i(TAG, "intercept: " + lr.intercept());
        Log.i(TAG, "slope: " + lr.slope());
        int flagLux = getLux(make);
        thisCar = new CarScore(Integer.parseInt(year),
                Integer.parseInt(passengers), flagLux);
        score = thisCar.getScore();
        Log.i(TAG, "this car's score: " + score);
        prediction = lr.intercept() + lr.slope() * score;
        price = scoreToPrice((0.6 * score + 0.4 * prediction));
        return price;
    }

    public int calculateDays(){
        DateClient dateClient = new DateClient();
        Date currentDate = Calendar.getInstance().getTime();
        for (Car car : allCars){
            scoreX.add((double)car.getScore());
            int daysElapsed = dateClient.getDuration(car.getCreatedAt(), currentDate);
            double monthsElapsed = Math.ceil(daysElapsed / 30 + 1);
            daysY.add(Double.parseDouble(car.getEventCount().toString()) / monthsElapsed);
        }
        LinearRegression lr = new LinearRegression(scoreX,daysY);
        double prediction = lr.intercept() + lr.slope() * thisCar.getScore();
        if (prediction <= 0){
            prediction = (Math.abs(prediction) + 1) * 0.7;
        }
        if (prediction * 2 > 30){
            return (int)Math.ceil(prediction);
        }
        return (int)Math.ceil(prediction);
    }

    public int getLux(String make){
        int flagLux = 0;
        for (String s : UltLuxury){
            if (make.equals(s)){
                flagLux = 2;
                break;
            }
        }
        if (flagLux == 0){
            for (String s : Luxury){
                if (make.equals(s)){
                    flagLux = 1;
                    break;
                }
            }
        }
        return flagLux;
    }

    public void initLuxury(){
        UltLuxury.addAll(List.of("Bentley","Ferrari", "Lamborghini", "Maserati", "Mclaren", "Rolls Royce", "Aston Martin", "Corvette"));
        Luxury.addAll(List.of("BMW", "Audi", "Land Rover", "Range Rover", "Mercedes-Benz", "Tesla"));
    }

    private int scoreToPrice(double score){
        return (int) score;
    }

    public void updateAllCars(List<Car> cars){
        allCars.addAll(cars);
    }
    public void removeFromAllCars(Car car){
        allCars.remove(car);
    }

    public void updateMake(String make){
        this.make = make;
    }
    public void updateYear(String year){
        this.year = year;
    }
    public void updatePassengers(String passengers){
        this.passengers = passengers;
    }

}
