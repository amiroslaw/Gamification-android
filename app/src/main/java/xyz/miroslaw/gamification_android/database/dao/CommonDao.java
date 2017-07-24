package xyz.miroslaw.gamification_android.database.dao;

import java.util.List;

import xyz.miroslaw.gamification_android.model.BaseModel;

public interface CommonDao {

    public void create(BaseModel baseModel);
    public void update(BaseModel baseModel);
    public void delete(BaseModel baseModel);
    public void createOrUpdate(BaseModel baseModel);
    public BaseModel findById(int id);
    public List<?> findAll();

}
