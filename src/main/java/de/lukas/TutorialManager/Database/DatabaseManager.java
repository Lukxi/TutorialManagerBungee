package de.lukas.TutorialManager.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import me.oxolotel.utils.wrapped.database.Database;
import me.oxolotel.utils.wrapped.plugin.Plugin;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DatabaseManager extends Database {
    public DatabaseManager(Plugin plugin) {
        super(plugin, List.of(QuizTable.class));
    }

    public void write(QuizTable qt){
        EntityManager em = null;
        try {
            em = getSessionFactory().get(20, TimeUnit.SECONDS).createEntityManager();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        em.getTransaction().begin();
        em.merge(qt);
        em.getTransaction().commit();
        em.close();
    }

    public List<QuizTable> read(){
        EntityManager em;
        try {
            em = getSessionFactory().get(20, TimeUnit.SECONDS).createEntityManager();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        em.getTransaction().begin();
        TypedQuery<QuizTable> qt = em.createQuery(
                "SELECT qd " +
                        "FROM QuizTable qd ", QuizTable.class);
        List<QuizTable> wq = qt.getResultList();
        em.getTransaction().commit();
        em.close();
        return wq;
    }

    public QuizTable readPlayerByID(int id){
        try {
            EntityManager em;
            try {
                em = getSessionFactory().get(20, TimeUnit.SECONDS).createEntityManager();
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            em.getTransaction().begin();
            TypedQuery<QuizTable> qtT = em.createQuery(
                    "SELECT qt " +
                            "FROM QuizTable qt " +
                            "WHERE qt.playerID = :pID ", QuizTable.class);
            qtT.setParameter("pID", id);
            em.getTransaction().commit();
            QuizTable s = qtT.getSingleResult();
            em.close();
            return s;
        }catch (NoResultException e){

        }
        return new QuizTable(id, true);
    }

    public void updateQuizTable(Boolean status, int id){
        EntityManager em;
        try {
            em = getSessionFactory().get(20, TimeUnit.SECONDS).createEntityManager();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        em.getTransaction().begin();
        Query qNPCt = em.createNativeQuery(
                "UPDATE QuizTable SET solvedQuiz = ?1 WHERE playerID = ?2");
        qNPCt.setParameter(1, status);
        qNPCt.setParameter(2, id);
        qNPCt.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
