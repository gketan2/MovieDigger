package com.moviedigger.moviedigger.tmdbcalls;

import android.os.AsyncTask;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MoviePoster extends AsyncTask<Void,Void,List<String>>{

    private int movie_id;
    private TmdbResponse response = null;

    public MoviePoster(int movie_id, TmdbResponse response) {
        this.movie_id = movie_id;
        this.response = response;
    }
    @Override
    protected List<String> doInBackground(Void... voids) {
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
    protected void onPostExecute(List<String> s) {
        response.processFinish(s);
    }
    private List<String> parse(JSONObject jsonObject){
        String image_url = "https://image.tmdb.org/t/p/w185";
        String image_id = "";
        String rating = "";
        try{
            image_id   =  jsonObject.getString("poster_path");
            rating = jsonObject.getString("vote_average");

        }catch (Exception e){
        }
        image_url = image_url+image_id;
        List<String> l = new ArrayList<String>();
        l.add(image_url);l.add(rating);
        return l;
    }
}