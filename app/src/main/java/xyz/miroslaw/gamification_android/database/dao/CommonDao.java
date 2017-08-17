package xyz.miroslaw.gamification_android.database.dao;

import java.util.List;

import xyz.miroslaw.gamification_android.model.BaseModel;

public interface CommonDao {

    void create(BaseModel baseModel);
    void update(BaseModel baseModel);
    void delete(BaseModel baseModel);
    void createOrUpdate(BaseModel baseModel);

    void deleteById(int id);

    BaseModel findById(int id);
    List<?> findAll();
    int countAll();

}
