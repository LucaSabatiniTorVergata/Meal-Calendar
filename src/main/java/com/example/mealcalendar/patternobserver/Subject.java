package com.example.mealcalendar.patternobserver;


public interface Subject {

    void registerObserver(ReportObserver o);
    void notifyObservers();
}
