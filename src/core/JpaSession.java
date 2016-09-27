/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.persistence.EntityManager;

/**
 *
 * @author PHI
 */
public class JpaSession implements UnitOfWorkSession
{
    private EntityManager manager;
    protected  boolean sessionactive;

    public JpaSession(EntityManager manager)
    {
        this.manager = manager;
        manager.getTransaction().begin();
        sessionactive=true;
    }
    
    
    @Override
    public void commit()
    {
        manager.getTransaction().commit();
        manager.close();
        sessionactive=false;
    }

    @Override
    public void rollback()
    {
        manager.getTransaction().rollback();
        manager.close();
        sessionactive=false;
    }
    
    public EntityManager getManager()
    {
        return this.manager;
    }
    
    @Override
    public boolean isActive() {
        return sessionactive;
    }
    
}
