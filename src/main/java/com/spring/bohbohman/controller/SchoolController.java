package com.spring.bohbohman.controller;

import com.spring.bohbohman.bean.request.LoginRequest;
import com.spring.bohbohman.bean.request.UpdatePasswordRequest;
import com.spring.bohbohman.core.SubjectType;
import com.spring.bohbohman.repository.SchoolRepository;
import com.spring.bohbohman.repository.StudentRepository;
import com.spring.bohbohman.repository.TeacherRepository;
import com.spring.bohbohman.entity.SchoolEntity;
import com.spring.bohbohman.entity.StudentEntity;
import com.spring.bohbohman.entity.TeacherEntity;
import com.spring.bohbohman.util.WDWUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.OutputStream;

@RequestMapping(value = "/school")
@RestController
@Transactional
public class SchoolController {


    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest){
        SchoolEntity schoolDaoByUserName = schoolRepository.findByUserName(loginRequest.getUserName());
        if (null == schoolDaoByUserName){
            Map<String, Object> map = new HashMap<>();
            map.put("status", false);
            map.put("msg", "用户名错误！");
            return map;
        }
        SchoolEntity schoolEntity = schoolRepository.findByUserNameAndPassword(loginRequest.getUserName(),loginRequest.getPassword());
        if (null!=schoolEntity){
            Map<String, Object> map = new HashMap<>();
            map.put("status", true);
            map.put("msg", schoolEntity);
            return map;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("msg", "密码错误！");
        return map;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public String updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){

        Optional<SchoolEntity> schoolEntity = schoolRepository.findById(updatePasswordRequest.getId());
        if (schoolEntity.isPresent()){
            SchoolEntity entity = schoolEntity.get();
            entity.setPassword(updatePasswordRequest.getPassword());
            schoolRepository.save(entity);
        }
        return "success";
    }

    /**
     * 上传Excel,读取Excel中内容
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map<String, Object> batchimport(@RequestParam(value="filename") MultipartFile file,
                                           @RequestParam(value = "schoolId") Integer schoolId) throws IOException {
        Map<String, Object> result = new LinkedHashMap<>();

        SchoolEntity schoolEntity = schoolRepository.getOne(schoolId);
        if (null == schoolEntity) {
            result.put("status", false);
            result.put("msg", "学校不存在！");
            return result;
        }

        //判断文件是否为空
        if(null == file){
            result.put("status", false);
            result.put("msg", "文件为空！");
            return result;
        }

        //获取文件名
        String fileName = file.getOriginalFilename();

        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）验证文件名是否合格
        long size=file.getSize();
        if(null == fileName || ("").equals(fileName) && size == 0 && !WDWUtil.validateExcel(fileName)){
            result.put("status", false);
            result.put("msg", "文件格式不正确！请使用.xls或.xlsx后缀文档。");
            return result;
        }

        //创建处理EXCEL
        //初始化输入流
        InputStream is = file.getInputStream();
        //根据版本选择创建Workbook的方式
        Workbook wb = null;
        //根据文件名判断文件是2003版本还是2007版本
        if(WDWUtil.isExcel2007(fileName)){
            wb = new XSSFWorkbook(is);
        }else{
            wb = new HSSFWorkbook(is);
        }
        //处理excel

        //获取学校信息
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        String schoolType = sheet.getRow(2).getCell(1).getStringCellValue();
        sheet.getRow(3).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        schoolEntity.setGrandNum(sheet.getRow(3).getCell(1).getStringCellValue());
        schoolEntity.setPrincipal(sheet.getRow(4).getCell(1).getStringCellValue());
        sheet.getRow(5).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        schoolEntity.setTel(sheet.getRow(5).getCell(1).getStringCellValue());
        sheet.getRow(6).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        schoolEntity.setPhone(sheet.getRow(6).getCell(1).getStringCellValue());
        schoolEntity.setIsComplete("YES");

        //获取老师信息
        Map<TeacherEntity, List<TeacherEntity>> teacherMap = new LinkedHashMap<>();
        Sheet sheet1 = wb.getSheetAt(1);
        //得到Excel的行数
        int totalRows = sheet1.getPhysicalNumberOfRows();
        for (int i = 2; i < 11; i++) {
            //取组长信息
            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setName(sheet1.getRow(1).getCell(i).getStringCellValue());
            sheet1.getRow(2).getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            teacherEntity.setPhone(sheet1.getRow(2).getCell(i).getStringCellValue());
            teacherEntity.setType("组长");
            teacherEntity.setSchoolId(schoolId);
            teacherEntity.setSchoolType(schoolType);
            if (i == 2) {
                teacherEntity.setSubject(SubjectType.YUWEN);
            } else if (i == 3) {
                teacherEntity.setSubject(SubjectType.SHUXUE);
            } else if (i == 4) {
                teacherEntity.setSubject(SubjectType.YINGYU);
            } else if (i == 5) {
                teacherEntity.setSubject(SubjectType.WULI);
            } else if (i == 6) {
                teacherEntity.setSubject(SubjectType.HUAXUE);
            } else if (i == 7) {
                teacherEntity.setSubject(SubjectType.SHENGWU);
            } else if (i == 8) {
                teacherEntity.setSubject(SubjectType.ZHENGZHI);
            } else if (i == 9) {
                teacherEntity.setSubject(SubjectType.LISHI);
            } else if (i == 10) {
                teacherEntity.setSubject(SubjectType.DILI);
            }
            teacherEntity.setParentId(0);
            //取组员
            List<TeacherEntity> teacherEntityList = new ArrayList<>();
            for (int y = 3; y < totalRows; y++) {
                if (null != sheet1.getRow(y).getCell(i).getStringCellValue() && !StringUtils.isEmpty(sheet1.getRow(y).getCell(i).getStringCellValue().trim())) {
                    TeacherEntity teacherEntity1 = new TeacherEntity();
                    teacherEntity1.setType("组员");
                    teacherEntity1.setSchoolId(schoolId);
                    teacherEntity1.setName(sheet1.getRow(y).getCell(i).getStringCellValue());
                    teacherEntity1.setSubject(teacherEntity.getSubject());
                    teacherEntity1.setSchoolType(schoolType);
                    teacherEntityList.add(teacherEntity1);
                }
            }
            teacherMap.put(teacherEntity, teacherEntityList);
        }

        final boolean[] status = {true};
        final String[] msg = {""};
        //学生信息
        List<StudentEntity> studentEntityList = new ArrayList<>();
        for (int i = 2; i < 9; i++) {
            Sheet sheet2 = wb.getSheetAt(i);
            //得到Excel的行数
            int totalRows2 = sheet2.getPhysicalNumberOfRows();
            for (int y = 1; y < totalRows2; y++) {
                if (null != sheet2.getRow(y).getCell(0) && !StringUtils.isEmpty(sheet2.getRow(y).getCell(0).getStringCellValue())) {
                    StudentEntity studentEntity = new StudentEntity();
                    studentEntity.setSchoolId(schoolId);
                    studentEntity.setName(sheet2.getRow(y).getCell(0).getStringCellValue());
                    sheet2.getRow(y).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    studentEntity.setGrade(sheet2.getRow(y).getCell(1).getStringCellValue());
                    sheet2.getRow(y).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    studentEntity.setIdCard(sheet2.getRow(y).getCell(2).getStringCellValue());
                    sheet2.getRow(y).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    studentEntity.setExamRoomNum(sheet2.getRow(y).getCell(3).getStringCellValue());
                    sheet2.getRow(y).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    studentEntity.setSeatNum(sheet2.getRow(y).getCell(4).getStringCellValue());
                    if (i == 2) {
                        studentEntity.setIsJoin(sheet2.getRow(y).getCell(5).getStringCellValue());
                    } else {
                        studentEntity.setType(sheet2.getRow(y).getCell(5).getStringCellValue());
                    }
                    if (null != sheet2.getRow(y).getCell(6)) {
                        studentEntity.setDescription(sheet2.getRow(y).getCell(6).getStringCellValue());
                    }
                    if (i == 2) {
                        studentEntity.setSubject(SubjectType.YSY);
                    } else if(i == 3) {
                        studentEntity.setSubject(SubjectType.WULI);
                    } else if(i == 4) {
                        studentEntity.setSubject(SubjectType.HUAXUE);
                    } else if(i == 5) {
                        studentEntity.setSubject(SubjectType.SHENGWU);
                    } else if(i == 6) {
                        studentEntity.setSubject(SubjectType.ZHENGZHI);
                    } else if(i == 7) {
                        studentEntity.setSubject(SubjectType.LISHI);
                    } else if(i == 8) {
                        studentEntity.setSubject(SubjectType.DILI);
                    }
                    studentEntity.setSchoolType(schoolType);
                    studentEntityList.add(studentEntity);
                }
            }
        }
        //参数校验
        //身份证是否重复
        List<StudentEntity> allStudentList = studentRepository.findAll();
        allStudentList.addAll(studentEntityList);
        Map<String, Map<String, Long>> idCardMap = allStudentList.stream().collect(Collectors.groupingBy(StudentEntity::getIdCard, Collectors.groupingBy(StudentEntity::getSubject, Collectors.counting())));

        idCardMap.forEach((k, v) -> {
            if (null != v) {
                v.forEach((a, b) -> {
                    if (b > 1) {
                        status[0] = false;
                        msg[0] = "身份证重复！";
                    }
                });
            }
        });

        String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        studentEntityList.forEach(o -> {
            //身份证是否合格
            if (!Pattern.matches(REGEX_ID_CARD, o.getIdCard())) {
                status[0] = false;
                msg[0] = "身份证不合格！";
            }
            //是否参加成绩统计和学生类别
            List<String> isJoin = new ArrayList<>();
            isJoin.add("是");
            isJoin.add("否");
            if (null != o.getIsJoin() && !isJoin.contains(o.getIsJoin())) {
                status[0] = false;
                msg[0] = "是否参加成绩统计只能是'是'和'否'";
            }
            List<String> type = new ArrayList<>();
            type.add("H");
            type.add("X");
            if (null != o.getType() && !type.contains(o.getType())) {
                status[0] = false;
                msg[0] = "学生类别只能是'H'或'X'";
            }
        });


        if (!status[0]) {
            result.put("status", false);
            result.put("msg", msg[0]);
            return result;
        }

        schoolRepository.save(schoolEntity);
        if (!teacherMap.isEmpty()) {
            List<TeacherEntity> teacherEntityList = new ArrayList<>();
            teacherMap.forEach((k, v) -> {
                TeacherEntity teacherEntity = teacherRepository.save(k);
                if (null != v && !v.isEmpty()) {
                    v.forEach(o -> {
                        o.setParentId(teacherEntity.getId());
                    });
                    teacherEntityList.addAll(v);
                }
            });
            teacherRepository.saveAll(teacherEntityList);
        }
        if (!studentEntityList.isEmpty()) {
            studentRepository.saveAll(studentEntityList);
        }

        result.put("status", true);
        result.put("msg", "导入成功");
        return result;
    }

    /**
     * 上传Excel,读取Excel中内容
     * @param schoolId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deleteAll",method = RequestMethod.POST)
    public Map<String, Object> batchimport(@RequestParam(value = "schoolId") Integer schoolId) {
        Map<String, Object> result = new LinkedHashMap<>();
        SchoolEntity schoolEntity = schoolRepository.getOne(schoolId);
        if (null == schoolEntity) {
            result.put("status", false);
            result.put("msg", "该学校不存在！");
            return result;
        }
        //删除所有学生
        List<StudentEntity> studentEntityList = studentRepository.findBySchoolId(schoolId);
        if (null != studentEntityList && !studentEntityList.isEmpty()) {
            studentRepository.deleteAll(studentEntityList);
        }
        //删除所有老师
        List<TeacherEntity> teacherEntityList = teacherRepository.findBySchoolId(schoolId);
        if (null != teacherEntityList && !teacherEntityList.isEmpty()) {
            teacherRepository.deleteAll(teacherEntityList);
        }
        //变更学校状态
        schoolEntity.setIsComplete("NO");
        schoolEntity.setGrandNum("");
        schoolEntity.setPrincipal("");
        schoolEntity.setPhone("");
        schoolEntity.setTel("");
        schoolRepository.save(schoolEntity);

        result.put("status", true);
        result.put("msg", "删除成功！");
        return result;
    }

    /**
     * 下载Excel
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downLoad",method = RequestMethod.GET)
    public void downLoad(HttpServletRequest request,HttpServletResponse response,
                                        @RequestParam(value = "schoolId",required = false) Integer schoolId,
                                        @RequestParam(value = "type") String type,
                                        @RequestParam(value = "grade") String grade) throws Exception {
        String fname = "detial" + getTimeStamp();// Excel文件名
        OutputStream os = response.getOutputStream();// 取得输出流
        response.reset();// 清空输出流
        response.setHeader("Content-disposition", "attachment; filename="
                + fname + ".xls"); // 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
        response.setContentType("application/msexcel");

        if (SubjectType.ALL.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张联系方式表
            HSSFSheet contact = wb.createSheet("联系方式");
            HSSFRow row = contact.createRow((short) 0);
            contact.createFreezePane(0, 1);
            schoolCom(wb, contact, row);

            HSSFSheet yjjs = wb.createSheet("阅卷教师信息");
            HSSFRow yjjsRow = yjjs.createRow(0);
            yjjs.createFreezePane(0, 1);
            yjjsCom(grade, wb, yjjs, yjjsRow);


            HSSFSheet ysy = wb.createSheet("学生信息(语数英)");

            HSSFRow ysyRow = ysy.createRow(0);
            ysy.createFreezePane(0, 1);
            ysyCom(wb, ysy, ysyRow);

            HSSFSheet wl = wb.createSheet("学生信息(物理)");
            HSSFRow wlRow = wl.createRow(0);
            wl.createFreezePane(0, 1);
            List<StudentEntity> studentDaoBySubject = studentRepository.findBySubject(SubjectType.WULI);
            createCommon(studentDaoBySubject,wb,wl,wlRow);

            HSSFSheet hx = wb.createSheet("学生信息(化学)");
            HSSFRow hxRow = hx.createRow(0);
            hx.createFreezePane(0, 1);
            List<StudentEntity> studentDaoByHX = studentRepository.findBySubject(SubjectType.HUAXUE);
            createCommon(studentDaoByHX,wb,hx,hxRow);

            HSSFSheet sw = wb.createSheet("学生信息(生物)");
            HSSFRow swRow = sw.createRow(0);
            sw.createFreezePane(0, 1);
            List<StudentEntity> studentDaoBySW = studentRepository.findBySubject(SubjectType.HUAXUE);
            createCommon(studentDaoBySW,wb,sw,swRow);

            HSSFSheet ls = wb.createSheet("学生信息(历史)");
            HSSFRow lsRow = ls.createRow(0);
            ls.createFreezePane(0, 1);
            List<StudentEntity> studentDaoByLS = studentRepository.findBySubject(SubjectType.LISHI);
            createCommon(studentDaoByLS,wb,ls,lsRow);

            HSSFSheet dl = wb.createSheet("学生信息(地理)");
            HSSFRow dlRow = dl.createRow(0);
            dl.createFreezePane(0, 1);
            List<StudentEntity> studentDaoByDL = studentRepository.findBySubject(SubjectType.DILI);
            createCommon(studentDaoByDL,wb,dl,dlRow);

            HSSFSheet zz = wb.createSheet("学生信息(政治)");
            HSSFRow zzRow = zz.createRow(0);
            zz.createFreezePane(0, 1);
            List<StudentEntity> studentDaoByZZ = studentRepository.findBySubject(SubjectType.ZHENGZHI);
            createCommon(studentDaoByZZ,wb,zz,zzRow);
            wb.write(os);
            os.flush();
            os.close();
            System.out.println("文件生成");

        }else if (SubjectType.CONTACT.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张联系方式
            HSSFSheet sheet = wb.createSheet("联系方式");
            HSSFRow row = sheet.createRow((short) 0);
            sheet.createFreezePane(0, 1);
            cteateCell(wb, row, (short) 0, "学校名称");
            cteateCell(wb, row, (short) 1, "班级数");
            cteateCell(wb, row, (short) 2, "填报负责人姓名");
            cteateCell(wb, row, (short) 3, "联系电话");
            cteateCell(wb, row, (short) 4, "手机号码");
            Optional<SchoolEntity> schoolDaoById = schoolRepository.findById(schoolId);
            if (schoolDaoById.isPresent()){
                SchoolEntity schoolEntity = schoolDaoById.get();
                int i = 0;
                HSSFRow rowi = sheet.createRow((short) (++i));
                for (int j = 0; j < 4; j++) {
                    cteateCell(wb, rowi, (short) 0, schoolEntity.getName());
                    cteateCell(wb, rowi, (short) 1, schoolEntity.getGrandNum());
                    cteateCell(wb, rowi, (short) 2, schoolEntity.getPrincipal());
                    cteateCell(wb, rowi, (short) 3, schoolEntity.getTel());
                    cteateCell(wb, rowi, (short) 4, schoolEntity.getPhone());
                }
            }
            wb.write(os);
            os.flush();
            os.close();
            System.out.println("文件生成");
        }else if (SubjectType.YSY.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张学生信息表
            HSSFSheet sheet = wb.createSheet("学生信息(语数英)");
            HSSFRow row = sheet.createRow((short) 0);
            sheet.createFreezePane(0, 1);
            cteateCell(wb, row, (short) 0, "姓名");
            cteateCell(wb, row, (short) 1, "班级");
            cteateCell(wb, row, (short) 2, "身份证号(18位)");
            cteateCell(wb, row, (short) 3, "考场号");
            cteateCell(wb, row, (short) 4, "座位号");
            cteateCell(wb, row, (short) 5, "是否参加成绩统计");
            cteateCell(wb, row, (short) 6, "学校");

            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.YSY);
            if (null!=studentEntities&&studentEntities.size()>0){
                int i = 0;
                Iterator<StudentEntity> studentEntityIterator = studentEntities.iterator();
                while (studentEntityIterator.hasNext()){
                    HSSFRow rowi = sheet.createRow((short) (++i));
                    StudentEntity studentEntity = studentEntityIterator.next();
                    for (int j = 0; j < 6; j++) {
                        cteateCell(wb, rowi, (short) 0, studentEntity.getName());
                        cteateCell(wb, rowi, (short) 1, studentEntity.getGrade());
                        cteateCell(wb, rowi, (short) 2, studentEntity.getIdCard());
                        cteateCell(wb, rowi, (short) 3, studentEntity.getExamRoomNum());
                        cteateCell(wb, rowi, (short) 4, studentEntity.getSeatNum());
                        cteateCell(wb, rowi, (short) 5, studentEntity.getIsJoin());
                        SchoolEntity schoolDaoOne = schoolRepository.getOne(studentEntity.getSchoolId());
                        cteateCell(wb, rowi, (short) 6, schoolDaoOne.getName());

                    }
                }
            }
            wb.write(os);
            os.flush();
            os.close();
            System.out.println("文件生成");
        }else if (SubjectType.WULI.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张工作表
            HSSFSheet sheet = wb.createSheet("学生信息(物理)");
            HSSFRow row = sheet.createRow((short) 0);

            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.WULI);
            createFixationSheet(os, studentEntities,wb,sheet,row);
        }else if (SubjectType.SHENGWU.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张工作表
            HSSFSheet sheet = wb.createSheet("学生信息(生物)");
            HSSFRow row = sheet.createRow((short) 0);

            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.SHENGWU);

            createFixationSheet(os, studentEntities,wb,sheet,row);
        }else if (SubjectType.HUAXUE.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张工作表
            HSSFSheet sheet = wb.createSheet("学生信息(化学)");
            HSSFRow row = sheet.createRow((short) 0);

            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.HUAXUE);

            createFixationSheet(os, studentEntities,wb,sheet,row);
        }else if (SubjectType.LISHI.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张工作表
            HSSFSheet sheet = wb.createSheet("学生信息(历史)");
            HSSFRow row = sheet.createRow((short) 0);

            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.LISHI);

            createFixationSheet(os, studentEntities,wb,sheet,row);
        }else if (SubjectType.DILI.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张工作表
            HSSFSheet sheet = wb.createSheet("学生信息(地理)");
            HSSFRow row = sheet.createRow((short) 0);

            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.DILI);

            createFixationSheet(os, studentEntities,wb,sheet,row);
        }else if (SubjectType.ZHENGZHI.equals(type)){
            // 创建工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在工作薄上建一张工作表
            HSSFSheet sheet = wb.createSheet("学生信息(政治)");
            HSSFRow row = sheet.createRow((short) 0);
            List<StudentEntity> studentEntities =  studentRepository.findBySchoolIdAndSchoolTypeAndSubject(schoolId,grade,SubjectType.ZHENGZHI);

            createFixationSheet(os, studentEntities,wb,sheet,row);
        }
    }

    private void ysyCom(HSSFWorkbook wb, HSSFSheet ysy, HSSFRow ysyRow) {
        cteateCell(wb, ysyRow, (short) 0, "姓名");
        cteateCell(wb, ysyRow, (short) 1, "班级");
        cteateCell(wb, ysyRow, (short) 2, "身份证号(18位)");
        cteateCell(wb, ysyRow, (short) 3, "考场号");
        cteateCell(wb, ysyRow, (short) 4, "座位号");
        cteateCell(wb, ysyRow, (short) 5, "是否参加成绩统计");
        cteateCell(wb, ysyRow, (short) 6, "学校");
        List<StudentEntity> studentEntities =  studentRepository.findBySubject(SubjectType.YSY);
        if (null!=studentEntities&&studentEntities.size()>0){
            int i = 0;
            Iterator<StudentEntity> studentEntityIterator = studentEntities.iterator();
            while (studentEntityIterator.hasNext()){
                HSSFRow rowi = ysy.createRow((short) (++i));
                StudentEntity studentEntity = studentEntityIterator.next();
                for (int j = 0; j < 6; j++) {
                    cteateCell(wb, rowi, (short) 0, studentEntity.getName());
                    cteateCell(wb, rowi, (short) 1, studentEntity.getGrade());
                    cteateCell(wb, rowi, (short) 2, studentEntity.getIdCard());
                    cteateCell(wb, rowi, (short) 3, studentEntity.getExamRoomNum());
                    cteateCell(wb, rowi, (short) 4, studentEntity.getSeatNum());
                    cteateCell(wb, rowi, (short) 5, studentEntity.getIsJoin());
                    SchoolEntity schoolDaoOne = schoolRepository.getOne(studentEntity.getSchoolId());
                    cteateCell(wb, rowi, (short) 6, schoolDaoOne.getName());
                }
            }
        }
    }

    private void yjjsCom(@RequestParam(value = "grade") String grade, HSSFWorkbook wb, HSSFSheet yjjs, HSSFRow yjjsRow) {
        cteateCell(wb, yjjsRow, (short) 0, "科目");
        cteateCell(wb, yjjsRow, (short) 1, "类别");
        cteateCell(wb, yjjsRow, (short) 2, "姓名");
        cteateCell(wb, yjjsRow, (short) 3, "学校");
        List<TeacherEntity> teacherDaoAll = teacherRepository.findBySchoolType(grade);
        if (null!=teacherDaoAll&&teacherDaoAll.size()>0){
            int i = 0;
            Iterator<TeacherEntity> iterator = teacherDaoAll.iterator();
            while (iterator.hasNext()){
                HSSFRow rowi = yjjs.createRow((short) (++i));
                TeacherEntity teacherEntity = iterator.next();
                for (int j = 0; j < 4; j++) {
                    cteateCell(wb, rowi, (short) 0, teacherEntity.getSubject());
                    cteateCell(wb, rowi, (short) 1, teacherEntity.getType());
                    cteateCell(wb, rowi, (short) 2, teacherEntity.getName());
                    SchoolEntity schoolDaoOne = schoolRepository.getOne(teacherEntity.getSchoolId());
                    cteateCell(wb, rowi, (short) 3, schoolDaoOne.getName());
                }
            }
        }
    }

    private void schoolCom(HSSFWorkbook wb, HSSFSheet contact, HSSFRow row) {
        cteateCell(wb, row, (short) 0, "学校名称");
        cteateCell(wb, row, (short) 1, "班级数");
        cteateCell(wb, row, (short) 2, "填报负责人姓名");
        cteateCell(wb, row, (short) 3, "联系电话");
        cteateCell(wb, row, (short) 4, "手机号码");
        List<SchoolEntity> schoolDaoAll = schoolRepository.findAll();
        if (null!=schoolDaoAll&&schoolDaoAll.size()>0){
            int i = 0;
            Iterator<SchoolEntity> iterator = schoolDaoAll.iterator();
            while (iterator.hasNext()){
                HSSFRow rowi = contact.createRow((short) (++i));
                SchoolEntity next = iterator.next();
                for (int j = 0; j < 4; j++) {
                    cteateCell(wb, rowi, (short) 0, next.getName());
                    cteateCell(wb, rowi, (short) 1, next.getGrandNum());
                    cteateCell(wb, rowi, (short) 2, next.getPrincipal());
                    cteateCell(wb, rowi, (short) 3, next.getTel());
                    cteateCell(wb, rowi, (short) 4, next.getPhone());
                }
            }
        }
    }

    public void createFixationSheet(OutputStream os,
                                    List<StudentEntity> student,HSSFWorkbook wb, HSSFSheet sheet,HSSFRow row  ) throws Exception {


        createCommon(student, wb, sheet, row);
        wb.write(os);
        os.flush();
        os.close();
        System.out.println("文件生成");

    }

    private void createCommon(List<StudentEntity> student, HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row) {
        sheet.createFreezePane(0, 1);
        cteateCell(wb, row, (short) 0, "姓名");
        cteateCell(wb, row, (short) 1, "班级");
        cteateCell(wb, row, (short) 2, "身份证号(18位)");
        cteateCell(wb, row, (short) 3, "考场号");
        cteateCell(wb, row, (short) 4, "座位号");
        cteateCell(wb, row, (short) 5, "备注学生类别(H 或 X)");
        int i = 0;
        Iterator<StudentEntity> iterator = student.iterator();
        while (iterator.hasNext()) {
            HSSFRow rowi = sheet.createRow((short) (++i));
            StudentEntity studentEntity = iterator.next();
            for (int j = 0; j < 5; j++) {
                cteateCell(wb, rowi, (short) 0, studentEntity.getName());
                cteateCell(wb, rowi, (short) 1, studentEntity.getGrade());
                cteateCell(wb, rowi, (short) 2, studentEntity.getIdCard());
                cteateCell(wb, rowi, (short) 3, studentEntity.getExamRoomNum());
                cteateCell(wb, rowi, (short) 4, studentEntity.getSeatNum());
                cteateCell(wb, rowi, (short) 5, studentEntity.getType());
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void cteateCell(HSSFWorkbook wb, HSSFRow row, short col,
                            String val) {
        HSSFCell cell = row.createCell(col);
        cell.setCellValue(val);
//        HSSFCellStyle cellstyle = wb.createCellStyle();
//        cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//        cell.setCellStyle(cellstyle);
    }
    /**
     * 该方法用来产生一个时间字符串（即：时间戳）
     *
     * @return
     */
    public static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:MM:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}



