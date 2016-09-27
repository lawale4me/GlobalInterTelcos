/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import entities.Transactions;
import java.util.List;


/**
 *
 * @author Ahmed
 */
public interface AdminRepository extends UnitOfWork
{
    public List<Transactions> getTransactions();    
    public Transactions getTransaction(String amount, String source);
    public Transactions getPendingTransactions(String airtel);
    public void updateTranx(Transactions tranx);
  
}
