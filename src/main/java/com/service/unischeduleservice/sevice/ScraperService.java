package com.service.unischeduleservice.sevice;


import com.service.unischeduleservice.dto.resposes.DataAppResponseDTO;
import com.service.unischeduleservice.dto.requests.DataAppRequestDTO;

import java.util.List;

public interface ScraperService {
    DataAppResponseDTO scrappingData(DataAppRequestDTO request);
    List<String> getSemesterList();
}
