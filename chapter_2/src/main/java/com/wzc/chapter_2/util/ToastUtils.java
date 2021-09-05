package com.wzc.chapter_2.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
      
    /** 之前显示的内容 */  
    private static String sLastmsg;
    /** Toast对象 */  
    private static Toast sToast = null ;
    private static long lastTime = 0 ;
    private static long currTime = 0 ;

    private ToastUtils() {
        //no instance
    }
    /** 
     * 显示Toast 
     * @param context 
     * @param message 
     */  
    public static void showToast(Context context, String message){
        if(sToast == null){
            sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            sToast.show() ;
            lastTime = System.currentTimeMillis() ;
        }else{  
            currTime = System.currentTimeMillis() ;
            if(message.equals(sLastmsg)){
                if(currTime - lastTime > Toast.LENGTH_SHORT){
                    sToast.show() ;
                }  
            }else{  
                sLastmsg = message ;
                sToast.setText(message) ;
                sToast.show() ;
            }  
        }  
        lastTime = currTime;
    }  
}  