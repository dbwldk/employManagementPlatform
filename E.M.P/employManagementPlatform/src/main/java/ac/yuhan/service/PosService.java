package ac.yuhan.service;

import java.util.List;

import ac.yuhan.domain.Pos;

public interface PosService {
	public Pos getPos(Long posNo);
	public List<Pos> getAllPos();
}
