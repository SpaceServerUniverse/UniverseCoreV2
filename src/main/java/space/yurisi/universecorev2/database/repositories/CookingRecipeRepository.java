package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.CookingRecipe;
import space.yurisi.universecorev2.exception.CookingRecipeNotFoundException;

import java.util.List;

public class CookingRecipeRepository {

    private final SessionFactory sessionFactory;

    public CookingRecipeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CookingRecipe createCookingRecipe(String player_uuid, byte[] recipeFlags) {
        CookingRecipe cookingRecipe = new CookingRecipe(null, player_uuid, recipeFlags);
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.persist(cookingRecipe);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return cookingRecipe;
    }

    /**
     * レシピIDからレシピのフラグバイト配列を取得します。
     *
     * @param recipeId int レシピID
     * @return byte[] レシピのフラグバイト配列
     * @throws CookingRecipeNotFoundException レシピデータが存在しない場合
     */
    public CookingRecipe getRecipeFlagsFromId(int recipeId) throws CookingRecipeNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            List<CookingRecipe> data = session.createSelectionQuery("FROM CookingRecipe WHERE id = :id", CookingRecipe.class)
                    .setParameter("id", recipeId)
                    .getResultList();
            session.getTransaction().commit();
            if(data.isEmpty()){
                throw new CookingRecipeNotFoundException("レシピのデータが存在しませんでした。id: "+recipeId);
            }
            return data.getFirst();
        }finally {
            session.close();
        }
    }

    /**
     * プレイヤーUUIDからレシピのフラグバイト配列を取得します。
     *
     * @param uuid
     * @return
     * @throws CookingRecipeNotFoundException
     */
    public CookingRecipe getRecipeFlagsFromPlayer(String uuid) throws CookingRecipeNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            List<CookingRecipe> data = session.createSelectionQuery("FROM CookingRecipe WHERE player_uuid = :uuid", CookingRecipe.class)
                    .setParameter("uuid", uuid)
                    .getResultList();
            session.getTransaction().commit();
            if(data.isEmpty()){
                throw new CookingRecipeNotFoundException("レシピのデータが存在しませんでした。uuid: "+uuid);
            }
            return data.getFirst();
        }finally {
            session.close();
        }
    }

    public void updateRecipeFlags(CookingRecipe cookingRecipe) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(cookingRecipe);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
