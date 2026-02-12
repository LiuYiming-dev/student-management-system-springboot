package com.liu.studentmanagement.common.handler;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.liu.studentmanagement.common.enums.GenderEnum;

public class GenderConverter implements Converter<GenderEnum> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return GenderEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(GenderEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        // 🌟 1. 极其重要的判空逻辑
        if (value == null) {
            return new WriteCellData<>(""); // 如果是空，格子里就留白
        }

        // 🌟 2. 这里的 getDesc() 必须确保在 GenderEnum 类上有 @Getter 或者手写了该方法
        String desc = value.getDesc();

        // 🌟 3. 返回封装好的 Excel 数据对象
        return new WriteCellData<>(desc);
    }
    @Override
    public GenderEnum convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String value = cellData.getStringValue();
        if ("男".equals(value)) return GenderEnum.MALE;
        if ("女".equals(value)) return GenderEnum.FEMALE;
        return null;
    }
}