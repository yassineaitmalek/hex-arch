package com.yatmk.test.ports.input;

import java.util.List;

import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;

public interface TestUseCase {
    public TestDTO create(TestCreation creation);

    public List<TestDTO> getAudit(Long id);

    public TestDTO get(Long id);

    public void delete(Long id);

    public TestDTO update(Long id, TestUpdate update);
}
