package com.service.unischeduleservice.sevices;


import com.service.unischeduleservice.dtos.UserDTO;
import com.service.unischeduleservice.apis.requests.UserDataRequest;

import java.util.List;

public interface ScraperService {
    UserDTO scrappingData(UserDataRequest request);
    List<String> getSemesterList();
}
