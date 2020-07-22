package com.github.supermoonie;

import lombok.Data;

/**
 * @author supermoonie
 * @since 2020/7/21
 */
@Data
public class DefaultPageResult {

    /**
     * __VIEWSTATE
     */
    private String viewState;

    /**
     * __VIEWSTATEGENERATOR
     */
    private String viewStateGenerator;

    /**
     * txtolists
     */
    private String txtoLists;

    /**
     * ontname
     */
    private String ontName;

    /**
     * btnlist
     */
    private String btnList = "检 索";
}
