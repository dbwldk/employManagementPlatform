package ac.yuhan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.Employ_view;
import ac.yuhan.persistence.Employ_viewRepository;

@Service
public class Employ_viewServiceImpl implements Employ_viewService {
	
	@Autowired
	private Employ_viewRepository employ_viewRepo;
	
	@Override
	public Page<Employ_view> findEmploy_view(String searchEname, int searchDept, int searchPos, int searchEsstate,
			Pageable pageable) {
		if(searchEname != null && !searchEname.equals(""))
		{
			String searchingName = "%" + searchEname + "%";
			Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByEnameLikeOrderByEkey(searchingName, pageable);
			return employ_view;
		}
		else
		{
			if(searchDept == 0 && searchPos == 0 && searchEsstate == 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findAll(pageable);
				return employ_view;
			}
			
			if(searchDept != 0 && searchPos == 0 && searchEsstate == 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByedeptOrderByEkey(searchDept, pageable);
				return employ_view;
			}
			if(searchDept == 0 && searchPos != 0 && searchEsstate == 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByeposOrderByEkey(searchPos, pageable);
				return employ_view;
			}
			if(searchDept == 0 && searchPos == 0 && searchEsstate != 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByesstateOrderByEkey(searchEsstate, pageable);
				return employ_view;
			}
			if(searchDept != 0 && searchPos != 0 && searchEsstate == 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByeposAndEdeptOrderByEkey(searchPos, searchDept, pageable);
				return employ_view;
			}
			if(searchDept == 0 && searchPos != 0 && searchEsstate != 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByeposAndEsstateOrderByEkey(searchPos, searchEsstate, pageable);
				return employ_view;
			}
			if(searchDept != 0 && searchPos == 0 && searchEsstate != 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByesstateAndEdeptOrderByEkey(searchEsstate, searchDept, pageable);
				return employ_view;
			}
			if(searchDept != 0 && searchPos != 0 && searchEsstate != 2)
			{
				Page<Employ_view> employ_view = employ_viewRepo.findEmploy_viewByesstateAndEdeptAndEposOrderByEkey(searchEsstate, searchDept, searchPos, pageable);
				return employ_view;
			}
		}
		return null;
	}
	
}
