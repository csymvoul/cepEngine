package com.matilda5g.cep.streamingData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.matilda5g.cep.fusionEngine.HttpResponseListener;
import org.json.*;
import javax.xml.ws.http.HTTPException;
import java.util.Timer;
import java.util.TimerTask;

public class StreamingData {

    private String url;
    private volatile JSONObject httpResponse;

    public StreamingData(String url){
        this.url = url;
    }

    public void httpRequest() throws Exception{
        try {
            URL obj = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional, since the default method is GET
            con.setRequestMethod("GET");

            // the response code of the httpRequest (e.g. 400, 200, etc.)
//            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            setHttpResponse(new JSONObject(response.toString()));

        } catch (ConnectException e){
            System.out.println("Connection timeout");

        } catch (HTTPException e){
            System.out.println("HTTPException");
        }
//        return null;
    }

    public void setHttpResponse(JSONObject httpResponse, HttpResponseListener) {
        this.httpResponse = httpResponse;
        System.out.println("this.httpResponse" + this.httpResponse.toString());
    }

    public JSONObject getHttpResponse() {
        System.out.println(this.httpResponse.toString());
        return this.httpResponse;
    }

    public void StreamHttpRequest() {
//        final long timeInterval = 1000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while(true){
                    try {
                        httpRequest();
//                        setHttpResponse(httpResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    try {
//                        Thread.sleep(timeInterval);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }, 0, 1000);

//        Thread thread = new Thread(runnable);
//        thread.start();
    }
}
