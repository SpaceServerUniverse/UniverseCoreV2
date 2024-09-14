package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import space.yurisi.universecorev2.database.models.AutoTppSetting;
import space.yurisi.universecorev2.exception.UserNotFoundException;

import java.util.List;

public class AutoTppSettingRepository {

    private final SessionFactory sessionFactory;

    private final UserRepository userRepository;

    public AutoTppSettingRepository(SessionFactory sessionFactory, UserRepository userRepository) {
        this.sessionFactory = sessionFactory;
        this.userRepository = userRepository;
    }

    public AutoTppSetting createAutoTppSetting(Player player){
        Long user_id = null;
        try {
            user_id = userRepository.getPrimaryKeyFromPlayerName(player.getName());
        } catch (UserNotFoundException e) {
            player.sendMessage("ユーザーが見つかりませんでした。");
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.getTransaction();
            if(transaction.isActive()){
                transaction.rollback();
            }
            session.close();
            throw new RuntimeException(e);
        }

        AutoTppSetting autoTPPSetting = new AutoTppSetting(null, user_id, false);

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(autoTPPSetting);
        session.getTransaction().commit();
        session.close();

        return autoTPPSetting;
    }

    public boolean getIsAutoAccept(Player player){
        Long user_id = null;
        try {
            user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        AutoTppSetting autoTPPSetting = getAutoTPPSetting(user_id);
        return autoTPPSetting.getIs_auto_accept();
    }

    public AutoTppSetting getAutoTPPSetting(Long user_id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        AutoTppSetting data = session.get(AutoTppSetting.class, user_id);
        session.getTransaction().commit();
        session.close();

        return data;
    }

    public AutoTppSetting getAutoTPPSettingFromPlayer(Player player) throws UserNotFoundException{
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        return getAutoTPPSetting(user_id);
    }

    public void updateAutoTPPSetting(AutoTppSetting autoTPPSetting){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(autoTPPSetting);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAutoTPPSetting(AutoTppSetting autoTPPSetting){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(autoTPPSetting);
        session.getTransaction().commit();
        session.close();
    }


}