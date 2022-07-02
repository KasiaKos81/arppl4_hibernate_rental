package pl.sda.arppl4.hibernaterental.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernaterental.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDao <T> {

    public void addCar(T addedObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(addedObject);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void removeCar(T removedObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.remove(removedObject);

            transaction.commit();
        }
    }

    public Optional<T> getOneCar(Long id, Class<T> classType) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            T foundObject = session.get(classType, id);

            return Optional.ofNullable(foundObject);
        }
    }

    public List<T> getAllCars(Class<T> classType) {
        List<T> list = new ArrayList<>();

        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            TypedQuery<T> request = session.createQuery("from " + classType.getName(), classType);
            List<T> requestResult = request.getResultList();

            list.addAll(requestResult);
        } catch (SessionException sessionException) {
            System.err.println("Error.");
        }

        return list;
    }

    public void updateCar(T updatedObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(updatedObject);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null){
                transaction.rollback();
            }
        }
    }

}
