package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Ammo;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;

import java.util.Date;

public class AmmoRepository {

    private final SessionFactory sessionFactory;

    /**
     * Instantiates a new Ammo repository.
     *
     * @param sessionFactory session factory
     */
    public AmmoRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * create ammo based on user
     *
     * @param user User
     * @return ammo Ammo
     */
    public Ammo createAmmo(User user) {
        Long user_id = user.getId();
        Ammo ammo = new Ammo(null, user_id, 60L, 120L, 120L, 60L, 60L, 120L, 10L, new Date(), new Date());

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.persist(ammo);//save
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return ammo;
    }

    /**
     * get ammo from primary key
     *
     * @param id Long(PrimaryKey)
     * @return Ammo
     */
    public Ammo getAmmo(Long id) throws AmmoNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Ammo ammo = session.get(Ammo.class, id);
            session.getTransaction().commit();
            if(ammo == null) {
                throw new AmmoNotFoundException("弾薬データが見つかりません");
            }
            return ammo;
        } finally {
            session.close();
        }
    }

    /**
     * get ammo from user_id
     *
     * @param user_id Long
     * @param gunType GunType
     * @return Long
     */
    public Long getAmmoFromUserId(Long user_id, GunType gunType) throws AmmoNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Ammo ammo = session.createSelectionQuery("from Ammo where user_id = ?1", Ammo.class)
                    .setParameter(1, user_id).getSingleResultOrNull();
            if (ammo == null) {
                throw new AmmoNotFoundException("弾薬データが見つかりません");
            }
            switch (gunType) {
                case HG -> {
                    return ammo.getHg();
                }
                case SMG -> {
                    return ammo.getSmg();
                }
                case AR -> {
                    return ammo.getAr();
                }
                case SG -> {
                    return ammo.getSg();
                }
                case SR -> {
                    return ammo.getSr();
                }
                case LMG -> {
                    return ammo.getLmg();
                }
                case EX -> {
                    return ammo.getEx();
                }
            }
            throw new AmmoNotFoundException("弾薬データが見つかりません");
        } finally {
            session.close();
        }
    }

    /**
     * update ammo
     *
     * @param user_id Long
     * @param newAmmo Long
     * @param gunType GunType
     */
    public void updateAmmo(Long user_id, Long newAmmo, GunType gunType) throws AmmoNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            Ammo ammo = session.createSelectionQuery("from Ammo where user_id = ?1", Ammo.class)
                    .setParameter(1, user_id).getSingleResultOrNull();
            if(ammo == null) {
                throw new AmmoNotFoundException("弾薬データが見つかりません");
            }
            switch (gunType) {
                case HG -> ammo.setHg(newAmmo);
                case SMG -> ammo.setSmg(newAmmo);
                case AR -> ammo.setAr(newAmmo);
                case SG -> ammo.setSg(newAmmo);
                case SR -> ammo.setSr(newAmmo);
                case LMG -> ammo.setLmg(newAmmo);
                case EX -> ammo.setEx(newAmmo);
            }
            session.merge(ammo);
            session.getTransaction().commit();
        }finally {
            session.close();
        }
    }



}
