package com.liu.studentmanagement.service.clazzService;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.entity.vo.ClazzExcelVO;
import com.liu.studentmanagement.mapper.ClazzMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {

    @Override
    public Page<Clazz> getClazzPage(Integer pageNum, Integer pageSize) {
        Page<Clazz> pageParam = new Page<>(pageNum, pageSize);
        return this.page(pageParam);
    }

    @Override
    public List<Clazz> listAll() {
        return this.list();
    }

    @Override
    public void clazzExport(HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("class", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            List<Clazz> ClazzList = this.list();
            List<ClazzExcelVO> clazzExcelVOList = new ArrayList<>();
            for (Clazz clazz : ClazzList) {
                ClazzExcelVO clazzExcelVO = new ClazzExcelVO();
                BeanUtils.copyProperties(clazz, clazzExcelVO);
                clazzExcelVOList.add(clazzExcelVO);
            }
            EasyExcel.write(response.getOutputStream(), ClazzExcelVO.class).sheet("Clazz").doWrite(clazzExcelVOList);
        }catch (Exception e){
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(map);
        }
    }
}
