/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import entities.Othermessages;
import entities.Transactions;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;


/**
 *
 * @author Ahmed
 */
public class AppManager 
{
    
    private final AdminRepository adminrepo;


    public AppManager(AdminRepositoryImpl adminRepositoryImpl) {
        this.adminrepo = adminRepositoryImpl;
    }

     
    

    public List<Transactions> getTransactions() {
        UnitOfWorkSession ses = adminrepo.begin(); 
         List<Transactions> tranxs=adminrepo.getTransactions();
        ses.commit();
        return tranxs;
    }

    public void insertTranx(Transactions transactions) {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(transactions);
        manager.getTransaction().commit();
    }

    public boolean updateTranx(String amount, String source,String message) {
        
        UnitOfWorkSession ses = adminrepo.begin();
        Transactions tranx = adminrepo.getTransaction(amount,source);
        if(tranx!=null){
            System.out.println("Trans is NOT NULL"+"    SOURCE"+source+" AMOUNT:"+amount);
        tranx.setStatus(1);
        tranx.setConfirmationMessage(message);
        tranx.setDateUpdated(new Date());
        System.out.println("Trans"+"    "+tranx.getRequestPayload());
        ses.commit();    
        return true;
        }
        else{
            ses.commit();    
            return false;
        }
        
                
    }

    public Transactions checkPending(String Network) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Transactions tranxs=adminrepo.getPendingTransactions(Network);
        ses.commit();
        return tranxs;
    }

    public void updateTranx(Transactions tranx) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        adminrepo.updateTranx(tranx);    
    }

    public void insertOtherMsg(Othermessages othermessages) {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(othermessages);
        manager.getTransaction().commit();
    }
     
    
     
     
}
