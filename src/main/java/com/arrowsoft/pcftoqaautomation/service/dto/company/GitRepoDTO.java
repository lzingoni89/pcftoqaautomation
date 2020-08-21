package com.arrowsoft.pcftoqaautomation.service.dto.company;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GitRepoDTO {

    private static final String PREFIX_FULL = "refs/remotes/";
    private static final String PREFIX = "origin/";
    private static final String EMPTY = "";

    private String branchView;

    @EqualsAndHashCode.Include
    private String fullBranchView;

    public GitRepoDTO(String fullBranchView) {
        this.fullBranchView = fullBranchView.replace(PREFIX_FULL, EMPTY);
        this.branchView = this.fullBranchView.replace(PREFIX, EMPTY);

    }

}
