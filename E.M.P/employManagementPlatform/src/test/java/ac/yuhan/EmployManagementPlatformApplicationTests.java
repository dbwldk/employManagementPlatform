package ac.yuhan;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ac.yuhan.domain.Work_history;
import ac.yuhan.persistence.Work_historyRepository;
@SpringBootTest
class EmployManagementPlatformApplicationTests {
	
	@Autowired
	private Work_historyRepository repo;
	
	@Test

	void contextLoads() {
		/*
		UnitedEmploy em = new UnitedEmploy();
		em.setE_num("A0000001");
		Work_history work_history = new Work_history();
		work_history.setH_comment("");
		work_history.setH_date(new Date());
		work_history.setHnum(em.getE_num());
		work_history.setHstate(2);
		work_history.setH_key(repo.findAll().size());
		repo.save(work_history);
		*/
		//Optional<Work_history> historyList = repo.findWork_historyByhstateAndHnum(0, "A0000001");
		
		
		Work_history his = repo.findWork_historyByhstateAndHnum(0, "A0000001").get();
		his.setH_comment("");
		his.setH_date(new Date());
		his.setHstate(1);
		System.out.println(his.toString());
		repo.save(his);
			//repo.save(history);
		
	}

}
