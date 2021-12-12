package com.freestack.evaluation.apis;

import com.freestack.evaluation.EntityManagerFactorySingleton;
import com.freestack.evaluation.models.Booking;
import com.freestack.evaluation.models.UberDriver;
import com.freestack.evaluation.models.UberUser;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UberApi {



    private static List<UberDriver> uberDrivers = new ArrayList<>();
    private static List<Booking> bookings=new ArrayList<>();

    public static void enrollUser(UberUser uberUser){
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(uberUser);
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }

    }
    public static void enrollDriver(UberDriver uberDriver){

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(uberDriver);
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }

    }

    public static Booking bookOneDriver(UberUser uberUser){
        Booking booking=new Booking();

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

        // sur une vraie db, cela engendrerait plein de travail pour le serveur inutile , pourquoi ne pas silmplement filtrer  dans la requete 
        //Query query= entityManager.createQuery("select d from UberDriver d ");
        //uberDrivers=query.getResultList();
        /*for (UberDriver uberDriver:uberDrivers) {

            if(uberDriver.getAvalable()){

                uberDriver.setAvalable(false);
                booking.setDriver(uberDriver);
                booking.setUser(uberUser);

                try {
                    entityManager.getTransaction().begin();
                    entityManager.merge(uberDriver);
                    entityManager.persist(booking);
                    entityManager.getTransaction().commit();

                }
                finally
                {
                    entityManager.close();
                }

                return booking;
            }
        }

        return null;*/
        List<UberDriver> uberDrivers = (List<UberDriver>) em.createQuery("SELECT u From UberDriver u WHERE u.available = true").setMaxResults(1).getResultList();
        if (!uberDriverList.isEmpty()) {
            Booking booking = new Booking();
            em.getTransaction().begin();
            UberDriver uberDriver = uberDriverList.get(0);
            booking.setUberDriver(uberDriver);
            booking.setUberUser(uberUser);
            booking.setStartOfBooking(new Date());
            uberDriver.setAvailable(false);
            em.persist(uberDriver);
            em.persist(booking);
            em.getTransaction().commit();
            em.close();
            return booking;
        } else return null;
        


         
    }
    public static void finishBooking(Booking booking){

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

        //meme remarque qu'au dessus, la requete SQL est le meilleur endroit pour filtrer les résultats
        // et le booking n est pas dit finit (endOfBooking)
        /*Query query= entityManager.createQuery("select b from Booking b");
        bookings=query.getResultList();


        for (Booking bookingBDD:bookings) {

            if( bookingBDD.getId().equals(booking.getId())){

                booking.getDriver().setAvalable(true);

                try {
                    entityManager.getTransaction().begin();
                    entityManager.merge(booking.getDriver());
                    entityManager.merge(booking);
                    entityManager.getTransaction().commit();

                }
                finally
                {
                    entityManager.close();
                }

            }
        }*/
         EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b= :booking");
        query.setParameter("booking", booking);
        Booking result = (Booking) query.getSingleResult();
        result.setEndOfBooking(new Date());
        em.persist(result);
        UberDriver uberDriver = result.getUberDriver();
        uberDriver.setAvailable(true);
        em.persist(uberDriver);
        em.getTransaction().commit();
        em.close();

    }
    public static void evaluateDriver(Booking booking,int evaluation){

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
/* idem
        Query query= entityManager.createQuery("select b from Booking b");
        bookings=query.getResultList();


        for (Booking bookingBDD:bookings) {

            if(bookingBDD.getId().equals(booking.getId())){

                booking.setScore(evaluation);

                try {
                    entityManager.getTransaction().begin();
                    entityManager.merge(booking);
                    entityManager.getTransaction().commit();

                }
                finally
                {
                    entityManager.close();
                }

            }
        }*/
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b= :booking");
        query.setParameter("booking", booking);
        Booking booking = (Booking) query.getSingleResult();

        booking.setScore(evaluation);
        em.persist(result);
        em.getTransaction().commit();
        em.close();


    }
    public static List<Booking> listDriverBookings(UberDriver uberDriver){
/* idem
        List<Booking> listDriverBookings=new ArrayList<>();

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();



        Query query= entityManager.createQuery("select b from Booking b ");
        bookings=query.getResultList();


        for (Booking bookingBDD:bookings) {

            if( bookingBDD.getDriver().getId().equals(uberDriver.getId()) && bookingBDD.getDriver().getAvalable().equals(uberDriver.getAvalable())){

                listDriverBookings.add(bookingBDD);
            }
        }*/
        EntityManager em = emf.createEntityManager();
        List<Booking> driverBookings = (List<Booking>) em.createQuery("SELECT b FROM Booking b where b.uberDriver = :uberDriver")
            .setParameter("uberDriver", uberDriver)
            .getResultList();
        em.close();
        return driverBookings;

        return listDriverBookings;
    }
    public static List<Booking> listUnfinishedBookings(){
        List<Booking> unfinishedBookings=new ArrayList<>();

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

        Query query= entityManager.createQuery("select b from Booking b ");
        bookings=query.getResultList();


        for (Booking bookingBDD:bookings) {

            if(!bookingBDD.getDriver().getAvalable() && bookingBDD.getScore()==null){

                unfinishedBookings.add(bookingBDD);

            }
        }


        return unfinishedBookings;
    }

    public static float meanScore(UberDriver uberDriver){

        float meanScore=0;
        Integer nbScore=0;
        Integer totalScore=0;
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

        Query query= entityManager.createQuery("select b from Booking b ");
        bookings=query.getResultList();


        for (Booking bookingBDD:bookings) {

            if(bookingBDD.getDriver().getId().equals(uberDriver.getId()) && bookingBDD.getScore()!=null){
                totalScore=totalScore+bookingBDD.getScore();
                nbScore++;
            }
        }
        meanScore=totalScore/nbScore;
        return meanScore;
    }
}
