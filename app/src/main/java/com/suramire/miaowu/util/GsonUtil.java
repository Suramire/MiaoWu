package com.suramire.miaowu.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Suramire on 2017/6/20.
 * JSon 工具类
 * getJSONObject
 * getJSONArray
 */



public class GsonUtil {
    /**
     * 根据json字符串转成单个对象
     * @param jsonString
     * @param tClass 目标对象的类型
     * @param <T>
     * @return
     */
   public static <T>Object jsonToObject(String jsonString, Class<T> tClass ){
       Gson gson = new Gson();
       T t = gson.fromJson(jsonString, tClass);
       return t;
   }


    /**
     * 根据json字符串获取响应对象的List
     * @param jsonString json字符串
     * @param tClass 需要装换的对象
     * @param <T> 泛型
     * @return List
     */
    public static  <T> List<T> jsonToList(String jsonString, final Class<T> tClass ){
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        try{
            JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
            for(final JsonElement elem : array){
                list.add(gson.fromJson(elem, tClass));
            }
        }catch (Exception e){
            Log.e("GsonUtil", "jsonToList: "+e);
        }

        return list;
    }

    /**
     * 将存放对象的List转换成JSONArray字符串
     * @param <E> Object
     * @param lists 存放对象的List
     * @return JSONArray字符串
     */
    public static <E> String listToJson(List<E> lists){
        return new GsonBuilder().setPrettyPrinting().create().toJson(lists);
    }

    /**
     * 根据对象获取JSON字符串
     * @param object 目标对象
     * @return JSON字符串
     */
    public static String objectToJson(Object object){
        Gson gson = new Gson();
        String s = gson.toJson(object);
        return  s;
    }

}