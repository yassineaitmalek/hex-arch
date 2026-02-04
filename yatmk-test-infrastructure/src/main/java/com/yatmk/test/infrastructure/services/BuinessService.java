package com.yatmk.test.infrastructure.services;

import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;
import com.yatmk.test.ports.input.TestUseCase;
import com.yatmk.test.ports.output.TestPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuinessService implements TestUseCase {

    private final TestPort testPort;

    @Override
    public TestDTO create(TestCreation creation) {
        return testPort.save(creation);
    }

    @Override
    public List<TestDTO> getAudit(Long id) {
        return testPort.getAudit(id);
    }

    @Override
    public TestDTO get(Long id) {
        return testPort.get(id);
    }

    @Override
    public void delete(Long id) {
        testPort.delete(id);
    }

    @Override
    public TestDTO update(Long id, TestUpdate update) {
        return testPort.update(id, update);
    }
}
