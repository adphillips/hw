package org.aphillips.hw.impl;

import java.io.Serializable;

import javax.ws.rs.Path;

import org.aphillips.hw.api.UserDao;
import org.aphillips.hw.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class UserDaoImpl implements UserDao {
  
  private SessionFactory sessionFactory;

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
  }

//  public Collection loadProductsByCategory(String category) {
//      return this.sessionFactory.getCurrentSession()
//              .createQuery("from test.Product product where product.category=?")
//              .setParameter(0, category)
//              .list();
//  }

  /* (non-Javadoc)
   * @see org.aphillips.hw.impl.UserDao#saveUser(org.aphillips.hw.domain.User)
   */
  public void saveUser(User user) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    final Serializable id = session.save(user);
    session.getTransaction().commit();
    session.close();
    
  }

  /* (non-Javadoc)
   * @see org.aphillips.hw.impl.UserDao#readUser(java.lang.String)
   */
  public User readUser(String id) {
    return null;
  }

}
