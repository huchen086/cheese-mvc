package com.chen.cheesemvc.models.data;

import com.chen.cheesemvc.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public interface MenuDao extends CrudRepository<Menu, Integer> {
}
