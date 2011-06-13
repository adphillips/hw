package org.aphillips.hw.impl;

import java.text.MessageFormat;
import java.util.List;

import org.aphillips.hw.api.UserDao;
import org.aphillips.hw.domain.User;
import org.aphillips.hw.domain.UserValidator;
import org.hibernate.Query;
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

  @Override
  public Long saveUser(User user) {

    UserValidator v = new UserValidator(user);
    v.validate();
    Session session = null;
    Long id = null;
    try {
      session = sessionFactory.openSession();
      session.beginTransaction();
      id = (Long) session.save(user);
      session.getTransaction().commit();
    } finally {
      if (session != null)
        session.close();
    }

    return (Long) id;
  }

  @Override
  public User readUser(Long id) {
    Session session = null;
    @SuppressWarnings("rawtypes")
    List results = null;
    try {
      session = sessionFactory.openSession();

      Query q = session.createQuery("from User where id = :id");
      q.setLong("id", id);
      results = q.list();

      if (results.size() < 1) {
        return null;
      }
      if (results.size() > 1) {
        throw new IllegalStateException(MessageFormat.format("Somehow we have more than one user with id {0}", id));
      }
    } finally {
      if (session != null)
        session.close();
    }

    return (User) results.get(0);

  }
}
