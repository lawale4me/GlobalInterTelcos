/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;


import entities.Transactions;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Ahmed
 */

public class AdminRepositoryImpl extends RepositoryBase implements AdminRepository 
{
    
    public AdminRepositoryImpl() {
    }
    
    @Override
    public List<Transactions> getTransactions() {
        if(session!=null&&session.isActive())
        {
        List<Transactions> tranxs = manager.createNamedQuery("Transactions.findAll", Transactions.class).getResultList();
        return tranxs.isEmpty() ? null : tranxs;            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Transactions> tranxs = manager.createNamedQuery("Transactions.findAll", Transactions.class).getResultList();        
        manager.close();
        return tranxs.isEmpty() ? null : tranxs;   
        }
    }

    @Override
    public Transactions getTransaction(String amount, String sourceNumber) {
         if(session!=null&&session.isActive())
        {
        List<Transactions> tranxs = manager.createNamedQuery("Transactions.findPending", Transactions.class).setParameter("amount", amount).setParameter("sourceNumber", sourceNumber).getResultList();
        return tranxs.isEmpty() ? null : tranxs.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Transactions> tranxs = manager.createNamedQuery("Transactions.findPending", Transactions.class).setParameter("amount", amount).setParameter("sourceNumber", sourceNumber).getResultList();        
        manager.close();
        return tranxs.isEmpty() ? null : tranxs.get(0); 
        }        
    }

    @Override
    public Transactions getPendingTransactions(String airtel) {
         if(session!=null&&session.isActive())
        {
        List<Transactions> tranxs = manager.createNamedQuery("Transactions.findByDestinationNetworkANDStatus", Transactions.class).setParameter("destinationNetwork", airtel).getResultList();
        return tranxs.isEmpty() ? null : tranxs.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Transactions> tranxs = manager.createNamedQuery("Transactions.findByDestinationNetworkANDStatus", Transactions.class).setParameter("destinationNetwork", airtel).getResultList();
        manager.close();
        return tranxs.isEmpty() ? null : tranxs.get(0); 
        }
    }

    @Override
    public void updateTranx(Transactions tranx) {
        if (session!=null&&session.isActive()) {
            manager.merge(tranx);
        }else{
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.merge(tranx);
        manager.getTransaction().commit();
        }
    }
   
    
}
