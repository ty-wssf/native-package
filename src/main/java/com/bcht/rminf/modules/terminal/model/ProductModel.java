package com.bcht.rminf.modules.terminal.model;


public enum ProductModel {

    ALL((byte) 0x0000, "所有型号"),
    SIMPLE((byte) 0x0001, "简易版（校时报警器）"),
    STANDARD((byte) 0x0002, "标准版"),
    HIGH_END((byte) 0x0003, "高配版"),
    INTEGRATED_BOX((byte) 0x0004, "一体机箱"),
    NEW_STANDARD((byte) 0x0005, "新标准版");

    private byte code;

    private String name;

    ProductModel(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根据code查找对应的枚举名称
    public static String findByCode(byte code) {
        for (ProductModel model : ProductModel.values()) {
            if (model.getCode() == code) {
                return model.getName();
            }
        }
        return null; // 如果没有找到匹配的code，返回null
    }

}
