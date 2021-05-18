package hu.unideb.inf.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseRepository {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("database");

    private static EntityManager getEntityManager() {

        return emf.createEntityManager();
    }
    public void uploadResultToDatabase(Database entity){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            em.close();

        }catch (Exception e){
            System.out.println("Error");
        } finally {
            em.close();
        }
    }
}
