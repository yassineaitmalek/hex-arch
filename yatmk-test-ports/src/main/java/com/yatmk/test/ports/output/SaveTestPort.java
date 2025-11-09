package com.yatmk.test.ports.output;

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;

public interface SaveTestPort {

    public TestDTO saveTest(TestCreation testCreation);
}
