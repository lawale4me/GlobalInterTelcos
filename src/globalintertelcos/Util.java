/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package globalintertelcos;

import java.util.regex.Pattern;

/**
 *
 * @author Ahmed
 */
public class Util {
    
    public static String[] splitMessage(String message){
        
        String reqArray[];        
        if(message.contains("*"))
            {
             String ray[] = message.split("\\*");
             reqArray=ray;
            }else
            {
             String ray[] = message.split(" ");
             reqArray=ray;
            }
            String keyword = reqArray[0];

            if (keyword.trim().equals("ASHARE"))
            {
                try {
                    if (reqArray[1] == null || reqArray[2] == null) {
                    }
                } catch (Exception e) {
                    System.out.println("One Value is Missing!!!");                    
                    return null;
                }                                
             }
            else 
             {
               System.out.println("Your request is invalid.");                                
               return null;
             }
            return reqArray;
    }
    
    public static String getNetwork(String phone) {

        String destinationMsisdn = phone;
        System.out.println("PHONE:"+phone);
        String formattedDest = "invalid";
        try {
             if (destinationMsisdn.length() == 11) {                
                if (destinationMsisdn.startsWith("0")) {                    
                    if (Pattern.matches("[0-9]*", destinationMsisdn)){                
                    String prefix=destinationMsisdn.substring(0, 4);
                    System.out.println("prefix:"+prefix);
                    if(prefix.equals("0802")||prefix.equals("0808")||prefix.equals("0812")){
                        return "AIRTEL";
                    }
                    else if(prefix.equals("0805")||prefix.equals("0807")){
                        return "GLO";
                    }
                    else if(prefix.equals("0803")||prefix.equals("0806")){
                        return "MTN";
                    }
                }
                }
            }            

        } catch (NullPointerException e) {
           System.out.println(e);
        }
        return formattedDest;
    }
    
    
    public static String formatDestination(String phone) {

        String destinationMsisdn = phone;
        String formattedDest = "invalid";
        try {
            if (destinationMsisdn.length() == 14) {
                if (destinationMsisdn.startsWith("+234")) {
                    if (Pattern.matches("[+][2][3][4][0-9]*", destinationMsisdn)) {
                        formattedDest = destinationMsisdn;

                    }
                }
            } else if (destinationMsisdn.length() == 13) {
                if (destinationMsisdn.startsWith("234")) {
                    if (Pattern.matches("[2][3][4][0-9]*", destinationMsisdn)) {
                        formattedDest = "+"+destinationMsisdn;

                    }
                }
            } else if (destinationMsisdn.length() == 11) {                
                if (destinationMsisdn.startsWith("0")) {                    
                    if (Pattern.matches("[0-9]*", destinationMsisdn));
                    formattedDest = destinationMsisdn.replaceFirst("0", "+234");                    
                }
            }
            else if (destinationMsisdn.length() == 10) {                
                if (!destinationMsisdn.startsWith("0")) {                    
                    if (Pattern.matches("[0-9]*", destinationMsisdn));
                    formattedDest = "+234"+destinationMsisdn ;                    
                }
            }
             if (destinationMsisdn.contains("\\/")) {
                formattedDest = "invalid";                
                return formattedDest;
            }

             if (!Pattern.matches("[+][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", formattedDest)) {
                formattedDest = "invalid";                
                return formattedDest;
            }

        } catch (NullPointerException e) {
           System.out.println(e);
        }
        return formattedDest;
    }

   public static String[] splitConfirmMessage(String message) {          
        
            String reqArray[];        
        
            String ray[] = message.split(" ");
            reqArray=ray;            
            String keyword = reqArray[0];

            if (message.contains("transfered to your account"))
            {
                try {
                    //  0          1      2    3    4      5    6         7
                    //50.00NGN transfered to your account from MSISDN 07055932235
                    if (reqArray[1] == null || reqArray[2] == null||reqArray[3] == null || reqArray[4] == null||reqArray[5] == null || reqArray[6] == null || reqArray[7] == null) {
                    }
                } catch (Exception e) {
                    System.out.println("One Value is Missing!!!");                    
                    return null;
                }                                
             }
            else 
             {
               System.out.println("Your request is invalid.");                                
               return null;
             }
            return reqArray;
    }

   public static String[] splitConfirmMessageAirtel(String message) {
       
            String reqArray[];        
        
            String ray[] = message.split(" ");
            reqArray=ray;            
            String keyword = reqArray[0];

            if (message.contains("You have received"))
            {
                try {
                    //  0          1               2    3    4       5   6    7       8
                     //Ref ID: C141012.1715.320017 You have received 50 NGN from 8024403945. Need a job? Dial *108*11# for free.                                        
                    if (reqArray[1] == null || reqArray[2] == null||reqArray[3] == null || reqArray[4] == null||reqArray[5] == null || reqArray[6] == null || reqArray[7] == null) {
                    }
                } catch (Exception e) {
                    System.out.println("One Value is Missing!!!");                    
                    return null;
                }                                
             }
            else 
             {
               System.out.println("Your request is invalid.");                                
               return null;
             }
            return reqArray;
    }

    public static String[] splitConfirmMessageMTN(String message) {
        
            String reqArray[];        
        
            String ray[] = message.split(" ");
            reqArray=ray;            
            String keyword = reqArray[0];

            if (message.contains("was transferred to you from"))
            {
                try {
                    //  0     1      2       3   4    5    6            7
                    //N50.00 was transferred to you from 2348038430865 via share n sell service.
                    if (reqArray[1] == null || reqArray[2] == null||reqArray[3] == null || reqArray[4] == null||reqArray[5] == null || reqArray[6] == null || reqArray[7] == null) {
                    }
                } catch (Exception e) {
                    System.out.println("One Value is Missing!!!");                    
                    return null;
                }                                
             }
            else 
             {
               System.out.println("Your request is invalid.");                                
               return null;
             }
            return reqArray;
    }
    
}
