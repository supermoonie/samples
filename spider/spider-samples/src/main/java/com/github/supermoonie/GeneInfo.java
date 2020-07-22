package com.github.supermoonie;

import lombok.Data;

/**
 * @author supermoonie
 * @since 2020/7/22
 */
@Data
public class GeneInfo {

    /**
     * 登录号
     */
    private String loginNo;

    /**
     * 影响特征
     */
    private String feature;

    /**
     * 基因英文名
     */
    private String geneEnName;

    /**
     * 基因中文名
     */
    private String geneZhName;

    /**
     * 基因所在位置
     */
    private String position;

    /**
     * 所在染色体
     */
    private String chromosome;
}
