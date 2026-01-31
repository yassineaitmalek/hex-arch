package com.yatmk.test.infrastructure.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;
import com.yatmk.test.ports.output.TestPort;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BuinessServiceTest {

    @InjectMocks
    private BuinessService buinessService;

    @Mock
    private TestPort testPort;

    @BeforeEach
    public void before() {
        //
    }

    @AfterEach
    public void after() {
        //
    }

    @Test
    public void testCreate() {
        TestCreation creation = new TestCreation("Test Name", BigInteger.ONE, Boolean.FALSE, BigDecimal.TEN);

        TestDTO testDTO = new TestDTO(1L, "Test Name", BigInteger.ONE, Boolean.FALSE, BigDecimal.TEN);

        when(testPort.save(any(TestCreation.class))).thenReturn(testDTO);

        TestDTO createdTest = buinessService.create(creation);

        assertNotNull(createdTest);
        verify(testPort, times(1)).save(any(TestCreation.class));
    }

    @Test
    public void testGetAudit() {
        Long id = 1l;

        TestDTO testDTO = new TestDTO(1L, "Test Name", BigInteger.ONE, Boolean.FALSE, BigDecimal.TEN);

        List<TestDTO> testDTOs = Arrays.asList(testDTO);
        when(testPort.getAudit(any(Long.class))).thenReturn(testDTOs);

        List<TestDTO> audit = buinessService.getAudit(id);
        assertNotNull(audit);
        assertFalse(audit.isEmpty());
        verify(testPort, times(1)).getAudit(id);
    }

    @Test
    public void testGet() {
        Long id = 1l;

        TestDTO testDTO = new TestDTO(1L, "Test Name", BigInteger.ONE, Boolean.FALSE, BigDecimal.TEN);

        when(testPort.get(any(Long.class))).thenReturn(testDTO);

        TestDTO fetchedTest = buinessService.get(id);

        assertNotNull(fetchedTest);
        verify(testPort, times(1)).get(id);
    }

    @Test
    public void testDelete() {
        Long id = 1l;

        doNothing().when(testPort).delete(any(Long.class));

        buinessService.delete(id);

        verify(testPort, times(1)).delete(id);
    }

    @Test
    public void testUpdate() {
        Long id = 1l;
        TestUpdate update = new TestUpdate("Updated Name", BigInteger.TEN, Boolean.TRUE, BigDecimal.ONE);
        TestDTO updatedTestDTO = new TestDTO(1L, "Updated Name", BigInteger.TEN, Boolean.TRUE, BigDecimal.ONE);

        when(testPort.update(any(Long.class), any(TestUpdate.class))).thenReturn(updatedTestDTO);

        TestDTO updatedTest = buinessService.update(id, update);

        assertNotNull(updatedTest);
        verify(testPort, times(1)).update(id, update);
    }
}
