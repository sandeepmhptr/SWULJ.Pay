package com.example.swuljpay;

import javax.xml.transform.Source;

public class PayData {
    String url;
    String busNumber;
    String fare;
    String Source;
    String Destination;
    String Id;
    PayData(String urlArg,
            String fareArg,
            String busNumberArg,
            String SourceArg,
            String DestinationArg,
            String IdArg)
    {
        url = urlArg;
        fare = fareArg;
        busNumber = busNumberArg;
        Source = SourceArg;
        Destination = DestinationArg;
        Id = IdArg;
    }
}
