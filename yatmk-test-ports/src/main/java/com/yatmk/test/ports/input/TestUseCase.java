package com.yatmk.test.ports.input;

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;
import java.util.List;

public interface TestUseCase {
    public TestDTO create(TestCreation creation);

    public List<TestDTO> getAudit(Long id);

    public TestDTO get(Long id);

    public void delete(Long id);

    public TestDTO update(Long id, TestUpdate update);
}
