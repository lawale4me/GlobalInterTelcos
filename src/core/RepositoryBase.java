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
public class RepositoryBase implements UnitOfWork
{
    protected  JpaSession session;    
    protected  EntityManager manager;
    @Override
    public UnitOfWorkSession begin()
    {
        manager = RepositoryManager.getManager();
        session = new JpaSession(manager);        
        return session;
    }

    @Override
    public void join(UnitOfWorkSession session)
    {
        if(session instanceof JpaSession)
        {         
            manager = ((JpaSession)session).getManager();  
            this.session=((JpaSession)session);
        }        
    }

 
}
