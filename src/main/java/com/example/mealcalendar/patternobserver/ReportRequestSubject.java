package com.example.mealcalendar.patternobserver;

public interface ReportRequestSubject {

    void registerObserver(ReportRequestObserver observer);
    void removeObserver(ReportRequestObserver observer);
    void notifyObservers();
}
