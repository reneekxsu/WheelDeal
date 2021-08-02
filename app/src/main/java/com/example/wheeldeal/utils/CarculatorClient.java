package com.example.wheeldeal.utils;

import android.util.Log;

import com.example.wheeldeal.models.Car;
import com.example.wheeldeal.models.CarScore;

import java.util.ArrayList;
import java.util.List;

public class CarculatorClient {
    public static final String TAG = "CarculatorClient";
    ArrayList<Car> allCars;
    ArrayList<String> UltLuxury;
    ArrayList<String> Luxury;
    ArrayList<CarScore> scores;
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
        scores = new ArrayList<>();
        x = new ArrayList<>();
        y = new ArrayList<>();
        scoreX = new ArrayList<>();
        daysY = new ArrayList<>();
        this.make = make;
        this.year = year;
        this.passengers = passengers;
    }
    public int calculatePricing() {
        int price;
        double score, prediction;
        int flagLux = 0;
        for (CarScore cs : scores){
            x.add(cs.getScore());
            y.add(Double.parseDouble(cs.getCar().getRate().toString()));
        }
        LinearRegression lr = new LinearRegression(x,y);
        Log.i(TAG, "intercept: " + lr.intercept());
        Log.i(TAG, "slope: " + lr.slope());
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
        thisCar = new CarScore(Integer.parseInt(year),
                Integer.parseInt(passengers), flagLux);
        score = thisCar.getScore();
        prediction = lr.intercept() + lr.slope() * score;
        price = scoreToPrice((0.6 * score + 0.4 * prediction));
        return price;
    }

    public int calculateDays(){
        for (CarScore cs : scores){
            scoreX.add(cs.getScore());
            daysY.add(Double.parseDouble(cs.getCar().getEventCount().toString()));
        }
        LinearRegression lr = new LinearRegression(scoreX,daysY);
        Log.i(TAG, "intercept: " + lr.intercept());
        Log.i(TAG, "slope: " + lr.slope());
        double prediction = lr.intercept() + lr.slope() * thisCar.getScore();
        Log.i(TAG, "predicted days: " + prediction);
        if (prediction <= 0){
            prediction = (Math.abs(prediction) + 1) * 0.7;
        }
        return (int)Math.ceil(prediction * 5);
    }

    public void updateCategories(){
        for (Car car : allCars){
            Log.i(TAG, "car: " + car.getModel());
            int flagLux = 0;
            for (String s : UltLuxury){
                if (car.getMake().equals(s)){
                    flagLux = 2;
                    break;
                }
            }
            if (flagLux == 0){
                for (String s : Luxury){
                    if (car.getMake().equals(s)){
                        flagLux = 1;
                        break;
                    }
                }
            }
            CarScore thisCar = new CarScore(Integer.parseInt(car.getYear()),
                    Integer.parseInt(car.getPassengers()), flagLux, car);

            scores.add(thisCar);
        }
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
