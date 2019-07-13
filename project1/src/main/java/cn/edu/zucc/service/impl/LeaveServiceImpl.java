package cn.edu.zucc.service.impl;

import cn.edu.zucc.domain.dao.LeaveRepository;
import cn.edu.zucc.domain.entity.Leavee;
import cn.edu.zucc.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    @Override
    public void create(Leavee leave) {
        leaveRepository.save(leave);
    }

    @Override
    public List<Leavee> getLeaves(String applicant) {
        return leaveRepository.findByName(applicant);
    }

    @Override
    public Leavee getLeaves(Long id) {
        return leaveRepository.findById(id).get();
    }

    @Override
    public List<Leavee> getAllLeavs() {
        return leaveRepository.findAll();
    }

    @Override
    public List<Leavee> getAllLeaves(String offstatus) {
        return leaveRepository.findByOffstatus(offstatus);
    }

}
