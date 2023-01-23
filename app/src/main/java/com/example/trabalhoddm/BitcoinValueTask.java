package com.example.trabalhoddm;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BitcoinValueTask extends AsyncTask<Void, Void, Double> {
    private TextView resultTextView;

    private String CriptoMoeda;
    private String moedaGovernamental;
    private double quantidadeConverter;
;
    public BitcoinValueTask(TextView resultTextView,String coin,double quantidadeConverter, String moedaGovernamental) {
        this.resultTextView = resultTextView;
        this.CriptoMoeda=coin;
        this.quantidadeConverter= quantidadeConverter;
        this.moedaGovernamental = moedaGovernamental;
    }

    @Override
    protected Double doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.coingecko.com/api/v3/simple/price?ids="+CriptoMoeda+"&vs_currencies="+moedaGovernamental)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getJSONObject(CriptoMoeda).getDouble(moedaGovernamental.toLowerCase());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Double result) {
        if (result != null) {
            double resultado = result ;
            resultTextView.setText(""+resultado);
        } else {
            resultTextView.setText("Error");
        }



    }
}