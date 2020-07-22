package com.github.supermoonie;

import lombok.Data;

import java.util.List;

/**
 * @author supermoonie
 * @since 2020/7/22
 */
@Data
public class GeneNameListPage {

    private int pageIndex;

    private int totalPage;

    private int totalCount;

    private int pageSize;

    private List<GeneName> nameList;

    public boolean hasMore() {
        return pageIndex < totalPage;
    }
}
