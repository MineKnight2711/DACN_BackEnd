package com.example.dacn.modules.map;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.map.model.LocationResponse;
import com.example.dacn.modules.map.model.PredictLocationResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class MapService {
     private final String apiListLocation="https://rsapi.goong.io/geocode?address=";
    private final String apiAutoCompleteLocation="https://rsapi.goong.io/Place/AutoComplete";
    final String apiKey="OZBPCTmWt2BYBrbR26LivZEyB9zcPp8VP0ZtoCrb";
    //Sửa chuỗi nhập vào khi có khoảng trắng
    public String fixString(String string)  {return string.replace(" ", "%20");}
    private HttpResponse<String>  sendGetRequest(String apiUrl) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public ResponseModel getListLocation(String location)
    {
        String formattedLocation = fixString(location);
        String apiUrl = apiListLocation + formattedLocation + "&api_key=" + apiKey;
        try {
            HttpResponse<String> response = sendGetRequest(apiUrl);
            if(response.statusCode()==200){
                LocationResponse locationResponse=new Gson().fromJson(response.body(),LocationResponse.class);
                return new ResponseModel("Success",locationResponse);
            }
            return new ResponseModel("Fail",null);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseModel("FailToSendRequest",null);
        }
    }
    public ResponseModel predictLocation(String predictString)
    {
        String formattedPredictLocation = fixString(predictString);
        String apiUrl = apiAutoCompleteLocation + "?api_key=" + apiKey+"&input="+formattedPredictLocation;
        try {
            HttpResponse<String> response = sendGetRequest(apiUrl);
            if(response.statusCode()==200){
                PredictLocationResponse predictLocationResponse=new Gson().fromJson(response.body(),PredictLocationResponse.class);
                if(predictLocationResponse.getPredictions()==null){
                    return new ResponseModel("NoLocation",predictLocationResponse);
                }
                return new ResponseModel("Success",predictLocationResponse);
            }
            return new ResponseModel("Fail",null);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseModel("FailToSendRequest",null);
        }
    }
}
