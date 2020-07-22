package com.github.supermoonie;

import lombok.Data;

/**
 * @author supermoonie
 * @since 2020/7/21
 */
@Data
public class SearchResult {

    /**
     * 登录号
     */
    private String loginNo;

    /**
     * 术语名称
     */
    private String name;

    /**
     * 登录号对应的Url
     */
    private String loginNoUrl;
}
