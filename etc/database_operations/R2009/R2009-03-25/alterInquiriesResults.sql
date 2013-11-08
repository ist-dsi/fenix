

alter table `STUDENT_INQUIRIES_COURSE_RESULT` add column `HEADERS` text;
alter table `STUDENT_INQUIRIES_COURSE_RESULT` add column `UPLOAD_DATE_TIME` timestamp NULL default NULL;
alter table `STUDENT_INQUIRIES_COURSE_RESULT` add column `RAW_VALUES` text;
alter table `STUDENT_INQUIRIES_TEACHING_RESULT` add column `HEADERS` text;
alter table `STUDENT_INQUIRIES_TEACHING_RESULT` add column `UPLOAD_DATE_TIME` timestamp NULL default NULL;
alter table `STUDENT_INQUIRIES_TEACHING_RESULT` add column `RAW_VALUES` text;

alter table `TEACHING_INQUIRY` add column `AUTONOMOUS_WORK` int(11);
alter table `TEACHING_INQUIRY` add column `AVAILABLE_INFRASTRUCTURE_SUITABLE_REASON` text;
alter table `TEACHING_INQUIRY` add column `AVAILABLE_INFRASTRUCTURE_SUITABLE` int(11);
alter table `TEACHING_INQUIRY` add column `CLARIFICATION_OF_DOUBTS_OUTSIDE_CLASSES` int(11);
alter table `TEACHING_INQUIRY` add column `DISTURBING_EVENTS_IN_CLASSES_REASON_INDISCIPLINE` tinyint(1);
alter table `TEACHING_INQUIRY` add column `DISTURBING_EVENTS_IN_CLASSES_REASON_INFRASTRUCTURE` tinyint(1);
alter table `TEACHING_INQUIRY` add column `DISTURBING_EVENTS_IN_CLASSES_REASON_INTERMEDIATE_EVALUATIONS` tinyint(1);
alter table `TEACHING_INQUIRY` add column `DISTURBING_EVENTS_IN_CLASSES_REASON_LOW_ASSIDUITY` tinyint(1);
alter table `TEACHING_INQUIRY` add column `GENERAL_COMMENT_TO_C_U_OPERATION` text;
alter table `TEACHING_INQUIRY` add column `INCREASE_AUTONOUMOUS_LEARNING_CAPACITY` int(11);
alter table `TEACHING_INQUIRY` add column `REPORT_DISCLOSURE_TO_ACADEMIC_COMUNITY` tinyint(1);
alter table `TEACHING_INQUIRY` add column `RESPONSIBLE_TEACHER_REPORT_DISCLOSURE_TO_ACADEMIC_COMUNITY` tinyint(1);
alter table `TEACHING_INQUIRY` add column `RESULTS_DISCLOSURE_TO_ACADEMIC_COMUNITY` tinyint(1);
alter table `TEACHING_INQUIRY` add column `SOCIAL_AND_PROFESSIONAL_CONTEXT_ANALYSIS` int(11);

alter table `TEACHING_INQUIRY` add column `SEMESTER_END_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_END_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_PROBLEMS` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_END_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_PROJECT` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_END_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_SEMINARY` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_END_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_THEORICAL` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_MIDDLE_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_MIDDLE_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_PROBLEMS` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_MIDDLE_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_PROJECT` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_MIDDLE_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_SEMINARY` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_MIDDLE_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_THEORICA` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_START_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_START_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_PROBLEMS` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_START_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_PROJECT` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_START_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_SEMINARY` varchar(255);
alter table `TEACHING_INQUIRY` add column `SEMESTER_START_ACTIVE_AND_INTERESSED_STUDENTS_RATIO_IN_THEORICAL` varchar(255);

alter table `TEACHING_INQUIRY` modify column `SEMESTER_END_AVERAGE_STUDENT_NUMBER_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_END_AVERAGE_STUDENT_NUMBER_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_END_AVERAGE_STUDENT_NUMBER_IN_PROBLEMS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_END_AVERAGE_STUDENT_NUMBER_IN_PROJECT` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_END_AVERAGE_STUDENT_NUMBER_IN_SEMINARY` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_END_AVERAGE_STUDENT_NUMBER_IN_THEORICAL` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_MIDDLE_AVERAGE_STUDENT_NUMBER_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_MIDDLE_AVERAGE_STUDENT_NUMBER_IN_PROBLEMS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_MIDDLE_AVERAGE_STUDENT_NUMBER_IN_PROJECT` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_MIDDLE_AVERAGE_STUDENT_NUMBER_IN_SEMINARY` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_MIDDLE_AVERAGE_STUDENT_NUMBER_IN_THEORICAL` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_START_AVERAGE_STUDENT_NUMBER_IN_LABS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_START_AVERAGE_STUDENT_NUMBER_IN_PROBLEMS` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_START_AVERAGE_STUDENT_NUMBER_IN_PROJECT` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_START_AVERAGE_STUDENT_NUMBER_IN_SEMINARY` varchar(255);
alter table `TEACHING_INQUIRY` modify column `SEMESTER_START_AVERAGE_STUDENT_NUMBER_IN_THEORICAL` varchar(255);