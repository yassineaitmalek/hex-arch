package com.yatmk.test.ports.output;


import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;
import java.util.List;


public interface TestPort {
	public TestDTO save(TestCreation testCreation);

	public List<TestDTO> getAudit(Long id);

	public TestDTO get(Long id);

	public void delete(Long id);

	public TestDTO update(Long id, TestUpdate update);

}
