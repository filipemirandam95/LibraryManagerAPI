package com.example.application.service.impl;

import com.example.application.controller.dto.UserDTO;
import com.example.application.model.Loan;
import com.example.application.model.User;
import com.example.application.repository.LoanRepository;
import com.example.application.repository.UserRepository;
import com.example.application.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserServiceImpl(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userToCreate) {
        if(userRepository.existsById(userToCreate.getId())){
            throw new IllegalArgumentException("This User ID already exists.");
        }
        return new UserDTO(userRepository.save(userToCreate.toModel()));
    }

    @Override
    public UserDTO modifyUser(UserDTO userToModify) {
        Optional<User> userBd = userRepository.findById(userToModify.getId());
        if(userBd.isPresent()){
            return new UserDTO(userRepository.save(userToModify.toModel()));
        }else{
            throw new NoSuchElementException("This user id was not found.");
        }
    }

    @Override
    public void deleteUser(Long userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);
        if(!loans.isEmpty()){
            throw new IllegalArgumentException("This user ID was found in a loan therefore it can not be deleted.");
        }
        userRepository.deleteById(userId);
    }
}
