package com.service.unischeduleservice.sevice;

import com.service.unischeduleservice.constant.DateTimeConstant;
import com.service.unischeduleservice.dto.resposes.DataAppResponseDTO;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.model.CourseModel;
import com.service.unischeduleservice.model.MeetingModel;
import com.service.unischeduleservice.model.ScoreModel;
import com.service.unischeduleservice.model.SemesterModel;
import com.service.unischeduleservice.utils.date_time.MyDateTime;
import com.service.unischeduleservice.dto.requests.DataAppRequestDTO;
import com.service.unischeduleservice.utils.jsoup.ProcessCaptcha;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.jsoup.Jsoup.*;

@Service
public class ScraperServiceImpl implements ScraperService{

    @Value("${url.tuition}")
    private String tuitionUrl;
    @Value("${url.schedule}")
    private String scheduleUrl;
    @Value("${url.test_schedule}")
    private String testScheduleUrl;
    @Value("${url.score_semester}")
    private String scoreSemesterUrl;
    @Value("${url.all_semester}")
    private String allSemesterUrl;

    @Value("${lesson.firstPeriod}")
    private String firstPeriod;
    @Value("${lesson.secondPeriod}")
    private String secondPeriod;
    @Value("${lesson.thirdPeriod}")
    private String thirdPeriod;
    @Value("${lesson.fourthPeriod}")
    private String fourthPeriod;
    @Value("${lesson.fifthPeriod}")
    private String fifthPeriod;
    @Value("${lesson.sixthPeriod}")
    private String sixthPeriod;
    @Value("${lesson.seventhPeriod}")
    private String seventhPeriod;
    @Value("${lesson.eighthPeriod}")
    private String eighthPeriod;
    @Value("${lesson.ninthPeriod}")
    private String ninthPeriod;
    @Value("${lesson.tenthPeriod}")
    private String tenthPeriod;
    @Value("${lesson.eleventhPeriod}")
    private String eleventhPeriod;
    @Value("${lesson.twelfthPeriod}")
    private String twelfthPeriod;
    @Value("${lesson.thirteenthPeriod}")
    private String thirteenthPeriod;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    private Map<String, String> cookies = Map.of();

    @SneakyThrows
    @Override
    public List<String> getSemesterList() {
        Document document = connect("https://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu").get();

        if(document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblquenmk").text()
                .equals("XÁC THỰC ĐĂNG NHẬP WEBSITE ĐĂNG KÝ MÔN HỌC")) {
            cookies = ProcessCaptcha.process("https://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu");
            document = connect(allSemesterUrl)
                    .timeout(1000*45)
                    .cookies(cookies)
                    .get();
        } else {
            document = connect(allSemesterUrl).timeout(45*1000).get();
        }

        if(document.getElementById("ctl00_ContentPlaceHolder1_ctl00_ddlChonNHHK") == null){
            throw new ResourceNotFoundException("List semester is empty!");
        }

        Elements elements = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_ddlChonNHHK").children();
        List<String> semesterList = new ArrayList<>();
        for (Element element: elements) {
            semesterList.add(element.text());
        }
        return semesterList;
    }

    @Override
    public DataAppResponseDTO scrappingData(DataAppRequestDTO request) throws InterruptedException {
        return getDataUser(request);
    }

    @SneakyThrows
    private DataAppResponseDTO getDataUser(DataAppRequestDTO request) throws InterruptedException {
        // SchedulePage, get document of page to check exist of captcha
        Document document = connect("https://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu")
                .timeout(1000*45).get();
        // Case exist captcha
        if(document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblquenmk").text()
                .equals("XÁC THỰC ĐĂNG NHẬP WEBSITE ĐĂNG KÝ MÔN HỌC")) {
            // Assign cookie
            cookies = ProcessCaptcha.process("https://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu");
            document = connect(scheduleUrl + request.getUserId()).timeout(1000*45).cookies(cookies).get();
        }
        // Case not exist captcha
        else {
            document = connect(scheduleUrl + request.getUserId()).timeout(1000*45).get();
        }

        if(document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentMaSV") == null){
            throw new ResourceNotFoundException("Not found userId = " + request.getUserId());
        }

        boolean isStudent = request.getUserId().matches(".*[a-zA-Z].*");
        return isStudent
                ? getDataTeacher(document, request.getSemester())
                : getDataStudent(document, request.getSemester());
    }

    private DataAppResponseDTO getDataTeacher(Document document, String semester) throws InterruptedException {
        // Get all information of teacher
        String teacherId = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentMaSV").text().trim();
        String teacherName = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV").text().trim();
        String dateStartSemester = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblNote").text().trim();

        DataAppResponseDTO dataAppResponseDTO = new DataAppResponseDTO();
        dataAppResponseDTO.setUserId(teacherId);
        dataAppResponseDTO.setIsStudent("0");
        dataAppResponseDTO.setUserName(teacherName);
        dataAppResponseDTO.setCurrentSemester(semester);
        dateStartSemester = dateStartSemester.substring(
                dateStartSemester.lastIndexOf(")") - 10, dateStartSemester.length() - 1).trim();
        dataAppResponseDTO.setDateStartSemester(dateStartSemester);

        // DownLatch for virtual thread
//        CountDownLatch downLatch = new CountDownLatch(1);

        // to get all data course(test, meeting)
//        executorService.submit(() -> {
//            scrapeDataCourse(dataAppResponseDTO, false, semester, dataAppResponseDTO, downLatch);
//        });
//        downLatch.await();
        scrapeDataCourse(dataAppResponseDTO, false, semester, dataAppResponseDTO);


        return dataAppResponseDTO;
    }

    protected DataAppResponseDTO getDataStudent(Document document, String semester) throws InterruptedException {
        //Get information of student
        String userId = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentMaSV").text().trim();
        String nameAndDateOfBirth = document
                .getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV").text().trim();

        // Get all information of student
        String userName = nameAndDateOfBirth.substring(0, nameAndDateOfBirth.indexOf("-")).trim();
        String dateOfBirth = nameAndDateOfBirth.substring(nameAndDateOfBirth.indexOf(":") + 1).trim();
        String classAndDepartmentAndSpecialized = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentLopSV").text().trim();
        String classOfUser = classAndDepartmentAndSpecialized.substring(0, classAndDepartmentAndSpecialized.indexOf("-")).trim();
        String department = classAndDepartmentAndSpecialized.substring(classAndDepartmentAndSpecialized.lastIndexOf(":") + 1).trim();
        String specialized = classAndDepartmentAndSpecialized.substring(
                classAndDepartmentAndSpecialized.indexOf(":") + 1, classAndDepartmentAndSpecialized.lastIndexOf("-")).trim();
        String dateStartSemester = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblNote").text().trim();
        dateStartSemester = dateStartSemester.substring(dateStartSemester.lastIndexOf(")") - 10, dateStartSemester.length() - 1).trim();

        DataAppResponseDTO dataAppResponseDTO = DataAppResponseDTO.builder()
                .userId(userId)
                .userName(userName)
                .dateOfBirth(dateOfBirth)
                .classOfUser(classOfUser)
                .department(department)
                .specialized(specialized)
                .currentSemester(semester)
                .dateStartSemester(dateStartSemester)
                .isStudent("1")
                .build();

        CountDownLatch downLatch = new CountDownLatch(3);

        // Tuition page, get tuition data of student
        executorService.submit(() -> {
            scrapeDataTuition(dataAppResponseDTO, semester, downLatch);
        });

        // Score page, get score data of student
        executorService.submit(() -> {
            scrapeDataScore(dataAppResponseDTO, downLatch);
        });

        executorService.submit(() -> {
            getDataSemesterScore(dataAppResponseDTO, dataAppResponseDTO, downLatch);
        });

        // Schedule Page, get all data meeting
//        executorService.submit(() -> {
//            scrapeDataCourse(dataAppResponseDTO, true, semester, dataAppResponseDTO, downLatch);
//        });
        scrapeDataCourse(dataAppResponseDTO, true, semester, dataAppResponseDTO);

        downLatch.await();
        return dataAppResponseDTO;
    }

    void scrapeDataScore(DataAppResponseDTO dataAppResponseDTO, CountDownLatch downLatch) {
        try {
            Document document;
            try {
                document = connect(scoreSemesterUrl + dataAppResponseDTO.getUserId())
                        .cookies(cookies).get();
            } catch (IOException e) {
                throw new RuntimeException("Score unService!");
            }
            Elements elements = document.getElementsByClass("row-diemTK");
            String totalCredit = elements.get(elements.size() - 2).text();
            totalCredit = totalCredit.substring(totalCredit.indexOf(":") + 1).trim();
            String gpa = elements.get(elements.size() - 4).text();
            gpa = gpa.substring(gpa.indexOf(":") + 1).trim();
            dataAppResponseDTO.setTotalCredit(totalCredit);
            dataAppResponseDTO.setGpa(gpa);
        } finally {
            downLatch.countDown();
        }
    }

    void scrapeDataTuition(DataAppResponseDTO dataAppResponseDTO, String semester, CountDownLatch downLatch) {
        try {
            Document document;
            try {
                document = connect(tuitionUrl + dataAppResponseDTO.getUserId())
                        .cookies(cookies).get();
            } catch (IOException e) {
                throw new RuntimeException("Tuition unService!");
            }

            Elements elementsTuition = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_pnlTongKetHocPhiCacHocKy")
                    .firstElementChild().firstElementChild().children();

            for(int i=0; i<elementsTuition.size(); i++) {
                if(elementsTuition.get(i).text().trim().contains(semester)) {
                    // Data tuitionFee current semester
                    if(elementsTuition.get(i + 1) != null) {
                        String tuition = elementsTuition.get(i + 1).text();
                        tuition = tuition.substring(tuition.indexOf(":") + 1).replaceAll("\\s+", "");
                        dataAppResponseDTO.setTuitionFee(tuition);
                    }
                    // get data OldSemesterTuitionDebt
                    if(elementsTuition.get(i + 2) != null) {
                        String tuition = elementsTuition.get(i + 2).text();
                        tuition = tuition.substring(tuition.indexOf(":") + 1).replaceAll("\\s+", "");
                        dataAppResponseDTO.setOldSemesterTuitionDebt(tuition);
                    }
                    // get data PaidTuitionFee
                    if(elementsTuition.get(i + 3) != null) {
                        String tuition = elementsTuition.get(i + 3).firstElementChild().text();
                        tuition = tuition.substring(tuition.indexOf(":") + 1).replaceAll("\\s+", "");
                        dataAppResponseDTO.setPaidTuitionFee(tuition);
                    }
                    break;
                }
            }
        } finally {
            downLatch.countDown();
        }
    }

    @SneakyThrows
    void scrapeDataCourse(DataAppResponseDTO user, boolean isStudent, String semester, DataAppResponseDTO dataAppResponseDTO){
        try {
            List<CourseModel> courseModelList = new ArrayList<>();

            // Meeting
            Document document;
            try {
                document = connect(scheduleUrl + user.getUserId())
                        .cookies(cookies).get();
            } catch (IOException e) {
                throw new RuntimeException("Schedule unService!");
            }
            // choose semester in dao tao page to read data meeting
            document = selectSemester(document, scheduleUrl + user.getUserId(), semester);
            /// CPU-bound
            Elements elementsTable = document.getElementsByClass("grid-roll2").first().children();
            CourseModel courseModel;
            for(Element elementTable: elementsTable){
                Elements elementsTd = elementTable.child(0).child(0).children();

                courseModel = CourseModel.builder()
                        .courseId(elementsTd.get(0).text().trim())
                        .courseName(elementsTd.get(1).text().trim())
                        .groupCode(elementsTd.get(2).text().trim())
                        .credit(elementsTd.get(3).text().trim())
                        .classCode(elementsTd.get(4).text().trim())
                        .build();

                //Get list practice Team
                int size = elementsTd.get(7).children().size();
                List<String> practiceTeamList = new ArrayList<>();
                for(int i=0; i<size; i++){
                    String practiceTeam = elementsTd.get(7).children().get(i).text();
                    practiceTeamList.add(practiceTeam);
                }
                List<String> day = List.of(elementsTd.get(8).text().split(" "));
                List<String> startSlot = List.of(elementsTd.get(9).text().split(" "));
                List<String> sumSlot = List.of(elementsTd.get(10).text().split(" "));
                List<String> room = List.of(elementsTd.get(11).text().split(" "));
                List<String> time = List.of(elementsTd.get(13).text().split(" "));

                List<MeetingModel> meetingList = new ArrayList<>();
                MeetingModel meeting;
                for(byte i=0; i<time.size(); i++){
                    List<Byte> listWeek = formatWeek(time.get(i));
                    for (Byte week : listWeek) {
                        try {
                            meeting = MeetingModel.builder()
                                    .startEndTime(
                                            dateTimeFormat(
                                                    formatDay(day.get(i)),
                                                    week,
                                                    MyDateTime.convertStringToDate(
                                                            user.getDateStartSemester(),
                                                            DateTimeConstant.DATE_FORMAT
                                                    ),
                                                    startSlot.get(i),
                                                    sumSlot.get(i)
                                            )
                                    )
                                    .practiceTeam(practiceTeamList.get(i))
                                    .lesson(startSlot.get(i) + "," + sumSlot.get(i))
                                    .roomName(room.get(i))
                                    .courseModel(courseModel)
                                    .week(time.get(i))
                                    .currentWeek(week.toString())
                                    .build();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        meetingList.add(meeting);
                    }
                }
                courseModel.setMeetingList(meetingList);
                courseModelList.add(courseModel);
            }

            // if is not student stop scrap TestSchedule data
            if (!isStudent) {
                dataAppResponseDTO.setCourseModelList(courseModelList);
                return;
            }

            // The student is doing a thesis and has no exam schedule
            String currentSemester = "";
            try {
                document = connect(testScheduleUrl + user.getUserId())
                        .cookies(cookies).get();
            } catch (IOException e) {
                throw new RuntimeException("TestSchedule unService!");
            }

            currentSemester = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_dropNHHK")
                    .child(0).text().trim();
            if(currentSemester.equals(user.getCurrentSemester()) && document.getElementById("ctl00_ContentPlaceHolder1_ctl00_gvXem") != null){
                Elements elementsTr = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_gvXem")
                        .child(0).children();
                for(int i=1; i<elementsTr.size(); i++){
                    Elements elementsTd = elementsTr.get(i).children();
                    String courseCode = elementsTd.get(1).text().trim();
                    for (CourseModel courseModelDTO : courseModelList) {
                        if (courseCode.equals(courseModelDTO.getCourseId())) {
                            courseModelDTO.setTestRoom(elementsTd.get(9).text().trim());
                            String startTime = elementsTd.get(6).text().trim() + " " +
                                    timeFormat(
                                            elementsTd.get(7).text().trim(),
                                            elementsTd.get(8).text().trim()
                                    ).get(0);
                            String endTime = elementsTd.get(6).text().trim() + " " +
                                    timeFormat(
                                            elementsTd.get(7).text().trim(),
                                            elementsTd.get(8).text().trim()
                                    ).get(1);
                            courseModelDTO.setLesson(elementsTd.get(7).text().trim() + "," + elementsTd.get(8).text().trim());
                            courseModelDTO.setTestStartDateTime(startTime);
                            courseModelDTO.setTestEndDateTime(endTime);
                        }
                    }
                }
            }

            // Tuition of course
            try {
                document = connect(tuitionUrl + user.getUserId())
                        .cookies(cookies).get();
            } catch (IOException e) {
                throw new RuntimeException("Tuition unService!");
            }

            currentSemester = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblNHHKOnline").text().trim();

            if(currentSemester.equals(user.getCurrentSemester()) && document.getElementById("ctl00_ContentPlaceHolder1_ctl00_gvHocPhi") != null){
                Elements elementsTr = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_gvHocPhi")
                        .child(0).children();
                for(int i=1; i<elementsTr.size(); i++){
                    Elements elementsTd = elementsTr.get(i).children();
                    String courseCode = elementsTd.get(1).text().trim();
                    for(CourseModel courseModelDTO : courseModelList){
                        if(courseCode.equals(courseModelDTO.getCourseId())){
                            String tuition = elementsTd.get(7).text().trim();
                            tuition = tuition.replaceAll("\\s+", "");
                            courseModelDTO.setTuitionFee(tuition);
                        }
                    }
                }
            }
            dataAppResponseDTO.setCourseModelList(courseModelList);
        } catch (Exception e) {
            e.printStackTrace();  // Hoặc xử lý lỗi một cách thích hợp
        } finally {
//            downLatch.countDown();
        }
    }

    private void getDataSemesterScore(DataAppResponseDTO user, DataAppResponseDTO dataAppResponseDTO, CountDownLatch downLatch) {
        try {
            List<SemesterModel> semesterModelList = new ArrayList<>();
            SemesterModel semesterModel;

            Document document;
            try {
                document = connect(scoreSemesterUrl + user.getUserId())
                        .cookies(cookies).get();
            } catch (IOException e) {
                throw new RuntimeException("Schedule unService!");
            }

            Elements elementTable = document.getElementsByClass("view-table").first().child(0).children();

            for(int i=0; i<elementTable.size(); i++){
                if(elementTable.get(i).hasClass("title-hk-diem")){
                    semesterModel = new SemesterModel();
                    semesterModel.setSemesterName(elementTable.get(i).child(0).text().trim());
                    List<ScoreModel> scoreModelList = new ArrayList<>();
                    for(int j=i+1; j<elementTable.size(); j++){
                        if(elementTable.get(j).hasClass("title-hk-diem")) {
                            i = j-1;
                            break;
                        } else {
                            if(elementTable.get(j).hasClass("row-diem")){
                                ScoreModel scoreModel = new ScoreModel();
                                if(!elementTable.get(j).child(3).text().trim().equals("0")){
                                    scoreModel.setScoreName(elementTable.get(j).child(2).text().trim());
                                    scoreModel.setCredit(elementTable.get(j).child(3).text().trim());
                                    if(!elementTable.get(j).child(10).text().trim().isEmpty()){
                                        scoreModel.setScore(elementTable.get(j).child(10).text().trim());
                                        scoreModel.setGpa(elementTable.get(j).child(12).text().trim());
                                    }
                                    scoreModelList.add(scoreModel);
                                }
                            }else {
                                String inf = elementTable.get(j).child(0).text();
                                String gpaOfTotalCredit = inf.substring(inf.indexOf(":") + 1).trim();
                                if(inf.contains("Điểm trung bình học kỳ hệ 4:")){
                                    semesterModel.setGpa(gpaOfTotalCredit);
                                } else if(inf.contains("Số tín chỉ đạt:")){
                                    semesterModel.setTotalCredit(gpaOfTotalCredit);
                                }
                            }
                        }
                    }
                    semesterModel.setScoreModelList(scoreModelList);
                    semesterModel.setUser(user);
                    semesterModelList.add(semesterModel);
                }
            }
            dataAppResponseDTO.setSemesterModelList(semesterModelList);
        } finally {
            downLatch.countDown();
        }
    }

    @SneakyThrows
    Document selectSemester(Document document,String url, String semester) {
        Element elementDropdownSelectSemester = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_ddlChonNHHK");
        if(elementDropdownSelectSemester == null) {
            return null;
        }
        // generate GET request __VIEWSTATE and __VIEWSTATEGENERATOR
        Connection.Response response = connect(url)
                .cookies(cookies)
                .method(Connection.Method.GET)
                .execute();
        Document documentCookies = response.parse();
        String viewState = documentCookies.select("input[name=__VIEWSTATE]").attr("value");
        String viewStateGenerator = documentCookies.select("input[name=__VIEWSTATEGENERATOR]").attr("value");

        String valueOfSelect = "";
        //get option list semester
        Elements options = elementDropdownSelectSemester.select("option");
        for(Element option: options) {
            if(option.text().trim().equals(semester.trim())) {
                valueOfSelect = option.attr("value");
            }
        }

        // Select the semester again to get data
        return connect(url)
                .timeout(30000)
                .cookies(cookies)
                .data("__VIEWSTATE", viewState)
                .data("__VIEWSTATEGENERATOR", viewStateGenerator)
                .data("ctl00$ContentPlaceHolder1$ctl00$ddlChonNHHK", valueOfSelect)
                .post();

    }

    private List<Byte> formatWeek(String num){
        List<Byte> integerList = new ArrayList<>();
        for(byte i=0; i<num.length(); i++){
            if(num.charAt(i) == '-')
                continue;
            byte temp = (byte) (i+1);
            integerList.add(temp);
        }
        return integerList;
    }

    private Byte formatDay(String day){
        return switch (day) {
            case "Hai" -> 2;
            case "Ba" -> 3;
            case "Tư" -> 4;
            case "Năm" -> 5;
            case "Sáu" -> 6;
            case "Bảy" -> 7;
            case "CN" -> 1;
            default -> 0;
        };
    }

    private List<String> dateTimeFormat(Byte day, Byte week, Date timeStartSemeter, String startSlot, String sumSlot){

        //Setup startDate of week
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStartSemeter);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        if (day == 1) week++;

        calendar.add(Calendar.DAY_OF_WEEK, (week-1)*7);
        calendar.set(Calendar.DAY_OF_WEEK, day);

        List<Time> timeList = timeFormat(startSlot, sumSlot);

        Date sDate = calendar.getTime();
        sDate.setHours(timeList.get(0).getHours());
        sDate.setMinutes(timeList.get(0).getMinutes());


        Date eDate = calendar.getTime();
        eDate.setHours(timeList.get(1).getHours());
        eDate.setMinutes(timeList.get(1).getMinutes());

        List<String> dateList = new ArrayList<>();
        dateList.add(MyDateTime.convertDateToString(sDate, DateTimeConstant.DATE_TIME_FORMAT));
        dateList.add(MyDateTime.convertDateToString(eDate, DateTimeConstant.DATE_TIME_FORMAT));
        return dateList;
    }

    private List<Time> timeFormat(String startSlot, String sumSlot){
        List<Time> listTime = new ArrayList<>();

        new Time(0);
        Time startTime = switch (startSlot) {
            case "2" -> Time.valueOf(secondPeriod);
            case "3" -> Time.valueOf(thirdPeriod);
            case "4" -> Time.valueOf(fourthPeriod);
            case "5" -> Time.valueOf(fifthPeriod);
            case "6" -> Time.valueOf(sixthPeriod);
            case "7" -> Time.valueOf(seventhPeriod);
            case "8" -> Time.valueOf(eighthPeriod);
            case "9" -> Time.valueOf(ninthPeriod);
            case "10" -> Time.valueOf(tenthPeriod);
            case "11" -> Time.valueOf(eleventhPeriod);
            case "12" -> Time.valueOf(twelfthPeriod);
            case "13" -> Time.valueOf(thirteenthPeriod);
            default -> Time.valueOf(firstPeriod);
        };

        listTime.add(startTime);

        LocalTime localTime = startTime.toLocalTime();
        long smSlot = Long.parseLong(sumSlot);
        localTime = localTime.plusMinutes(50*smSlot + (smSlot > 3 ? (5*(smSlot-1) + 10) : (5*(smSlot-1))));

        Time endTime = Time.valueOf(localTime);
        listTime.add(endTime);

        return listTime;
    }
}


