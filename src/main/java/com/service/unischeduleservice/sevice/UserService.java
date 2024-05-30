package com.service.unischeduleservice.sevice;

import com.service.unischeduleservice.dto.requests.user.UserRequestDTO;
import com.service.unischeduleservice.dto.resposes.user.UserDetailResponse;

import java.util.List;

public interface UserService {
    String subscribeUser(UserRequestDTO request);
    void updateUser(UserRequestDTO request);
    void deleteUser(String userId);
    UserDetailResponse getUser(String userId);
    List<UserDetailResponse> getAllUser(int pageNo, int pageSize);
}
