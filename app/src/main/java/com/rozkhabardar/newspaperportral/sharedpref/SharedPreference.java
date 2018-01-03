package com.rozkhabardar.newspaperportral.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.rozkhabardar.newspaperportral.models.Items;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SharedPreference {
      public static final String PREFS_NAME = "PRODUCT_APP";
      public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }
    public void saveFavorites(Context context, List<Items> fav)
    {
        SharedPreferences settings;
         Editor   editor;
        settings=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor=settings.edit();
        Gson gson=new Gson();
        String jsonfav=gson.toJson(fav);
        editor.putString(FAVORITES,jsonfav);
        editor.apply();
    }
    public void addFavorite(Context context,Items item)
    {
        List<Items> fav=getFavorites(context);
        if(fav==null)
        {
            fav=new ArrayList<Items>();

        }
        fav.add(item);
        saveFavorites(context,fav);
    }
       public void removeFavorites(Context context,Items item)
       {
           ArrayList<Items> fav=getFavorites(context);
           if(fav!=null)
           {
               for(Items items:fav)
               {
                   if(items.getLink().equalsIgnoreCase(item.getLink()))
                   {
                       fav.remove(items);
                       saveFavorites(context,fav);
                       break;
                   }
               }

           }
       }
    public ArrayList<Items> getFavorites(Context context) {
        List<Items> favorites;
        SharedPreferences settings;
        settings=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if(settings.contains(FAVORITES))
        {
           String jsonfav=settings.getString(FAVORITES,null);
            Gson gson=new Gson();
            Items[] favItem=gson.fromJson(jsonfav,Items[].class);
            favorites= Arrays.asList(favItem);
            favorites=new ArrayList<Items>(favorites);
        }
        else
        {
            return null;
        }
        return (ArrayList<Items>) favorites;
    }
}
