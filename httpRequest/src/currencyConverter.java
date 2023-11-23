
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class App{
    
public static void main(String[] args)
{
    
    
    String from, to;
    double amount;
    try (Scanner sc = new Scanner(System.in)) {
        System.out.println("Currency converting from?");
        from = sc.nextLine().toUpperCase();
        System.out.println("Currency converting to?");
        to = sc.nextLine().toUpperCase();
        System.out.println("Enter amount to convert: ");
        amount = sc.nextDouble();
    }
    sendHttpRequest(from,to,amount);
}


    public static void  sendHttpRequest(String from, String to, double amount)
    {

    
    try{
        
        URL url = new URL("https://v6.exchangerate-api.com/v6/071bd2cd518327fe6e371516/latest/" + from);
         
      HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int status = connection.getResponseCode();
        System.out.println("Connection : "+status);
        String line;
        StringBuffer responseContent = new StringBuffer();
        if(status==200)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line=reader.readLine())!=null)
            {
                responseContent.append(line);

            }
            reader.close();
           // System.out.println(responseContent);
            System.out.println("");
        
         JSONObject obj = new JSONObject(responseContent.toString());
            //obj.getJSONObject("rates").getDouble(from);
            double toExchangeRate = obj.getJSONObject("conversion_rates").getDouble(to);
          // System.out.println(obj.getJSONObject("conversion_rates"));
           System.out.println("");
           System.out.println("Exchange rate = "+toExchangeRate);
           System.out.println("");
            System.out.println(amount + from + " = " + amount*toExchangeRate + to);
        }

       else{
            System.out.println("GET request failed!");
        }
        
    }catch(MalformedURLException e){
        e.printStackTrace();
    }catch(IOException e){
        e.printStackTrace();
    }
    
}
}


