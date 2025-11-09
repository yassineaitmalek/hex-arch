package com.yatmk.test.infrastructure.services;
 

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.input.CreateTestUseCase;
import com.yatmk.test.ports.output.SaveTestPort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

 

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuinessService implements CreateTestUseCase {

    private final ModelMapper modelMapper;

    private final SaveTestPort saveTestPort;


    @Override
    public TestDTO createTest(TestCreation testCreation) {
        return saveTestPort.saveTest(testCreation);
    }
}
