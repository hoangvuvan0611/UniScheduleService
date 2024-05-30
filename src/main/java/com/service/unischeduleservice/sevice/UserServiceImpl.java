package com.service.unischeduleservice.sevice;

import com.service.unischeduleservice.constant.enums.RoleEnum;
import com.service.unischeduleservice.dto.requests.user.UserRequestDTO;
import com.service.unischeduleservice.dto.resposes.user.UserDetailResponse;
import com.service.unischeduleservice.entity.User;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public String subscribeUser(UserRequestDTO request) {

        User user = User.builder()
                .userId(request.getUserId())
                .monthsCount(request.getMonthsCount())
                .role(RoleEnum.USER)
                .build();

        userRepository.save(user);
        log.info("User created success!");
        return user.getUserId();
    }

    @Override
    public void updateUser(UserRequestDTO request) {
        User user = getUserByUserId(request.getUserId());
        user.setMonthsCount(request.getMonthsCount());
        userRepository.save(user);
        log.info("User update successfully!");
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
        log.info("User deleted, userId={}", userId);
    }

    @Override
    public UserDetailResponse getUser(String userId) {
        return modelMapper.map(getUserByUserId(userId), UserDetailResponse.class);
    }

    @Override
    public List<UserDetailResponse> getAllUser(int pageNo, int pageSize) {
        int pageIndex = 0;
        if(pageNo > 0) {
            pageIndex = pageNo - 1;
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return userRepository.findAll(pageable).map(user -> modelMapper.map(user, UserDetailResponse.class)).stream().toList();
    }

    private User getUserByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
