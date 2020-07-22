package com.github.supermoonie;

import lombok.Data;

import java.util.List;

/**
 * @author supermoonie
 * @since 2020/7/22
 */
@Data
public class GeneNameListPage {

    private int pageIndex = 0;

    private int totalPage = 0;

    private int totalCount = 0;

    private int pageSize = 0;

    private List<GeneName> nameList;

    public boolean hasMore() {
        return pageIndex < totalPage;
    }
}
