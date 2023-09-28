package com.example.dacn.modules.address.repository;

import com.example.dacn.entity.Address;
import com.example.dacn.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>
{

}
