package com.moviedigger.moviedigger.tmdbcalls;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MoviePoster extends AsyncTask<Void,Void,ArrayList<String>>{

    private int movie_id;
    private TmdbResponse response = null;

    public MoviePoster(int movie_id, TmdbResponse response) {
        this.movie_id = movie_id;
        this.response = response;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {


        String uri = "https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=f6f1784088c8c27e9fa707584a1a1d34";
        HttpURLConnection conn = null;


        try{
            URL url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(conn.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String result = bufferedReader.readLine();
            JSONObject jObject = new JSONObject(result);
            return parse(jObject);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            conn.disconnect();
        }

        return null;

    }
    @Override
    protected void onPostExecute(ArrayList<String> s) {
        response.processFinish(s);
    }

    private ArrayList<String> parse(JSONObject jsonObject){
        String image_url = "https://image.tmdb.org/t/p/w185";
        String image_id = "";
        String rating = "";
        String genres = "";
        try{
            image_id   =  jsonObject.getString("poster_path");
            rating = jsonObject.getString("vote_average");
            JSONArray temp = jsonObject.getJSONArray("genres");
            for(int i = 0;i < temp.length();i++){
                String x = temp.getJSONObject(i).getString("name");
                genres += x+"|";
            }

        }catch (Exception e){
        }
        image_url = image_url+image_id;
        ArrayList<String> l = new ArrayList<String>();
        l.add(image_url);l.add(rating);l.add(genres);
        return l;
    }
}