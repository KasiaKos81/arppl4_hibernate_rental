package pl.sda.arppl4.hibernaterental.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements ICarDao{


    @Override
    public void addCar(Car car) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;

        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(car);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeCar(Car car) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()){
            Transaction transaction = session.beginTransaction();

            session.remove(car);

            transaction.commit();
        }

    }

    @Override
    public Optional<Car> getOneCar(Long id) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            Car objectCar = session.get(Car.class, id);

            return Optional.ofNullable(objectCar);
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> productList = new ArrayList<>();

        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {

            TypedQuery<Car> request = session.createQuery("from Car", Car.class);
            List<Car> requestResult = request.getResultList();

            productList.addAll(requestResult);
        } catch (SessionException sessionException) {
            System.err.println("Error, wrong request");

        }
        return productList;
    }

    @Override
    public void updateCar(Car car) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(car);

            transaction.commit();

        } catch (SessionException sessionException) {
            if (transaction != null){
                transaction.rollback();
            }
        }

    }
}
