/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package globalintertelcos;

/**
 *
 * @author Ahmed
 */
import org.smslib.AGateway;
import org.smslib.AGateway.Protocols;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
 
import java.util.ArrayList;
import java.util.List;
 
public class ReadMessages
{
    public void doIt() throws Exception
    {
        Service service=Service.getInstance();
        InboundNotification inboundNotification = new InboundNotification(); 
        try
        {
 
            
            SerialModemGateway gateway = new SerialModemGateway("modem", "COM19", 11200, "Huawei", "WCDMA");            
            gateway.setProtocol(Protocols.PDU);            
            gateway.setInbound(true);            
            gateway.setOutbound(true);            
            gateway.setSimPin("0000");            
            service.setInboundMessageNotification(inboundNotification);            
            service.addGateway(gateway);            
            service.startService();
            
            System.out.println();
            System.out.println("Modem Information:");
            System.out.println("  Manufacturer: " + gateway.getManufacturer());
            System.out.println("  Model: " + gateway.getModel());
            System.out.println("  Serial No: " + gateway.getSerialNo());
            System.out.println("  SMSC Number: " + gateway.getSmscNumber());
            System.out.println("  SIM IMSI: " + gateway.getImsi());
            System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
            System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
            System.out.println();
 
            
            
            InboundMessage[] msgList=service.readMessages(MessageClasses.ALL);
            for (InboundMessage msg : msgList)
                System.out.println(msg);
            System.out.println("Now Sleeping - Hit <enter> to stop service.");
            //System.in.read();
 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            service.stopService();
        }
    }
 
    public class InboundNotification implements IInboundMessageNotification
    {
        public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg)
        {
            if (msgType == MessageTypes.INBOUND) System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            else if (msgType == MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }
 
 
 
//    public static void main(String args[])
//    {
//        ReadMessages app = new ReadMessages();
//        try
//        {
//            app.doIt();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}