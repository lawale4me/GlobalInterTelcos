/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author Ahmed
 */
public interface UnitOfWork
{
    UnitOfWorkSession begin();
    void  join(UnitOfWorkSession session);    
}
