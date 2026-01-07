package space.yurisi.universecorev2.database.repositories;


import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Job;
import space.yurisi.universecorev2.exception.AlreadyOnJobException;
import space.yurisi.universecorev2.exception.NotEnoughDurationJobChangeException;
import space.yurisi.universecorev2.exception.PlayerJobNotFoundException;
import space.yurisi.universecorev2.utils.Message;

import java.time.Duration;
import java.time.Instant;
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

    public Job createJob(Player player){
        // 初期は8日前にしておく
        Date newLastChanged = new Date(
                System.currentTimeMillis() - Duration.ofDays(8).toMillis()
        );
        Job job = new Job(null, player.getUniqueId().toString(), 0, newLastChanged, new Date(), new Date());
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(job);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return job;
    }

    public int getJobIDFromPlayer(Player player) throws PlayerJobNotFoundException {
        Session session = sessionFactory.openSession();
        try {
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", player.getUniqueId().toString())
                    .uniqueResult();
            if(job == null){
                throw new PlayerJobNotFoundException("プレイヤーの職業データが見つかりませんでした。" + player.getName());
            }
            return job.getJob_id();
        } finally {
            session.close();
        }
    }

    public Job getJobFromPlayer(Player player) throws PlayerJobNotFoundException {
        Session session = sessionFactory.openSession();
        try {
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", player.getUniqueId().toString())
                    .uniqueResult();
            if(job == null){
                throw new PlayerJobNotFoundException("プレイヤーの職業データが見つかりませんでした。" + player.getName());
            }
            return job;
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

    public void updateJob(Player player, int jobID) throws PlayerJobNotFoundException, AlreadyOnJobException, NotEnoughDurationJobChangeException {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Job job = (Job) session.createQuery("FROM Job WHERE uuid = :uuid")
                    .setParameter("uuid", player.getUniqueId().toString())
                    .uniqueResult();
            if(job == null){
                throw new PlayerJobNotFoundException("プレイヤーの職業データが見つかりませんでした。" + player.getName());
            }
            if(job.getJob_id() == jobID){
                throw new AlreadyOnJobException("プレイヤーはすでにその職業に就いています。" + player.getName() + " jobID:" + jobID);
            }

            Date lastChanged = job.getLast_changed();
            // 1週間以上経過していないと変更できない
            Instant lastChangedInstant = lastChanged.toInstant();
            Instant now = Instant.now();
            int diff = Duration.between(lastChangedInstant, now).compareTo(Duration.ofDays(7));
            if(diff < 0){
                throw new NotEnoughDurationJobChangeException("プレイヤーはまだ職業を変更できる期間に達していません。" + player.getName() + " lastChanged:" + lastChanged);
            }

            job.setJob_id(jobID);
            job.setLast_changed(new Date());
            job.setUpdated_at(new Date());
            session.merge(job);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
