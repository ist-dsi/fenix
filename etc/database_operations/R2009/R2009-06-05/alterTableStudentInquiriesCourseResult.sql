
alter table `STUDENT_INQUIRIES_COURSE_RESULT` add column `COURSE_RESULTS_COORDINATOR_COMMENT_DATE` timestamp NULL default NULL;
alter table `STUDENT_INQUIRIES_COURSE_RESULT` add column `KEY_COORDINATOR_COMMENT` int(11);
alter table `STUDENT_INQUIRIES_COURSE_RESULT` add column `OID_COORDINATOR_COMMENT` bigint(20);
alter table `STUDENT_INQUIRIES_COURSE_RESULT` add index (`KEY_COORDINATOR_COMMENT`), add index (`OID_COORDINATOR_COMMENT`);

