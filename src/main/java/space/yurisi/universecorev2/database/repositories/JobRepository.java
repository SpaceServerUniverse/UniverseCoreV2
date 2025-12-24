package space.yurisi.universecorev2.database.repositories;


import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Job;
import space.yurisi.universecorev2.utils.Message;

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
        // 初期は8日前にしておく
        Date lastChanged = new Date(System.currentTimeMillis() - 8L * 24 * 60 * 60 * 1000);
        Job job = new Job(null, player.getUniqueId().toString(), 0, lastChanged, new Date(), new Date());
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(job);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public int getJobIDFromPlayer(Player player){
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

    public int getJobIDFromUUID(String uuid){
        Session session = sessionFactory.openSession();
        try {
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .uniqueResult();
            if(job == null){
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
                Message.sendErrorMessage(player, "[職業AI]", "職業情報が見つかりません。");
                return false;
            }
            if(job.getJob_id() == jobID){
                Message.sendErrorMessage(player, "[職業AI]", "あなたは既にその職業に就いています。");
                return false;
            }
            Date date = job.getLast_changed();
            // 1週間以上経過していないと変更できない
            long diff = new Date().getTime() - date.getTime();
            if(diff < 7 * 24 * 60 * 60 * 1000){
                Message.sendErrorMessage(player, "[職業AI]", "職業は一度変更すると1週間変更できません。");
                return false;
            }
            job.setJob_id(jobID);
            job.setLast_changed(new Date());
            job.setUpdated_at(new Date());
            session.merge(job);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
}
