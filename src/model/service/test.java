package model.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class test {
	
	public String[] getXY(String address){
//		List<String> list = new ArrayList<String>();
		String[] list = new String[3];
		try {
//            String sKeyWord = "台北市101";
            URL url  = new URL(String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW", 
            URLEncoder.encode(address, "UTF-8")));//p=%s is KeyWord in
            URLConnection connection = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            while ((line = reader.readLine()) != null) {builder.append(line);}
            JSONObject json = new JSONObject(builder.toString()); //轉換json格式
            JSONArray ja = json.getJSONArray("results");//取得json的Array物件
            for (int i = 0; i < ja.length(); i++) {
//                  System.out.print("地址：" + ja.getJSONObject(i).getString("formatted_address") + " ");
//                  System.out.print("緯度：" + ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat") + " ");
//                  System.out.print("經度：" + ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
//                  System.out.println("");
//                  list.add(ja.getJSONObject(i).getString("formatted_address"));
//                  list.add(String.valueOf(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat")));
//                  list.add(String.valueOf(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng")));
            		list[0] = ja.getJSONObject(i).getString("formatted_address");
            		list[1] = String.valueOf(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
            		list[2] = String.valueOf(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
            }
        } catch (JSONException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
		return list;
	}
	
	public static void main(String[] args) {
//		test t = new test();
//		List<String> list = t.getXY("台北車站");
//		for(String s:list){
//			System.out.println(s);
//		}
    }
}
