package com.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.web.data.User;
import com.web.data.dto.UserDto;

public interface UserRepo extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	@Query("select new com.web.data.dto.UserDto(" +
            "   u, " +
			"   count(uf), " +
            "   sum(case when uf = :user then 1 else 0 end) > 0" +
            ") " +
            "from User u left join u.userFriends uf " +
            "where u.id = :id " +
            "group by u")
    UserDto findOneUser(@Param("user") User user, @Param("id") Long id);
	
}
