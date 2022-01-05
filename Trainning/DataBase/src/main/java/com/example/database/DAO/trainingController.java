package com.example.database.DAO;

import com.example.database.Entity.course;
import com.example.database.Entity.exam;
import com.example.database.Entity.log;
import com.example.database.Entity.staff;
import com.example.database.Repository.courseRepository;
import com.example.database.Repository.examRepository;
import com.example.database.Repository.logRepository;
import com.example.database.Repository.staffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/training")

public class trainingController {
    private staffRepository staffRepository;
    private courseRepository courseRepository;
    private logRepository logRepository;
    private examRepository examRepository;

    public trainingController(com.example.database.Repository.staffRepository staffRepository, com.example.database.Repository.courseRepository courseRepository, com.example.database.Repository.logRepository logRepository, com.example.database.Repository.examRepository examRepository) {
        this.staffRepository = staffRepository;
        this.courseRepository = courseRepository;
        this.logRepository = logRepository;
        this.examRepository = examRepository;
    }

    //普通
//    @PostMapping("/staff")
//    @ResponseStatus(HttpStatus.CREATED)
//    public staff uploadStaff(@RequestBody DTO dto) {
//        return staffRepository.uploadStaff(dto);
//    }

    @PostMapping(value="/login",produces="application/json")
    @CrossOrigin
//    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
      public List<staff> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        System.out.println(username+"  "+password);
        DTO dto =new DTO(username);//username相当于员工id
        return staffRepository.selectStaffs(dto);
    }


    @PostMapping (value="/SetGrade",produces = "application/json")
    @CrossOrigin
    //@ResponseStatus(HttpStatus.OK)
    public String modifyExam(@RequestParam(value = "staff_id") String staff_id,@RequestParam(value = "grade") int grade,@RequestParam(value = "course_id") String course_id) throws Exception {
        DTO dto=new DTO();
        dto.setIs_endClass("否");
        dto.setStaff_id(staff_id);
        dto.setGrade(grade);
        dto.setCourse_id(course_id);
        examRepository.modifyExam(dto);
        return "OK";
    }

//    @PostMapping("/login")
//        @ResponseStatus(HttpStatus.OK)
//        public String uploadStaff(@RequestBody HashMap<String, String> map) {
//        System.out.println(map.toString());
//        return map.toString();
//        }

    //教员和主管
//    @PostMapping("/staff")
//    @ResponseStatus(HttpStatus.CREATED)
//    public staff uploadSpecialStaff(@RequestBody DTO dto) {
//        return staffRepository.uploadSpecialStaff(dto);
//    }
//
//    @GetMapping("/staff")
//    @ResponseStatus(HttpStatus.OK)
//    public List<staff> selectStaffs(@RequestBody DTO dto) {
//        return staffRepository.selectStaffs(dto);
//    }
//
//    @GetMapping("/staff")
//    @ResponseStatus(HttpStatus.OK)
//    public staff modifyStaff(@RequestBody DTO dto) {
//        return staffRepository.modifyStaff(dto);
//    }
//
//    @GetMapping("/staff")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteStaff(@RequestBody DTO dto) {
//        staffRepository.deleteStaff(dto);
//    }
//
//    //加新课,还涉及自动分配
//    @PostMapping("/staff")
//    @ResponseStatus(HttpStatus.CREATED)
//    public course uploadCourse(@RequestBody DTO dto) {
//        return courseRepository.uploadCourse(dto);
//    }
//
//    //删除课
//    @GetMapping("/staff")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteCourse(@RequestBody DTO dto) {
//        courseRepository.deleteCourse(dto);
//    }
//

    //教员查看自己开设的课程
    @GetMapping("/teacherCourse/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<course> selectCourses(@PathVariable(value = "teacher_id") String teacher_id) {
        DTO dto=new DTO(teacher_id);//教师根据自己的id查找自己开设的课程（到了dto对象里staff_id=teacher_id）
        return courseRepository.selectCourses(dto);
    }

    @GetMapping("/getAllStaff/{department}")//管理员取得所有员工信息
    @ResponseStatus(HttpStatus.OK)
    public List<staff> getAllStaff(@PathVariable(value = "department") String department) {
        DTO dto=new DTO();
        dto.setDepartment_name(department);
        return staffRepository.selectStaffsByDepartment(dto);
    }

    @GetMapping("/getAllCourse/{department}")//管理员取得部门下所有课程
    @ResponseStatus(HttpStatus.OK)
    public List<course> getAllCourse(@PathVariable(value = "department") String department) {
        DTO dto=new DTO();
        dto.setDepartment_name(department);
        return courseRepository.getAllCourses(dto);
    }

    @GetMapping("/getStaffGrade/{staff_id}/{course_id}/{pass}")//管理员查看员工成绩
    @ResponseStatus(HttpStatus.OK)
    public List<exam> getStaffGrade(@PathVariable(value = "staff_id") String staff_id,@PathVariable(value = "course_id") String course_id,@PathVariable(value = "pass") String pass) {
        DTO dto=new DTO();
        dto.setStaff_id(staff_id);
        if(!Objects.equals(course_id, "")){
           dto.setCourse_id(course_id);
        }
        if(!Objects.equals(pass, "")){
            dto.setPass(pass);
        }
        return examRepository.selectExams(dto);
    }

//
//    //改课
//    @GetMapping("/staff")
//    @ResponseStatus(HttpStatus.OK)
//    public course modifyCourse(@RequestBody DTO dto) {
//        return courseRepository.modifyCourse(dto);
//    }
//
//    //为考试（课程）分配staff
//    @PostMapping("/staff")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void uploadExamByManager(@RequestBody DTO dto) {
//        examRepository.uploadExamByManager(dto);
//    }
//
//    //为课程/考试分配教师在uploadSpecialStaff中自动实现
//
//
    //修改考试成绩

//
//
    //查看一门课的学生（考生）,看自己的课，看教的课
    @GetMapping("/getOnClass/{staff_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<exam> getclass(@PathVariable(value = "staff_id") String staff_id) {
        DTO dto=new DTO(staff_id);
        return examRepository.selectOnClass(dto);
    }

    @GetMapping("/getClassHistory/{staff_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<exam> getclasshistory(@PathVariable(value = "staff_id") String staff_id) {
        DTO dto=new DTO(staff_id);
        return examRepository.selectClassHistory(dto);
    }

//    @GetMapping("/getEndClass/{staff_id}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<exam> getEndClass(@PathVariable(value = "staff_id") String staff_id) {
//        DTO dto=new DTO(staff_id);
//        return examRepository.selectExams(dto);
//    }
//
//
//    //查看日志
//    @GetMapping("/staff")
//    @ResponseStatus(HttpStatus.OK)
//    public List<log> selectLogs(@RequestBody DTO dto) {
//        return logRepository.selectLogs(dto);
//    }

}
