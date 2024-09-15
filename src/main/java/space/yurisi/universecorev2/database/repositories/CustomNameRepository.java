package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.CustomName;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.exception.CustomNameNotFoundException;

import java.util.Date;

public class CustomNameRepository {
    private final SessionFactory sessionFactory;


    /**
     * Instantiates a new CustomName repository.
     *
     * @param sessionFactory session factory
     */
    public CustomNameRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * ユーザーに基づき称号を作成します。
     *
     * @param user User
     * @return custom_name CustomName
     */
    public CustomName createCustomName(User user) {
        Long user_id = user.getId();
        CustomName custom_name = new CustomName(null, user_id, "", new Date(), new Date());

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(custom_name);//save
        session.getTransaction().commit();
        session.close();

        return custom_name;
    }

    /**
     * 称号をプライマリーキーから取得します。
     *
     * @param id Long(PrimaryKey)
     * @return CustomName
     * @exception CustomNameNotFoundException 称号データが存在しない
     */
    public CustomName getCustomName(Long id) throws CustomNameNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        CustomName data = session.get(CustomName.class, id);
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new CustomNameNotFoundException("称号データが存在しませんでした。 ID:" + id);
        }
        return data;
    }

    /**
     * 称号をuser_idから取得します。
     *
     * @param user_id Long
     * @return CustomName
     * @exception CustomNameNotFoundException 称号データが存在しない
     */
    public CustomName getCustomNameFromUserId(Long user_id) throws CustomNameNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        CustomName data = session.createSelectionQuery("from CustomName where user_id = ?1", CustomName.class)
                .setParameter(1, user_id).getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();

        if (data == null) {
            throw new CustomNameNotFoundException("称号データが存在しませんでした。 user_id:" + user_id);
        }
        return data;
    }

    /**
     * 指定したプライマリーキーがデータベースに存在するかを返します
     *
     * @param id Long(Primary key)
     * @return boolean
     */
    public boolean existsCustomName(Long id) {
        try{
            getCustomName(id);
            return true;
        }catch (CustomNameNotFoundException e){
            return false;
        }
    }

    /**
     * 指定したユーザーIDのカラムがデータベースに存在するかを返します
     *
     * @param user_id Long
     * @return boolean
     */
    public boolean existsCustomNameFromUserId(Long user_id) {
        try {
            getCustomNameFromUserId(user_id);
            return true;
        } catch (CustomNameNotFoundException e){
            return false;
        }
    }



    /**
     * お金モデルに基づきデータをアップデートします。
     *
     * @param custom_name CustomName
     */
    public void updateCustomName(CustomName custom_name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(custom_name);//update
        session.getTransaction().commit();
        session.close();
    }


    /**
     * 称号モデルに基づきデータを削除します。
     *
     * @param custom_name CustomName
     */
    public void deleteCustomName(CustomName custom_name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(custom_name); //delete
        session.getTransaction().commit();
        session.close();
    }
}
