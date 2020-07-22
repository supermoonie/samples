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
    private String loginNo = "无";

    /**
     * 影响特征
     */
    private String feature = "";

    /**
     * 基因英文名
     */
    private String geneEnName = "无";

    /**
     * 基因中文名
     */
    private String geneZhName = "无";

    /**
     * 基因所在位置
     */
    private String position = "无";

    /**
     * 所在染色体
     */
    private String chromosome = "未定位";
}
