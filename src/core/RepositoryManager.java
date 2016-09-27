/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PHI
 */
public class RepositoryManager
{
    private static EntityManagerFactory factory;    
    static 
    {
        factory = Persistence.createEntityManagerFactory("GlobalInterTelcosPU");
    }
    
    public static EntityManager getManager()
    {
        return factory.createEntityManager();
    }
}
