package com.festival.festival001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ArrayList<String> lista = new ArrayList<String>();
	private String currentApresentation ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LinearLayout llHome = (LinearLayout)findViewById(R.id.llHome);
        LinearLayout llLista= (LinearLayout)findViewById(R.id.llLista);
        LinearLayout llSettings = (LinearLayout)findViewById(R.id.llSettings);
        llHome.setVisibility(View.VISIBLE);
        llLista.setVisibility(View.INVISIBLE);
        llSettings.setVisibility(View.INVISIBLE);
        
        ListView listaApres = (ListView)findViewById(R.id.listaApresent);
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista);
        listaApres.setAdapter(adapter);
        
        ImageButton btVotar = (ImageButton)findViewById(R.id.btVotar);
        btVotar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout llHome = (LinearLayout)findViewById(R.id.llHome);
		        LinearLayout llLista= (LinearLayout)findViewById(R.id.llLista);
		        LinearLayout llSettings = (LinearLayout)findViewById(R.id.llSettings);
		        TextView tvCurrent = (TextView)findViewById(R.id.tvCurrent);
		        tvCurrent.setText(currentApresentation);
		        llHome.setVisibility(View.VISIBLE);
		        llHome.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		        llLista.setVisibility(View.INVISIBLE);
		        llLista.setLayoutParams(new LinearLayout.LayoutParams(0,0));
		        llSettings.setVisibility(View.INVISIBLE);
		        llSettings.setLayoutParams(new LinearLayout.LayoutParams(0,0));
				
			}
		});
        
		
        ImageButton btVotos = (ImageButton)findViewById(R.id.btVotos);
        btVotos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout llHome = (LinearLayout)findViewById(R.id.llHome);
		        LinearLayout llLista= (LinearLayout)findViewById(R.id.llLista);
		        LinearLayout llSettings = (LinearLayout)findViewById(R.id.llSettings);
		        ListView lvApres = (ListView)findViewById(R.id.listaApresent);
		        
		        //new LongOperation().execute("http://mymovieapi.com/?title=godfather&type=json&plot=simple&episode=0&limit=1&yg=0&mt=M&lag=en-US&offset=&aka=simple&release=simple&business=0&tech=0");
		        
		        DownloadWebPageTask task = new DownloadWebPageTask();
		        //task.execute(new String[] { "http://mymovieapi.com/?title=godfather&type=json&plot=simple&episode=0&limit=1&yg=0&mt=M&lag=en-US&offset=&aka=simple&release=simple&business=0&tech=0" });
		        
		        task.execute(new String[] { "http://10.0.2.2:62425/RestService.svc/CurrentApresentation"});
		        
		        /*
		        try {
		        	HttpGet request = new HttpGet("http://mymovieapi.com/?title=godfather&type=json&plot=simple&episode=0&limit=1&yg=0&mt=M&lag=en-US&offset=&aka=simple&release=simple&business=0&tech=0");
			        request.setHeader("Accept","application/json");
			        request.setHeader("Content-type","application/json");
			        DefaultHttpClient httpClient = new DefaultHttpClient();
			        
			        HttpResponse response = httpClient.execute(request);
			        
			        HttpEntity responseEntity = response.getEntity();
			        String json = EntityUtils.toString(responseEntity);
			        
			        new AlertDialog.Builder(getApplicationContext()).setTitle("Pinguim").setMessage(json).setNeutralButton("Close", null).show();
			        lista.add("Entrou na lista");
		        } catch (Exception e) {
		        	//lista.add(e.getMessage());
		        	lista.add("Deu erro");
		        }*/
		        
		        
		        
		        //lista.add("Renan");
		        //lista.add("Rambo");
		        adapter.notifyDataSetChanged();
		        
		        llHome.setVisibility(View.INVISIBLE);		    
		        llHome.setLayoutParams(new LinearLayout.LayoutParams(0,0));	    
		        llLista.setVisibility(View.VISIBLE);
		        llLista.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		        llSettings.setVisibility(View.INVISIBLE);
		        llSettings.setLayoutParams(new LinearLayout.LayoutParams(0,0));
			}
		});
        
        ImageButton btSettings = (ImageButton)findViewById(R.id.btSettings);
        btSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout llHome = (LinearLayout)findViewById(R.id.llHome);
		        LinearLayout llLista= (LinearLayout)findViewById(R.id.llLista);
		        LinearLayout llSettings = (LinearLayout)findViewById(R.id.llSettings);
		        llHome.setVisibility(View.INVISIBLE);
		        llHome.setLayoutParams(new LinearLayout.LayoutParams(0,0));
		        llLista.setVisibility(View.INVISIBLE);
		        llLista.setLayoutParams(new LinearLayout.LayoutParams(0,0));
		        llSettings.setVisibility(View.VISIBLE);
		        llSettings.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*
    private class LongOperation extends AsyncTask<String, Void, String> {

  	  @Override
  	  protected String doInBackground(String... params) {
  			for(int i=0;i<5;i++) {
  				try {
  					Thread.sleep(1000);
  				} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  			}

  			return "Executed";
  	  }      

  	  @Override
  	  protected void onPostExecute(String result) {
  			//TextView txt = (TextView) findViewById(R.id.output);
  			//txt.setText("Executed"); // txt.setText(result);
  			//might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
  	  }

  	  @Override
  	  protected void onPreExecute() {
  	  }

  	  @Override
  	  protected void onProgressUpdate(Void... values) {
  	  }
  }*/
    
   
    
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
          //String response = "";
        	StringBuilder builder = new StringBuilder();
          for (String url : urls) {
        	  
            DefaultHttpClient client = new DefaultHttpClient();
            
            //HttpHost proxy = new HttpHost("192.168.21.130", 8080);
            //client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
            
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json");
     	   httpGet.setHeader("Content-type", "application/json");
            try {
              HttpResponse execute = client.execute(httpGet);
              InputStream content = execute.getEntity().getContent();

              BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
              String s = "";
              while ((s = buffer.readLine()) != null) {
                //response += s;
            	  builder.append(s);
              }

            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          //return response;
          return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
        	String s = "";
        	for (int i=0;i<result.length(); i++){
        		if (!result.substring(i, i+1).equals("\\")) {
        			s = s + result.substring(i,i+1); 
        		}
        	}
        	result = s.substring(1, s.length()-1);
        	try {
        		//JSONArray jsonArray = new JSONArray(result);
        	      //for (int i = 0; i < jsonArray.length(); i++) {
        	          //JSONObject jsonObject = jsonArray.getJSONObject(i);
        		JSONObject jsonObject = new JSONObject(result);

        	          lista.add(jsonObject.getString("Name"));
        	          currentApresentation = jsonObject.getString("Name");
        	       //}
        	} catch (Exception e) {
        		lista.add("Deu erro: " + e.getMessage());
        	}
        	
          //lista.add(s);
        }
      }
    
}
