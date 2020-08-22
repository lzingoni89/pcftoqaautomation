package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Log4j2
@Service
public class GitRepositoryService {

    public void loadGitInformation(List<ProjectDTO> projectDTOList) {
        if (projectDTOList == null || projectDTOList.isEmpty()) {
            return;

        }
        for (ProjectDTO dto : projectDTOList) {
            if (!dto.isAdminGitRepository()) {
                continue;

            }
            try {
                var existingRepo = getGitRepository(dto.getRootPath());
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

    public void doCheckout(ProjectDTO projectDTO) {
        if (!projectDTO.isAdminGitRepository() || projectDTO.getSelectedBranch() == null) {
            return;

        }
        try {
            var gitRepository = getGitRepository(projectDTO.getRootPath());
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

    public void pullRepository(ProjectEntity projectEntity) {
        if (projectEntity == null
                || !projectEntity.isAdminGitRepository()
                || projectEntity.getRootPath() == null
                || projectEntity.getRootPath().isBlank()) {
            return;

        }
        log.info("Updating Git repository...");
        try {
            var process = Runtime
                    .getRuntime()
                    .exec("git pull", null, new File(projectEntity.getRootPath()));
            printResults(process);

        } catch (IOException e) {
            log.error(e);

        }

    }

    private Repository getGitRepository(String rootPath) throws IOException {
        if (rootPath == null || rootPath.isBlank()) {
            return null;

        }
        var rootFolder = new File(rootPath + File.separator + ".git");
        if (!rootFolder.exists()) {
            log.error("It's not a git repository: " + rootFolder.getPath());
            return null;

        }
        return new FileRepositoryBuilder()
                .setGitDir(rootFolder)
                .build();

    }

    private void printResults(Process process) {
        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        var line = "";
        try {
            while ((line = reader.readLine()) != null) {
                log.info(line);

            }

        } catch (Exception e) {
            log.error(e);

        }

    }

}
