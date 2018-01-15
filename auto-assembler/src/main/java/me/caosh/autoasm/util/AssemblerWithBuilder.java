package me.caosh.autoasm.util;

import me.caosh.autoasm.AutoAssembler;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * Auto assembler与ConvertibleBuilder的绑定
 *
 * @author caosh/shuhaoc@qq.com
 * @date 2018/1/15
 */
public class AssemblerWithBuilder<T, BT extends ConvertibleBuilder<T>> {
    private final AutoAssembler autoAssembler;
    private final BT convertibleBuilder;

    public AssemblerWithBuilder(AutoAssembler autoAssembler, BT convertibleBuilder) {
        this.autoAssembler = autoAssembler;
        this.convertibleBuilder = convertibleBuilder;
    }

    public AutoAssembler getAutoAssembler() {
        return autoAssembler;
    }

    public BT getConvertibleBuilder() {
        return convertibleBuilder;
    }

    public AssemblerWithBuilder<T, BT> assemble(Object sourceObject) {
        autoAssembler.assemble(sourceObject, convertibleBuilder);
        return this;
    }

    public AssemblerWithBuilder<T, BT> disassemble(Object targetObject) {
        autoAssembler.disassemble(targetObject, convertibleBuilder);
        return this;
    }

    public T build() {
        return convertibleBuilder.build();
    }
}
