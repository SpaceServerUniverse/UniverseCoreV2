package space.yurisi.universecorev2.database.repositories;


import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Job;

import java.util.Date;


public class JobRepository {

    private final SessionFactory sessionFactory;

    public JobRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Boolean isExistJob(Player player){
        Session session = sessionFactory.openSession();
        try {
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", player.getUniqueId().toString())
                    .uniqueResult();
            return job != null;
        } finally {
            session.close();
        }
    }

    public void createJob(Player player){
        Job job = new Job(null, player.getUniqueId().toString(), 0, new Date(), new Date(), new Date());
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(job);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public int getJobID(Player player){
        Session session = sessionFactory.openSession();
        try {
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", player.getUniqueId().toString())
                    .uniqueResult();
            if(job == null){
                createJob(player);
                return 0;
            }
            return job.getJob_id();
        } finally {
            session.close();
        }
    }

    public boolean updateJob(Player player, int jobID){
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", player.getUniqueId().toString())
                    .uniqueResult();
            if(job == null){
                return false;
            }
            Date date = job.getLast_changed();
            // 1週間以上経過していないと変更できない
            long diff = new Date().getTime() - date.getTime();
            if(diff < 7 * 24 * 60 * 60 * 1000){
                return false;
            }
            job.setJob_id(jobID);
            job.setUpdated_at(new Date());
            session.merge(job);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
}
