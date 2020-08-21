package com.arrowsoft.pcftoqaautomation;

import lombok.extern.log4j.Log4j2;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
@SpringBootTest
class PcfToQaAutomationApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testGitRepository() throws IOException, GitAPIException {
        var existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File("C:\\gw\\sura\\BillingCenter\\modules\\.git"))
                .build();
        log.info("Actual branch: " + existingRepo.getBranch());
        log.info("Actual branch full: " + existingRepo.getFullBranch());
        log.info("List of branches");
        var git = new Git(existingRepo);
        var remoteRefs = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
        for (Ref ref : remoteRefs) {
            log.info(ref.getName());
        }


    }

    @Test
    void testPullGitRepository() throws IOException, GitAPIException {
        Process process = Runtime.getRuntime()
                .exec("git pull", null, new File("C:\\gw\\sura\\PolicyCenterQA\\policy-center"));
        printResults(process);

    }

    private void printResults(Process process) {
        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
        } catch (Exception e) {
            log.error(e);

        }
    }

}
