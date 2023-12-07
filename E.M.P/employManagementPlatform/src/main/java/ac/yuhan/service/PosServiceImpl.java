package ac.yuhan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.Pos;
import ac.yuhan.persistence.PosRepository;

@Service
public class PosServiceImpl implements PosService {
	@Autowired
	private PosRepository posRepo;
	
	@Override
	public Pos getPos(Long posNo) {
		Optional<Pos> findPos = posRepo.findById(posNo);
		if(findPos.isPresent())
		{
			return findPos.get();
		}
		else return null;
	}

}
