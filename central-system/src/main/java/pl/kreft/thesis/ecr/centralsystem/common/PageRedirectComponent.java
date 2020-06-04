package pl.kreft.thesis.ecr.centralsystem.common;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.REDIRECT;

public class PageRedirectComponent {
    public static String redirectTo(String target) {
        return new StringBuilder().append(REDIRECT).append(target).toString();
    }
}
