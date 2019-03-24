package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends CrudRepository <StateEntity, Integer> {

    // query to select name from the states
    @Query(nativeQuery = true,value = "SELECT * FROM STATES WHERE id=?1")
    StateEntity isValidState(Integer id);

    // get all states
    @Query(nativeQuery = true,value="select * from states")
    Iterable<StateEntity> getAllStates();

    //query to select state Name for the state_id.
    @Query(nativeQuery = true,value = "SELECT * FROM STATES WHERE id=?1")
    StateEntity getStatebyId(Integer id);

    //query to get ID
    @Query(nativeQuery = true, value = "SELECT * FROM states WHERE state_id = (?1)")
    StateEntity getById(Integer stateId);
}
