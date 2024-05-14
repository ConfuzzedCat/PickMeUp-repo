package dk.lyngby.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        truncateRoutes(emf);
        populateRoutes(emf);
    }


    /**
     * Populates the "route" table with mock data
     * @param emf Entitymanagerfactory needed for the Entitymanager
     * @author pelle112112
     */
    @NotNull
    private static void populateRoutes (EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            // Insert test rows
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('1','Nørregade 10','1172','Rovsingsgade 31','2200');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('2','Nørregade 10','1172','Duevej 92','2000');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('3','Nørregade 10','1172','Frederiksvej 10','2000');").executeUpdate();


            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('1','Nørregade 10','1172','Rovsingsgade 31','2200');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('2','Nørregade 10','1172','Duevej 92','2000');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('3','Nørregade 10','1172','Frederiksvej 10','2000');").executeUpdate();

            em.getTransaction().commit();
        }
    }

    /**
     * Truncates the "route" table
     * @param emf Entitymanagerfactory needed for the Entitymanager
     * @author pelle112112
     */
    private static void truncateRoutes (EntityManagerFactory emf){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY").executeUpdate();
            em.getTransaction().commit();

        }
    }
}
