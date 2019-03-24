package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final StateDao stateDao;
    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao, StateDao stateDao){
        this.addressDao = addressDao;
        this.stateDao = stateDao;
    }


    @Override
    public Integer addAddress(AddressEntity addressEntity) { return addressDao.addAddress(addressEntity.getFlatbuildNumber(), addressEntity.getLocality(), addressEntity.getCity() , addressEntity.getZipcode() , addressEntity.getStateEntity().getId()); }


    @Override
    public StateEntity checkValidState(Integer id) { return stateDao.isValidState(id); }


    @Override
    public Integer countAddress(){
        return addressDao.countAddress() ;
    }


    @Override
    public Integer addCustomerAddress(String temp, Integer user_id, Integer address_id) { return addressDao.addCustomerAddress(temp, user_id, address_id); }


    @Override
    public Iterable<StateEntity> getAllStates() {
        return stateDao.getAllStates();
    }

    @Override
    public AddressEntity getAddressById(Integer addressId) {
        return addressDao.getAddressById(addressId);
    }


    @Override
    public AddressEntity getaddressById( Integer addressId) { return addressDao.findAddressById(addressId) ;}


    @Override
    public Integer updateAddressById (String flat_build_num , String locality, String city, String zipcode , Integer state_id , Integer id)
    {
        return addressDao.updateAddressById(flat_build_num,locality,city,zipcode,state_id,id);
    }


    @Override
    public Integer deleteAddressById (Integer id )
    {
        return addressDao.deleteAddressById(id);
    }


    @Override
    public Integer deleteCustomerAddressById(Integer id) { return addressDao.deleteCustomerAddressById(id); }


    @Override
    public Boolean getAddress(Integer addressId){
        if (addressDao.findAddressById(addressId) == null )
            return false;
        else
            return true ;
    }


    @Override
    public Iterable<AddressEntity> getPermAddress(Integer userId)
    {
        List<AddressEntity> customerList = new ArrayList<>();

        Iterable<Integer> permAddressIdList = addressDao.getPermAdd(userId);

        System.out.println("Length of address list: "+permAddressIdList.toString());

        if( permAddressIdList.iterator().hasNext() )
        {
            for (Integer addressId: permAddressIdList ) {
                System.out.println("Details for Address id "+addressId);
                AddressEntity  add = addressDao.findAddressById(addressId) ;
                StateEntity stateEntity = stateDao.getStatebyId(add.getStateEntity().getId());

                AddressEntity resp = new AddressEntity(add.getId(),add.getFlatbuildNumber(), add.getLocality(), add.getCity(), add.getZipcode(), stateEntity);
                customerList.add(resp);
            }
        }
        return customerList;
    }
}
