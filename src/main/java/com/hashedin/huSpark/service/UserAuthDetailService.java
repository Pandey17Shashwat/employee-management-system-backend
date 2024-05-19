package com.hashedin.huSpark.service;

import com.hashedin.huSpark.model.User;
import com.hashedin.huSpark.model.UserAuthDetails;
import com.hashedin.huSpark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username) // Replace with correct findBy method
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserAuthDetails.build(user);
    }
}
