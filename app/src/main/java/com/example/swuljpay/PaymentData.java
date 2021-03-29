package com.example.swuljpay;

public class PaymentData {
    String Source;
    String Destination;
    String url;
    String busNumber;
    String ID;
    PaymentData(String SourceArg, String DestinationArg, String busNumberArg, String urlArg, String IDArg)
    {
        Source = SourceArg;
        Destination = DestinationArg;
        busNumber = busNumberArg;
        url = urlArg;
        ID = IDArg;
    }
}
