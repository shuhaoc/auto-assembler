package me.caosh.autoasm;

/**
 * Auto assembler工具类
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/15
 */
public class AutoAssemblers {
    private static final AutoAssembler DEFAULT = new AutoAssembler();

    public static AutoAssembler getDefault() {
        return DEFAULT;
    }

    private AutoAssemblers() {
    }

    private static final AutoAssemblers _CODE_COVERAGE = new AutoAssemblers();
}
