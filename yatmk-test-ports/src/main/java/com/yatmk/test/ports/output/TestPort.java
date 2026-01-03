package com.yatmk.test.ports.output;

import java.util.List;

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;

public interface TestPort {

    public TestDTO save(TestCreation testCreation);

    public List<TestDTO> getAudit(Long id);

    public TestDTO get(Long id);

    public void delete(Long id);

    public TestDTO update(Long id, TestUpdate update);

}
