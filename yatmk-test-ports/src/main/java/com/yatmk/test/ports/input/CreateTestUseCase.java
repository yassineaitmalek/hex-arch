package com.yatmk.test.ports.input;

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;

public interface CreateTestUseCase {

    public TestDTO createTest(TestCreation testCreation);
}
