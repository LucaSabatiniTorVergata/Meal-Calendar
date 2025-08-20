package com.example.mealcalendar.patternobserver.observerasknutri;

import java.util.ArrayList;
import java.util.List;

public class ReportRequestNotifier implements ReportRequestSubject {

    private static ReportRequestNotifier instance=null;

    private final List<ReportRequestObserver> observers = new ArrayList<>();

    private ReportRequestNotifier() {}

    public static synchronized ReportRequestNotifier getInstance() {
        if (instance == null) {
            instance = new ReportRequestNotifier();
        }
        return instance;
    }

    @Override
    public void registerObserver(ReportRequestObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }


    @Override
    public void notifyObservers() {
        for (ReportRequestObserver observer : new ArrayList<>(observers)) {
            observer.onNewReportRequest();
        }
    }
}
