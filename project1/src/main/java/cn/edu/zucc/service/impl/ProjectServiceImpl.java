package cn.edu.zucc.service.impl;

import cn.edu.zucc.domain.dao.ProjectRepository;
import cn.edu.zucc.domain.entity.Mission;
import cn.edu.zucc.domain.entity.Project;
import cn.edu.zucc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;


    @Override
    public void create(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> getProjectByContractorName(String contractorName) {
        return projectRepository.findProjectByContractor(contractorName);
    }

    @Override
    public List<Mission> getMissionByProjectName(String projectName) {
        return  projectRepository.getMissionByProject(projectName);
    }

    @Override
    public List<Project> getProjectByNameLike(String pname) {
        return projectRepository.findProjectByNameLike(pname);
    }

    @Override
    public List<Project> getMyProjectByNameLike(String userName, String projectName) {
        return projectRepository.findMyProjectByNameLike(userName,projectName);
    }

    @Override
    public List<Project> getProjectByPuber(String username) {
        return projectRepository.findProjectByPuber(username);
    }

    @Override
    public List<Project> getProjectByPuberName(String username, String puberName) {
        return projectRepository.findProjectByPuberName(username,puberName);
    }

    @Override
    public List<Project> getProjectByPstatus(String username, String pstatus) {
        return projectRepository.findProjectByPstatus(username,pstatus);
    }

    @Override
    public List<Project> getProjectByPuberNameAndPstatus(String username, String puberName, String pstatus) {
        return projectRepository.findProjectByNameAndPstatus(username,puberName,pstatus);
    }

    @Override
    public String selectPuberByProject(String project) {
        return projectRepository.findPuberByProject(project);
    }

    @Override
    public List<Project> getAcceptProject(String username, String pstatus) {
        return projectRepository.findAcceptedProject(username,pstatus);
    }

    @Override
    public int getNumOfDev(String pname) {
        return projectRepository.findNumOfDev(pname);
    }

    @Override
    public int getTotalPersonDays(String pname) {
        return projectRepository.findTotalPersonDays(pname);
    }


}
