package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import com.arrowsoft.pcftoqaautomation.repository.ProjectRepository;
import com.arrowsoft.pcftoqaautomation.service.dto.company.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.GitRepoDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.ProjectDTO;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;

    public CompanyService(CompanyRepository companyRepository,
                          ProjectRepository projectRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
    }

    public List<CompanyDTO> getCompanyList() {
        return this.companyRepository.findAll().stream().map(CompanyDTO::new).collect(Collectors.toList());

    }

    public CompanyDTO findCompanyByID(String id) {
        var companyLongID = Long.parseLong(id);
        var companyDTO = this.companyRepository
                .findById(companyLongID)
                .map(CompanyDTO::new)
                .orElse(null);
        if (companyDTO == null) {
            return null;

        }
        companyDTO.setProjectList(this.projectRepository
                .findAllByCompany_Id(companyLongID)
                .stream().map(ProjectDTO::new)
                .collect(Collectors.toList()));
        loadGitInformation(companyDTO.getProjectList());
        return companyDTO;

    }

    private void loadGitInformation(List<ProjectDTO> projectDTOList) {
        if (projectDTOList == null || projectDTOList.isEmpty()) {
            return;

        }
        for (ProjectDTO dto : projectDTOList) {
            if (!dto.isAdminGitRepository()) {
                continue;

            }
            try {
                var existingRepo = getGitRepository(dto);
                if (existingRepo == null) {
                    continue;

                }
                var git = new Git(existingRepo);
                var remoteRefs = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
                var branchSelected = existingRepo.getBranch();
                for (Ref ref : remoteRefs) {
                    var gitRepoDTO = new GitRepoDTO(ref.getName());
                    dto.addGitRepoToList(gitRepoDTO);
                    if (gitRepoDTO.getBranchView().equals(branchSelected)) {
                        dto.setSelectedBranch(gitRepoDTO);

                    }

                }

            } catch (IOException | GitAPIException e) {
                log.error(e);

            }

        }

    }

    public void updateCompany(CompanyDTO companyDTO) {
        var result = this.companyRepository.findById(companyDTO.getId());
        if (result.isEmpty()) {
            log.error("Company not found");
            return;

        }
        var company = result.get();
        company.setCodNamespace(companyDTO.getCodNamespace());
        company.setRetired(companyDTO.isRetired());
        this.companyRepository.saveAndFlush(company);

    }

    public void createOrUpdateProject(CompanyDTO companyDTO, ProjectDTO projectDTO) {
        ProjectEntity projectEntity = null;
        if (projectDTO.getId() != null) {
            projectEntity = this.projectRepository.findById(projectDTO.getId()).orElse(null);

        }
        if (projectEntity == null) {
            projectEntity = new ProjectEntity();
            projectEntity.setCompany(this.companyRepository.findById(companyDTO.getId()).orElse(null));

        }
        projectEntity.setModule(projectDTO.getModule());
        projectEntity.setVersion(projectDTO.getVersion());
        var checkOut = true;
        if (projectDTO.getRootPath() != null) {
            var fileSeparator = File.separator;
            var rootPathNormalized = projectDTO.getRootPath()
                    .replace("//", fileSeparator)
                    .replace("\\", fileSeparator);
            if (rootPathNormalized.substring(rootPathNormalized.length() - 1).equals(fileSeparator)) {
                rootPathNormalized = rootPathNormalized.substring(0, rootPathNormalized.length() - 1);

            }
            projectDTO.setRootPath(rootPathNormalized);
            checkOut = projectEntity.getRootPath() == null || projectEntity.getRootPath().equals(projectDTO.getRootPath());

        }
        projectEntity.setRootPath(projectDTO.getRootPath());
        projectEntity.setAdminGitRepository(projectDTO.getAdminGitRepositoryView().isOriginalValue());
        this.projectRepository.saveAndFlush(projectEntity);
        if (!checkOut) {
            return;

        }
        doCheckout(projectDTO);

    }

    private Repository getGitRepository(ProjectDTO dto) throws IOException {
        if (dto.getRootPath() == null || dto.getRootPath().isBlank()) {
            return null;

        }
        var rootFolder = new File(dto.getRootPath() + File.separator + ".git");
        if (!rootFolder.exists()) {
            log.error("It's not a git repository: " + rootFolder.getPath());
            return null;

        }
        return new FileRepositoryBuilder()
                .setGitDir(rootFolder)
                .build();

    }

    private void doCheckout(ProjectDTO projectDTO) {
        if (!projectDTO.isAdminGitRepository() || projectDTO.getSelectedBranch() == null) {
            return;

        }
        try {
            var gitRepository = getGitRepository(projectDTO);
            if (gitRepository == null) {
                return;

            }
            var git = new Git(gitRepository);
            if (gitRepository.getBranch().equals(projectDTO.getSelectedBranch().getBranchView())) {
                return;

            }
            try {
                git.checkout().
                        setName(projectDTO.getSelectedBranch().getBranchView()).
                        setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
                        setStartPoint(projectDTO.getSelectedBranch().getFullBranchView()).
                        call();
            } catch (Exception e) {
                git.checkout().
                        setCreateBranch(true).
                        setName(projectDTO.getSelectedBranch().getBranchView()).
                        setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
                        setStartPoint(projectDTO.getSelectedBranch().getFullBranchView()).
                        call();

            }

        } catch (IOException | GitAPIException e) {
            log.error(e);

        }


    }

}
