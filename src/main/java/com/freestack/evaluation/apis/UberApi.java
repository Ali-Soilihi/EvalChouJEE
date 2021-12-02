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

        Query query= entityManager.createQuery("select d from UberDriver d ");
        uberDrivers=query.getResultList();


         for (UberDriver uberDriver:uberDrivers) {

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

        return null;
    }
    public static void finishBooking(Booking booking){

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

        Query query= entityManager.createQuery("select b from Booking b");
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
        }

    }
    public static void evaluateDriver(Booking booking,int evaluation){

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

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
        }


    }
    public static List<Booking> listDriverBookings(UberDriver uberDriver){

        List<Booking> listDriverBookings=new ArrayList<>();

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();



        Query query= entityManager.createQuery("select b from Booking b ");
        bookings=query.getResultList();


        for (Booking bookingBDD:bookings) {

            if( bookingBDD.getDriver().getId().equals(uberDriver.getId()) && bookingBDD.getDriver().getAvalable().equals(uberDriver.getAvalable())){

                listDriverBookings.add(bookingBDD);
            }
        }

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
