package com.service.unischeduleservice.sevices;


import com.service.unischeduleservice.dtos.UserDTO;
import com.service.unischeduleservice.requests.SetupDataRequest;

import java.util.List;

public interface ScraperService {
    UserDTO scrappingData(SetupDataRequest request);
    List<String> getSemesterList();
}
