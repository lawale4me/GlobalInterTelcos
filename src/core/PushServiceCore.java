/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

/**
 *
 * @author Ahmed
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


/**
 *
 * @author Ahmed
 */
public class PushServiceCore {
      
        
        public void pushToAndroidDevice(String deviceId,String info,String type){
            try
            {
              Sender sender=new Sender(AppInfo.ANDROID_PUSH_ID);
              Message msg = new com.google.android.gcm.server.Message.Builder()
               .addData("info",info).addData("type", type).build();   
              Result result = sender.send(msg, deviceId, 5);
              System.out.println("Sent"+type+" message to Android device: " + result);
             
            }
            catch(Exception e){
               System.out.println("Exception Occured while pushing to the Google server...........");  
            }
              
        }
    
}
